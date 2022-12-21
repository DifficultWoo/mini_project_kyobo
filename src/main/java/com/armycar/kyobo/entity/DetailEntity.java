package com.armycar.kyobo.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Immutable

@Table(name = "book_detail_info")
@Entity
public class DetailEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bdi_seq") private Long bdiSeq;
    @Column(name = "bdi_bi_seq") private Long bdiBiSeq;
    @Column(name = "bdi_reg_dt") private Date bdiRegDt;
    @Column(name = "bdi_show") private String bdiShow;
}
