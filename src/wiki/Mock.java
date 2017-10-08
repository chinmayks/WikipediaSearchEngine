package wiki;
import java.util.*;


public class Mock {
   
	ArrayList<String> sendLink(){
		ArrayList<String> link = new ArrayList<String>();
		link.add("abhishek");
		link.add("chinmay");
		link.add("yuching");
		link.add("ist");
		link.add("qiyu");
		return link;
	}
	
	String mainFileCheck(String name){
		System.out.println("In mainFileCheck"+name);
		HashMap<String,String> mainFile = new HashMap<String,String>();
		String value="";
		mainFile.put("abhishek","abhishek is member1");
		mainFile.put("chinmay", "chinmay is member2");
		mainFile.put("yuching", "yuching is member3");
		mainFile.put("ist", "ist department");
		mainFile.put("qiyu", "professor qi yu");
		
		for (Map.Entry<String, String> entry : mainFile.entrySet()) {
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    if(entry.getKey().equals(name)){
		    	value = entry.getValue();
		    	System.out.println("Match found");
		    }
		}
		return value;
	}
	

}
