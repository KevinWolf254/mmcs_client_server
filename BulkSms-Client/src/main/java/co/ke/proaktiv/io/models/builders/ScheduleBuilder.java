package co.ke.proaktiv.io.models.builders;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.helpers.Day;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;

public final class ScheduleBuilder {

	private Long id;
	private String name;
	private String senderId;
	private String createdBy;
	private ScheduleType type;
	private Date date;
	private Day dayOfWeek;
	private int dayOfMonth;
	private String cronExpression;
	private Set<Group_> groups = new HashSet<Group_>();
	
	public ScheduleBuilder setId(Long id) {
		this.id = id;
		return this;
	}
	public ScheduleBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public ScheduleBuilder setSenderId(String senderId) {
		this.senderId = senderId;
		return this;
	}
	public ScheduleBuilder setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}
	public ScheduleBuilder setType(ScheduleType type) {
		this.type = type;
		return this;
	}
	public ScheduleBuilder setDate(Date date) {
		this.date = date;
		return this;
	}
	public ScheduleBuilder setDayOfWeek(Day dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}
	public ScheduleBuilder setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		return this;
	}
	public ScheduleBuilder setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
		return this;
	}
	public ScheduleBuilder setGroups(Set<Group_> groups) {
		this.groups = groups;
		return this;
	}
	public Schedule build() {
		return new Schedule(id, name, senderId, createdBy, type, date, dayOfWeek, 
				dayOfMonth, cronExpression, groups);
	}
}
