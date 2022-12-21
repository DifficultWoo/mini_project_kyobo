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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.armycar.kyobo.entity.ViewEntity;
import com.armycar.kyobo.repoistory.BookRepository;
import com.armycar.kyobo.repoistory.DetailRepository;
import com.armycar.kyobo.repoistory.ViewRepository;
import com.armycar.kyobo.vo.ViewDetailInfoVO;

//home, booklist, member, detail
@RestController
@RequestMapping("/api")
public class APIController {
  @Autowired BookRepository bookRepo; 
  @Autowired DetailRepository detailRepo;
  @Autowired ViewRepository viewRepo;

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
  
  @GetMapping("book/search")
  public Map<String, Object> searchBookTitleList(
    @RequestParam @Nullable String keyword
  ) {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    if(keyword == null) keyword ="";
 
    // resultMap.put("keyword", keyword);
    List<ViewEntity> search = viewRepo.searchBookTitle(keyword);
    List<ViewDetailInfoVO> list = new ArrayList<ViewDetailInfoVO>() ;
    for(ViewEntity data : search) {
      ViewDetailInfoVO obj = new ViewDetailInfoVO();
      obj.copyValues(data);
      list.add(obj);
    }
    resultMap.put("totalCount", list.size()); // 추가
    resultMap.put("list",list);
    return resultMap;
  }
  @GetMapping("book/price/") // 가격별로 정렬
  public Map<String, Object> getPrice(
    @PageableDefault(size=5) Pageable pageable
  ) {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

    
    Page<ViewEntity> page = viewRepo.getPriceList(pageable);
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
}

