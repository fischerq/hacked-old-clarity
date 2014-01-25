package Dataset;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*File dir = new File("data");
		for (File child : dir.listFiles()) {
			MatchAnalysis analysis = new MatchAnalysis(child.getAbsolutePath());
			analysis.process();
		}*/
		Writer w = new Writer();
		MatchAnalysis analysis = new MatchAnalysis(303487989, "data/303487989.dem", w);
		analysis.process();
		
		w.finish();
	}

}
