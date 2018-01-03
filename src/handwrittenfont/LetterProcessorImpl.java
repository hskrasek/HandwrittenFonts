package handwrittenfont;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class LetterProcessorImpl  implements LetterProcessor{

	protected int heightOfLetter;
	protected static int height;
	protected static int width;
	protected int[] rgbArray;
	protected int count2;
	protected int thickness;
	protected Map<Set<BezierCurve>, Integer> letterMap;
	protected Set<BezierCurve> curveSet;
	protected int lastPoint2;
	protected double targetAccuracy=1;
	protected int minimumCurveSize=3;
	protected BezierCurve foundCurve;
	protected int highblack= ( 0x000000-0xFFFFFF-2)+0x090000;
	protected int count3=-1;
	public LetterProcessorImpl(BufferedImage letterImage)
	{
		width=letterImage.getWidth();
		height=letterImage.getHeight();
		this.rgbArray= new int [width*height];
		this.rgbArray=letterImage.getRGB(0, 0, width, height, rgbArray, 0, width);
		this.rgbArray=getPerfectFrame();
		this.heightOfLetter= Math.abs(getTopOfLetter()-getBottomOfLetter());
		this.letterMap=new HashMap<Set<BezierCurve>, Integer>();
		this.curveSet=new HashSet<BezierCurve>();
		this.foundCurve=null;
		
	}
// ideas to consider
//	write a different top point alogrithm to fit the needs of the current 
//	getsection
//	or change get section to be better suited
	//problem is to handle thickness
	//develope a getfirst pixel that finds the left most pixel!
	@Override
	public Map<Set<BezierCurve>,Integer> getCurves()  {
//		count3=count3*-1;
//		int topPoint=0;
//		if(count3==-1)
//		{
//		topPoint=getTopOfLetter();
//		}
//		else
//		{
//			topPoint=getNextPixel();
//		}
//		
		int topPoint=0;
		count3++;
		if(count3==2)
		{
			count3=0;
		}
		if(count3==0)
		{
			topPoint=getTopOfLetter();
		}else
		{
			if(count3==1)
			{
				topPoint= getNextPixel();
			}
			else
			{
				if(count3==2)
				{
					topPoint=getNextPixelRight();
					
				}
				else
				{
					if(count3==3)
					{
						topPoint=getBottomOfLetter();
					}
				}
			}
		}
		//System.out.println(topPoint);
		if(topPoint!=-1)
		//if(count2<60)
		{
			Set<Integer> pointSet= new HashSet<Integer>();
					pointSet= getSectionToCheck2(topPoint);
			Iterator<Integer> setIter= pointSet.iterator();
			
				int count=0;
			while(setIter.hasNext())
			{
				count=setIter.next();
					rgbArray[count]=0x0FF000;
				
				
			}
			if(pointSet.size()<minimumCurveSize)
			{
				
				
			}
			else
			{
				if(foundCurve==null)
				{
			int firstPoint=topPoint;
//			rgbArray[firstPoint]=0x000FFF;
			int firstPointX=firstPoint%width;
			int firstPointY=(firstPoint-firstPointX)/width;
//			rgbArray[lastPoint2]=0x000FFF;
			int lastPointX=lastPoint2%width;
			int lastPointY=(lastPoint2-lastPointX)/width;
			ControlPoint first= new ControlPointImpl(firstPointX,firstPointY);
			ControlPoint last= new ControlPointImpl(lastPointX,lastPointY);
			List<ControlPoint> temp= new ArrayList<ControlPoint>();
			temp.add(first);
			temp.add(last);
			BezierCurve letterCurve= new BezierCurveImpl(temp, thickness);
				curveSet.add(letterCurve);
				}
				else
				{
					curveSet.add(foundCurve);
					foundCurve=null;
				}
		
			}
			count2++;
			getCurves();
			
		}
		else
		{
			letterMap.put(curveSet, thickness);
			System.gc();
		//exportArrayAsImage(rgbArray,"C:/Users/sazua/Desktop/blah.jpeg");
		}
		
		
		
		return letterMap;
	}

	public double getBaseLine(Letter letter)
	{

		if(letter==Letter.g||letter==Letter.j||letter==Letter.p||letter==Letter.q||letter==Letter.y)
			return heightOfLetter*0.66;
		if(letter==Letter.Q)
			return heightOfLetter*0.80;
		return heightOfLetter;
	}



	private int getTopOfLetter()
	{

		
		for(int i= 0; i<rgbArray.length;i++)
		{
			if(rgbArray[i]<=highblack)
			{

				return i;
			}
		}
		return -1;
	}

	private int getBottomOfLetter()
	{

		
		for(int i= rgbArray.length-1; i>-1;i--)
		{
			if(rgbArray[i]<=highblack)
			{

				return i;
			}
		}
		return -1;
	}
	
	private int getNextPixel()
	{
		int count=0;
		for(int x=0;x<width;x++)
		{
			count=x;
			for(int y=0; y<height; y++)
			{

				if(rgbArray[count]<=highblack)
				{

					return count;
				}	
				count=count+width;
			}
			
		}
		return -1;
	}
	
	private int getNextPixelRight()
	{
		int count=0;
		for(int x=width-1;x>-1;x--)
		{
			count=x;
			for(int y=0; y<height; y++)
			{
				
				if(rgbArray[count]<=highblack)
				{
					//System.out.println(count);
					return count;
				}	
				count=count+width;
			}
			
		}
		return -1;
	}
	
	private int [] getPerfectFrame()
	{

		int white= 0x00FFFF;
		for(int i= (rgbArray.length-width*12)+1;i<rgbArray.length;i++)
		{
			rgbArray[i]=white;
		}
		for(int i= 0; i<width*9;i++)
		{
			//if(rgbArray[i]>=lowblack&&rgbArray[i]<=highblack)
			{
				rgbArray[i]=white;
				//break;
			}
		}
		int mark=0;
		int layer=0;
		double preciseHeight=height;
		double preciseWidth=width;
		for(int i=0; i<rgbArray.length;i++)
		{
			if(layer<preciseHeight/4)
			{
				for(int j=0; j<preciseWidth/3.9;j++)
				{
					rgbArray[mark]=white;
					mark++;
				}
			}
			else
			{

				for(int j=0; j<preciseWidth/50;j++)
				{
					rgbArray[mark]=white;
					mark++;
				}}

			
			i=i+width-1;
			mark=i;
			layer++;
		}
		int count=0;
		for(int i= width-15; i<width;i++)
		{
			count=i;
			for(int j =0; j<height;j++)
			{
				rgbArray[count]=white;
				//break;
				count=count+width;
			}
			
		}
		return rgbArray;
	}

	private void exportArrayAsImage(int [] pointArray, String file)
	{
		BufferedImage temp=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int count=0;
		for(int y=0;y<height;y++)
		{
			for(int x=0; x<width; x++)
			{
				int rgb=pointArray[count];
				temp.setRGB(x, y, rgb);
			count++;	
			}

		}
		File holder= new File(file);
		try {
			ImageIO.write(temp, "JPEG",holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Set<Integer> getSectionToCheck2(int firstPoint)
	{
		Set<Integer> pointSet = new HashSet<Integer>();
		//
		int count=-1;
		
		int firstHolder= firstPoint;
		int previous=firstPoint;
		pointSet.add(firstPoint);
		if(count3==2)
		{
			firstHolder++;
		}
		else
		{
		firstHolder=firstHolder-width;// jump above firstPoint
		firstHolder--;
		}
		int previous2=0;
		//this is where canFindCurve(Set pointSet) goes
		
		while(true)
		{
//			count++;
//			if(count==2)
//			{
			//System.out.println("P "+previous+" P2 "+previous2);
			if(previous2==previous)
			{
			count++;
			}else
			{
				count=0;
			}
			if(count==4)
			{
				break;
//				thickness=1;
//				return pointSet;
			}
			if(canFindCurve(firstPoint, previous,pointSet)==false)
			{
				
				pointSet.remove(previous);
				break;
			}
//			else
//			{
//				count=0;
//			}
//			}
			if(rgbArray[firstHolder+width]<highblack&&rgbArray[firstHolder+1]<highblack)
			{
				previous2=previous;
				previous=firstHolder+1;
				firstHolder--;
				pointSet.add(previous);
				if(rgbArray[previous+width-1]<highblack)
				{
					previous2=previous;
					previous=previous+width-1;
					pointSet.add(previous);
					
				}
				
				continue;
			}

			if(rgbArray[firstHolder-width]<highblack&&rgbArray[firstHolder+1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-width;
				firstHolder=firstHolder+width;
				pointSet.add(previous);
				if(rgbArray[previous+width+1]<highblack)
				{
					previous2=previous;
					previous=previous+width+1;
					pointSet.add(previous);
					
				}
				
				continue;
			}
			if(rgbArray[firstHolder+width]<highblack&&rgbArray[firstHolder-1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-1;
				firstHolder=firstHolder-width;
				pointSet.add(previous);
				if(rgbArray[previous+width+1]<highblack)
				{
					previous2=previous;
					previous=previous+width+1;
					pointSet.add(previous);
					
				}
				
				continue;
			}
			if(rgbArray[firstHolder-width]<highblack&&rgbArray[firstHolder-1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-1;
				firstHolder++;
				pointSet.add(previous);
				if(rgbArray[previous-width+1]<highblack)
				{
					previous2=previous;
					previous=previous-width+1;
					pointSet.add(previous);
					
				}
				
				continue;
			}

			if(rgbArray[firstHolder-width]<highblack&&rgbArray[firstHolder+width+1]<highblack)
			{
				firstHolder=firstHolder+width;
				continue;
			}
			if(rgbArray[firstHolder+width]<highblack)//trying to move right below
			{
				previous2=previous;
				previous=firstHolder+width;
				firstHolder--;
				pointSet.add(previous);
				
				continue;
			}
			if(rgbArray[firstHolder+1]<highblack)
			{
				previous2=previous;
				previous=firstHolder+1;
				firstHolder=firstHolder+width ;
				pointSet.add(previous);
				
				continue;
			}
			if(rgbArray[firstHolder-width]<highblack)
			{
				previous2=previous;
				previous=firstHolder-width;
				firstHolder++;
				pointSet.add(previous);
				
				continue;
			}
			if(rgbArray[firstHolder-1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-1;
				firstHolder=firstHolder-width;
				pointSet.add(previous);
				
				continue;
			}
			if(rgbArray[firstHolder+(width)+1]<highblack)
			{
				previous2=previous;
				previous=firstHolder+(width)+1;
				firstHolder=firstHolder+(width);
				pointSet.add(previous);
				
				continue;
			}

			if(rgbArray[firstHolder-width+1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-width+1;
				firstHolder++;
				pointSet.add(previous);
				
				continue;
			}

			if(rgbArray[firstHolder-width-1]<highblack)
			{
				previous2=previous;
				previous=firstHolder-width-1;
				firstHolder=firstHolder-width;
				pointSet.add(previous);
				
				continue;
			}


			if(rgbArray[firstHolder+width-1]<highblack)
			{
				previous2=previous;
				previous=firstHolder+width-1;
				firstHolder--;
				pointSet.add(previous);
				
				continue;
			}


			
		}
		
		
				Object [] temp= pointSet.toArray();
				previous= (Integer) temp[temp.length-1];
		Iterator<Integer> iter= pointSet.iterator();
		int [] points= new int[pointSet.size()];
		count=0;
		while(iter.hasNext())
		{
			points[count]= iter.next();
			count++;
		} 
	lastPoint2=previous2;
	
		
		count=0;
		int cycle=1;
		thickness=cycle;
		while(true)
		{
			if(rgbArray[(points[count]+cycle)]<highblack)
			{
				if(count==points.length-1)
				{
					for(int i=0; i<points.length;i++)
					{
						pointSet.add(points[i]+cycle);
						thickness=cycle;
					}
					
					cycle++;
					count=0;
					continue;
				}
				count++;
			}else
			{
				break;
			}
		}
		count=0;
		if(thickness==1)
		{
			while(true)
			{
				if(rgbArray[(points[count]-cycle)]<highblack)
				{
					if(count==points.length-1)
					{
						for(int i=0; i<points.length;i++)
						{
							pointSet.add(points[i]-cycle);
							thickness=cycle*-1;
						}
						
						cycle++;
						count=0;
						continue;
					}
					count++;
				}else
				{
					break;
				}
			}
		}
//			if(thickness==1&&pointSet.size()>=minimumCurveSize)
//			{
//				//pointSet.remove(firstPoint);
//				pointSet.remove(previous2);
//				count3++;
//		
//				//pointSet=getSectionToCheck2(previous2);
//			}else count3=0;
//		//System.out.println(thickness);	
		
		

				//		Object [] temp= pointSet.toArray();
				//		previous= (Integer) temp[temp.length-1];

		return pointSet;
	}

	
	private boolean canFindCurve(int firstPoint, int lastPoint,Set<Integer> pointSet)
	{
//		if(pointSet.size()<=3)
//		{
//			return true;
//		}
		
		int firstPointX=firstPoint%width;
		int firstPointY=(firstPoint-firstPointX)/width;
		int lastPointX=lastPoint%width;
		int lastPointY=(lastPoint-lastPointX)/width;
		Set<Integer> checkPoints= drawLineInArray(firstPointX,firstPointY,lastPointX,lastPointY);
	
		Iterator<Integer> i1=checkPoints.iterator();
		double correctPoints=0;
		double actualPoints=pointSet.size();
		int next=0;
		while(i1.hasNext())
		{
		next=i1.next();
			if(pointSet.contains(next))//||pointSet.contains(next-1)||pointSet.contains(next+1))
					{
				correctPoints++;
					}
			
		}

		double percentAccuracy=correctPoints/actualPoints;
		//System.out.println(percentAccuracy);
		//exportArrayAsImage(pointArray,"C:/Users/Serg/Desktop/blah3.jpeg");
		if(percentAccuracy>=targetAccuracy)
			{return true;}
		else
		{
//			//attempt the same start end but now call method to include point 2 aand 3
//			foundCurve=drawCurveInArray(firstPointX,firstPointY,lastPointX,lastPointY);
//			
//			if(foundCurve!=null)
//			{
//				//check upstairs for nullness decides if curve is two points or three
//				return true;
//			}
		}
			return false;
	}

	private Set<Integer> drawLineInArray(int firstX, int firstY, int lastX, int lastY)
	{
		BufferedImage temp=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2= temp.createGraphics();
		g2.setColor(Color.white);
		g2.drawLine(firstX, firstY, lastX, lastY);
		g2.dispose();
		
		Set<Integer> points= new HashSet<Integer>();
//		File holder= new File("C:/Users/Serg/Desktop/blah3.jpeg");
//		try {
//			ImageIO.write(temp, "JPEG",holder);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//when the drawing of 4 point curves is implemented 
		//calculation of height of curve from straight line is required 
		//with that the Y coordinate is set to optimize then the x coordinates
		//are placed to match the curve. 
		int start=firstY;
		int end= lastY;
		if(firstY>lastY)
		{
			 start=lastY;
			 end= firstY;
		}
		for(int y=start;y<end+1;y++)
		{
			for(int x=0; x<width; x++)
			{
				if(temp.getRGB(x, y)>highblack)
				{
					points.add((width*y)+x);
				}
				
			}

		}
		return points;
	}
	
	private BezierCurve drawCurveInArray(int firstX, int firstY, int lastX, int lastY)
	{
		double t;
		double k = .025;
		double x1,x2,y1,y2;
		BufferedImage temp=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2= temp.createGraphics();
		g2.setColor(Color.white);
		double firstAnchorX = 0, secondAnchorX = 0, firstAnchorY = 0, secondAnchorY = 0;
		x1 = firstX;
		y1 = firstY;
		
		for(t = k; t <= 1; t+= k)
		{
			x2 = (firstX + t * (-firstX * 3 + t * (3 * firstX - firstX * t)))
					+ t * (3 * firstAnchorX + t * (-6 * firstAnchorX + firstAnchorX * 3 * t))
					+ t * t * (secondAnchorX * 3 - secondAnchorX * 3 * t)
					+ lastX * t * t * t;
			
			y2  = (firstY + t * (-firstY * 3 + t * (3 * firstY - firstY * t)))
					+ t * (3 * firstAnchorY + t * (-6 * firstAnchorY + firstAnchorY * 3 * t))
					+ t * t * (secondAnchorY * 3 - secondAnchorY * 3 * t)
					+ lastY * t * t * t;
			
			g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
			x1 = x2;
			y1 = y2;
		}
		
		return foundCurve;
	}
}
