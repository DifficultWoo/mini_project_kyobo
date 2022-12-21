package com.armycar.kyobo.repoistory;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.armycar.kyobo.entity.ViewEntity;

@Repository
public interface ViewRepository extends JpaRepository<ViewEntity, Long>{

  
  @Query (value = "select * from book_all_info order by bi_seq", nativeQuery = true)
  Page<ViewEntity> getBookList(Pageable page);

  // 타이틀 특정키워드로 검색 5개 1페이지  // 총페이징 카운트
  @Query(value = "select ceil(count(b)/5) from ViewEntity b where b.biTitle like %:keyword%") 
  Integer getBookPageCount(@Param("keyword") String keyword);

  // 성공한거 // 페이징처리 MySQL문법
  @Query(value = "select * from book_all_info where bi_title like %:keyword%", nativeQuery=true) 
  List<ViewEntity> searchBookTitle(@Param("keyword") String keyword, Pageable page);

  // 작가이름으로 검색
  @Query(value = "select * from book_all_info where bw_name like %:keyword%", nativeQuery=true)
  List<ViewEntity> searchWriterName(@Param("keyword") String keyword, Pageable page);

  @Query (value = "select * from book_all_info order by bi_price", nativeQuery = true) // 가격별 정렬
  Page<ViewEntity> getPriceList(Pageable page);

  @Query(value = "select * from book_all_info where bi_title like %:keyword%", nativeQuery=true) // 책 이름으로 검색하기
  List<ViewEntity> searchBookTitle(@Param("keyword") String keyword);



  // 작가이름으로 검색 5개 1페이지 // 총페이징 카운트
  @Query(value = "select ceil(count(b)/5) from ViewEntity b where b.bwName like %:keyword%") 
  Integer getWriterPageCount(@Param("keyword") String keyword);
}
