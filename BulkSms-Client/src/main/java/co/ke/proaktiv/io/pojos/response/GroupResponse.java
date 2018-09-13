package co.ke.proaktiv.io.pojos.response;

import co.ke.proaktiv.io.models.Group;

public class GroupResponse extends Response {

	private Group group;

	public GroupResponse() {
		super();
	}
	public GroupResponse(int code, String title, 
			String message, Group group) {
		super(code, title, message);
		this.group = group;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
}
