package com.armycar.kyobo.entity;

import java.time.LocalDate;


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
@Entity
@Immutable
@Table(name = "book_all_info")
public class ViewEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bi_seq")                private Long biSeq;
  @Column(name = "bi_title")              private String biTitle;
  @Column(name = "bi_sub_title")          private String biSubTitle;
  @Column(name = "bi_price")              private Integer biPrice;
  @Column(name = "discount")              private Double disCount;
  @Column(name = "discountprice")         private Integer disCountPrice;
  @Column(name = "bw_name")               private String  bwName;
  @Column(name = "bp_name")               private String  bpName;
  @Column(name = "bdi_reg_dt")            private LocalDate  bdiRegDt;
  @Column(name = "bdi_show")              private String bdiShow;
  @Column(name = "bt_name")               private String btName;
  @Column(name = "bimg_uri")               private String bimgUri;
}
