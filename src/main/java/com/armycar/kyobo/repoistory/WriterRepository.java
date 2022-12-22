package com.armycar.kyobo.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armycar.kyobo.entity.WriterEntity;

public interface WriterRepository extends JpaRepository<WriterEntity, Long>{
    WriterEntity findByBwName(String bwName);
}
