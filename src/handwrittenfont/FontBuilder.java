package handwrittenfont;
import java.io.File;
import java.util.*;
public interface FontBuilder 
{

	public void makeFile(File theFile, Map<Letter, Map<Set<BezierCurve>, Integer>> letterAndCurveMap);
	
	public Map<Letter, Map<Set<BezierCurve>, Integer>> readFile(File fileToRead);
	
}
