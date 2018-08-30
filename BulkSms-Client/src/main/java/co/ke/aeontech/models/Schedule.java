package co.ke.aeontech.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.aeontech.pojos.helpers.Days;
import co.ke.aeontech.pojos.helpers.ScheduleType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="schedule")
public class Schedule {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", unique = true)
	private String name;
	
	@Column(name="type", nullable = false)
	private ScheduleType type;
	
	@Column(name="date", nullable = true)
	private Date date;
	
	@Column(name="day_of_week", nullable = true)
	private Days dayOfWeek;
	
	@Column(name="day_of_month", nullable = true)
	private int dayOfMonth;
	
	@Column(name="cron_expression", nullable = true)
	private String cronExpression;
	
	@OneToOne(mappedBy = "schedule", 
	        cascade = CascadeType.ALL, orphanRemoval = true)
	private Text text;
		
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="org_schedules",
				inverseJoinColumns=@JoinColumn(name = "org_fk"),
				joinColumns=@JoinColumn(name = "schedule_fk"))
	private Organisation organisation;
	
	@ManyToMany
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="schedule_groups",
				inverseJoinColumns=@JoinColumn(name = "group_fk"),
				joinColumns=@JoinColumn(name = "schedule_fk"))
	private List<Group> groups = new ArrayList<Group>();//new
	
	public Schedule() {
		super();
	}
		
	/**
	 * Date/Daily Schedule sent on a particular date/time at a particular time to all
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param text
	 * @param organisation
	 */
	public Schedule(String name, ScheduleType type, Date date, 
			 Organisation organisation) {
		super();
		this.id = 0L;
		this.name = name;
		this.type =  type;
		this.date = date;
		this.organisation = organisation;
	}
	
	/**
	 * Date/Daily Schedule sent on a particular date at a particular time to particular 
	 * groups of contacts
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param text
	 * @param organisation
	 * @param groups
	 */
	public Schedule(String name, ScheduleType type, Date date, 
			Organisation organisation, List<Group> groups) {
		super();
		this.id = 0L;
		this.name = name;
		this.type = ScheduleType.DATE;
		this.date = date;
		this.organisation = organisation;
		this.groups = groups;
	}
	
	/**
	 * DayOfWeek Schedule sent on a particular day of week to all contacts
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param dayOfWeek
	 * @param cronExpression
	 * @param text
	 * @param organisation
	 */
	public Schedule(String name, Date date, Days dayOfWeek, 
			String cronExpression, Organisation organisation) {
		super();
		this.id = 0L;
		this.name = name;
		this.type = ScheduleType.WEEKLY;
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.cronExpression = cronExpression;
		this.organisation = organisation;
	}
	
	/**
	 * DayOfWeek Schedule sent on a particular day of week to grouped contacts
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param dayOfWeek
	 * @param cronExpression
	 * @param text
	 * @param organisation
	 * @param groups
	 */
	public Schedule(String name, Date date, Days dayOfWeek, String cronExpression,
			 Organisation organisation, List<Group> groups) {
		super();
		this.id = 0L;
		this.name = name;
		this.type = ScheduleType.WEEKLY;
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.cronExpression = cronExpression;
		this.organisation = organisation;
		this.groups = groups;
	}
	
	/**
	 * Monthly Schedule sent on a particular day of month to all contacts
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param dayOfMonth
	 * @param cronExpression
	 * @param text
	 * @param organisation
	 */
	public Schedule(String name, Date date, int dayOfMonth, String cronExpression,
			 Organisation organisation) {
		super();
		this.id = 0L;
		this.name = name;
		this.type = ScheduleType.MONTHLY;
		this.date = date;
		this.dayOfMonth = dayOfMonth;
		this.cronExpression = cronExpression;
		this.organisation = organisation;
	}
	
	/**
	 * Monthly Schedule sent on a particular day of month to grouped contacts
	 * @param name
	 * @param type
	 * @param time
	 * @param date
	 * @param dayOfMonth
	 * @param cronExpression
	 * @param text
	 * @param organisation
	 * @param groups
	 */
	public Schedule(String name, Date date, int dayOfMonth, String cronExpression,
			 Organisation organisation, List<Group> groups) {
		super();
		this.id = 0L;
		this.name = name;
		this.type = ScheduleType.MONTHLY;
		this.date = date;
		this.dayOfMonth = dayOfMonth;
		this.cronExpression = cronExpression;
		this.organisation = organisation;
		this.groups = groups;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text message) {
		this.text = message;
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
	
	@JsonIgnore
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	@JsonIgnore
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Schedule [name=")
				.append(name)
				.append(", type=")
				.append(type)
				.append(", date=")
				.append(date)
				.append(", cronExpression=")
				.append(cronExpression)
				.append("]");
		return builder.toString();
	}
}
