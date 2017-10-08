package wiki;
/* 

 * MatrixBuilder.java 
 * 
 * Version: 1.0 07/26/2017
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Jama.*;
/**
 * This method creates indexes of wikipedia dumps to do an Latent Semantic Index.
 * It creates matrix based on InvertedINdex and then calls LSI.java.
 * @author    : Team 02
 *
 */
public class MatrixBuilder_old {
	
	ArrayList<String> termList;
	ArrayList<ArrayList<Doc>> docLists;
	HashMap<Integer, String> titleList;
	int totalFiles;
	
	
	
	public MatrixBuilder_old(ArrayList<String> tl, ArrayList<ArrayList<Doc>> dl, int total){
		termList = tl;
		docLists = dl;
		totalFiles = total;
		writeToFile();
	}
	
	
	
	public void createMatrixTFIDF(ArrayList<String> tl, ArrayList<ArrayList<Doc>> dl, int total){
		termList = tl;
		docLists = dl;
		totalFiles = total;
		writeToFileTFIDF(toStringTFIDF());
	}
	
	public void createTitleList(HashMap<Integer, String> title){
		titleList = title;
		createTitleFile();
	}
	
	
	public void writeToFile(){
		
		
		//System.out.println(matrix);
		try {
			FileWriter fw = new FileWriter("matrix.txt");
			ArrayList<Doc> docList;
			for(int i=0;i<termList.size();i++){
				String matrixString = new String();
				
				matrixString += termList.get(i)+" ";
				docList = docLists.get(i);
				int[] docSize = new int[totalFiles+1];
				for(int x =0;x<totalFiles+1;x++){
					docSize[x] =0;
				}
				for(int j=0;j<docList.size();j++)
				{
					docSize[docList.get(j).docId] =  (int)docList.get(j).tw;
					
				}
				for(int x =0;x<totalFiles;x++){
					matrixString += docSize[x]+ " ";
				}
				matrixString += "\n";
				fw.write(matrixString);
			}
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void writeToFileTFIDF(String mat){
		
		String matrix = mat;
		//System.out.println(matrix);
		try {
			FileWriter fw = new FileWriter("matrixTFIDF.txt");
			fw.write(matrix);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String toString()
	{
		String matrixString = new String();
		ArrayList<Doc> docList;
		for(int i=0;i<termList.size();i++){
				matrixString += termList.get(i)+" ";
				docList = docLists.get(i);
				int[] docSize = new int[totalFiles+1];
				for(int x =0;x<totalFiles+1;x++){
					docSize[x] =0;
				}
				for(int j=0;j<docList.size();j++)
				{
					docSize[docList.get(j).docId] =  (int)docList.get(j).tw;
					
				}
				for(int x =0;x<totalFiles;x++){
					matrixString += docSize[x]+ " ";
				}
				matrixString += "\n";
			}
		return matrixString;
	}
	
	
	public String toStringTFIDF()
	{
		String matrixString = new String();
		ArrayList<Doc> docList;
		for(int i=0;i<termList.size();i++){
				matrixString += termList.get(i)+" ";
				docList = docLists.get(i);
				double[] docSize = new double[totalFiles+1];
				for(int x =0;x<totalFiles+1;x++){
					docSize[x] =0;
				}
				for(int j=0;j<docList.size();j++)
				{
					docSize[docList.get(j).docId] =  docList.get(j).tw;
					
				}
				for(int x =0;x<totalFiles;x++){
					matrixString += docSize[x]+ " ";
				}
				matrixString += "\n";
			}
		return matrixString;
	}
	/**
	 * Creating a file titleList.txt to store all the title of the dumps along with their docID.
	 */
	public void createTitleFile(){
		String title = new String();
		title = titleList.toString();
		try {
			FileWriter fw = new FileWriter("titleList.txt");
			fw.write(title);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


