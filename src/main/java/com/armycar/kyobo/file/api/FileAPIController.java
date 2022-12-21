package com.armycar.kyobo.file.api;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.armycar.kyobo.entity.ImageEntity;
import com.armycar.kyobo.service.KyoboService;

@RestController
public class FileAPIController {
  @Autowired KyoboService kService;
  @Value("${file.image.book}") String kyobo_img_path;
  @PutMapping ("/upload")
  public ResponseEntity<Object> putImageUpload (@RequestPart MultipartFile file, @RequestParam Long seq) {
    Map <String,Object> map = new LinkedHashMap<String, Object>();
    Path folderLocation = Paths.get(kyobo_img_path);
    String originFileName = file.getOriginalFilename();
    String [] split = originFileName.split("\\.");
    String ext = split[split.length - 1];
    String filename = "";
    for (int i = 0; i<split.length-1; i++) {
      filename += split[i];
    }
    String saveFilename = "book_";
    Calendar c = Calendar.getInstance();
    saveFilename += c.getTimeInMillis()+"."+ext;
    Path targetFile = folderLocation.resolve(saveFilename);
    try{
    Files.copy(file.getInputStream(), targetFile,StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ImageEntity data = new ImageEntity();
    data.setBimgFilename(saveFilename);
    data.setBimgUri(filename);
    kService.addTooImage(data, seq);
    return new ResponseEntity<>(map,HttpStatus.OK);
  }
  @GetMapping("/image/{uri}")
  public ResponseEntity<Resource> getImage (@PathVariable String uri, HttpServletRequest request) throws Exception {
    Path folderLocation = Paths.get(kyobo_img_path);
    String filename = kService.getFilenameByUri(uri);
    String[] split = filename.split("\\.");
    String ext = split[split.length-1];
    String exportName = uri+"."+ext;
    Path targetFile = folderLocation.resolve(filename);
    Resource r = null; 
      try { r = new UrlResource(targetFile.toUri()); }
      catch(Exception e) { e.printStackTrace(); } 
      String contentType = null;
    try { contentType = request.getServletContext().getMimeType(r.getFile().getAbsolutePath());
    if(contentType == null) { 
      
      contentType = "application/octet-stream"; } }
      catch(Exception e) { e.printStackTrace(); } 
      return ResponseEntity.ok()
    .contentType(MediaType.parseMediaType(contentType)) 
    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+URLEncoder.encode(exportName, "UTF-8")+"\"")
    .body(r);
    }

  }
