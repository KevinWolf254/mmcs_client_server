package co.ke.proaktiv.io.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import co.ke.proaktiv.io.pojos.helpers.Day;
import co.ke.proaktiv.io.pojos.helpers.ScheduleType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="schedule")
public class Schedule {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", unique = true)
	private String name;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="type", nullable = false)
	private ScheduleType type;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="day_of_week")
	private Day dayOfWeek;
	
	@Column(name="day_of_month")
	private int dayOfMonth;
	
	@Column(name="cron_expression")
	private String cronExpression;
	
	@OneToOne(mappedBy = "schedule", 
	        cascade = CascadeType.ALL, orphanRemoval = true)
	private Text text;
	
	@ManyToMany
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
			@JoinTable(name="group_schedule",
				inverseJoinColumns=@JoinColumn(name = "group_id"),
				joinColumns=@JoinColumn(name = "schedule_id"))
	private Set<Group_> groups = new HashSet<Group_>();
	
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
	 * @param groupAll; get the organization's group that has all the contacts. Every
	 * contact is added to this group
	 */
	public Schedule(String name, String createdBy, ScheduleType type, Date date) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.type =  type;
		this.date = date;
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
	 * @param groupAll; get the organization's group that has all the contacts. Every
	 * contact is added to this group
	 */
	public Schedule(String name, String createdBy, Date date, Day dayOfWeek, 
			String cronExpression) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.type = ScheduleType.WEEKLY;
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.cronExpression = cronExpression;
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
	 * @param groupAll; get the organization's group that has all the contacts. Every
	 * contact is added to this group
	 */
	public Schedule(String name, String createdBy, Date date, int dayOfMonth, 
			String cronExpression) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.type = ScheduleType.MONTHLY;
		this.date = date;
		this.dayOfMonth = dayOfMonth;
		this.cronExpression = cronExpression;
	}

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	@JsonIgnore
	public Set<Group_> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group_> groups) {
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
