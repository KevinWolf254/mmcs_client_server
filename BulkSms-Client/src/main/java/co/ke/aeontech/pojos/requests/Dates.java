package co.ke.aeontech.pojos.requests;

import java.util.Date;

public class Dates {

	private String orgName;
	private Date from;
	private Date to;
	public Dates() {
		super();
	}
	public Dates(Date from, Date to) {
		super();
		this.orgName = "";
		this.from = from;
		this.to = to;
	}
	public Dates(String orgName, Date from, Date to) {
		super();
		this.orgName = orgName;
		this.from = from;
		this.to = to;
	}

	public String getOrgName() {
		return orgName;
	}

	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
}
