package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.models.Group_;

public class GroupResponse extends Response {

	private Group_ group;

	public GroupResponse() {
		super();
	}
	public GroupResponse(int code, String title, 
			String message, Group_ group) {
		super(code, title, message);
		this.group = group;
	}
	public Group_ getGroup() {
		return group;
	}
	public void setGroup(Group_ group) {
		this.group = group;
	}
}
