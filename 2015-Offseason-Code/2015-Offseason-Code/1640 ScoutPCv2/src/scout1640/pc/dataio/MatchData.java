package scout1640.pc.dataio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONObject;

import scout1640.pc.core.Controller;

public class MatchData {
	
	private Controller controller;
	
	public static final int TYPE_DATA_FROM_QR = 0;
	public static final int TYPE_DATA_FROM_RECORD = 1;
	public static final int TYPE_DATA_FROM_IMPORT = 2;
	
	public static final int POSITION_R1 = 0;
	public static final int POSITION_R2 = 1;
	public static final int POSITION_R3 = 2;
	public static final int POSITION_B1 = 3;
	public static final int POSITION_B2 = 4;
	public static final int POSITION_B3 = 5;
	
	public static final int MATCH_PRACTICE = 1;
	public static final int MATCH_QUALIFIER = 2;
	public static final int MATCH_ELIMINATION = 3;
	
	public static final int STEP_CAN_LF = 0x1;					// 0001 - 1
	public static final int STEP_CAN_LC = 0x2;					// 0010 - 2
	public static final int STEP_CAN_RC = 0x4;					// 0100 - 4
	public static final int STEP_CAN_RF = 0x8;					// 1000 - 8
	
	public static final int POSITION_LEFT = 0x1;  				// 001 - 1
	public static final int POSITION_MID = 0x2;   				// 010 - 2
	public static final int POSITION_RIGHT = 0x4; 				// 100 - 4
	
	private static final int AUTON_MOVED = 0x1;					// 00 0000 0000 0001 - 1
	private static final int AUTON_STEP_CANS = 0x1E;			// 00 0000 0001 1110 - 0x1E
	private static final int AUTON_FIELD_TOTES = 0xE0;			// 00 0000 1110 0000 - 0xE0
	private static final int AUTON_FIELD_CANS = 0x700;			// 00 0111 0000 0000 - 0x700
	private static final int AUTON_CAN_WAR = 0x3FFF;			// 11 1111 1111 1111 - 0x3FFF
	
	// private static final int AUTON_SHIFT_MOVED = 0;			
	private static final int AUTON_SHIFT_STEP_CANS = 1;		
	private static final int AUTON_SHIFT_FIELD_TOTES = 5;	
	private static final int AUTON_SHIFT_FIELD_CANS = 8;
	private static final int AUTON_SHIFT_CAN_WAR = 11;
	
	private static final int TELEOP_STEP_CANS = 0xF;			// 0000 0000 0000 1111
	private static final int TELEOP_KNOCK_OUTS = 0xF0;			// 0000 0000 1111 0000
	private static final int TELEOP_FUMBLES = 0xF00;			// 0000 1111 0000 0000
	private static final int TELEOP_STACK_COUNT = 0xF000;		// 1111 0000 0000 0000
	
	// private static final int TELEOP_SHIFT_STEP_CANS = 0;
	private static final int TELEOP_SHIFT_KNOCK_OUTS = 4;
	private static final int TELEOP_SHIFT_FUMBLES = 8;
	private static final int TELEOP_SHIFT_STACK_COUNT = 12;
	
	private static final int STACK_ELEMENTS = 0x3F;				// 0 0000 0011 1111
	private static final int STACK_CAN = 0x40;					// 0 0000 0100 0000
	private static final int STACK_NOODLE = 0x80;				// 0 0000 1000 0000
	private static final int STACK_LANDFILL = 0x100;			// 0 0001 0000 0000
	private static final int STACK_HP = 0x200;					// 0 0010 0000 0000
	private static final int STACK_CAN_POS = 0x0;				// 1 1100 0000 0000
	
	private static final int STACK_SHIFT_CAN_POS = 10;
	
	private static final String[] csvColumns = {
		"scout initials",
		"alliance station",
		"team number",
		"match type",
		"match number",
		"played",
		
		"auton stack size",
		"auton moved",
		"auton step cans",
		"auton totes",
		"auton cans",
		"auton can war",
		
		"teleop step cans",
		"teleop KOs",
		"teleop fumbles",
		"number of stacks",
		
		"s1 totes",
		"s1 can",
		"s1 noodle",
		"s1 origin",
		"s1 can pos",
		
		"s2 totes",
		"s2 can",
		"s2 noodle",
		"s2 origin",
		"s2 can pos",
		
		"s3 totes",
		"s3 can",
		"s3 noodle",
		"s3 origin",
		"s3 can pos",
		
		"s4 totes",
		"s4 can",
		"s4 noodle",
		"s4 origin",
		"s4 can pos",
		
		"s5 totes",
		"s5 can",
		"s5 noodle",
		"s5 origin",
		"s5 can pos",
		
		"s6 totes",
		"s6 can",
		"s6 noodle",
		"s6 origin",
		"s6 can pos"
		
		/*
		"final score",
		"final auton score",
		"final tote score",
		"final can score",
		"final noodle score",
		"ranking",
		"average"
		*/
	};
	
	// General Information
	private int fieldPosition;
	private String scoutInitials;
	private int matchNumber;
	private int teamNumber;
	private int matchType;
	private boolean noShow;
	private String event;
	
	// Autonomous Information
	private boolean moved;
	private int autonRetrievedCans;
	private int movedTotes;
	private int movedCans;
	private int autonStack;
	private int autonData;
	
	// Teleop Information
	private int teleopRetrievedCans;
	private int kos;
	private int fumbles;
	private int teleopData;
	private ArrayList<Integer> stacks;
	
	// Scoring & Ranking
	private int finalAutonScore;
	private int finalToteScore;
	private int finalCanScore;
	private int finalNoodleScore;
	private int rank;
	private int average;
	
	public MatchData (Controller c) {
		controller = c;
		
		event = c.getEventName();
		
		fieldPosition = -1;
		scoutInitials = "";
		matchNumber = 0;
		teamNumber = -1;
		matchType = -1;
		noShow = true;
		
		moved = false;
		autonRetrievedCans = 0;
		movedTotes = 0;
		movedCans = 0;
		autonStack = 0;
		autonData = 0;
		
		teleopRetrievedCans = 0;
		kos = -1;
		fumbles = -1;
		teleopData = 0;
		stacks = new ArrayList<Integer>();
		
		average = 0;
		finalAutonScore = 0;
		finalToteScore = 0;
		finalCanScore = 0;
		finalNoodleScore = 0;
		rank = 0;
	}
	
	public static MatchData[] matchDataFromRecord (Controller c, String eventName) {
		
		LinkedList<MatchData> matchList = new LinkedList<MatchData>();
		MatchData md;
		ResultSet rs = c.executeSQLQuery("SELECT * FROM `" + eventName + "`;");
		if (rs == null) { c.printToConsole("UNABLE TO CREATE MATCH DATA OBJECT FROM RECORD"); return null; }
		try {
			while (rs.next()) {
				md = new MatchData(c);
				md.setFieldPosition((rs.getInt("alliance station")));
				md.setScoutInitials(rs.getString("scout initials"));
				md.setMatchNumber(rs.getInt("match number"));
				md.setTeamNumber(rs.getInt("team number"));
				md.setMatchType(rs.getInt("match type"));
				md.setTeamPlayed(rs.getInt("played") != 0);
				md.setAutonData(rs.getInt("auton data"));
				md.setAutonStack(rs.getInt("auton stack"));
				md.setAverage(rs.getInt("average"));
				md.setFinalAutonScore(rs.getInt("final auton score"));
				md.setFinalToteScore(rs.getInt("final tote score"));
				md.setFinalCanScore(rs.getInt("final can score"));
				md.setFinalNoodleScore(rs.getInt("final noodle score"));
				md.setRank(rs.getInt("ranking"));
				md.setTeleopData(rs.getInt("teleop data"));
				md.addStack(rs.getInt("stack 1"));
				md.addStack(rs.getInt("stack 2"));
				md.addStack(rs.getInt("stack 3"));
				md.addStack(rs.getInt("stack 4"));
				md.addStack(rs.getInt("stack 5"));
				md.addStack(rs.getInt("stack 6"));
				matchList.add(md);
			}
		} catch (SQLException e) {
			c.printToConsole("Unable to create CSV record");
		}
		
		return matchList.toArray(new MatchData[matchList.size()]);
		
	}
	
	public static MatchData[] matchDataFromJSONSet (Controller c, JSONObject jo) {
		LinkedList<MatchData> matchList = new LinkedList<MatchData>();
		for (String s : JSONObject.getNames(jo)) {
			JSONObject ro = jo.getJSONObject(s);
			MatchData md = new MatchData(c);
			for (String s2 : JSONObject.getNames(ro)) {
				switch (s2) {
					case "uid": { /* do nothing */ } break;
					case "event": { /* uh...  */ } break;
					case "team number": { md.setTeamNumber(ro.getInt(s2)); } break;
					case "match number": { md.setMatchNumber(ro.getInt(s2)); } break;
					case "scout initials": { md.setScoutInitials(ro.getString(s2)); } break;
					case "match type": { md.setMatchType(ro.getInt(s2)); } break;
					case "alliance station": { md.setMatchType(ro.getInt(s2)); } break;
					case "played": { md.setTeamPlayed(ro.getInt(s2) != 0); } break;
					case "auton stack": { md.setAutonStack(ro.getInt(s2)); } break;
					case "auton data": { md.setAutonData(ro.getInt(s2)); } break;
					case "teleop data": { md.setTeleopData(ro.getInt(s2)); } break;
					case "final score": { /* do nothing */ } break;
					case "final auton score": { md.setFinalAutonScore(ro.getInt(s2)); } break;
					case "final tote score": { md.setFinalToteScore(ro.getInt(s2)); } break;
					case "final can score": { md.setFinalCanScore(ro.getInt(s2)); } break;
					case "final noodle score": { md.setFinalNoodleScore(ro.getInt(s2)); } break;
					case "ranking": { md.setRank(ro.getInt(s2)); } break;
					case "average": { md.setAverage(ro.getInt(s2)); } break;
					case "stack count": { /* do nothing */ } break;
					case "stack 1": { md.addStack(ro.getInt(s2)); } break;
					case "stack 2": { md.addStack(ro.getInt(s2)); } break;
					case "stack 3": { md.addStack(ro.getInt(s2)); } break;
					case "stack 4": { md.addStack(ro.getInt(s2)); } break;
					case "stack 5": { md.addStack(ro.getInt(s2)); } break;
					case "stack 6": { md.addStack(ro.getInt(s2)); } break;
					case "stack 7": { md.addStack(ro.getInt(s2)); } break;
					case "stack 8": { md.addStack(ro.getInt(s2)); } break;
					case "stack 9": { md.addStack(ro.getInt(s2)); } break;
					case "stack 10": { md.addStack(ro.getInt(s2)); } break;
					
					default: { c.printToConsole("unknown data column: " + s2); } break;
				}
				
			}
			matchList.add(md);
		}
		
		return matchList.toArray(new MatchData[matchList.size()]);
	}
	
	public static MatchData matchDataFromJSON (Controller c, String js) {
		MatchData md = new MatchData(c);
		JSONObject jo = new JSONObject(js);
		
		String[] keys = JSONObject.getNames(jo);
		for (String key : keys) {
			switch (key) {
			case "c": md.setFieldPosition(jo.getInt("c")); break;
			case "i": md.setScoutInitials(jo.getString("i")); break;
			case "m": md.setMatchNumber(jo.getInt("m")); break;
			case "t": md.setTeamNumber(jo.getInt("t")); break;
			case "mt": md.setMatchType(jo.getInt("mt")); break;
			case "ns": md.setTeamPlayed(jo.getInt("ns") == 0); break;
			case "a": md.setAutonData(jo.getInt("a")); break;
			case "as": md.setAutonStack(jo.getInt("as")); break;
			case "av": md.setAverage(jo.getInt("av")); break;
			case "fa": md.setFinalAutonScore(jo.getInt("fa")); break;
			case "ft": md.setFinalToteScore(jo.getInt("ft")); break;
			case "fc": md.setFinalCanScore(jo.getInt("fc")); break;
			case "fn": md.setFinalNoodleScore(jo.getInt("fn")); break;
			case "r": md.setRank(jo.getInt("r")); break;
			default: break;
			}
		}
		
		JSONObject to = jo.getJSONObject("tp");
		md.setTeleopData(to.getInt("td"));
		keys = JSONObject.getNames(to);
		for (int i = 1; i < keys.length; i++) {
			md.addStack(to.getInt(keys[i]));
		}
		
		return md;
	}
	
	public void setEventName (String eventName) { this.event = eventName; }
	
	public void setFieldPosition (int fieldPosition) { this.fieldPosition = fieldPosition; }
	
	public void setScoutInitials (String scoutInitials) { this.scoutInitials = scoutInitials; }
	
	public void setMatchNumber (int matchNumber) { this.matchNumber = matchNumber; }
	
	public void setTeamNumber (int teamNumber) { this.teamNumber = teamNumber; }
	
	public void setMatchType (int matchType) { this.matchType = matchType; }
	
	public void setTeamPlayed (boolean teamPlayed) { noShow = !teamPlayed; }
	
	/*
	public void setAutonMoved (boolean moved) { this.moved = moved; }
	
	public void setAutonRetrievedCans (int retrievedCans) { this.autonRetrievedCans = retrievedCans; }
	
	public void setAutonMovedTotes (int movedTotes) { this.movedTotes = movedTotes; }
	
	public void setAutonMovedCans (int movedCans) { this.movedCans = movedCans; }
	*/
	
	public void setAutonData (int autonData) {
		this.autonData = autonData;
		moved = ((autonData & AUTON_MOVED) != 0);
		autonRetrievedCans = (autonData & AUTON_STEP_CANS) >> AUTON_SHIFT_STEP_CANS;
		movedTotes = (autonData & AUTON_FIELD_TOTES) >> AUTON_SHIFT_FIELD_TOTES;
		movedCans = (autonData & AUTON_FIELD_CANS) >> AUTON_SHIFT_FIELD_CANS;
	}
	
	public void setAutonStack (int count) { autonStack = count; }
	
	/*
	public void setTeleopRetrievedCans (int retrievedCans) { this.teleopRetrievedCans = retrievedCans; }
	
	public void setKOs (int KOs) { this.kos = KOs; }
	
	public void setFumbles (int fumbles) { this.fumbles = fumbles; }
	*/
	
	public void setTeleopData (int teleopData) {
		this.teleopData = teleopData;
		teleopRetrievedCans = (teleopData & TELEOP_STEP_CANS);
		kos = (teleopData & TELEOP_KNOCK_OUTS) >> TELEOP_SHIFT_KNOCK_OUTS;
		fumbles = (teleopData & TELEOP_FUMBLES) >> TELEOP_SHIFT_FUMBLES;
	}
	
	public void addStack (int stackData) { stacks.add(stackData); }
	
	public void setAverage (int avg) { average = avg; }
	
	public void setFinalAutonScore (int score) { finalAutonScore = score; }
	
	public void setFinalToteScore (int score) { finalToteScore = score; }
	
	public void setFinalCanScore (int score) { finalCanScore = score; }
	
	public void setFinalNoodleScore (int score) { finalNoodleScore = score; }
	
	public void setRank (int rank) { this.rank = rank; }
	
	public String getEventName () { return this.event; }
	
	public String getFieldPosition () {
		switch (fieldPosition) {
			case POSITION_R1: return "RED 1";
			case POSITION_R2: return "RED 2";
			case POSITION_R3: return "RED 3";
			case POSITION_B1: return "BLUE 1";
			case POSITION_B2: return "BLUE 2";
			case POSITION_B3: return "BLUE 3";
			default: return "INVALID FIELD POSITION";
		}
	}
	
	public String getScoutInitials () { return scoutInitials; }
	
	public int getMatchNumber () { return matchNumber; }
	
	public int getTeamNumber () { return teamNumber; }
	
	public String getMatchType () { 
		switch (matchType) {
			case MATCH_PRACTICE: return "PRACTICE MATCH";
			case MATCH_QUALIFIER: return "QUALIFICATION MATCH";
			case MATCH_ELIMINATION: return "ELIMINATION MATCH";
			default: return "INVALID MATCH TYPE";
		}
	}
	
	public boolean getRobotPlayed () { return !noShow; }
	
	public boolean getAutonomousMoved () { return moved; }
	
	public boolean getAutonomousRetrievedCans (int stepCan) {  return (autonRetrievedCans & stepCan) != 0; }
	
	public String getAutonStepCansString () {
		String sc = "";
		
		sc += (getAutonomousRetrievedCans(STEP_CAN_LF)) ? "LF " : "";
		sc += (getAutonomousRetrievedCans(STEP_CAN_LC)) ? "LC " : "";
		sc += (getAutonomousRetrievedCans(STEP_CAN_RC)) ? "RC " : "";
		sc += (getAutonomousRetrievedCans(STEP_CAN_RF)) ? "RF " : "";
		
		return sc;
	}
	
	public boolean getMovedAutonomousTotes (int position) { return (movedTotes & position) != 0; }
	
	public String getAutonToteString () {
		String ts = "";
		
		ts += (getMovedAutonomousTotes(POSITION_LEFT)) ? "L " : "";
		ts += (getMovedAutonomousTotes(POSITION_MID)) ? "M " : "";
		ts += (getMovedAutonomousTotes(POSITION_RIGHT)) ? "R " : "";
		
		return ts;
	}
	
	public boolean getMovedAutonomousCans (int position) { return (movedCans & position) != 0; }
	
	public String getAutonCanString () {
		String cs = "";
		
		cs += (getMovedAutonomousCans(POSITION_LEFT)) ? "L " : "";
		cs += (getMovedAutonomousCans(POSITION_MID)) ? "M " : "";
		cs += (getMovedAutonomousCans(POSITION_RIGHT)) ? "R " : "";
		
		return cs;
	}
	
	public int getAutonStack () { return autonStack; }
	
	public int getAutonCanWar () { return ((autonData & (AUTON_CAN_WAR << AUTON_SHIFT_CAN_WAR)) >> AUTON_SHIFT_CAN_WAR); }
	
	public boolean getTeleopRetrievedCans (int stepCan) { return (teleopRetrievedCans & stepCan) != 0; }
	
	public String getTeleopStepCansString () {
		String sc = "";
		
		sc += (getTeleopRetrievedCans(STEP_CAN_LF)) ? "LF " : "";
		sc += (getTeleopRetrievedCans(STEP_CAN_LC)) ? "LC " : "";
		sc += (getTeleopRetrievedCans(STEP_CAN_RC)) ? "RC " : "";
		sc += (getTeleopRetrievedCans(STEP_CAN_RF)) ? "RF " : "";
		
		return sc;
	}
	
	public int getKOs () { return kos; }
	
	public int getFumbles () { return fumbles; }
	
	public int getNumberOfStacks () { return stacks.size(); }
	
	public int getAltNumberOfStacks () { return (teleopData & TELEOP_STACK_COUNT) >> TELEOP_SHIFT_STACK_COUNT; }
	
	public String getStackTotesString (int stackNum) {
		if (stackNum < 0 || stackNum >= stacks.size()) { return "n/a"; }
		int stackData = stacks.get(stackNum);
		return getZeroPaddedString(6,Integer.toBinaryString(stackData & 0x3F));
	}
	
	private String getZeroPaddedString(int pad, String s) {
		if (pad < s.length()) { return s.substring(s.length()-pad); }
		String paddedString = s;
		for (int i = s.length(); i < pad; i++) { paddedString = "0" + paddedString; }
		return paddedString;
	}
	
	public boolean getStackNoodle (int stackNum) {
		if (stackNum >= 0 && stackNum < stacks.size()) { return (stacks.get(stackNum) & STACK_NOODLE) != 0; }
		return false;
	}
	
	public boolean getStackCan (int stackNum) {
		if (stackNum >= 0 && stackNum < stacks.size()) { return (stacks.get(stackNum) & STACK_CAN) != 0; }
		return false;
	}
	
	public String getToteOrigin (int stackNum) {
		if (stackNum >= 0 && stackNum < stacks.size()) { 
			String s = "";
			if (((stacks.get(stackNum) & STACK_LANDFILL) != 0) && ((stacks.get(stackNum) & STACK_HP) != 0)) {
				s = "LF & HP"; 
			}
			else if ((stacks.get(stackNum) & STACK_LANDFILL) != 0) { s = "LF"; }
			else if ((stacks.get(stackNum) & STACK_HP) != 0) { s = "HP"; }
			else { s = "N/A"; }
			return s;
		} 
		return "INVALID TOTE ORIGIN";
	}
	
	public int getStackCanPosition (int stackNum) {
		if (stackNum >= 0 && stackNum < stacks.size()) {
			return (stacks.get(stackNum) & STACK_CAN_POS) >> STACK_SHIFT_CAN_POS;
		}
		return -1;
	}
	
	public int getStackSize (int stackNum) {
		if (stackNum >= 0 && stackNum < stacks.size()) { 
			int sd = stacks.get(stackNum) & STACK_ELEMENTS;
			if (sd == 0) { return 0; }
			else { return (int) (Math.log(Integer.highestOneBit(sd))/Math.log(2)) + 1; }
		}
		return 0;
	}
	
	public int getFinalScore () { return finalAutonScore + finalToteScore + finalCanScore + finalNoodleScore; }
	
	public int getFinalAutonScore () { return finalAutonScore; }
	
	public int getFinalToteScore () { return finalToteScore; }
	
	public int getFinalCanScore () { return finalCanScore; }
	
	public int getFinalNoodleScore () { return finalNoodleScore; }
	
	public int getRank () { return rank; }
	
	public int getAverage () { return average; }
	
	public String getCSVHeadings () {
		String out = "";
		for (String s : csvColumns) { out += s + ","; } 
		return out + "\n";
	}
	
	public String generateCSVLine () {
		String csv = "";
		
		// Match Data
		csv += this.getScoutInitials() + ",";
		csv += this.getFieldPosition() + ",";
		csv += this.getTeamNumber() + ",";
		csv += this.getMatchType() + ",";
		csv += this.getMatchNumber() + ",";
		csv += ((this.getRobotPlayed()) ? "played" : "no show") + ",";
		
		// Autonomous Data
		csv += this.getAutonStack() + ",";
		csv += this.getAutonomousMoved() + ",";
		csv += this.getAutonStepCansString() + ",";
		csv += this.getAutonToteString() + ",";
		csv += this.getAutonCanString() + ",";
		csv += this.getAutonCanWar() + ",";
		
		// Teleop Data
		csv += this.getTeleopStepCansString() + ",";
		csv += this.getKOs() + ",";
		csv += this.getFumbles() + ",";
		csv += this.getNumberOfStacks() + ",";
		
		// Stack Data
		for (int i = 0; i < 6; i++) {
			csv += this.getStackTotesString(i) + ",";
			csv += ((this.getStackCan(i)) ? 1 : 0) + ",";
			csv += ((this.getStackNoodle(i)) ? 1 : 0) + ",";
			csv += this.getToteOrigin(i) + ",";
			csv += this.getStackCanPosition(i) + ",";
		}
		
		/*
		// Scoring Data
		csv += this.getFinalScore() + ",";
		csv += this.getFinalAutonScore() + ",";
		csv += this.getFinalToteScore() + ",";
		csv += this.getFinalCanScore() + ",";
		csv += this.getFinalNoodleScore() + ",";
		csv += this.getRank() + ",";
		csv += this.getAverage() + ",";
		*/
		
		return csv + "\n";
	}
	
	public String generateSQLInsertQuery () {
		return generateSQLInsertQuery(controller.getEventName());
	}
	
	public String generateSQLInsertQuery (String tableName) {
		String sqlQuery = "";
		
		sqlQuery += "INSERT INTO `" + tableName + "` ("
				+ "`event`,"
				+ "`team number`,"
				+ "`match number`,"
				+ "`scout initials`,"
				+ "`match type`,"
				+ "`alliance station`,"
				+ "`played`,"
				+ "`auton stack`,"
				+ "`auton data`,"
				+ "`teleop data`,"
				+ "`final score`,"
				+ "`final auton score`,"
				+ "`final tote score`,"
				+ "`final can score`,"
				+ "`final noodle score`,"
				+ "`ranking`,"
				+ "`average`,"
				+ "`stack count`";
				
		for (int i = 0; i < stacks.size(); i++) { sqlQuery += ",`stack " + (i+1) + "`"; }
		
		sqlQuery += ") VALUES ("
				+ "\"" + tableName + "\","
				+ this.getTeamNumber() + ","
				+ this.getMatchNumber() + ","
				+ "\"" + this.getScoutInitials() + "\","
				+ this.matchType + ","
				+ this.fieldPosition + ","
				+ ((this.getRobotPlayed()) ? 1 : 0) + ","
				+ this.getAutonStack() + ","
				+ this.autonData + ","
				+ this.teleopData + ","
				+ this.getFinalScore() + ","
				+ this.getFinalAutonScore() + ","
				+ this.getFinalToteScore() + ","
				+ this.getFinalCanScore() + ","
				+ this.getFinalNoodleScore() + ","
				+ this.getRank() + ","
				+ this.getAverage() + ","
				+ this.getAltNumberOfStacks();
				
		for (int i = 0; i < stacks.size(); i++) {
			sqlQuery += "," + stacks.get(i);
		}
				
		sqlQuery += ");";
		
		return sqlQuery;
	}
	
	@Override
	public String toString () {
		String s = "";
		s += "Scout's initials: " + this.getScoutInitials() + "\n";
		s += "Team Number: " + this.getTeamNumber() + "\n";
		s += "Match Number: " + this.getMatchNumber() + "\n";
		s += "Match Type: " + this.getMatchType() + "\n";
		s += "Field Position: " + this.getFieldPosition() + "\n";
		s += "Made an appearance: " + this.getRobotPlayed() + "\n";
		s +=  "--------------------------------------------------------" + "\n";
		s += "Autonomous" + "\n";
		s += "\n";
		s += "\t" + "Moved: " + this.getAutonomousMoved() + "\n";
		s += "\t" + "Stacked " + this.getAutonStack() + " totes. \n";
		s += "\t" + "LF Step Can: " + this.getAutonomousRetrievedCans(STEP_CAN_LF) + "\n";
		s += "\t" + "LC Step Can: " + this.getAutonomousRetrievedCans(STEP_CAN_LC) + "\n";
		s += "\t" + "RC Step Can: " + this.getAutonomousRetrievedCans(STEP_CAN_RC) + "\n";
		s += "\t" + "RF Step Can: " + this.getAutonomousRetrievedCans(STEP_CAN_RF) + "\n";
		s += "\n";
		s += "\t" + "Left Tote: " + this.getMovedAutonomousTotes(POSITION_LEFT) + "\n";
		s += "\t" + "Middle Tote: " + this.getMovedAutonomousTotes(POSITION_MID) + "\n";
		s += "\t" + "Right Tote: " + this.getMovedAutonomousTotes(POSITION_RIGHT) + "\n";
		s += "\n";
		s += "\t" + "Left Can: " + this.getMovedAutonomousCans(POSITION_LEFT) + "\n";
		s += "\t" + "Middle Can: " + this.getMovedAutonomousCans(POSITION_MID) + "\n";
		s += "\t" + "Right Can: " + this.getMovedAutonomousCans(POSITION_RIGHT) + "\n";
		s += "\n";
		s += "\t" + "Can War with: " + this.getAutonCanWar() + "\n";
		s += "\n";
		s += "Teleop" + "\n";
		s += "\n";
		s += "\t" + "LF Step Can: " + this.getTeleopRetrievedCans(STEP_CAN_LF) + "\n";
		s += "\t" + "LC Step Can: " + this.getTeleopRetrievedCans(STEP_CAN_LC) + "\n";
		s += "\t" + "RC Step Can: " + this.getTeleopRetrievedCans(STEP_CAN_RC) + "\n";
		s += "\t" + "RF Step Can: " + this.getTeleopRetrievedCans(STEP_CAN_RF) + "\n";
		s += "\n";
		s += "\t" + "KOs: " + this.getKOs() + "\n";
		s += "\t" + "Fumbles: " + this.getFumbles() + "\n";
		s += "\n";
		
		for (int i = 0; i < this.getAltNumberOfStacks(); i++) {
			s += "\t" + "Stack " + (i+1) + "\n";
			s += "\t\t" + "Source #: " + this.stacks.get(i) + "\n";
			s += "\t\t" + "Size: " + this.getStackSize(i) + "\n";
			s += "\t\t" + "Can: " + this.getStackCan(i) + "\n";
			s += "\t\t" + "Noodle: " + this.getStackNoodle(i) + "\n";
			s += "\t\t" + "Tote Source: " + this.getToteOrigin(i) + "\n";
			s += "\t\t" + "Can position on stack: " + this.getStackCanPosition(i) + "\n";
		}
		
		s += "\n";
		s += "Final Score: " + this.getFinalScore() + "\n";
		s += "Final Auton Score: " + this.getFinalAutonScore() + "\n";
		s += "Final Tote Score: " + this.getFinalToteScore() + "\n";
		s += "Final Can Score: " + this.getFinalCanScore() + "\n";
		s += "Final Noodle Score: " + this.getFinalNoodleScore() + "\n";
		s += "Rank: " + this.getRank() + "\n";
		s += "Average: " + this.getAverage() + "\n";
		
		return s;
	}
	
}
