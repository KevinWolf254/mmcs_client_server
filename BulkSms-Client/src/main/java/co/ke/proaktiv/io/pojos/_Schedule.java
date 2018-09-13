package co.ke.proaktiv.io.pojos;

import java.util.Date;

import co.ke.aeontech.pojos.helpers.Days;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;

public class _Schedule {	
	private String name;	
	private String createdBy;
	private ScheduleType type;
	private Date date;
	private Days dayOfWeek;
	private int dayOfMonth;
	private String cronExpression;
	public _Schedule() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ScheduleType getType() {
		return type;
	}
	public void setType(ScheduleType type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Days getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Days dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
//	@Override
//	public String toString() {
//		return "_Schedule [name=" + name + ", type=" + type + ", date=" + date + ", dayOfWeek="
//				+ dayOfWeek + ", dayOfMonth=" + dayOfMonth + ", cronExpression=" + cronExpression + "]";
//	}	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
