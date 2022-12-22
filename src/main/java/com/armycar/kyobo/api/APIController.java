package com.armycar.kyobo.api;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.armycar.kyobo.entity.ViewEntity;
import com.armycar.kyobo.repoistory.BookRepository;
import com.armycar.kyobo.repoistory.DetailRepository;
import com.armycar.kyobo.repoistory.ViewRepository;
import com.armycar.kyobo.service.KyoboService;
import com.armycar.kyobo.vo.ViewDetailInfoVO;

//home, booklist, member, detail
@RestController
@RequestMapping("/api")
public class APIController {
  @Autowired BookRepository bookRepo; 
  @Autowired DetailRepository detailRepo;
  @Autowired ViewRepository viewRepo;
  @Autowired KyoboService kyoboService;

  @GetMapping("/book/list/")
  public Map<String, Object> getBookList(
    @PageableDefault(size=5) Pageable pageable
  ) {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    
    Page<ViewEntity> page = viewRepo.getBookList(pageable);
    List<ViewDetailInfoVO> list = new ArrayList<ViewDetailInfoVO>() ;
    for(ViewEntity data : page.getContent()) {
      ViewDetailInfoVO obj = new ViewDetailInfoVO();
      obj.copyValues(data);
      list.add(obj);
    }
    resultMap.put("total", page.getTotalPages());
    resultMap.put("curentpage",page.getNumber());
    resultMap.put("list",list);

    return resultMap;
  }
  // 타이틀 특정키워드로 검색기능 
  @GetMapping("book/search")
  public Map<String, Object> searchBookTitleList(
    @RequestParam @Nullable String keyword,
    @PageableDefault(size=5) Pageable pageable
  ) {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    List<ViewEntity> page = viewRepo.searchBookTitle(keyword, pageable);

    // viewDetailInfo에 숫자 포맷팅 있음
    List<ViewDetailInfoVO> list = new ArrayList<ViewDetailInfoVO>();
    for(ViewEntity data : page) {
      ViewDetailInfoVO obj = new ViewDetailInfoVO();
      obj.copyValues(data);
      list.add(obj);
    }
    resultMap.put("totalPage", viewRepo.getBookPageCount(keyword));
    resultMap.put("totalCount", list.size()); // 추가
    resultMap.put("list",list);
    return resultMap;
  }
  // 작가이름으로 검색기능
  @GetMapping("writer/search")
  public Map<String, Object> searchWriterNameList(
    @RequestParam @Nullable String keyword,
    @PageableDefault(size=5) Pageable pageable
  ) {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    List<ViewEntity> page = viewRepo.searchWriterName(keyword, pageable);

    // viewDetailInfo에 숫자 포맷팅 있음
    List<ViewDetailInfoVO> list = new ArrayList<ViewDetailInfoVO>();
    for(ViewEntity data : page) {
      ViewDetailInfoVO obj = new ViewDetailInfoVO();
      obj.copyValues(data);
      list.add(obj);
    }
    resultMap.put("totalPage", viewRepo.getWriterPageCount(keyword));
    resultMap.put("totalCount", list.size()); // 추가
    resultMap.put("list",list);
    return resultMap;
  }
  // 가격별 정렬

    @PutMapping("/book/add")
    public Map<String, Object> getBookInfo(
       @RequestBody ViewEntity data
    ){
     Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
     resultMap = kyoboService.saveBookInfo(data);
     return resultMap;
  }
}

