package wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class WikiBean {
	private final String pathName = "/Users/AbhiKemp/Desktop/KPT Project Work/August 3rd/Chinmay/WikiProjectIntegration-2";
	private int id;
	private String title;
	private String answerBody;
	private String fullAnswerBody;
	private HashMap<Integer, String> spoilers;
	private ArrayList<String> setAnswerBody;
	private HashMap<Integer, String> titles;
	public HashMap<Integer, String> getTitles() {
		return titles;
	}
	public void setTitles(HashMap<Integer, String> titles) {
		this.titles = titles;
	}
	public HashMap<Integer, String> getVsmSpoilers() {
		return vsmSpoilers;
	}
	public void setVsmSpoilers(HashMap<Integer, String> vsmSpoilers) {
		this.vsmSpoilers = vsmSpoilers;
	}

	private HashMap<Integer, String> vsmSpoilers;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	private String query;
	
	public ArrayList<String> getSetAnswerBody() {
		return setAnswerBody;
	}
	public void setSetAnswerBody(ArrayList<String> setAnswerBody) {
		this.setAnswerBody = setAnswerBody;
	}
	public HashMap<Integer, String> getSpoilers() {
		return spoilers;
	}
	public void setSpoilers(HashMap<Integer, String> spoilers) {
		this.spoilers = spoilers;
	}

	HashMap<Integer, Double> resultQuery;
	HashMap<Integer, String> titleList;
	public LinkedHashMap<Integer, String> getTitleLinkedList() {
		return titleLinkedList;
	}
	public void setTitleLinkedList(LinkedHashMap<Integer, String> titleLinkedList) {
		this.titleLinkedList = titleLinkedList;
	}
	
	LinkedHashMap<Integer, String> titleLinkedList;
	private LinkedHashMap<Integer, String> vsmTitleLinkedList;

	
	
	
	
	
	public LinkedHashMap<Integer, String> getVsmTitleLinkedList() {
		return vsmTitleLinkedList;
	}
	public void setVsmTitleLinkedList(
			LinkedHashMap<Integer, String> vsmTitleLinkedList) {
		this.vsmTitleLinkedList = vsmTitleLinkedList;
	}
	public HashMap<Integer, Double> getResultQuery() {
		return resultQuery;
	}
	public void setResultQuery(HashMap<Integer, Double> resultQuery) {
		this.resultQuery = resultQuery;
		
	}
	public HashMap<Integer, String> getTitleList() {
		return titleList;
	}
	public void setTitleList(HashMap<Integer, String> titleList) {
		this.titleList = titleList;
	}
	public String getPathName() {
		return pathName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswerBody() {
		return answerBody;
	}
	public void setAnswerBody(String answerBody) {
		this.answerBody = answerBody;
	}
	public String getFullAnswerBody() {
		return fullAnswerBody;
	}
	public void setFullAnswerBody(String fullAnswerBody) {
		this.fullAnswerBody = fullAnswerBody;
	}


}