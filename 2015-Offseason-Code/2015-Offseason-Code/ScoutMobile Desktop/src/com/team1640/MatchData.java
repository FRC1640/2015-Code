package com.team1640;

public class MatchData {
	private Object[] data;
	
	public static final int TEAM_NUM = 0, CANS = 1, TOTES = 2, IS_PRESENT = 3, SCOUT_NAME = 4; // Customize data here
	
	public MatchData() {
		data = new Object[5];
		data[TEAM_NUM] = 0;
	}
	
	public void set(Object element, int type) {
		data[type] = element;
	}
	
	public Object get(int type) {
		return data[type];
	}
	
	public void remove(int type) {
			data[type] = null;
	}
}
