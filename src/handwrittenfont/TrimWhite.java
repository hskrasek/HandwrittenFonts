package handwrittenfont;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrimWhite implements GridTrimmer{
	
	private BufferedImage unchangedImg;
    private BufferedImage thresholdedImg;
    private final int ROWS = 7;
	private final int COLS = 10;

	// Constructor
    public TrimWhite(File input) {
        try {
            this.unchangedImg = ImageIO.read(input);
            BufferedImage b = deepCopy(this.unchangedImg);
            
            // Threshold the image so that we can find the boarder
            Thresholder t = new Thresholder();
            this.thresholdedImg = t.thresholdImage(b, 60, 60, 60);
//            try {
//    			ImageIO.write(this.thresholdedImg, "PNG", new File("/Users/Rob/Desktop/thresholdedImage.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//            
//            try {
//    			ImageIO.write(this.unchangedImg, "PNG", new File("/Users/Rob/Desktop/unchangedImage.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
            
        } catch (IOException e) {
			e.printStackTrace();
        }
    }
    
    private BufferedImage deepCopy(BufferedImage bi)
	{
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
    
    // *******************************************
    
    public void trimWhiteSpace()
    {
    	// Trim White Space around black grid boarder
    	System.out.print("Trimming White Space...");
        Point rightMidPoint  = getRightPointForRemovingWhite();
        System.out.print("...");
        Point leftMidPoint = getLeftPointForRemovingWhite();
        System.out.print("...");
        Point topMidPoint = getTopPointForRemovingWhite();
        System.out.print("...");
        Point bottomMidPoint = getBottomPointForRemovingWhite();
        System.out.print("...\n");
        
        // We have wiggle room to squeeze in on the image since the boarder is so thick
        int width = (rightMidPoint.x - 20) - (leftMidPoint.x + 20);
        int height = (bottomMidPoint.y - 20) - (topMidPoint.y + 20);
        Point topLeftPoint = new Point(leftMidPoint.x + 20, topMidPoint.y + 20);
        
        if(height != 0 && width != 0)
        {
        	System.out.println("("+ topLeftPoint.x + ", " + topLeftPoint.y + ")");
        	this.unchangedImg = this.unchangedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);
        	this.thresholdedImg = this.thresholdedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);

//        	try {
//    			ImageIO.write(this.unchangedImg, "PNG", new File("/Users/Rob/Desktop/whiteBoarderRemoved.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//        	
//        	try {
//    			ImageIO.write(this.thresholdedImg, "PNG", new File("/Users/Rob/Desktop/thresholdImageWhiteRemoved.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
        	System.out.print("Done!\n");
        }
        else
        {
        	System.out.print("None\n");
        }
    }
    
    public void trimBlackBoarder()
    {
    	// Trim White Space around black grid boarder
    	System.out.print("Trimming Black Boarder...");
        Point rightMidPoint  = getRightPointForRemovingBlackBoarder();
        System.out.print("...");
        Point leftMidPoint = getLeftPointForRemovingBlackBoarder();
        System.out.print("...");
        Point topMidPoint = getTopPointForRemovingBlackBoarder();
        System.out.print("...");
        Point bottomMidPoint = getBottomPointForRemovingBlackBoarder();
        System.out.print("...\n");
        int width = (rightMidPoint.x - 20) - (leftMidPoint.x + 20);
        int height = (bottomMidPoint.y - 20) - (topMidPoint.y + 20);
        
        Point topLeftPoint = new Point(leftMidPoint.x + 20,topMidPoint.y + 20);
        if(height != 0 && width != 0)
        {
        	System.out.println("("+ topLeftPoint.x + ", " + topLeftPoint.y + ")");
        	this.unchangedImg = this.unchangedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);
        	this.thresholdedImg = this.thresholdedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);
//        	try {
//    			ImageIO.write(this.unchangedImg, "PNG", new File("/Users/Rob/Desktop/blackBoarderRemoved.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//        	
//        	try {
//    			ImageIO.write(this.thresholdedImg, "PNG", new File("/Users/Rob/Desktop/blackBoarderRemovedThreshold.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
        	System.out.print("Done!\n");
        }
        else
        {
        	System.out.print("None\n");
        }
        
    }
    
    public void cutOutFinalGrid() 
    {
    	Thresholder t = new Thresholder();
        this.thresholdedImg = t.thresholdImage(this.unchangedImg, 170, 170, 170);
//        try {
//			ImageIO.write(this.thresholdedImg, "PNG", new File("/Users/Rob/Desktop/lowerThreshold.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        
        Point topPoint = getTopPointForGettingGrid();
        System.out.println(topPoint.x + ", " + topPoint.y);
        
        Point bottomPoint = getBottomPointForGettingGrid();
        System.out.println(bottomPoint.x + ", " + bottomPoint.y);
        
        Point leftPoint = getLeftPointForGettingGrid();
        System.out.println(leftPoint.x + ", " + leftPoint.y);
        
        Point topLeftPoint = new Point(leftPoint.x, topPoint.y);
        int height = bottomPoint.y - topPoint.y;
        int width = (this.thresholdedImg.getWidth() - leftPoint.x) - leftPoint.x;
        if(height != 0 && width != 0)
        {
        	
        	this.unchangedImg = this.unchangedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);
        	this.thresholdedImg = this.thresholdedImg.getSubimage(topLeftPoint.x, topLeftPoint.y, width, height);
//        	try {
//    			ImageIO.write(this.unchangedImg, "PNG", new File("/Users/Rob/Desktop/regularGridCut.png"));
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
        }
        
//        for(int i = 0; i < this.thresholdedImg.getWidth() - 1; i ++)
//        {
//            this.thresholdedImg.setRGB(i, topPoint.y, Color.RED.getRGB());
//        }
//        for(int i = 0; i < this.thresholdedImg.getWidth() - 1; i ++)
//        {
//        	this.thresholdedImg.setRGB(i, bottomPoint.y, Color.RED.getRGB());
//        }
//        for(int i = 0; i < this.thresholdedImg.getHeight() - 1; i ++)
//        {
//        	this.thresholdedImg.setRGB(leftPoint.x, i, Color.RED.getRGB());
//        }
//        for(int i = 0; i < this.thresholdedImg.getHeight() - 1; i ++)
//        {
//        	this.thresholdedImg.setRGB(this.thresholdedImg.getWidth() - leftPoint.x, i, Color.RED.getRGB());
//        }
    }
    
    // ********************************************
    
    private Point getTopPointForRemovingWhite()
    {
    	int x = this.thresholdedImg.getWidth()/2;
        Point point = new Point();
        
        for(int y = 0; y < this.thresholdedImg.getHeight() - 1 ; y++) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    private Point getBottomPointForRemovingWhite()
    {
    	int x = this.thresholdedImg.getWidth()/2;
        Point point = new Point();
        
        for(int y = this.thresholdedImg.getHeight() - 1; y > 0; y--) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    private Point getLeftPointForRemovingWhite()
    {
        int y = this.thresholdedImg.getHeight()/2;
        Point point = new Point();
        
        for(int x = 0; x < this.thresholdedImg.getWidth() - 1 ; x++) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }

    private Point getRightPointForRemovingWhite()
    {
    	// This will be the mid-point vertically
        int y = this.thresholdedImg.getHeight()/2;
        Point point = new Point();

        // Move from the right of the image inward to the center staying at the same y value
        for(int x = this.thresholdedImg.getWidth() - 1; x > 0; x--) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    // *********************************************
    
    private Point getTopPointForRemovingBlackBoarder()
    {
    	int x = this.thresholdedImg.getWidth()/2;
        Point point = new Point();
        
        for(int y = 0; y < this.thresholdedImg.getHeight() - 1 ; y++) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB() && y > 100) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    private Point getBottomPointForRemovingBlackBoarder()
    {
    	int x = this.thresholdedImg.getWidth()/2;
        Point point = new Point();
        
        for(int y = this.thresholdedImg.getHeight() - 1; y > 0; y--) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.WHITE.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    private Point getLeftPointForRemovingBlackBoarder()
    {
        int y = this.thresholdedImg.getHeight()/2;
        Point point = new Point();
        
        for(int x = 0; x < this.thresholdedImg.getWidth() - 1 ; x++) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.WHITE.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }

    private Point getRightPointForRemovingBlackBoarder()
    {
    	// This will be the mid-point vertically
        int y = this.thresholdedImg.getHeight()/2;
        Point point = new Point();

        // Move from the right of the image inward to the center staying at the same y value
        for(int x = this.thresholdedImg.getWidth() - 1; x > 0; x--) 
        {
        	if(thresholdedImg.getRGB(x, y) == Color.WHITE.getRGB()) 
        	{
        		point.setLocation(x, y);
        		return point;
        	}
        }
        return point;
    }
    
    // ***********************************************
    
    private Point getTopPointForGettingGrid()
    {
        Point point = new Point();
        
        for(int y = 0; y < this.thresholdedImg.getHeight() - 1 ; y++) 
        {
            for(int x = 0; x < this.thresholdedImg.getWidth() - 1; x ++)
            {
            	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB() && y > 20) 
            	{
            		point.setLocation(x, y);
            		return point;
            	}
            }
        }
        return point;
    }
    
    private Point getBottomPointForGettingGrid()
    {
    	 Point point = new Point();
         for(int y = this.thresholdedImg.getHeight() - 1; y > 0 ; y--) 
         {
             for(int x = 0; x < this.thresholdedImg.getWidth() - 1; x ++)
             {
             	if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB() && y < this.thresholdedImg.getHeight() - 20) 
             	{
             		point.setLocation(x, y);
             		return point;
             	}
             }
         }
         return point;
    }
    
    private Point getLeftPointForGettingGrid()
    {
        Point point = new Point();
        for(int x = 0; x < this.thresholdedImg.getWidth() - 1 ; x++) 
        {
        	for(int y = 0; y < this.thresholdedImg.getHeight() - 1; y++)
        	{
        		if(thresholdedImg.getRGB(x, y) == Color.BLACK.getRGB() && x > 20 && y > 20) 
            	{
            		point.setLocation(x, y);
            		return point;
            	}
        	}
        }
        return point;
    }
    
    // http://kalanir.blogspot.com/2010/02/how-to-split-image-into-chunks-java.html
    public BufferedImage[][] cutGridPieces()
    {
    	System.out.print("Cutting Grid Pieces...");
    	Thresholder t = new Thresholder();
    	this.unchangedImg = t.thresholdImage(this.unchangedImg, 120, 120, 120);
    	
    	int count = 0;
        int chunkWidth = this.unchangedImg.getWidth() / COLS;
        int chunkHeight = this.unchangedImg.getHeight() / ROWS;
        
        System.out.println("Chunk Width: " + chunkWidth);
        System.out.println("Chunk Height: " + chunkHeight);
        BufferedImage imgs[][] = new BufferedImage[ROWS][COLS]; // 2D BufferedImage array to hold image chunks  
        for (int x = 0; x < ROWS; x++) {
        	System.out.print(".");
            for (int y = 0; y < COLS; y++) {
                //Initialize the image array with image chunks 
                imgs[x][y] = new BufferedImage(chunkWidth, chunkHeight, this.unchangedImg.getType());
                // draws each image chunk
                Graphics2D gr = imgs[x][y].createGraphics();
                gr.drawImage(this.unchangedImg, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
                gr.dispose();
                
                BufferedImage i = imgs[x][y];
//                try {
//        			ImageIO.write(i, "PNG", new File("/Users/Rob/Desktop/blockFolder/block" + count + ".png"));
//        		} catch (IOException e) {
//        			e.printStackTrace();
//        		}
                count++;
            }
        }  
        System.out.print("Done!\n");
        return imgs;
    }
    
    // Checks to make sure that we have at least 1 pixel width of white around the black boarder
    public boolean checkIfGridIsUsable()
	{
		// If we don't at least have 1 pixel width worth of white around boarder on the left
		// we don't accept the grid image
		for(int x = 0; x < 1; x++)
		{
			for(int y = 0; y < this.thresholdedImg.getHeight() - 1; y++)
			{
				int c = this.thresholdedImg.getRGB(x, y);
				if(c == Color.BLACK.getRGB())
				{
					return false; // There was no white outer edge on the left
				}
			}	
		}
		
		// If we don't at least have 1 pixel width worth of white around boarder on the right
		// we don't accept the grid image
		for(int x = this.thresholdedImg.getWidth() - 1; x > this.thresholdedImg.getWidth() - 3; x--)
		{
			for(int y = this.thresholdedImg.getHeight() - 1; y > 0; y--)
			{
				int c = this.thresholdedImg.getRGB(x, y);
				if(c == Color.BLACK.getRGB())
				{
					return false; // There was no white outer edge on the left
				}
			}
		}
		return true;
	}
  
}
