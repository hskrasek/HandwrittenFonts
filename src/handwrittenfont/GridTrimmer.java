package handwrittenfont;

import java.awt.image.BufferedImage;

public interface GridTrimmer 
{
	public void trimWhiteSpace();
	public void trimBlackBoarder();
	public void cutOutFinalGrid();
	public boolean checkIfGridIsUsable();
	public BufferedImage[][] cutGridPieces();
}
