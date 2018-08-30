package co.ke.aeontech.services;

import java.util.Set;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.pojos._Administrator;
import co.ke.aeontech.pojos._Organisation;
import co.ke.aeontech.pojos.response.Response;

public interface OrganisationService {
	
	public Response register(_Organisation new_org, _Administrator admin, String senderId);
	
	public Organisation findByEmployeesId(Long id);

	public Organisation save(Organisation orgDetails);

	public Organisation findById(Long id);

	public Organisation findBySchedulesId(Long id);

	public Set<Organisation> findByContactsId(Long id);

	public Organisation findByName(String orgName);

}
