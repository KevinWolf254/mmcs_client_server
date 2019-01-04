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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.models.Group_;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.Prefix;
import co.ke.proaktiv.io.models.ServiceProvider;
import co.ke.proaktiv.io.models.Subscriber;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.ServiceProviderReport;
import co.ke.proaktiv.io.pojos.Subscriber_;
import co.ke.proaktiv.io.pojos.response.Response;
import co.ke.proaktiv.io.repository.SubscriberRepository;
import co.ke.proaktiv.io.services.CountryService;
import co.ke.proaktiv.io.services.GroupService;
import co.ke.proaktiv.io.services.PrefixService;
import co.ke.proaktiv.io.services.ServiceProviderService;
import co.ke.proaktiv.io.services.SubscriberService;
import co.ke.proaktiv.io.services.UserService;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	private SubscriberRepository repository;
	@Autowired
	private ServiceProviderService providerService;
	@Autowired
	private UserService userService;
	@Autowired
	private PrefixService prefixService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private GroupService groupService;
	private static final Logger log = LoggerFactory.getLogger(SubscriberServiceImpl.class);

	@Override
	public Set<Subscriber> findByGroupId(final Long id) {
		return repository.findByGroupsId(id);
	}
	
	@Override
	public Set<Subscriber> findByGroupsId(final Set<Long> groupIds) {
		return groupIds.stream()
				.map(id-> findByGroupId(id))
				.flatMap(group_subscribers -> group_subscribers.stream())
				.collect(Collectors.toSet());	
	}

	@Override
	public Set<Subscriber> findAll(final Long id){
		final StringBuilder build = new StringBuilder(""+id)
				.append("_All_Contacts");
		final Group_ group = groupService.findByName(build.toString()).get();
		return findByGroupId(group.getId());
	}
	
	@Override
	public Set<ServiceProviderReport> calculateTotalSubsPerProvider(final Collection<Subscriber> subscribers) {	
		final Set<ServiceProviderReport> list = new HashSet<ServiceProviderReport>();
		final List<ServiceProvider> providers = providerService.findAll();
		
		providers.stream()
			.forEach(provider->{
				if(subscribers.size() != 0) {
					final Set<Subscriber> subs = subscribers.stream()
							.filter(subscriber -> subscriber.getServiceProvider().equals(provider))
							.collect(Collectors.toSet());
						if(subs.size() != 0)
							list.add(new ServiceProviderReport(provider, subs.size()));
				}
			}
		);	
		return list;
	}

	@Override
	public Optional<Subscriber> findByFullPhoneNo(final String fullPhoneNo) {
		return repository.findByFullPhoneNo(fullPhoneNo);
	}
		
	@Override
	public Subscriber save(final Subscriber_ subscriber) {
		final String name = new String("All_Subscribers");
		final Optional<Group_> optGroup = groupService.findByName(name);
		if(optGroup.isPresent()) {
			final Group_ group = optGroup.get();
			return save(subscriber, group);	
		}
		return new Subscriber();
	}
	
	@Override
	public Subscriber save(final Subscriber_ subscriber, final Group_ group) {			
		final String code = subscriber.getCode();
		final String provider = subscriber.getNumber().substring(0, 3);
		final String number = subscriber.getNumber().substring(3);
				
		final String fullPhoneNo = new StringBuilder(code)
				.append(provider)
				.append(number)
				.toString();
		
		final Optional<Subscriber> sub = findByFullPhoneNo(fullPhoneNo);
		
		if(sub.isPresent()) 
			return addExisting(sub.get(), group);
		
		return addNew(subscriber, group, code, provider);	
	}

	private Subscriber addNew(final Subscriber_ subscriber, final Group_ group, final String code,
			final String provider) {
		final Country country = countryService.findByCode(code);
		final String ndc = provider.substring(0, 2);
		final Prefix prefix = prefixService.findByNumber(ndc);
		final ServiceProvider serviceProvider = providerService.find(country, prefix);
		final Subscriber subscriber_ = repository.save(new Subscriber(code, subscriber.getNumber(), prefix, serviceProvider, group));
		return subscriber_;
	}
	
	private Subscriber addExisting(final Subscriber subscriber, final Group_ group) {
		subscriber.getGroups().add(group);
		final Subscriber subscriber_ = repository.save(subscriber);
		return subscriber_;
	}
	
	private Subscriber save(final Subscriber subscriber) {
		
		final Group_ group = subscriber.getGroups().stream().collect(Collectors.toList()).get(0);
		final Optional<Subscriber> sub = findByFullPhoneNo(subscriber.getFullPhoneNo());
		
		if(sub.isPresent()) 
			return addExisting(sub.get(), group);
		
		final Subscriber subscriber_ = repository.save(subscriber);
		return subscriber_;	
	}
	
	@Override
	public Set<Subscriber> save(final MultipartFile csvfile){
		final String name = new String("All_Subscribers");
		final Optional<Group_> optGroup = groupService.findByName(name);
		Set<Subscriber> result = new HashSet<Subscriber>();
		if(optGroup.isPresent()) {
			final Group_ group = optGroup.get();
			result = save(csvfile, group);
			return result;		
		}
		return result;
	}
	
	@Override
	public Set<Subscriber> save(final MultipartFile csvfile, final Group_ group) {
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
				.filter(subscriber -> (subscriber.getCode().length() == 3))
				.filter(subscriber -> subscriber.getCode().startsWith("2"))
				.filter(subscriber -> (subscriber.getNumber().length() == 9))
				.filter(subscriber -> 
					(subscriber.getNumber().startsWith("7") || subscriber.getNumber().startsWith("6")))
				.map(subscriber -> {
					final String code = new StringBuilder("+").append(subscriber.getCode()).toString();
					subscriber.setCode(code);
					return subscriber;})
				.map(sub ->{	
					final String code = sub.getCode();
					final String _prefix = sub.getNumber().substring(0, 2);
					final String number = sub.getNumber();

					final Country country = countryService.findByCode(code);
					final Prefix prefix = prefixService.findByNumber(_prefix);
					final ServiceProvider serviceProvider = providerService.find(country, prefix);
					
					return new Subscriber(code, number, prefix, serviceProvider, group);
				})
				.collect(Collectors.toSet())
				.forEach(subscriber ->{
					final Subscriber sub = save(subscriber);
					subscribers.add(sub);});
			
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException: "+e.getMessage());
			return subscribers;
		} catch (IOException e) {
			log.error("IOException: "+e.getMessage());
			return subscribers;
		}catch (Exception e) {
			log.error("Exception: "+e.getMessage());
			return subscribers;
		}
		return subscribers;
	}

	@Override
	public Response delete(final Long contactId, final Long groupId) {
		final Subscriber contact = repository.findById(contactId).get();
		final Group_ group = groupService.findById(groupId).get();
		
		group.getSubscribers().remove(contact);
		groupService.save(group);
		
		contact.getGroups().remove(group);
		repository.save(contact);
		return new Response(200, "success", "removed subscriber");
	}
	
	@Override
	public boolean isValid(final Subscriber_ subscriber) {
		final String code = subscriber.getCode();
		final String provider = subscriber.getNumber().substring(0, 3);
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
	public String toString(final Set<Subscriber> subs) {
		final StringBuilder recipients = new StringBuilder();
		subs.stream()
			.map(sub->{
				return new StringBuilder(sub.getFullPhoneNo())
						.append(",").toString();
			})
			.forEach(sub->{
				recipients.append(sub);
			});
		return recipients.toString();
	}

	@Override
	public Set<ServiceProviderReport> findAllByUser(User user) {
		final Organisation org = userService.getSignedInUser().getOrganisation();
		
		final Set<Group_> groups = groupService.findByOrganisationId(org.getId());
		final Set<Long> groupIds = groups.stream()
				.map(group -> group.getId())
				.collect(Collectors.toSet());
		
		final Set<Subscriber> subs = findByGroupsId(groupIds);
		final Set<ServiceProviderReport> report = calculateTotalSubsPerProvider(subs);
		return report;
	}
}
