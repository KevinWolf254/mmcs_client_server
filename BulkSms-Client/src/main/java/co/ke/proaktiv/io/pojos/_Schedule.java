package co.ke.proaktiv.io.pojos;

import java.util.Date;

import co.ke.proaktiv.io.pojos.helpers.Day;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;

public class _Schedule {	
	private String name;	
	private String senderId;
	private String createdBy;
	private ScheduleType type;
	private Date date;
	private Day dayOfWeek;
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
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
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
	public Day getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Day dayOfWeek) {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
