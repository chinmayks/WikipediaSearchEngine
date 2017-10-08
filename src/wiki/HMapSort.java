package wiki;
/* 
 * HMapSort.java 
 * 
 * Version: 1.0 06/23/2017
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.util.*;
/**
 * This method contains sort method to sort a hash map in a ascending manner
 * @author    : Chinmay Kumar Singh (cks9089)
 *
 */
public class HMapSort
{
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static HashMap sortHMap(HashMap map) { 
	       List query = new LinkedList(map.entrySet());
	      
	       Collections.sort(query, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });

	       
	       HashMap queryMap = new LinkedHashMap();
	       for (Iterator iterator = query.iterator(); iterator.hasNext();) {
	              Map.Entry entry = (Map.Entry) iterator.next();
	              queryMap.put(entry.getKey(), entry.getValue());
	       } 
	       return queryMap;
	  }
	
}