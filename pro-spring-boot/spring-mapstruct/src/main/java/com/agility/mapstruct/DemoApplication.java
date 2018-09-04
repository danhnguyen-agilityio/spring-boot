package com.agility.mapstruct;

import com.agility.mapstruct.mapper.ContactMapper;
import com.agility.mapstruct.mapper.SimpleSourceDestinationMapper;
import com.agility.mapstruct.models.BusinessContact;
import com.agility.mapstruct.models.PrimaryContact;
import com.agility.mapstruct.models.SimpleDestination;
import com.agility.mapstruct.models.SimpleSource;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
