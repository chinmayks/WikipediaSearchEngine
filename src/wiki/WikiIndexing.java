package wiki;
/* 
 * WikiIndexing.java 
 * 
 * Version: 1.0 07/26/2017
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.lang.reflect.Field;
import java.util.Scanner;


/**
 * This method creates an index of Wikipedia dumps using 2 different approaches.
 * Approach 1 is using Apache Lucene
 * Approach 2 using Vector Space Model.
 * It creates inverted index and calls WikiSearch.java, VSM.java and LSI.java
 * @author    : Team 02
 *
 */
public class WikiIndexing implements Cloneable{
	
	VSM vsm;
	HashMap<Integer, Double> resultQueryValue;
	HashMap<Integer, Double> vsmResult;
	String query;

	
	public void startWikiSearchEngine(String query) {
		// TODO Auto-generated method stub
		//location of wikipedia dumps
		WikiBean wikiBean = new WikiBean();
		
		this.query = query;
		
		String indexDirectoryPath = wikiBean.getPathName();
		
		vsm = new VSM(indexDirectoryPath);
		
		try {
			
			indexDocuments(indexDirectoryPath);
			
			System.out.println("Searching "+ query + " using Vector Space Model \n");
			
			vsmResult = vsm.rankSearchCos(query.split(" "));
			
			System.out.println("************************************ \n");
			
			// test LSI and VSM
			System.out.println("Searching " + query + " using Latent Sematic Indexing \n");
			TestLSI testLSI = new TestLSI(indexDirectoryPath);
			resultQueryValue = testLSI.search(query);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}
	/**
	 * It creates a document containing Lucene Index and also calls VSM.java to create invertedIndex and calculates TFIDF
	 * weights. After TFIDF weights are calculated it creates Latent Semantic Index to do a Semantic based search.
	 * @param iwriter IndexWriter object
	 * @param file folder containing wiki dumps
	 * @param indexDirectoryPath
	 * @throws IOException
	 */
	public void indexDocuments(String indexDirectoryPath) throws IOException{
		try {
			

			File folder = new File(indexDirectoryPath+"/wikiTextFilesClean");
			File[] files = folder.listFiles();
			
			//System.out.println("Reading "+ files.length +" Wikipedia documents");
			
			if( files.length > 0 ){
				
				for(File f:files){
					//System.out.println(f);
					//String fileName = indexDirectoryPath+ "/" +f;
					Scanner scan = new Scanner(new FileReader(f));
					
					scan.useDelimiter("docid:|title:|content:");
					String docID  = scan.next().split(",")[0];
					String title = scan.next().split(",")[0];
					String body = scan.next();
					
					
					
					// Instantiating Vector space model object
					vsm.wikiIndex(title, docID, body);
					
					//vsmTF.wikiIndex(title, docID, body.split("[ .,&?!:;$%()\\-\"]+"));
					//System.out.println(vsm);
					
					
					
					
					//System.out.println("done with file "+f);
					scan.close();
				}
				
				MatrixBuilder_old matrix = new MatrixBuilder_old( vsm.termList, vsm.docLists, files.length );
				
				matrix.createTitleList( vsm.titleList );
				
				vsm.calculateTFIDF();
				
				//matrix.createMatrixTFIDF(vsm.termList, vsm.docLists, files.length);
				
				
				
			}
		
			
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	
	
}

