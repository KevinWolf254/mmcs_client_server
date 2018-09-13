package co.ke.proaktiv.io.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import co.ke.proaktiv.io.models.Group;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.pojos.SubscriberReport;
import co.ke.proaktiv.io.pojos.Subscriber_;
import co.ke.proaktiv.io.pojos.helpers.ServiceProvider;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.repository.SubscriberRepository;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.SubscriberService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	private SubscriberRepository repository;

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;

	@Override
	public Set<Subscriber> findByGroupsId(Long id) {
		return repository.findByGroupsId(id);
	}
	
	@Override
	public Set<Subscriber> findByGroupsIds(final Set<Long> groupIds) {
		final Set<Subscriber> subscribers = groupIds.stream()
				.map(id-> findByGroupsId(id))
				.flatMap(subscribersByGGroup -> subscribersByGGroup.stream())
				.collect(Collectors.toSet());	
		
		return subscribers;
	}

	@Override
	public Set<Subscriber> findAll(final Long id){
		final StringBuilder build = new StringBuilder(""+id)
				.append("_All_Contacts");
		final Group group = groupService.findByName(build.toString()).get();
		return findByGroupsId(group.getId());
	}
	@Override
	public Set<SubscriberReport> createReport(final Collection<Subscriber> subscribers) {	
		final Set<SubscriberReport> phones = new HashSet<SubscriberReport>();
		ServiceProvider.stream()
			.forEach(provider->{				
				final int total = subscribers.stream()
					.filter(subscriber -> subscriber.getCategory().equals(provider))
					.collect(Collectors.toSet()).size();
				phones.add(new SubscriberReport(provider, total));
			}
		);
		
		return phones;
	}

	@Override
	public Subscriber findByCodePhoneNo(final String code, final String serviceProvider, 
			final String number) {
		return repository.findByCodePhoneNo(code, serviceProvider, number);
	}
	
	@Override
	public boolean exists(String code, String serviceProvider, String number) {
		return repository.exists(code, serviceProvider, number);
	}
		
	@Override
	public Subscriber save(final Subscriber_ subscriber) {
		final Organisation organisation = userService.getSignedInUser().getOrganisation();
		final Group group = groupService.findByName(organisation.getId()+"_All_Contacts").get();
		return save(subscriber, group);	
	}
	
	@Override
	public Subscriber save(final Subscriber_ subscriber, final Group group) {		
		final String code = subscriber.getCode();
		final String provider = subscriber.getNumber().substring(0, 2);
		final String number = subscriber.getNumber().substring(3);
		if(exists(code, provider, number)) {
			return save(code, provider, number, group);
		}
		final ServiceProvider serviceProvider = getServiceProvider(code, provider);		
		return repository.save(new Subscriber(code, provider, number, serviceProvider, group));	
	}
	
	private Subscriber save(final String code, final String provider, final String number, 
			final Group group) {
		final Subscriber subscriber = findByCodePhoneNo(code, provider, number);
		group.getSubscribers().add(subscriber);
		groupService.save(group);
		return subscriber;
	}
	
	@Override
	public Set<Subscriber> save(final MultipartFile csvfile){	
		final Organisation organisation = userService.getSignedInUser().getOrganisation();
		final Group group = groupService.findByName(organisation.getId()+"_All_Contacts").get();
		return save(csvfile, group);
		
	}
	
	@Override
	public Set<Subscriber> save(final MultipartFile csvfile, final Group group) {
		final File file = new File(csvfile.getOriginalFilename());
		final Set<Subscriber> subscribers = new HashSet<Subscriber>();
		try {
			final Reader reader = new FileReader(file);
			final FileOutputStream fos = new FileOutputStream(file);
			fos.write(csvfile.getBytes());
			fos.close();
		
			final CsvToBean<Subscriber_> csvToBean = new CsvToBeanBuilder<Subscriber_>(reader)
					.withType(Subscriber_.class)
					.withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
					.withIgnoreLeadingWhiteSpace(true)
					.withOrderedResults(false)
					.withSkipLines(0)
					.build();
			
			//If memory is not a problem, read using CsvToBean.parse(), 
			//which will read all beans at once and is multi_threaded.
			//If your memory is limited, use CsvToBean.iterator() and iterate over the input. 
			//Only one bean is read at a time, making multi_threading impossible and slowing down reading,

			final List<Subscriber_> contactList = csvToBean.parse();			
			
			contactList.stream()
				.filter(subscriber -> {
					if(!subscriber.getCode().isEmpty() || subscriber.getCode() != null || 
							!subscriber.getNumber().isEmpty() || subscriber.getNumber() != null)
						return true;
					return false;})
				.filter(subscriber -> {
					if(subscriber.getCode().length() == 3)
						return true;
					return false;})
				.filter(subscriber -> {
					return subscriber.getCode().startsWith("2");})
				.filter(subscriber -> {
					if(subscriber.getNumber().length() >= 8 && subscriber.getNumber().length() <=9)
						return true;
					return false;})
				.filter(subscriber ->{
					return (subscriber.getNumber().startsWith("7") || subscriber.getNumber().startsWith("6"));})
				.map(subscriber -> {
					final StringBuilder code = new StringBuilder("+").append(subscriber.getCode());
					subscriber.setCode(code.toString());
					return subscriber;})
				.forEach(subscriber ->{
					final Subscriber sub = save(subscriber, group);
					subscribers.add(sub);});
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subscribers;
	}

	@Override
	public Response delete(Long contactId, Long groupId) {
		final Subscriber contact = repository.findById(contactId).get();
		final Group group = groupService.findById(groupId).get();
		
		group.getSubscribers().remove(contact);
		groupService.save(group);
		
		contact.getGroups().remove(group);
		repository.save(contact);
		return new Response(200, "success", "removed subscriber");
	}
	
	@Override
	public boolean validate(final Subscriber_ subscriber) {
		final String code = subscriber.getCode();
		final String provider = subscriber.getNumber().substring(0, 2);
		final String number = subscriber.getNumber().substring(3);
		
		if(code.isEmpty() || code == null || provider.isEmpty() || provider == null || 
				number.isEmpty() || number == null)
			return false;
		if(!code.startsWith("+2"))
			return false;
		if(code.length() != 4)
			return false;
		if (!(provider.startsWith("7") || provider.startsWith("6")))
			return false;
		if(provider.length() != 3)
			return false;
		if(number.length() != 6)
			return false;
		return true;
	}
	
	@Override
	public String convert(final Set<Subscriber> subs) {
		final StringBuilder recipients = new StringBuilder();
		subs.stream()
			.map(sub->{
				final StringBuilder build = new StringBuilder(sub.getCode())
						.append(sub.getServiceProvider())
						.append(sub.getNumber())
						.append(",");
				return build.toString();
			})
			.forEach(sub->{
				recipients.append(sub);
			});
		return recipients.toString();
	}
	
	private ServiceProvider getServiceProvider(final String code, final String provider) {
		if(code.equals("+250")) {
			if(provider.startsWith("73")) 
				return ServiceProvider.RW_AIRTEL;
			else
				return ServiceProvider.RW_OTHERS;
		}else if(code.equals("+254")) {
			if(provider.startsWith("73") || provider.startsWith("75") || 
					provider.startsWith("78")) 
				return ServiceProvider.KE_AIRTEL;
			else
				return ServiceProvider.KE_OTHERS;
		}else if(code.equals("+255")) {
			if(provider.startsWith("68") || provider.startsWith("69")) 
				return ServiceProvider.TZ_AIRTEL;
			else
				return  ServiceProvider.TZ_OTHERS;
		}else if(code.equals("+256")) {
			if(provider.startsWith("75")) 
				return  ServiceProvider.UG_AIRTEL;
			else
				return ServiceProvider.UG_OTHERS;
		}else 
			return ServiceProvider.OTHER;
	}
}
