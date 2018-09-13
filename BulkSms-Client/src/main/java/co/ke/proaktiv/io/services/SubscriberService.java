package co.ke.proaktiv.io.services;

import java.util.Collection;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.pojos.SubscriberReport;
import co.ke.proaktiv.io.pojos.Subscriber_;
import co.ke.proaktiv.io.pojos.response.Response;

public interface SubscriberService {

	public Set<Subscriber> findByGroupsIds(Set<Long> groupIds);
	
	public Set<Subscriber> findByGroupsId(Long id);
	
	public Set<Subscriber> findAll(final Long id);
	
	public Subscriber findByCodePhoneNo(String code, String serviceProvider, String number);

	public Subscriber save(Subscriber_ subscriber);

	public Subscriber save(Subscriber_ subscriber, Group group);
	
	public Set<Subscriber> save(MultipartFile csvfile);
	
	public Set<Subscriber> save(MultipartFile csvfile, Group group);
	
	public String convert(final Set<Subscriber> subs);
	
	public boolean validate(Subscriber_ subscriber);
	
	public boolean exists(String code, String serviceProvider, String number);

	public Set<SubscriberReport> createReport(Collection<Subscriber> subscribers);
	
	public Response delete(Long contactId, Long groupId);
}
