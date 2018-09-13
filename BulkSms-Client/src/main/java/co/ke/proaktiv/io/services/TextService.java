package co.ke.proaktiv.io.services;

import co.ke.proaktiv.io.models.Text;

public interface TextService {

	public Text findByScheduleId(Long id);
	
	public Text save(Text text);
}
