package com.guitar.db.repository;

import com.guitar.db.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Long> {
    List<Manufacturer> findByFoundedDateBefore(Date date);

    List<Manufacturer> findByActiveTrue();

    List<Manufacturer> findByActiveFalse();
}
