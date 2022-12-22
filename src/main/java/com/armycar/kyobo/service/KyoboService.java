package com.armycar.kyobo.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.armycar.kyobo.entity.BookEntity;
import com.armycar.kyobo.entity.DetailEntity;
import com.armycar.kyobo.entity.ImageEntity;
import com.armycar.kyobo.entity.PublisherEntity;
import com.armycar.kyobo.entity.TranslatorEntity;
import com.armycar.kyobo.entity.ViewEntity;
import com.armycar.kyobo.entity.WriterEntity;
import com.armycar.kyobo.repoistory.BookRepository;
import com.armycar.kyobo.repoistory.DetailRepository;
import com.armycar.kyobo.repoistory.ImageRepository;
import com.armycar.kyobo.repoistory.PublisherRepository;
import com.armycar.kyobo.repoistory.TranslateRepository;
import com.armycar.kyobo.repoistory.WriterRepository;

@Service
public class KyoboService {
  @Autowired ImageRepository imageRepo;
  @Autowired BookRepository bookRepo;
  @Autowired DetailRepository detailRepo;
  @Autowired TranslateRepository translateRepo;
  @Autowired WriterRepository writerRepo;
  @Autowired PublisherRepository publisherRepo;
  
  public Map<String,Object> addTooImage(ImageEntity data, Long bimgBiSeq ) {
    Map <String, Object>  resultMap = new LinkedHashMap<String,Object>();
    data.setBimgBiSeq(bimgBiSeq);
    imageRepo.save(data);
    resultMap.put("status",true);
    resultMap.put("message","이미지가 저장되었습니다.");
    resultMap.put("code",HttpStatus.OK);
    return resultMap;
  }
  public String getFilenameByUri (String uri) {
    List<ImageEntity> data = imageRepo.findTopByBimgUriOrderByBimgSeqDesc(uri);
    return data.get(0).getBimgFilename();
  }

  public Map<String, Object> saveBookInfo(ViewEntity data){
    Map <String, Object> resultMap = new LinkedHashMap<String,Object>();
    if (data == null){
      resultMap.put("status",false);
      resultMap.put("message","결과가 존재하지않습니다");
      resultMap.put("code",HttpStatus.NOT_FOUND);
    }
    else if (!bookRepo.findByBiTitle(data.getBiTitle()).isEmpty()){
      resultMap.put("status",false);
      resultMap.put("message","이미 존재하는 책입니다");
      resultMap.put("code",HttpStatus.NOT_ACCEPTABLE);
    }
    else {
      BookEntity book = new BookEntity(data.getBiTitle(),data.getBiSubTitle(),data.getBiPrice(),data.getDisCount());
      book = validCheckSave(data,book);
      DetailEntity bookDetail = new DetailEntity();
      Date bdiRegDt = java.util.Date.from(data.getBdiRegDt().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
      bookDetail.setBdiRegDt(bdiRegDt);
      bookDetail.setBdiShow(data.getBdiShow());
      bookDetail.setBdiBiSeq(book.getBiSeq());
      detailRepo.save(bookDetail);
      
      resultMap.put("status",true);
      resultMap.put("message","추가했습니다");
      resultMap.put("code",HttpStatus.OK);
    }
    return resultMap;
  }
  public BookEntity validCheckSave(ViewEntity data, BookEntity book){
    TranslatorEntity trans = new TranslatorEntity();
    TranslatorEntity transtmp = translateRepo.findByBtName(data.getBtName());
    if (data.getBtName() == null || data.getBtName().equals("")){
      int i = 0;
    }
    else if(transtmp == null){
      trans.setBtName(data.getBtName());
      trans = translateRepo.save(trans);
      book.setBiBtSeq(trans.getBtSeq());
    }
    else{
      book.setBiBtSeq(transtmp.getBtSeq());
    }
    WriterEntity writer = new WriterEntity();
    WriterEntity writertmp = writerRepo.findByBwName(data.getBwName());
    if (writertmp == null){
      writer.setBwName(data.getBwName());
      writer = writerRepo.save(writer);
      book.setBiBwSeq(writer.getBwSeq());
    }
    else{
      book.setBiBwSeq(writertmp.getBwSeq());
    }

    PublisherEntity publisher = new PublisherEntity();
    PublisherEntity publishertmp = publisherRepo.findByBpName(data.getBpName());
    if (publishertmp == null){
      publisher.setBpName(data.getBwName());
      publisher = publisherRepo.save(publisher);
      book.setBiBpSeq(publisher.getBpSeq());
    }
    else{
      book.setBiBpSeq(publishertmp.getBpSeq());
    }
    book = bookRepo.save(book);
    return book;
  }

}
