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
  // http://192.168.0.154:9999/api/book/list/?page=0
  // @Query (value = "select * from book_all_info order by bi_seq", nativeQuery = true )
  
  @Query (value = "select * from book_all_info order by bi_seq", nativeQuery = true)
  Page<ViewEntity> getBookList(Pageable page);
  
  // 성공한거
  // @Query(value = "select * from book_all_info where bi_title like %:keyword% ", nativeQuery=true) // 페이징처리 MySQL문법
  // List<ViewEntity> searchBookTitle(@Param("keyword") String keyword);

  // 5개 페이지만 나오게 검색
  @Query(value = "select * from book_all_info where bi_title like %:keyword%", nativeQuery=true) // 페이징처리 MySQL문법
  List<ViewEntity> searchBookTitle(@Param("keyword") String keyword);

  // // 5개 페이지만 나오게 검색
  // @Query(value = "select * from book_all_info where bi_title like %:keyword% limit 5 offset :offset ", nativeQuery=true) // 페이징처리 MySQL문법
  // List<ViewEntity> searchBookTitle(@Param("keyword") String keyword, @Param("offset") Integer offset);

  // @Query(value = "select * from book_all_info limit 5 offset :offset ", nativeQuery=true)
  // List<ViewEntity> searchBookList( @Param("offset") Integer offset);
  // @Query(value = "select ceil(count(b)/5) from ViewEntity v where v.biTitle like %:keyword%")
  // Integer getBookPageCount(@Param("keyword") String keyword);

}
