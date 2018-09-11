package com.agility.jpa;

import com.agility.jpa.model.Company;
import com.agility.jpa.model.Product;
import com.agility.jpa.repository.CompanyRepository;
import com.agility.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class JpaOne2manyApplication implements CommandLineRunner {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaOne2manyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clearData();
		saveData();
		showData();
	}

	@Transactional
	private void clearData() {
		companyRepository.deleteAll();
		productRepository.deleteAll();
	}

	@Transactional
	private void saveData() {
		saveDataWithApproach1();
//		saveDataWithApproach2();
	}

	/**
	 * Save Company objects that include Product list
	 */
	@Transactional
	private void saveDataWithApproach1() {
		Company apple = new Company("apple");
		Company samsung = new Company("samsung");

		Product iphone7 = new Product("Iphone 7", apple);
		Product iPadPro = new Product("IPadPro", apple);

		Product galaxyJ7 = new Product("GalaxyJ7", samsung);
		Product galaxyTabA = new Product("GalaxyTabA", samsung);

		apple.setProducts(new HashSet<Product>() {{
			add(iphone7);
			add(iPadPro);
		}});

		samsung.setProducts(new HashSet<Product>() {{
			add(galaxyJ7);
			add(galaxyTabA);
		}});

		companyRepository.save(apple);
		companyRepository.save(samsung);
	}

	/**
	 * Save company first. Then saving products which had attached a company for each
	 */
	@Transactional
	private void saveDataWithApproach2() {
		Company apple = new Company("apple");
		Company samsung = new Company("samsung");
		companyRepository.save(apple);
		companyRepository.save(samsung);

		Product iphone7 = new Product("Iphone 7", apple);
		Product iPadPro = new Product("IPadPro", apple);
		Product galaxyJ7 = new Product("GalaxyJ7", samsung);
		Product galaxyTabA = new Product("GalaxyTabA", samsung);
		productRepository.save(iphone7);
		productRepository.save(iPadPro);
		productRepository.save(galaxyJ7);
		productRepository.save(galaxyTabA);
	}

	@Transactional
	private void showData() {
		List<Company> companyLst = companyRepository.findAll();
		List<Product> productLst = productRepository.findAll();

		System.out.println("===================Product List:==================");
		productLst.forEach(System.out::println);

		System.out.println("===================Company List:==================");
		companyLst.forEach(System.out::println);
	}
}
