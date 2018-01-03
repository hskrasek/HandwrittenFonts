package handwrittenfont;

public class ControlPointImpl implements ControlPoint{

	double xAxis;
	double yAxis;
	
	public  ControlPointImpl(double x, double y)
	{
		this.xAxis=x;
		this.yAxis=y;
	}
//	@Override
//	public void setCoordinate(double x, double y) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setCoordinate(int x, int y) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void moveControlPoint(double x, double y) {
		this.xAxis=x;
		this.yAxis=y;
		
	}

	@Override
	public ControlPoint getPointPosition() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.xAxis;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.yAxis;
	}
	
	public String toString()
	{
		return String.format("%f : %f", xAxis, yAxis);
	}

	@Override
	public void setCoordinate(double xcontrolPoint, double ycontrolPoint) 
	{
		this.xAxis = xcontrolPoint;
		this.yAxis = ycontrolPoint;
		
	}
}
