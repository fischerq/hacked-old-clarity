package Dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static List<String> IDs = new ArrayList<String>();
	static List<String> files = new ArrayList<String>();
	
	public static void findReplays(File root)
	{
	    File[] files = root.listFiles(); 
	    for (File file : files) {
	        if (file.isFile()) {
	            if(file.getName().endsWith(".dem"))
	            {
	            	Main.IDs.add(file.getName().substring(0,file.getName().length()-4));
	            	Main.files.add(file.getAbsolutePath());
	            }
	        } else if (file.isDirectory()) {
	            findReplays(file);
	        }
	    }
	}
	public static void main(String[] args) {
		findReplays(new File("data/Star Ladder Season 7"));
		//IDs.add(303487989);
		//files.add("303487989.dem");
		Writer w = new Writer();
		for(int i = 0; i < IDs.size(); ++i){
			System.out.println(i+"/"+IDs.size()+": "+IDs.get(i)+" "+files.get(i));
			MatchAnalysis analysis = new MatchAnalysis(IDs.get(i), files.get(i), w);
			analysis.process();
		}
		
		
		w.finish();
	}

}
