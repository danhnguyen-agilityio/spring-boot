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

import java.util.Arrays;
import java.util.List;

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
    wifeRepository.save(lisa);
    Wife mary = new Wife("Mary", new Husband("Peter"));
    wifeRepository.save(mary);
    Wife lauren = new Wife("Lauren", new Husband("Phillip"));
    wifeRepository.save(lauren);
  }

  @Transactional
  private void deleteData() {
    wifeRepository.deleteAll();
    husbandRepository.deleteAll();
  }

  @Transactional
  private void showData() {
    List<Wife> wifes = wifeRepository.findAll();
    List<Husband> husbands = husbandRepository.findAll();

    System.out.println("----------Wifes: ------------");
    wifes.forEach(System.out::println);

    System.out.println("----------Husband: ------------");
    husbands.forEach(System.out::println);
  }
}
