package com.agility.jpa;

import com.agility.jpa.model.Husband;
import com.agility.jpa.model.Wife;
import com.agility.jpa.repsitory.HusbandRepository;
import com.agility.jpa.repsitory.WifeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class JpaOne2oneApplication implements CommandLineRunner {

	@Autowired
	WifeRepository wifeRepository;

	@Autowired
	HusbandRepository husbandRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaOne2oneApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		deleteData();
		saveData();
		showData();
	}

	@Transactional
	private void saveData() {
//		Store a wife to DB
		Wife lisa = new Wife("Lisa", new Husband("David"));
	}

	@Transactional
	private void deleteData() {

	}

	private void showData() {

	}
}
