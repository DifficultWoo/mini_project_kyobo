package com.armycar.kyobo.repoistory;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.armycar.kyobo.entity.BookEntity;


public interface BookRepository extends JpaRepository<BookEntity, Long>{
    // List<ViewEntity> findByLastname(String lastName, Pageable pageable);
    @Query(value = "select * from book_info limit 5 offset :offset ", nativeQuery=true)
    List<BookEntity> searchBookTitle( @Param("offset") Integer offset);
    
    @Query(value = "select ceil(count(b)/5) from BookEntity b where b.biTitle like %:keyword%")
    Integer getBookPageCount(@Param("keyword") String keyword);

    List<BookEntity> findByBiTitle(String biTitle);

}
