package co.ke.aeontech.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class _Charges {

	private Long id;
	private double rwf;
	private double rwfAir;
	private double kes;
	private double kesAir;
	private double tzs;
	private double tzsAir;
	private double ugx;
	private double ugxAir;
	private double other;
	
	public _Charges() {
		super();
	}
	@JsonIgnore
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getRwf() {
		return rwf;
	}
	public void setRwf(double rwf) {
		this.rwf = rwf;
	}
	public double getKes() {
		return kes;
	}
	public void setKes(double kes) {
		this.kes = kes;
	}
	public double getKesAir() {
		return kesAir;
	}
	public void setKesAir(double kesAir) {
		this.kesAir = kesAir;
	}
	public double getTzs() {
		return tzs;
	}
	public void setTzs(double tzs) {
		this.tzs = tzs;
	}
	public double getUgx() {
		return ugx;
	}
	public void setUgx(double ugx) {
		this.ugx = ugx;
	}
	public double getUgxAir() {
		return ugxAir;
	}
	public void setUgxAir(double ugxAir) {
		this.ugxAir = ugxAir;
	}
	public double getOther() {
		return other;
	}
	public void setOther(double other) {
		this.other = other;
	}
	public double getRwfAir() {
		return rwfAir;
	}
	public void setRwfAir(double rwfAir) {
		this.rwfAir = rwfAir;
	}
	public double getTzsAir() {
		return tzsAir;
	}
	public void setTzsAir(double tzsAir) {
		this.tzsAir = tzsAir;
	}	
}
