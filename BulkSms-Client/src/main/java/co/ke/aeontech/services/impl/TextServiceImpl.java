package co.ke.aeontech.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Text;
import co.ke.aeontech.repository.TextRepository;
import co.ke.aeontech.services.TextService;

@Service
public class TextServiceImpl implements TextService {

	@Autowired
	private TextRepository repository;
	
	@Override
	public Text saveText(Text text) {
		return repository.save(text);
	}

	@Override
	public Text findByScheduleId(Long id) {
		return repository.findByScheduleId(id);
	}

}
