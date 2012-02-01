package edu.virusss8.extreminder.android;

public class DataBase {
	
	public DataBase() {
	}
	
	public DataBase(String name, int st_tablet, int everyXHours, long dbID) {
		super();
		this.name = name;
		this.st_tablet = st_tablet;
		this.everyXHours = everyXHours;
		this.dbID = dbID;
	}
	private String name;
	private int st_tablet;
	private int everyXHours;
	private long dbID;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSt_tablet() {
		return st_tablet;
	}
	public void setSt_tablet(int st_tablet) {
		this.st_tablet = st_tablet;
	}
	public int getEveryXHours() {
		return everyXHours;
	}
	public void setEveryXHours(int everyXHours) {
		this.everyXHours = everyXHours;
	}
	public long getDbID() {
		return dbID;
	}
	public void setDbID(long dbID) {
		this.dbID = dbID;
	}
}
