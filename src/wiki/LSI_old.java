package wiki;

import java.util.*;

import Jama.*;
import Jama.util.*;



public class LSI_old {
      static ArrayList<String> termList = new ArrayList<String>();
      Matrix docSigma;
      Matrix wordSigma;
      Matrix test;
      
      public LSI_old(Matrix matrix, ArrayList<String> termList){
    	 // this.addTerm();
    	  this.termList = termList;
    	  this.singleValueDecomposition(matrix);
      }
      
     

	/*
       * 
       */
	  private void singleValueDecomposition( Matrix matrix ) {
		  
		  
	    // phase 1: Singular value decomposition
	    SingularValueDecomposition svd = new SingularValueDecomposition( matrix );
	    
	    Matrix wordVector = svd.getU();
	    
	    Matrix sigma = svd.getS();
	    
	    Matrix documentVector = svd.getV();
	    
	    // compute the value of k (ie where to truncate)
	    ///int k = (int) Math.floor(Math.sqrt(matrix.getColumnDimension()));
	    
	    int k = 10;
	    
	    if( k < 2 ){ 
	    	k = 2;
	    }
	    System.out.println("tunable parameter k: " + k);
	    Matrix reducedWordVector = wordVector.getMatrix(
	                                       0, wordVector.getRowDimension() - 1, 0, k - 1);
	    double[][] s = reducedWordVector.getArray();
	    //System.out.println("S");
//	    for( int a = 0; a < s.length; a++ ){
//			  System.out.println(Arrays.toString(s[a]) + "\n");
//		}
//	    System.out.println();
	    
	    Matrix reducedSigma = sigma.getMatrix(0, k - 1, 0, k - 1);
	    
	    double[][] sig = reducedSigma.getArray();
	    //System.out.println("Sigma");
//	    for( int b = 0; b < sig.length; b++ ){
//			  System.out.println(Arrays.toString(sig[b]) + "\n");
//		}
//	    System.out.println();
	    
	    Matrix reducedDocumentVector = documentVector.getMatrix(
	                                           0, documentVector.getRowDimension() - 1, 0, k - 1);
	    
	    double[][] u = reducedDocumentVector.transpose().getArray();
	    //System.out.println("U");
//	    for( int c = 0; c < u.length; c++ ){
//			  System.out.println(Arrays.toString(u[c]) + "\n");
//		}
//	    System.out.println();
	    
	    
	    ///////////////////
	    wordSigma = reducedWordVector.times(reducedSigma);
	    
	    double[][] w = wordSigma.getArray();
	    //System.out.println("WORD times sigma");
//	    for( int d = 0; d < w.length; d++ ){
//			  System.out.println(Arrays.toString(w[d]) + "\n");
//		}
//	    System.out.println();
	    
	    ////////////////////////////////////////
	    docSigma = reducedDocumentVector.times(reducedSigma);
	    
	    
	    double[][] w1 = docSigma.getArray();
//	    System.out.println("Sigma: " + Arrays.toString(w1));
//	    System.out.println("DOC times sigma");
//	    for( int e = 0; e< w1.length; e++ ){
//			  System.out.println(Arrays.toString(w1[e]) + "\n");
//		}
//	    System.out.println();
	    
//	    Matrix weights = reducedWordVector.times(
//	                                reducedSigma).times(reducedDocumentVector.transpose());
//	    
//	    // Phase 2: normalize the word scrores for a single document
//	    for (int j = 0; j < weights.getColumnDimension(); j++) {
//	    	
//	      double sum = sum(weights.getMatrix(
//	        0, weights.getRowDimension() - 1, j, j));
//	      
//	      for (int i = 0; i < weights.getRowDimension(); i++) {
//	        weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
//	      }
//	    }
	   
//	    return weights;
	    
	  }
	  
//	  private void addTerm(){
//		  
//		  termList.add("romeo");
//		  termList.add("juliet");
//		  termList.add("happy");
//		  termList.add("dagger");
//		  termList.add("live");
//		  termList.add("die");
//		  termList.add("free");
//		  termList.add("new-hampshire");
//		  
//		  
//	  }
	  
	  // query vector
	  public HashMap<Integer, Double> queryVector(String[] arr){
		  
		//  System.out.println(termList);
		  
//		  Matrix mat = new Matrix(0,0);
		  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  Matrix mat = new Matrix(1,2);
		  
		  for( int i = 0; i < arr.length; i++ ){
			  
			  if( termList.contains(arr[i])){
				  int row = termList.indexOf(arr[i]);
				  //System.out.println("row: "  + row);
				  
				  /////////////////////////////////////////////////////////////////////
				  Matrix queryV = wordSigma.getMatrix(row, row, 0, wordSigma.getColumnDimension()-1);
//				  Matrix queryV = wordSigma.getMatrix(row, row, 0, 1);
				  //double[][] w1 = queryM.getArray();
				  
//					System.out.println(arr[i]);
//					for( int e = 0; e < w1.length; e++ ){
//						  System.out.println(Arrays.toString( w1[e]) + "\n" );
//					}
//					System.out.println();
				  if( i == 0 ){
					  mat = queryV;
				  }
				  else{
					  mat = mat.plus( queryV );
				  }
				  //double[][] w2 = mat.getArray();
				  
//					System.out.println("after plus ");
//					for( int e = 0; e < w2.length; e++ ){
//						  System.out.println(Arrays.toString( w2[e]) + "\n" );
//					}
//					System.out.println();
				  
			  }
			 
		  }// for
		  
		  //double d = Math.round(1/arr.length) / ;
		  mat = mat.times( 0.5 );
		  
//		  double[][] w1 = mat.getArray();
//		  
//			System.out.println("term query vector");
//			for( int e = 0; e < w1.length; e++ ){
//				  System.out.println(Arrays.toString( w1[e]) + "\n" );
//			}
//			System.out.println();
		return this.cosineDistance(mat);
		  
	  }
	  
	  private HashMap<Integer, Double> cosineDistance( Matrix queryV){
		  
		  double [] scores = new double [docSigma.getRowDimension()];
		  
		  for( int i = 0; i < docSigma.getRowDimension(); i++ ){
			  
			  /////////////////////////////////////////////////////////////////////////////////////////
			 Matrix queryM = docSigma.getMatrix(i, i, 0, docSigma.getColumnDimension()-1);
			  //Matrix queryM = docSigma.getMatrix(i, i, 0, 1);
			 // double result = dot( queryM, queryV );
//			  double magnitudeQuery = Math.pow( queryV.get(0,0), 2)+ Math.pow(queryV.get(0,1), 2);
//			  
//			  double magnitudeQ = Math.sqrt(magnitudeQuery);
			  double magnitudeQ = magnitude(queryV.getArray()[0]);
			  
			  
//			  double magnitudeDocument = Math.pow( queryM.get(0,0), 2)+ Math.pow(queryM.get(0,1), 2);
//			  
//			  double magnitudeD = Math.sqrt(magnitudeDocument);
			  double magnitudeD = magnitude(queryM.getArray()[0]);
			  
			 // System.out.println("Doc id: " + i + " Magnitude: " + magnitudeD);
			  
//			 scores[i]=(( ( ( queryV.get(0,0) * queryM.get(0,0) ) +
//					 (queryV.get(0,1) * queryM.get(0,1) ) ) /
//					 ( magnitudeQ * magnitudeD) ));
			 
			 scores[i]= dotProduct(queryV.getArray()[0],queryM.getArray()[0])/
					 (magnitudeQ * magnitudeD);
			 
			  
		  }
		  //System.out.println(Arrays.toString(scores));
		  
		  ArrayList<Node> nodeHolder = new ArrayList<Node>();
		  
		  for( int x = 0; x < scores.length; x ++ ){
			  
			  //System.out.println(x + ":" + scores[x]);
			  
			  Node node = new Node( x+1, scores[x]);
			  
			  nodeHolder.add(node);
			  
		  }
		  
		  Collections.sort(nodeHolder);
		  HashMap<Integer, Double> queryResult = new HashMap<>();
//		  for( int i = 0; i < nodeHolder.size(); i++){
		  for( int i = 0; i < 10; i++){
			  queryResult.put(nodeHolder.get(i).key, nodeHolder.get(i).score);
			 // System.out.println( "id:" + nodeHolder.get(i).key + ", score:" + nodeHolder.get(i).score);
			 
		  }
		  
		  
		  return queryResult;
		  
		//return Arrays.toString(scores);
		  
	  }
	  
	  private double dotProduct(double [] a, double [] b){
		  
		  double c = 0.0;
		  for( int i = 0; i < a.length; i++ ){
			  c += ( a[i] * b[i]);
		  }
		  
		  return c;
		  
	  }
	  
	  private double magnitude( double [] pos ){
		  
		  double acc = 0.0;
		  
		  for( int i = 0; i < pos.length; i++ ){
			  acc += Math.pow( pos[i], 2);
		  }
		  
		  return Math.sqrt(acc);
		  
	  }



	
	  
//	  private double sum( Matrix colMatrix ) {
//	    double sum = 0.0D;
//	    for ( int i = 0; i < colMatrix.getRowDimension(); i++ ) {
//	      sum += colMatrix.get(i, 0);
//	    }
//	    return sum;
//	  }
	  
//	  public static void main(String[] args){
//		  
//		  
//		 
//		  
//		  double[][] matrix = {{1, 0, 1,0,0}, 
//				  			   {1, 1, 0,0,0}, 
//				  			   {0, 1, 0,0,0},
//				  			   {0, 1, 1,0,0},
//				  			   {0, 0, 0,1,0},
//				  			   {0, 0, 1,1,0},
//				  			   {0, 0, 0,1,0},
//				  			   {0, 0, 0,1,1}}; 
//		  Matrix mat = new Matrix(matrix);
//		  LSI lsi = new LSI(mat, termList);
//		  
//		  lsi.addTerm();
//
//		  String[] test2 = {"die","dagger"};
//		  lsi.queryVector(test2);
//		  
//		 
//	  }
}

