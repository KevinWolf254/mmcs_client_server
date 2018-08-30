package co.ke.aeontech.services;

import co.ke.aeontech.models.Text;

public interface TextService {

	public Text findByScheduleId(Long id);
	
	public Text saveText(Text text);
}
