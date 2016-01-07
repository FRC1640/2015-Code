package com.team1640;

public class Event {
	
	private String name = "Untitled";
	
	private MatchList matchList;
	
	private final int ERROR_EVENT_FILE_NOT_FOUND = 0;
	private final int ERROR_DATABASE_CONNECTION_NOT_FOUND = 1;
	
	public Event(String name) {
		this.name = name;
		matchList = new MatchList();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addMatch(Match match) {
		matchList.addMatch(match);
	}
	
	public void insertMatch(int matchNum, Match match) {
		matchList.addMatch(match);
	}
	
	public void setMatch(int matchNum, Match match) {
		matchList.setMatch(match, matchNum);
	}
	
	public void removeMatch(int matchNum) {
		matchList.removeMatch(matchNum);
	}
	
	public Match getMatch(int matchNum) {
		return matchList.getMatch(matchNum);
	}
	
	public int numOfMatches() {
		return matchList.numOfMatches();
	}
	
	public void save() {
		
	}
	
	public void open() {
		displayError(ERROR_EVENT_FILE_NOT_FOUND);
	}
	
	private void displayError(int errorId) {
		System.out.println("Event Error");
		if (errorId == ERROR_EVENT_FILE_NOT_FOUND) {
			System.out.println("-Event File Not Found");
		}
		else if (errorId == ERROR_DATABASE_CONNECTION_NOT_FOUND) {
			System.out.println("-Database Connection Not Found");
		}
		else {
			System.out.println("-Unknown Error");
		}
	}
}
