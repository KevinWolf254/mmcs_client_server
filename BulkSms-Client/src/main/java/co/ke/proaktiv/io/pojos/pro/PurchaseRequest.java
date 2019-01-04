package co.ke.proaktiv.io.pojos.pro;

import java.util.Date;

public class PurchaseRequest {
	private Long id;
	private Date from;
	private Date to;
	public PurchaseRequest() {
		super();
	}
	public PurchaseRequest(Long id, Date from, Date to) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
}
