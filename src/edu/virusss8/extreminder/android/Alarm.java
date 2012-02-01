package edu.virusss8.extreminder.android;

public class Alarm {
	
	private String name;
	private int st_tablet;
	private int repeat;
	private int hour;
	private int minute;
	private int day;
	private int month;
	private int year;
	private long dbID;
	
	public Alarm() {
	}

	public Alarm(String name, int st_tablet, int repeat, int hour, int minute,
			int day, int month, int year, long dbID) {
		super();
		this.name = name;
		this.st_tablet = st_tablet;
		this.repeat = repeat;
		this.hour = hour;
		this.minute = minute;
		this.day = day;
		this.month = month;
		this.dbID = dbID;
	}

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
	
	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public long getDbID() {
		return dbID;
	}

	public void setDbID(long dbID) {
		this.dbID = dbID;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public void decRepeat() {
		repeat--;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
