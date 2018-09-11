package com.agility.springbootdemo.repository;

import com.agility.springbootdemo.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalRepository extends JpaRepository<Journal, Long> {

}
