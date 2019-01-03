package com.guitar.db.repository;

import com.guitar.db.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ModelJpaRepository extends JpaRepository<Model, Long> {
    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal lowest, BigDecimal highest);

    List<Model> findByModelTypeNameIn(List<String> types);
}
