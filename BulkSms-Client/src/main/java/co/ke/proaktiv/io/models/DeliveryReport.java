package co.ke.proaktiv.io.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="delivery_report")
public class DeliveryReport {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="message_id", nullable = false)
	private String messageId;
	
	@Column(name="received", nullable = false)
	private int received;
	
	@Column(name="rejected", nullable = false)
	private int rejected;
	
	@Column(name="phone_nos_rejected")
	private String phoneNosRejected;
	
	@Column(name="date", nullable = false)
	private Date date;
	
	@ManyToOne
	(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
	@JoinColumn(name = "organisation_id", nullable = false)
	private Organisation organisation;
	
	public DeliveryReport() {
		super();
	}
	public DeliveryReport(String messageId, int received, 
			int rejected, String phoneNosRejected,
			Organisation organisation) {
		super();
		this.messageId = messageId;
		this.received = received;
		this.rejected = rejected;
		this.phoneNosRejected = phoneNosRejected;
		this.organisation = organisation;
		this.date = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public int getReceived() {
		return received;
	}
	public void setReceived(int received) {
		this.received = received;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public String getPhoneNosRejected() {
		return phoneNosRejected;
	}
	public void setPhoneNosRejected(String phoneNosRejected) {
		this.phoneNosRejected = phoneNosRejected;
	}
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryReport other = (DeliveryReport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		return true;
	}	
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("DeliveryReport [id=").append(id)
				.append(", messageId=").append(messageId)
				.append(", received=").append(received)
				.append(", rejected=").append(rejected)
				.append(", phoneNosRejected=").append(phoneNosRejected)
				.append(", date=").append(date)
				.append("]");
		return builder.toString();
	}
}
