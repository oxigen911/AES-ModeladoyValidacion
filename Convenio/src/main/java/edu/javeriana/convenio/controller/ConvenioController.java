package edu.javeriana.convenio.controller;

import static edu.javeriana.convenio.constants.ApiConstants.MESSAGE_FOR_REGEX_NUMBER_MISMATCH;
import static edu.javeriana.convenio.constants.ApiConstants.REGEX_FOR_NUMBERS;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.javeriana.convenio.model.Convenio;
import edu.javeriana.convenio.results.ResponseWrapper;
import edu.javeriana.convenio.service.ConvenioService;

@Validated
@RestController
@RequestMapping("/api/v1/convenio")
public class ConvenioController
{

	@Autowired
	private ConvenioService convenioMainService;

	@GetMapping(value = "/{id}")
	public ResponseWrapper<Convenio> getConvenioById(
			@Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
	{
		return new ResponseWrapper<>( convenioMainService.getById( Integer.parseInt( id ) ), HttpStatus.OK );
	}

	@PostMapping
	public ResponseWrapper<Convenio> createConvenio( @Valid @RequestBody Convenio convenio )
	{
		return new ResponseWrapper<>( convenioMainService.add( convenio ), HttpStatus.OK );
	}

	@DeleteMapping(value = "/{id}")
	public ResponseWrapper<Convenio> deleteConvenio(
			@Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
	{
		return new ResponseWrapper<>( convenioMainService.deleteById( Integer.parseInt( id ) ), HttpStatus.OK );
	}

	@PatchMapping(value = "/{id}")
	public ResponseWrapper<Convenio> UpdateConvenio( @Valid @RequestBody Convenio convenio,
			@Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id )
	{
		return new ResponseWrapper<>( convenioMainService.update( convenio, Integer.parseInt( id ) ), HttpStatus.OK );
	}

}
