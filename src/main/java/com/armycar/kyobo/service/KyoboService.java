package com.armycar.kyobo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.armycar.kyobo.entity.ImageEntity;
import com.armycar.kyobo.repoistory.ImageRepository;

@Service
public class KyoboService {
  @Autowired ImageRepository imageRepo;
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


}
