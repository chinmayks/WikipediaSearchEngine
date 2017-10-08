package wiki;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import Jama.Matrix;

public class TestLSI {
	
	double [][] matrix;
	ArrayList<String> termList = new ArrayList<String>();
	HashMap<Integer, String> titleList = new HashMap<Integer, String>();
	
	public TestLSI(String fileName){
		matrixFromFile(fileName);
		readTitle(fileName);
		
	}
	
	private void matrixFromFile( String fileName ){
		
		try {
			FileInputStream fis = new FileInputStream(fileName+"/matrix.txt");
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
					
					for( int j = 1; j < tokens.length; j++ ){
						matrix[i][j-1] = Double.parseDouble(tokens[j]);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public HashMap<Integer, Double> search( String searchQuery ) {
		
		Matrix ma = new Matrix(matrix);
		LSI_old lsi = new LSI_old(ma, termList);
		String[] ar = searchQuery.split(" ");
		HashMap<Integer, Double> queryResult = lsi.queryVector(ar);
		HMapSort hsort = new HMapSort();
		queryResult = hsort.sortHMap(queryResult);
		System.out.println("\n Relevant documents are :");
		int count =0;
		for(Entry<Integer, Double> value:queryResult.entrySet()){
			if(count<20)
				System.out.println("Document "+(value.getKey())+".txt ******** title- "+titleList.get(value.getKey()-1));
			else break;
			
			count++;
		}
		//System.out.println(lsi.queryVector(searchQuery.split(" ")));
		return queryResult;
	}
	
	public void readTitle(String fileName){
		try {
			FileInputStream fis = new FileInputStream(fileName+"/titleList.txt");
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line ="";
			
			while((line = br.readLine()) != null){
				String[] tokens = line.split(",");
				for(String token :tokens){
					String[] tList = token.split("=");
					tList[0] = tList[0].replaceAll("[{ ]", "");
					tList[1] = tList[1].replaceAll("[}]", "");
					int docID = Integer.parseInt(tList[0]);
					String title = tList[1];
					titleList.put(docID, title);
					
				}
				
			}
			br.close();
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
