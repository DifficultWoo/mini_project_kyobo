package com.armycar.kyobo.repoistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.armycar.kyobo.entity.DetailEntity;

public interface DetailRepository extends JpaRepository<DetailEntity, Long>{
  @Query(value = "select * from v_detail", nativeQuery=true)
  
  List<DetailEntity> searchBookTitle(@Param("keyword") String keyword, @Param("offset") Integer offset);
  @Query(value = "select ceil(count(b)/10) from BookEntity b where b.biTitle like %:keyword%")
  Integer getBookPageCount(@Param("keyword") String keyword);
}
