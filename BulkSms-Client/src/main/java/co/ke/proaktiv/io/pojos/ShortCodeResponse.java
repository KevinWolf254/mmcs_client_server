package co.ke.proaktiv.io.pojos;

import co.ke.proaktiv.io.pojos.pro.ShortCode;
import co.ke.proaktiv.io.pojos.response.Response;

public class ShortCodeResponse extends Response {

	private ShortCode shortCode;

	public ShortCodeResponse() {
		super();
	}

	public ShortCodeResponse(int code, String title, String message) {
		super(code, title, message);
	}

	public ShortCodeResponse(int code, String title, String message, ShortCode shortCode) {
		super(code, title, message);
		this.shortCode = shortCode;
	}

	public ShortCode getShortCode() {
		return shortCode;
	}

	public void setShortCode(ShortCode shortCode) {
		this.shortCode = shortCode;
	}
	
}
