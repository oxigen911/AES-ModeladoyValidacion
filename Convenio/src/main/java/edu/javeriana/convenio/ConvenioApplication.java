package edu.javeriana.convenio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class ConvenioApplication
{

	public static void main( String[] args )
	{
		SpringApplication.run( ConvenioApplication.class, args );
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor()
	{
		return new MethodValidationPostProcessor();
	}

}
