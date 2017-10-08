package wiki;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatrixBuilder {
	
	int[] myDocs;
	ArrayList<String> termList;
	ArrayList<int[]> docLists;
	ArrayList<String> stopList;
	ArrayList<String> titleList;
	
	public MatrixBuilder( String directory ){
		
		docLists = new ArrayList<int[]>();
		titleList = new ArrayList<String>();
		
//		File folder = new File("wikiTextFilesClean");
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		
		//String[] listOfFiles = folder.list();
		
//		String [] listOfFiles = {"1","2","3","4","5","6","7","8","9","10"};
		
		myDocs = new int[listOfFiles.length];
		System.out.println("matrixBuilder: doc length" + myDocs.length);
		termList = new ArrayList<String>();
		
		String line = null;
		String allLines = new String();
		Integer docId;
		BufferedReader reader;
		
		// make stop word list
		/*try {
			stopWords();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//sort the stop word list
		Collections.sort(stopList);
		
		*/
		try {
			
			// index document
			for( int i = 0; i < listOfFiles.length; i++ ){
			
				docId = new Integer(listOfFiles[i].getName().replace(".txt", ""));
				myDocs[docId-1] = docId;
				
			}
			
			// Loops over documents and processes them
			for( int m = 0; m < myDocs.length; m++ ){////////////////////////////////////////////////////////////////////
			//for( int m = 0; m < 10; m++ ){	
				
				allLines = "";
				reader = new BufferedReader(new FileReader("wikiTextFilesClean/" + myDocs[m] +".txt"));
				
				// extract category
				String topLine = reader.readLine();
				String[] header = topLine.split("content:{1}");
				String title = this.extract(header[0]);
				titleList.add(title);
				allLines += header.length < 1 ? header[1] : "";
				
				while( (line = reader.readLine()) != null ){	
					allLines += " " + line;
				}
	
				String [] tokens = allLines.split("[ .,&?!:;$%()\\-_+/=*\"]+");
				
				// Add tokens to term list and record frequency
				for( String token: tokens){
					
					//if( searchStopword(token) == -1 ){					    
						if(!termList.contains(token)){
							termList.add(token);
							//////////////////////////////////////////////////////////////////
							int[] arr = new int[listOfFiles.length];
							//int[] arr = new int[10];

							arr[m] = 1;
							docLists.add(arr);
						}
						else{
							int index = termList.indexOf(token);
							
							int[] docList = docLists.remove(index);							
							docList[m] += 1;
							docLists.add(index,docList);
						}
					//}// if not found stopword	
				}
				
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		

//		int docLen = docLists.get(0).length;
//		int docLen = 10;
//		int termLen = termList.size();
		
//		System.out.println("doc length:" + docLen);
//		System.out.println("term length:" + termLen);
//		for( int t = 0; t < titleList.size(); t ++ ){
//			System.out.println("title : " + titleList.get(t));
//		}
//		
////		// Build the term-document matrix
//		double [][] matrix = new double[termLen][docLen];
//		for( int x=0; x < termLen; x++){			
//			for (int j=0; j < docLen; j++){
//				matrix[x][j] = docLists.get(x)[j];
//			}
//		}
		
		// Write the matrix to file
		writeToFile();
		
	}
	
	private void writeToFile(){
		
		String matrix = toString();
		//System.out.println(matrix);
		try {
			FileWriter fw = new FileWriter("matrix.txt");
			fw.write(matrix);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	//create stop word list
	private void stopWords() throws IOException{
		//stopList = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream("stopwords");
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String lines;
			while( (lines = br.readLine())!= null ){
				
				lines = lines.toLowerCase();
				
				// split on non-word character
				stopList.add(lines);
				
			}

			//System.out.println(stopList.size());
			dis.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
	}
		
	// check if the string is in stop word list
	private int searchStopword( String key )
		{
			int lo = 0;
			int hi = stopList.size() - 1;
			
			while( lo <= hi )
			{
				//Key is in a[lo..hi] or not present
				int mid = lo + (hi-lo)/2;
				int result = key.compareTo( stopList.get(mid) );
				if( result < 0 ) {
					hi = mid - 1;
				}
				else if( result >0 ){
					lo = mid+1;
				}
				else {
					return mid;
				}
			}
			return -1;
	}
		
	/**
	 * Returns title of document
	 * @param str
	 * @return
	 */
	private String extract( String str  ){
		
		String str1 = "";

		Pattern pattern = Pattern.compile("title:(.*,)");
		
		Matcher matcher = pattern.matcher(str);
		String[] tokens = null;
	
		//category.add( matcher.group(0));
		if( matcher.find()){
			str1 = matcher.group(0);
			tokens = str1.split(",");
			//System.out.println(str1);
			
		}
		String result = tokens[0].replace("title:", "");
		
        //System.out.println(result);
        
		return result;
	}
	

	
	
	public String toString(){
		String matrixString = new String();
		//System.out.println(termList.get(0));
		//System.out.println(termList.get(1));
		//System.out.println(Character.getNumericValue('-'));
		for(int i = 0;i < termList.size();i++){
			
			char first = termList.get(i).length() > 0 ? termList.get(i).charAt(0) : ' ';
			int value = Character.getNumericValue(first);
			
			if( value < 0 || value > 35 ){
				continue;
			}
			
			matrixString += termList.get(i) + " ";
			int[] docList = docLists.get(i);
			
			for( int j=0;j < docList.length;j++ ){
			//for( int j = 0; j < 5; j++ ){//////////////////////////////////////////////
				matrixString += docList[j] + " ";
			}
			matrixString += i == termList.size()-1 ? "" : "\n";
		}
		return matrixString;
	}
	
	@SuppressWarnings("unused")
	public static void main ( String [] args ) {
		MatrixBuilder build = new MatrixBuilder("wikiTextFilesClean");
		//System.out.println(build);
	}
	
}


