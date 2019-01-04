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
	
	@Column(name="name", unique = true, nullable = false)
	private String name;
	
	@Column(name="sender_id", nullable = false)
	private String senderId;
	
	@Column(name="created_by", nullable = false)
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
	public Schedule(Long id, String name, String senderId, String createdBy, ScheduleType type, Date date, 
			Day dayOfWeek, int dayOfMonth, String cronExpression, Set<Group_> groups) {
		super();
		this.id = id;
		this.name = name;
		this.senderId = senderId;
		this.createdBy = createdBy;
		this.type = type;
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.dayOfMonth = dayOfMonth;
		this.cronExpression = cronExpression;
		this.groups = groups;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSenderId() {
		return senderId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public ScheduleType getType() {
		return type;
	}
	public Date getDate() {
		return date;
	}
	public Day getDayOfWeek() {
		return dayOfWeek;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public String getCronExpression() {
		return cronExpression;
	}	
	@JsonIgnore
	public Set<Group_> getGroups() {
		return groups;
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
				.append(", senderId=")
				.append(senderId)
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
