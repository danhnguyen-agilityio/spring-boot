package com.guitar.db.repository;

import com.guitar.db.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {
    Location findFirstByState(String stateName);

    Long countByStateLike(String stateName);

    List<Location> findByStateLike(String stateName);

    List<Location> findByStateNotLikeOrderByStateAsc(String stateName);

    List<Location> findByStateIgnoreCaseStartingWith(String stateName);

    Location findFirstByStateIgnoreCaseStartingWith(String stateName);

    List<Location> findByStateOrCountry(String value, String value2);

    List<Location> findByStateAndCountry(String state, String country);

    List<Location> findByStateIsOrCountryEquals(String value, String value2);

    List<Location> findByStateNot(String stateName);

}
