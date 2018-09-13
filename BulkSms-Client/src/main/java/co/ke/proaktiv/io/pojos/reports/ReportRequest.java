package co.ke.proaktiv.io.pojos.reports;

import java.util.Date;

public class ReportRequest {

	private String organisation;
	private Date from;
	private Date to;
	public ReportRequest() {
		super();
	}
	public ReportRequest(Date from, Date to) {
		super();
		this.organisation = "";
		this.from = from;
		this.to = to;
	}
	public ReportRequest(String orgName, Date from, Date to) {
		super();
		this.organisation = orgName;
		this.from = from;
		this.to = to;
	}

	public String getOrganisation() {
		return organisation;
	}

	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
}
