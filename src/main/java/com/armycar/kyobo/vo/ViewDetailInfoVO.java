package com.armycar.kyobo.vo;

import java.text.NumberFormat;
import java.time.LocalDate;

import com.armycar.kyobo.entity.ViewEntity;

import lombok.Data;

@Data
public class ViewDetailInfoVO {
    private Long biSeq;
    private String biTitle;
    private String biSubTitle;
    private String biPrice;
    private Double disCount;
    private String disCountPrice;
    private String  bwName;
    private String  bpName;
    private LocalDate  bdiRegDt;
    private String bdiShow;
    private String btName;
    private String bimgUri;
    // private String strPrice;
    // private String strDiscount;
    public void copyValues(ViewEntity source) {
        NumberFormat f = NumberFormat.getInstance();
        this.biSeq = source.getBiSeq();
        this.biTitle = source.getBiTitle();
        this.biSubTitle = source.getBiSubTitle();
        this.biPrice = f.format(source.getBiPrice());
        this.disCount = source.getDisCount();
        this.disCountPrice = f.format(source.getDisCountPrice());
        this.bwName = source.getBwName();
        this.bpName = source.getBpName();
        this.bdiRegDt = source.getBdiRegDt();
        this.bdiShow = source.getBdiShow();
        this.btName = source.getBtName();
        this.bimgUri = source.getBimgUri();

        // strPrice = f.format(biPrice);
        // strDiscount = f.format(disCountPrice);
    }
}
