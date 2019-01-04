package co.ke.proaktiv.io.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.Country;
import co.ke.proaktiv.io.services.CountryService;

@RestController
public class CountryController {
	@Autowired
	private CountryService countryServices;
	
	@GetMapping(value = "/secure/country")
	public ResponseEntity<Object> getCountries(){
		List<Country> countries = new ArrayList<Country>();
		countries = countryServices.findAll();
		return new ResponseEntity<Object>(countries, HttpStatus.OK);
	}
}
