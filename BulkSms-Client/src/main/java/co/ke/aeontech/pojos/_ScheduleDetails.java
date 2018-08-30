package co.ke.aeontech.pojos;

import java.util.Date;

import co.ke.aeontech.pojos.helpers.ScheduleStatus;
import co.ke.aeontech.pojos.helpers.ScheduleType;

public class _ScheduleDetails {

	private String name;	
	private ScheduleType type;
	private Date schedule;
	private Date nextFire;
	private Date lastFired;
	private ScheduleStatus status;
	public _ScheduleDetails() {
		super();
	}
	public _ScheduleDetails(String name, ScheduleType type, Date schedule, 
			Date nextFire, Date lastFired,	ScheduleStatus status) {
		super();
		this.name = name;
		this.type = type;
		this.schedule = schedule;
		this.nextFire = nextFire;
		this.lastFired = lastFired;
		this.status = status;
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
	public Date getSchedule() {
		return schedule;
	}
	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}
	public Date getNextFire() {
		return nextFire;
	}
	public void setNextFire(Date nextFire) {
		this.nextFire = nextFire;
	}
	public Date getLastFired() {
		return lastFired;
	}
	public void setLastFired(Date lastFired) {
		this.lastFired = lastFired;
	}
	public ScheduleStatus getStatus() {
		return status;
	}
	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}
	
}
