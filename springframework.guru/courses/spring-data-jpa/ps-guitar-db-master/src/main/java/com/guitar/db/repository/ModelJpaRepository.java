package com.guitar.db.repository;

import com.guitar.db.model.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ModelJpaRepository extends JpaRepository<Model, Long> {
    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal lowest, BigDecimal highest);

    List<Model> findByModelTypeNameIn(List<String> types);

    @Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
    Page<Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal lowest,
                                             @Param("highest") BigDecimal high,
                                             @Param("wood") String wood,
                                             Pageable pageable);

    List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqualAndWoodTypeLike(BigDecimal lowest,
                                                                                BigDecimal high,
                                                                                String wood);

    // native query
    @Query(value = "select * from Model where name = ?0", nativeQuery = true)
    List<Model> queryByName(String name);

    // modifiable queries
    @Modifying
    @Query("update Model m set m.name = ?1")
    int updateByName(String name);

    List<Model> findAllModelsByType(@Param("name") String name);
}
