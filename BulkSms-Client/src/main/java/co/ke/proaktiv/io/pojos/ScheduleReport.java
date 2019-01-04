package co.ke.proaktiv.io.pojos;

import java.util.Set;

import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Schedule;
import co.ke.proaktiv.io.pojos.response.Response;

public class ScheduleReport extends Response{
	private Schedule schedule;
	private Set<Group_> groups;
	private ChargesReport charges;
	public ScheduleReport() {
		super();
	}
	public ScheduleReport(int code, String title, String message) {
		super(code, title, message);
	}
	public ScheduleReport(int code, String title, String message, Schedule schedule, Set<Group_> groups,
			ChargesReport charges) {
		super(code, title, message);
		this.schedule = schedule;
		this.groups = groups;
		this.charges = charges;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public Set<Group_> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group_> groups) {
		this.groups = groups;
	}
	public ChargesReport getCharges() {
		return charges;
	}
	public void setCharges(ChargesReport charges) {
		this.charges = charges;
	}
}
