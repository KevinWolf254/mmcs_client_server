package co.ke.aeontech.pojos.requests;

import java.util.List;

public class GroupedContacts {

	private List<Long> groupIds;

	public GroupedContacts() {
		super();
	}

	public List<Long> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
	}
	
}
