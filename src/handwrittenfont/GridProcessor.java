package handwrittenfont;

import java.awt.image.BufferedImage;
import java.io.*;


public interface GridProcessor
{
	//Pre: rowCount == 7 && columnCount == 10 (exact row and column count for the grid we are using)
	//Pre: Letter requested is on the handwritten font grid
	//Post: Returns an image file of the requested Letter (this is still subject to change...)
	public BufferedImage getLetterImage(Letter letter);
}
