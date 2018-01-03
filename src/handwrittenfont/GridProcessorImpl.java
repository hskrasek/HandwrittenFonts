package handwrittenfont;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GridProcessorImpl implements GridProcessor
{	
	private File gridImage;
	private Letter[][] gridArray;
	private BufferedImage[][] chunkArray;
	private final int ROWS = 7;
	private final int COLS = 10;
	
	// *************************** Interesting Information ****************************
	// *** http://stackoverflow.com/questions/454498/find-an-image-within-an-image  ***
	// *** http://homepages.inf.ed.ac.uk/rbf/HIPR2/hough.htm						***
	// ********************************************************************************
	// http://stackoverflow.com/questions/876142/finding-cropped-similar-images
	
	// CONSTRUCTOR
	// Perfecting the image will be finished for a later milestone
	// As of milestone 1, we are working with a "Perfect" grid image
	public GridProcessorImpl(File gridImage)
	{
		int count = 0;
		this.gridArray = new Letter[ROWS][COLS];
		this.gridImage = adjustAndPerfectGridImage(gridImage);
		GridTrimmer trimmer = new TrimWhite(this.gridImage);
		boolean isUsable = trimmer.checkIfGridIsUsable();
		if(isUsable)
		{
			trimmer.trimWhiteSpace();
			trimmer.trimBlackBoarder();
			trimmer.cutOutFinalGrid();
			this.chunkArray = trimmer.cutGridPieces();
			Letter[] letterArray = Letter.values();
			
			// Construct our grid with Letters
			for(int x = 0; x < this.ROWS; x++)
			{
				for(int y = 0; y < this.COLS; y++)
				{
					this.gridArray[x][y] = letterArray[count];
					count++;
				}
			}
		}
		else
		{
			this.gridImage = null;
		}
	}
	
	// We have getter just in case someone needs the image
	// Setter isn't needed. We'll just let them create a new ImageProcessorImpl with a new image
	// If the return value is null, the image was not usable
	public File getGridImage()
	{
		return this.gridImage;
	}
	
	public BufferedImage getLetterImage(Letter letter)
	{ 
		// Search for the Letter passed inside the 2D gridArray that is filled with Letters
		int row = 0;
		int column = 0;
		for(int i = 0; i < this.ROWS; i++)
		{
			for(int j = 0; j < this.COLS; j++)
			{
				if(letter == this.gridArray[i][j] )
				{
					row = i;
					column = j;
				}
			}
		}		
		BufferedImage letterImage = this.chunkArray[row][column];
		return letterImage;
	}
	
	// ********************************************************************
	// NOTE: This is only for a "Perfect Image" currently (First Milestone)
	// ********************************************************************
	// Creates a "Trimmer" to trim the grid and cut out the images
	// Searches for the letter image and returns it in BufferedImage style
	
	private File adjustAndPerfectGridImage(File gridImage)
	{
		return gridImage;
	}
	
	public static void main(String[] args)
	{	
		GridProcessor processor = new GridProcessorImpl(new File("/Users/Rob/Dropbox/Programs/cosc3339/HandwrittenFonts/src/handwrittenfont/photo1.jpeg"));
//		BufferedImage best = comparer.compareImageToBoarder();
	}
}
