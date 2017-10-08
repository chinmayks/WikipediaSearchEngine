package wiki;
/* 

 * VSM.java 
 * 
 * Version: 2.0 07/26/2017
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
/**
 * This method creates Inverted Index using the concept of Vector Space Model. 
 * It also calculates TFIDF weights for each term and does a search based on rank using 
 * the concept of Cosine Similarity.
 * @author qyuvks
 * @author    : Team 02
 *
 */

public class VSM implements Cloneable{
	String[] myDocs;
	ArrayList<String> termList;
	ArrayList<ArrayList<Doc>> docLists;
	double[] docLength;
	HashMap<Integer,String > titleList; 
	ArrayList<String> stopwordList;
	String pathName;
	int totalFiles;
	
	
	
	public int getTotalFiles() {
		return totalFiles;
	}

	public void setTotalFiles(int totalFiles) {
		this.totalFiles = totalFiles;
	}

	/**
	 * Construct an inverted index 
	 * @param docs List of input strings or file names
	 * 
	 */
	public VSM(String path)
	{
		
		termList = new ArrayList<String>();
		docLists = new ArrayList<ArrayList<Doc>>();
		titleList = new HashMap<>();
		stopwordList = new ArrayList<String>();
		pathName = path;
		totalFiles = 0;
		
		
			
	}
	
	public void wikiIndex(String title, String docid, String textBody ){
		
		
		ArrayList<Doc> docList;
		stopWord();
		int docID = Integer.parseInt(docid)-1;
		myDocs = textBody.split("[ :,]+");
		titleList.put( docID, title);
		//parse the documents to construct the index and collect the raw frequencies.
		for(int i=0;i<myDocs.length;i++)
		{
			String token = myDocs[i];
			token = token.replaceAll("(\\r|\\n)", "");
			if(!stopwordList.contains(token)){
				if(!termList.contains(token))
				{
					termList.add(token);
					docList = new ArrayList<Doc>();
					Doc doc = new Doc(docID,1); //initial raw frequency is 1
					docList.add(doc);
					docLists.add(docList);
				}
				else
				{
					int index = termList.indexOf(token);
					
					docList = docLists.get(index);
					
					int k=0;
					boolean match = false;
					
					//search the postings list for a document id, if match, insert a new position number to the document id
					for(Doc doc:docList)
					{
						if (doc.docId == docID)
						{
							doc.tw++; //increase word count
							match = true;
							break;
						}
						k++;
					}
					//if no match, add a new document id along with the position number
					if(!match)
					{
						Doc doc = new Doc(docID,1); 
						docList.add(doc);
					}
				}
				
			}//end with parsing
			
		}
		totalFiles++;
	}
	
	
	
	public void calculateTFIDF(){
		
		//LBE07: compute the tf-idf term weights and the doc lengths 
		int N = totalFiles;
		ArrayList<Doc> docList;
		docLength = new double[N];
		for(int i=0;i<termList.size();i++){
			docList = docLists.get(i);
			int df = docList.size();
			Doc doc;
			for(int j=0;j<docList.size();j++){
				doc = docList.get(j);
				double tfidf = (1+Math.log(doc.tw))*Math.log(N/(df*1.0));
				docLength[doc.docId] += Math.pow(tfidf, 2);
				doc.tw = tfidf;
				docList.set(j, doc);
			}
		}
		//update the length
		for(int i = 0; i < N; i++ ){
			docLength[i] = Math.sqrt(docLength[i]);
		}
	}
	
	public void Search(ArrayList<String> t, ArrayList<ArrayList<Doc>> d, String[] query){
		termList = t;
		docLists = d;
		rankSearchCos(query);
	}
	
	
	public HashMap<Integer, Double> rankSearchCos(String[] query){
		
		HashMap<Integer, Double> docs = new HashMap<Integer, Double>();
		
		HashMap<Integer, String> rankedDocs = new HashMap<Integer, String>();
		
		ArrayList<Doc> docList;
		
		for(String term: query){
			int index = termList.indexOf(term);
			if( index < 0 )
				continue;
			
			docList = docLists.get(index);
			double w_t = Math.log(getTotalFiles()*1.0/docList.size());
			Doc doc;
			for(int j=0;j<docList.size();j++){
				doc = docList.get(j);
				double queryNorm =0;
				double score = (w_t * doc.tw)/docLength[doc.docId];
				if(!docs.containsKey(doc.docId)){
					docs.put(doc.docId, score);
					
				}
				else{
					score += docs.get(doc.docId);
					docs.put(doc.docId, score);
					
				}
			}
		}
		
		HMapSort hm = new HMapSort();
		
		docs = hm.sortHMap(docs);
		
		System.out.println("Relevant documents are :");
		
		int count = 0;
		
		for( Entry<Integer, Double> value:docs.entrySet()){
			
			//////////////////////////////////////////
			if( count < 10 ){
				System.out.println("Document "+( value.getKey() + 1 ) + ".txt ******** title- "+ titleList.get(value.getKey()));
				
				//rankedDocs.put( value.getKey() , arg1 )
			}
			
			else break;
			
			count++;
		}
		
		return docs;
		
	}
	
	/**
	 * Return the string representation of a positional index
	 */
	public String toString()
	{
		String matrixString = new String();
		ArrayList<Doc> docList;
		for(int i=0;i<termList.size();i++){
				matrixString += String.format("%-15s", termList.get(i));
				docList = docLists.get(i);
				for(int j=0;j<docList.size();j++)
				{
					matrixString += docList.get(j)+ "\t";
				}
				matrixString += "\n";
			}
		return matrixString;
	}
	
	
	/**
     * This method searches a query if present in the dictionary
     * Works for single query only
     * @param query
     * @return postings
     */
    public ArrayList<Doc> search(String query){
    	
		int index = termList.indexOf(query);
		
		if(index < 0)
			return null;
		return docLists.get(index);
	}
    
    /**
     * This method searches a query if present in the dictionary
     * Works for multiple query.
     * @param query array of queries
     * @return postings
     */
    public ArrayList<Doc> search(String[] query){
		ArrayList<Doc> result = search(query[0]);
		int termId = 1;
		while(termId<query.length){
			//System.out.println(query[termId]);
			ArrayList<Doc> result1 = search(query[termId]);
			result = merge(result, result1);
			termId++;
		}
		return result;
	}
	/**
	 * If multiple queries are passed to search, this method merge different postings 
	 * @param l1 posting 1
	 * @param l2posting 2
	 * @return mergedList: combination of 2 postings which are in common.
	 */
	private ArrayList<Doc> merge(ArrayList<Doc> l1, ArrayList<Doc> l2){
		ArrayList<Doc> mergedList = new ArrayList<Doc>();
		int id1=0, id2=0;
		while(id1<l1.size() && id2<l2.size()){
			if(l1.get(id1).docId == l2.get(id2).docId){
				mergedList.add(l1.get(id1));
				id1++;
				id2++;
			}
			else if(l1.get(id1).docId < l2.get(id2).docId)
				id1++;
			else
				id2++;
		}
		return mergedList;
	}
	
	public void stopWord(){		
		
    	try{
    		BufferedReader reader = new BufferedReader(new FileReader(pathName+"/stopwords.txt"));
    		String line = null;
			while((line=reader.readLine())!=null){
				stopwordList.add(line.toLowerCase()); 
			}
			Collections.sort(stopwordList);
    		reader.close();
    	}
    	catch(IOException ioe){
			ioe.printStackTrace();
		}
    
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {

	    return super.clone();
	}
	
}

/**
 * 
 * @author qyuvks
 * Document id class that contains the document id and the term weight in tf-idf
 */
class Doc{
	int docId;
	double tw;
	public Doc(int did, double weight)
	{
		docId = did;
		tw = weight;
	}
	
	
	public String toString()
	{
		String docIdString = docId + ":" + tw;
		return docIdString;		
	}
}
