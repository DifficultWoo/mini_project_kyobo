package com.armycar.kyobo.repoistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armycar.kyobo.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long>{
  public List <ImageEntity>  findByBimgSeq(Long bimgSeq);
  public List <ImageEntity> findTopByBimgUriOrderByBimgSeqDesc(String uri);
    
}
