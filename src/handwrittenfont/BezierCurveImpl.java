package handwrittenfont;

import java.util.List;

public class BezierCurveImpl implements BezierCurve {

	List<ControlPoint> curvePoints;
	int thickness;
	public BezierCurveImpl(List<ControlPoint> curve, int thick)
	{
		this.curvePoints= curve;
		this.thickness=thick;
	}
	@Override
	public List<ControlPoint> getControlPointsOfCurve() {
		// TODO Auto-generated method stub
		return this.curvePoints;
	}
	
	public int getThickness()
	{
		return this.thickness;
	}
	
	public String toString()
	{
		return curvePoints + String.format(" | %d", thickness);
//		String temp = "Curve points: ";
//		temp += curvePoints.toString();
//		return temp;
	}
}
