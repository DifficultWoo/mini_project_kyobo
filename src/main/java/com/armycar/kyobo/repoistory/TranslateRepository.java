package com.armycar.kyobo.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armycar.kyobo.entity.TranslatorEntity;

public interface TranslateRepository extends JpaRepository<TranslatorEntity, Long>{
    TranslatorEntity findByBtName(String btName);
}
