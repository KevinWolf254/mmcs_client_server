package co.ke.proaktiv.io.pojos.pro;

import java.util.Date;
public class CreditDisbursment {

	private Long id;
	private boolean pending;
	private Date date;
	public CreditDisbursment() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
}
