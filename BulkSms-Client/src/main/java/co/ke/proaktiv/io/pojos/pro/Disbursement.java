package co.ke.proaktiv.io.pojos.pro;

import java.util.Set;

public class Disbursement{

	private int total;
	private Set<CreditDisbursment> list;
	
	public Disbursement() {
		super();
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Set<CreditDisbursment> getList() {
		return list;
	}
	public void setList(Set<CreditDisbursment> list) {
		this.list = list;
	}
}
