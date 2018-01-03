package handwrittenfont;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class CurveGeneratorImpl implements CurveGenerator {

	GridProcessor getLetters;
	
	public CurveGeneratorImpl(GridProcessor processor)
	{
		this.getLetters= processor;
		
	}
	@Override
	public Map<Set<BezierCurve>, Integer> getCurvesOfLetter(Letter letter) {
//		File holder= new File("C:/Users/Serg/Desktop/blah2.jpeg");
//		
//		BufferedImage letterImage=null;
//		try {
//			letterImage = ImageIO.read(holder);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		BufferedImage letterImage= getLetters.getLetterImage(letter);
//		
//				try {
//			ImageIO.write(letterImage, "JPEG",holder);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		LetterProcessor curve= new LetterProcessorImpl(letterImage);
		return curve.getCurves();
	}

	public double getBaseLineOfLetter(Letter letter)
	{
		return 0;
	}
	
//	public static void main(String []args)
//	{
//		File f1= HandwrittenFont.inputImage;
////		File f1= new File("C:/Users/Serg/Documents/fonts/HandwrittenFonts/src/handwrittenfont/testGrid.jpg");
//		GridProcessor g1= new GridProcessorImpl(f1);
//		CurveGenerator c1= new CurveGeneratorImpl(g1);
////		HandwrittenFont.awesomeMap = c1.getCurvesOfLetter(Letter.NINE);
////		c1.getCurvesOfLetter(Letter.NINE);
//		Letter[] letterArray = Letter.values();
////		System.out.println(mememe);
//		
//	}
}
