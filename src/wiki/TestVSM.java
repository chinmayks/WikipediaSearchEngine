package wiki;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class TestVSM {
	
	double [][] matrix;
	ArrayList<String> termList ;
	ArrayList<ArrayList<Doc>> docLists;
	int noOfFiles;
	
	public TestVSM(){
		termList = new ArrayList<String>();
		docLists = new ArrayList<ArrayList<Doc>>();
	}
	
	public void matrixFromFile( String fileName ){
		
		try {
			File folder = new File(fileName+"/wikiTextFilesClean");
			File[] files = folder.listFiles();
			noOfFiles = files.length;
			FileInputStream fis = new FileInputStream(fileName+"/matrixTFIDF.txt");
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line = null;
			ArrayList<String> lines = new ArrayList<String>();
			
			while((line = br.readLine()) != null ) {
				lines.add(line);
			}
			
			matrix = new double[(lines.size())][lines.get(1).split(" ").length-1];
			for( int i = 0; i < lines.size(); i++ ){
				
				String [] tokens = lines.get(i).split(" ");
				if(tokens.length >1){
					termList.add(tokens[0]);
					ArrayList<Doc> docList = new ArrayList<Doc>();					
					for( int j = 1; j < tokens.length; j++ ){
						double tfidf = Double.parseDouble(tokens[j]);
						if(tfidf >0.0){
							
							Doc doc = new Doc(j,tfidf);
							docList.add(doc);
							docLists.add(docList);
							
						}
						matrix[i][j-1] = tfidf;
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestVSM test = new TestVSM();
		WikiBean wikiBean = new WikiBean();
		test.matrixFromFile(wikiBean.getPathName());
		VSM vsm = new VSM(wikiBean.getPathName());
		vsm.setTotalFiles(test.noOfFiles);
		String[] query = {"anarchism"};
		vsm.Search(test.termList, test.docLists, query);	
		
		
		
	}

}
