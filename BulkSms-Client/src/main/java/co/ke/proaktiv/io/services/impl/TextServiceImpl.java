package co.ke.proaktiv.io.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.proaktiv.io.models.Text;
import co.ke.proaktiv.io.repository.TextRepository;
import co.ke.proaktiv.io.services.TextService;

@Service
public class TextServiceImpl implements TextService {

	@Autowired
	private TextRepository repository;
	
	@Override
	public Text save(final Text text) {
		return repository.save(text);
	}

	@Override
	public Text findByScheduleId(final Long id) {
		return repository.findByScheduleId(id);
	}

}
