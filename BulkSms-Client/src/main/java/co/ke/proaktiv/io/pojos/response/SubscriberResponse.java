package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.models.Subscriber;

public class SubscriberResponse extends Response {

	private Subscriber subscriber;

	public SubscriberResponse() {
		super();
	}
	public SubscriberResponse(int code, String title, String message, Subscriber subscriber) {
		super(code, title, message);
		this.subscriber = subscriber;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}
