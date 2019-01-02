package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {

}
