package co.ke.proaktiv.io.pojos.pro;

public class ShortCode {
	
	private Long id;
	private boolean paid;
	private String name;
	public ShortCode() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
