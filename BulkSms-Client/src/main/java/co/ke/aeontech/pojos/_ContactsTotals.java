package co.ke.aeontech.pojos;

import co.ke.aeontech.pojos.response.Response;

public class _ContactsTotals implements Response {

	private long rwf;
	private long rwfAir;
	private long kes;
	private long kesAir;
	private long tzs;
	private long tzsAir;
	private long ugx;
	private long ugxAir;
	private long other;
	public _ContactsTotals() {
		super();
	}
	
	public _ContactsTotals(_ContactsTotals currencies) {
		super();
		this.rwf = currencies.getRwf();
		this.kes = currencies.getKes();
		this.kesAir = currencies.getKesAir();
		this.tzs = currencies.getTzs();
		this.ugx = currencies.getUgx();
		this.ugxAir = currencies.getUgxAir();
		this.other = currencies.getOther();
	}

	public _ContactsTotals(long rwfContacts, long rwfAirContact, long kesContacts, long kesAirContacts,
			long tzsContacts, long tzsAirContacts, long ugxContacts, long ugxAirContacts, long other) {
		super();
		this.rwf = rwfContacts;
		this.rwfAir = rwfAirContact;
		this.kes = kesContacts;
		this.kesAir = kesAirContacts;
		this.tzs = tzsContacts;
		this.tzsAir = tzsAirContacts;
		this.ugx = ugxContacts;
		this.ugxAir = ugxAirContacts;
		this.other = other;
	}
	
	public long getRwf() {
		return rwf;
	}
	public void setRwf(long rwfContacts) {
		this.rwf = rwfContacts;
	}
	public long getKes() {
		return kes;
	}
	public void setKes(long kesContacts) {
		this.kes = kesContacts;
	}
	public long getKesAir() {
		return kesAir;
	}
	public void setKesAir(long kesAirContacts) {
		this.kesAir = kesAirContacts;
	}
	public long getTzs() {
		return tzs;
	}
	public void setTzs(long tzsContacts) {
		this.tzs = tzsContacts;
	}
	public long getUgx() {
		return ugx;
	}
	public void setUgx(long ugxContacts) {
		this.ugx = ugxContacts;
	}
	public long getUgxAir() {
		return ugxAir;
	}
	public void setUgxAir(long ugxAirContacts) {
		this.ugxAir = ugxAirContacts;
	}
	public long getOther() {
		return other;
	}
	public void setOther(long other) {
		this.other = other;
	}

	public long getRwfAir() {
		return rwfAir;
	}

	public void setRwfAir(long rwfAir) {
		this.rwfAir = rwfAir;
	}

	public long getTzsAir() {
		return tzsAir;
	}

	public void setTzsAir(long tzsAir) {
		this.tzsAir = tzsAir;
	}
	
}
