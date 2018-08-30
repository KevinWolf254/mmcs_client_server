package co.ke.aeontech.models;

import co.ke.aeontech.pojos.helpers.Paid;

public class SenderIdentifier {
	
	private Long id;
	private String name;
	private Paid paid;
	private Organisation organisation;

	public SenderIdentifier() {
		super();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Paid getPaid() {
		return paid;
	}

	public void setPaid(Paid paid) {
		this.paid = paid;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
}
