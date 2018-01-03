package handwrittenfont;




public interface ControlPoint 
{

//	public void setCoordinate(double x, double y);
//
//	public void setCoordinate(int x, int y);

	public void moveControlPoint(double x, double y);

	public ControlPoint getPointPosition();

	public double getX();

	public double getY();
	//equals();?
	//hashCode();?

	public void setCoordinate(double xcontrolPoint, double ycontrolPoint);
	

}
