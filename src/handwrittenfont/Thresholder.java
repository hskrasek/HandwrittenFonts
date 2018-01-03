package handwrittenfont;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Thresholder 
{	
	public BufferedImage thresholdImage(BufferedImage grid, int redThresh, int greenThresh, int blueThresh)
	{
		BufferedImage gridPicture = new BufferedImage(grid.getWidth(), grid.getHeight(), grid.getType());
        Graphics2D tg = (Graphics2D) gridPicture.getGraphics();
        tg.setComposite(AlphaComposite.Src);
        tg.drawImage(grid,0,0,grid.getWidth(),grid.getHeight(),0,0,grid.getWidth(),grid.getHeight(),null);
        tg.dispose();
        
		int WIDTH = gridPicture.getWidth();
		int HEIGHT = gridPicture.getHeight();
		
		Raster gridRaster = gridPicture.getRaster();
		
		System.out.println("Thresholding Image...");
		
		for(int x = 0; x < WIDTH - 1; x++){
			for(int y = 0; y < HEIGHT - 1; y++){
				
				int red = gridRaster.getSample(x, y, 0);
				int green = gridRaster.getSample(x, y, 1);
				int blue = gridRaster.getSample(x, y, 2);
//				int pixel = gridPicture.getRGB(x, y);
//				int alpha = (pixel >> 24) & 0x000000FF;
				
				if(red < redThresh && green < greenThresh && blue < blueThresh)
				{
					gridPicture.setRGB(x, y, Color.BLACK.getRGB());
				}
				else
				{
					gridPicture.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		return gridPicture;	
	}
}
