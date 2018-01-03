package handwrittenfont;


import java.util.List;

public interface BezierCurve {
		
	//public BezierCurve getBezierCurve(Collection<ControlPoint> points);
	
	//public BezierCurve getBezierCurve(ControlPoint point1, ControlPoint point2,ControlPoint point3,ControlPoint point4);
	
	public List<ControlPoint> getControlPointsOfCurve();
	
	public int getThickness();
	
}
