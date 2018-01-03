package handwrittenfont;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.*;
import java.util.*;

public class FontBuilderImpl implements FontBuilder
{

	protected int thickness;

	public void makeFile(File theFile, Map<Letter, Map<Set<BezierCurve>, Integer>> letterAndCurveMap)
	{
		System.out.println("Now creating 'font file'");
		if(!theFile.exists())
		{
			try
			{
				theFile.createNewFile();
			} catch (IOException e)
			{
				System.out.println("Problems creating new font file!");
				e.printStackTrace();
			}
		}
		Iterator<BezierCurve> setIterator;
		PrintStream out = null;
		try
		{
			out = new PrintStream(theFile);

			Letter[] letters = Letter.values();
			for (Letter letterToWrite : letters)
			{
				System.out.println("Writting Letter: " + letterToWrite);
				out.print(letterToWrite + ":");
				out.println("");
				Map<Set<BezierCurve>, Integer> curveToWrite = letterAndCurveMap
						.get(letterToWrite);
				Iterator<Set<BezierCurve>> curveIterator = curveToWrite
						.keySet().iterator();
				while (curveIterator.hasNext())
				{
					Set<BezierCurve> tempSet = curveIterator.next();
					setIterator = tempSet.iterator();
					while (setIterator.hasNext())
					{
						BezierCurve currentCurve = setIterator.next();
						
						out.println(currentCurve.toString());
					}
				}
			}
			
			System.out.println("Font file creation complete!");
		} catch (Exception ioe)
		{
			System.out.println("Could not write to file!\n"
					+ ioe.getMessage());
			ioe.printStackTrace();
		}
		out.println("*");
		out.close();
	}
	
	
	

//	public String readFile(File theFile)
//	{
//		
//	}

	public Map<Letter, Map<Set<BezierCurve>, Integer>> readFile(File theFile)

	{
		Map<Letter, Map<Set<BezierCurve>, Integer>> letterAndCurveMap = new HashMap<Letter, Map<Set<BezierCurve>, Integer>>();
		List<ControlPoint> controlPoints = new ArrayList<ControlPoint>();
		ControlPoint controlPoint = new ControlPointImpl(0, 0);
		ControlPoint controlPoint1 = new ControlPointImpl(0, 0);
		ControlPoint controlPoint2 = new ControlPointImpl(0, 0);
		int count=0;
		int count2=0;
		Map<Set<BezierCurve>, Integer> letterMap = new HashMap<Set<BezierCurve>, Integer>();
		Set<BezierCurve> curveSet =new HashSet<BezierCurve>();
		Letter readLetter;
		//int thickness;
		try
		{

			// Open the file that is the first
			// command line parameter
//			FileInputStream fstream = new FileInputStream(
//					"E:\\General storage\\Documents\\" + theFile);

			FileInputStream fstream = new FileInputStream(theFile);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			double FirstXcontrolPoint = 0.0;
			double FirstYcontrolPoint = 0.0;
			double SecondYcontrolPoint = 0.0;
			double SecondXcontrolPoint = 0.0;
			// Read File Line By Line
		
			while ((strLine = br.readLine()) != null)
			{
				count++;
				System.out.println("1 "+count);
				 curveSet =new HashSet<BezierCurve>();
				 if(strLine.startsWith("["))
				{
					letterMap = new HashMap<Set<BezierCurve>, Integer>();
					//while(!strLine.startsWith(" ")&&strLine.startsWith("["))
					while(strLine.startsWith("["))
					{
						
						count2++;
						System.out.println("2 "+count2);
						String curveString = strLine;
						//System.out.println("Working with curve: " + curveString);
						String[] curveThickSplit = curveString.split("\\|");
						controlPoints = new ArrayList<ControlPoint>();
						
//						for(String result : curveThickSplit)
//						{
//							//System.out.println("Split value: " + result.trim());
//						}
//						//System.out.println("Points: " + curveThickSplit[0]);
				//		System.out.println("Thickness" + curveThickSplit[1]);
						this.thickness = Integer.parseInt(curveThickSplit[1].trim());
						String[] cntrlSplit = curveThickSplit[0].substring(1, curveThickSplit[0].length() - 1).trim().split("!([-+]?([0-9]*\\.[0-9]+|[0-9]+))");
					   
						
//						String[] cntrlSplit = curveThickSplit[0].substring(1, curveThickSplit[0].length() - 1).trim().split("[X:|Y:] | [\\]| \\[]");
						for(String cp: cntrlSplit)
						{
							
							String[] Points = cp.split(":|,|]");
							
							
							
							
							int numPoints = Points.length;
							//System.out.println("number of points: " + numPoints);
							
								String FirstxCord = Points[0];
								String FirstyCord = Points[1];
								String SecondxCord = Points[2];
								String SecondyCord = Points[3];
//								System.out.println("First X Value: " + FirstxCord);
//								System.out.println("First Y Value: " + FirstyCord);
//								System.out.println("Second X Value: " + SecondxCord);
//								System.out.println("Second Y Value: " + SecondyCord);
//								
								

//								first control point
								  Double xVal = Double.valueOf(FirstxCord);
								  FirstXcontrolPoint = xVal;
								//  System.out.println("X1: " + xVal);
								  
								  Double yVal = Double.valueOf(FirstyCord);
								  FirstYcontrolPoint = yVal;
							//	  System.out.println("Y1: " + yVal);
						
							controlPoint1.setCoordinate(FirstXcontrolPoint, FirstYcontrolPoint);
						//	System.out.println("Here is First Control Point: " + controlPoint1);
							controlPoints.add(controlPoint1);
							
							
							
//							second control point
							  	Double xVal2 = Double.valueOf(SecondxCord);
							  	SecondXcontrolPoint = xVal2;
							//  	System.out.println("X2: " + xVal2);
							  
							  	Double yVal2 = Double.valueOf(SecondyCord);
							  	SecondYcontrolPoint = yVal2;
							  //	System.out.println("Y2: " + yVal2);
							  	
						controlPoint1 = new ControlPointImpl(0.0, 0.0);
						controlPoint2 = new ControlPointImpl(0.0, 0.0);
						//controlPoint = new ControlPointImpl(xVal, yVal);
						controlPoint2.setCoordinate(SecondXcontrolPoint, SecondYcontrolPoint);
					//	System.out.println("Here is Second Control Point: " + controlPoint2);
						controlPoints.add(controlPoint2);
						
						
						BezierCurve letterCurve= new BezierCurveImpl(controlPoints, this.thickness);
						curveSet.add(letterCurve);
						
							
						//System.out.println("Here is a curve consiting of Control Points: " + controlPoints);
						
							
						}
						
						strLine = br.readLine();
					}
					letterMap.put(curveSet, this.thickness);
					if(strLine.startsWith("*"))break;
					
					
				}
				
			
				if(strLine.length()!=0)
				{
				readLetter = Letter.valueOf(strLine.substring(0, strLine.length()-1));
			//	System.out.println("Letter " + readLetter);
				//letterAndCurveMap.put(readLetter, letterMap);
				}
				else break;
				letterAndCurveMap.put(readLetter, letterMap);
				
				
			}
			// Close the input stream
			in.close();
		} catch (IOException e)
		{// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		
		//letterAndCurveMap.put
		

		return letterAndCurveMap;
	}
	
	public static void main(String args[])
	{
		FontBuilderImpl test = new FontBuilderImpl();
		File theFile = new File("C:/Users/test/workspace/HandwrittenFonts/src/handwrittenfont/test3.txt");
		Map<Letter, Map<Set<BezierCurve>, Integer>> letterAndCurveMap = new HashMap<Letter, Map<Set<BezierCurve>, Integer>>();
		letterAndCurveMap = test.readFile(theFile);
		File newFile = new File("C:/Users/sazua/Desktop/outputafter.txt");
		test.makeFile(newFile, letterAndCurveMap);
	}
}
