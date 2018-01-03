package handwrittenfont;

import java.awt.image.*;
import java.util.*;
import java.util.*;

public interface CurveGenerator {

	
	public Map<Set<BezierCurve>, Integer> getCurvesOfLetter(Letter letter);
	
	public double getBaseLineOfLetter(Letter letter);
	// take image find points on letter which uses controlpoint to define it.
	// find two more points off the letter and sends it to BC getBC 
	//checks to determine accuracy. all is recursing or looping to try points until accuracy is met.
	//continues process until letter is complete. moves to next BufferedImage 
	//q(t) = p1(1-t)3 + 3p2t(1-t)2 + 3p3t2 + p4t3 
}
