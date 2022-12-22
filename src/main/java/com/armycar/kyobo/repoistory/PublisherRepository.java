package com.armycar.kyobo.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armycar.kyobo.entity.PublisherEntity;

public interface PublisherRepository extends JpaRepository<PublisherEntity, Long>{
    PublisherEntity findByBpName(String bpName);
}
