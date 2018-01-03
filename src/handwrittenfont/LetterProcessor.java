package handwrittenfont;
import java.util.*;

public interface LetterProcessor {


	public Map<Set<BezierCurve>,Integer> getCurves(); 
	
	public double getBaseLine(Letter letter);
}
