package wiki;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;















import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WikiConnector extends HttpServlet {
	
	WikiBean wikibean;
	WikiIndexing startobj;
	TestLSI testLSI;

	private static final long serialVersionUID=1L;
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException{
		  doPost(request,response);
		
	}
	 @SuppressWarnings({ "unused", "resource", "unchecked" })
	 
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException{
		
		 
		String uri = request.getRequestURI();
		
		if(uri.endsWith("connection.do")){
			HttpSession session=request.getSession(true);
			try{
				
				request.getRequestDispatcher("/search.jsp").forward(request, response);
				//System.out.println("flow pass to search page");
				
			}
			catch(Exception e){
				System.out.println("Error in the connection page");
				e.printStackTrace();
			}
		}
		
		if(uri.endsWith("search.do")){
			HttpSession session = request.getSession(true); //session is used to store the earlier entered values in http request
			String searchString = request.getParameter("searchTerm");
			if(searchString.isEmpty()){
				request.setAttribute("noterm","Enter search term");
			}
			System.out.println("** SearchString **"+searchString);
	/*		String textFileNumber = "/Users/AbhiKemp/Documents/DevWorkspace/Java/Practice1/WikiProjectIntegration/wikiTextFilesClean/"+"1"+".txt";
			System.out.println(textFileNumber);
			ArrayList<String> textFileBodyList = new ArrayList<String>();
			String str = readFile(textFileNumber);
			System.out.println(str);
			textFileBodyList.add(str); */
			System.out.println("from connectio");
			wikibean = new WikiBean();
			startobj = new WikiIndexing();
			testLSI = new TestLSI(wikibean.getPathName());
			System.out.println("done ...");
			startobj.startWikiSearchEngine(searchString);
			
	//		wikibean.setTitleList(testLSI.titleList);
			
			HashMap<Integer, Double> vsmResult = startobj.vsmResult;
			HashMap<Integer, Double> resultQueryValue;
			HashMap<Integer, String> titleListValue;
			LinkedHashMap<Integer, String> FrontEndValue=new LinkedHashMap<Integer,String>();
			resultQueryValue=testLSI.search(searchString);
			
			titleListValue=testLSI.titleList;
			HashMap<Integer, String> spoilers = new HashMap<Integer,String>();
			ArrayList<Integer> fireValue = new ArrayList<Integer>();
           int count = 1;
			
			for( Map.Entry<Integer, Double> queryEntry:resultQueryValue.entrySet()){
				
				if ( count <= 10 ){
					
					fireValue.add(queryEntry.getKey());
					
					System.out.println(queryEntry);
					
					int parentID = queryEntry.getKey();
					
					 int textFileNumber = parentID;
					 
					 System.out.println(textFileNumber);
					 String FileNumber = "/Users/AbhiKemp/Desktop/KPT Project Work/August 3rd/Chinmay/WikiProjectIntegration-2/wikiTextFilesClean/"+textFileNumber+".txt";
					 
					//System.out.println(FileNumber);
					
					String[] str = readFile(FileNumber);
					
//					String teaser = str.substring(32, 132).concat(".....");
					
					String teaser = str[1].substring(0,200).concat("...");
					
				//	System.out.println("description: " + teaser);
					
					spoilers.put( parentID, teaser );
					
					//titles.put(parentID, str[0]);
					FrontEndValue.put(queryEntry.getKey(), str[0]);
					count++;
				}
				else{
					break;
				}
			}
			
			
			HashMap<Integer, String> vsmSpoilers = new HashMap<Integer,String>();
			
			ArrayList<Integer> vsmFireValue = new ArrayList<Integer>();
			LinkedHashMap<Integer, String> vsmFrontEndValue=new LinkedHashMap<Integer,String>();
			
			count = 0;
			
			for( Map.Entry<Integer, Double> queryEntry:vsmResult.entrySet()){
				
				if ( count < 10 ){
					
					vsmFireValue.add(queryEntry.getKey());
					
					
					
					int parentID = queryEntry.getKey();
					
					 int textFileNumber = parentID+1;
					 
					 System.out.println(textFileNumber);
				 String FileNumber = "/Users/AbhiKemp/Desktop/KPT Project Work/August 3rd/Chinmay/WikiProjectIntegration-2/wikiTextFilesClean/"+textFileNumber+".txt";
					 
					//System.out.println(FileNumber);
					
					String[] str = readFile(FileNumber);
					
//					String teaser = str.substring(32, 132).concat(".....");
					
					String teaser = str[1].substring(0,200).concat("...");
					
					//System.out.println("description: " + teaser);
					
					vsmSpoilers.put( parentID, teaser );
					
					//titles.put(parentID, str[0]);
					vsmFrontEndValue.put(queryEntry.getKey(), str[0]);
					count++;
				}
				else{
					break;
				}
			}
			wikibean.setResultQuery(resultQueryValue);
			
			wikibean.setSpoilers(spoilers);
			//wikibean.setTitles(titles);
			
			Set<Integer> keys= resultQueryValue.keySet();
			
			System.out.println("sttt");
			System.out.println(FrontEndValue);
		
			
			wikibean.setTitleLinkedList(FrontEndValue);
			
			/////////////////////////////////////////////
			wikibean.setVsmSpoilers(vsmSpoilers);
			wikibean.setVsmTitleLinkedList(vsmFrontEndValue);
			wikibean.setQuery(searchString);
			
			
			if(wikibean!=null){
		
				request.setAttribute("storedValues",wikibean);

				request.getRequestDispatcher("/title-2.jsp").forward(request, response);
				
			} 
			
			
			
		}
		
		if(uri.endsWith("openPage.do")){
			HttpSession session=request.getSession(true);
			 int parentID = Integer.parseInt(request.getParameter("documentid"));
			 int textFileNumber = parentID;
			 System.out.println(textFileNumber);
			 String FileNumber = "/Users/AbhiKemp/Desktop/KPT Project Work/August 3rd/Chinmay/WikiProjectIntegration-2/wikiTextFilesClean/"+textFileNumber+".txt";
				System.out.println(FileNumber);
				ArrayList<String> textFileBodyList = new ArrayList<String>();
				String str = readFile1(FileNumber);
				int checkIndex=str.indexOf("content:");
			//	System.out.println("**checkIndex"+checkIndex);
				int newInd=checkIndex+8;
				int titleIndex=str.indexOf("title:");
				//System.out.println("**titleIndex"+titleIndex);
				String finalTitle=str.substring(14, checkIndex-1);
				String str1=str.substring(newInd,1000);
				String str2=str.substring(1001,2000);
				String str3=str.substring(2001, 3001);
			 
			    ArrayList<String> targetStr=new ArrayList<String>();
			    targetStr.add(str1);
			    targetStr.add(str2);
			    targetStr.add(str3);
				
				WikiBean wikibean1=new WikiBean();
				wikibean1.setSetAnswerBody(targetStr);
				wikibean1.setTitle(finalTitle);
			try{
				request.setAttribute("full",wikibean1);
				request.getRequestDispatcher("/openPage.jsp").forward(request, response);
				System.out.println("flow pass to openPage");
				
			}
			catch(Exception e){
				System.out.println("Error in the openPage");
				e.printStackTrace();
			}
		}
		 
		 
		 
	 }
	
	 String readFile1(String fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
		}

	 String[] readFile( String fileName ) throws IOException {
		 
		 BufferedReader reader = new BufferedReader(new FileReader(fileName));
		 String topLine = reader.readLine();
			String[] header = topLine.split("content:{1}");
			
			String[] info = header[0].split(",");
			String title = info[1].replace("title:","");
			
			String line = null;
			String allLines = "";
			allLines += header.length < 2 ? "" : header[1];
			//allLines += header[1];

			while( (line = reader.readLine()) != null ){	
				allLines += " " + line;
			}
			
			String [] parts = {title,allLines};
			
			return parts;
	 }
	public static void main(String[] args) {
	/*	WikiConnector wc = new WikiConnector();
		String textFileNumber = "/Users/AbhiKemp/Documents/DevWorkspace/Java/Practice1/WikiProjectIntegration/wikiTextFilesClean/"+"1"+".txt";
		System.out.println(textFileNumber);
		String str;
		try {
			str = wc.readFile(textFileNumber);
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	
	}

}
