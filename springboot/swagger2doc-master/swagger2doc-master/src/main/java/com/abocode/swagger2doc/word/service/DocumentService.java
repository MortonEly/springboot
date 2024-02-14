package com.abocode.swagger2doc.word.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by XiuYin.Cui on 2018/1/12.
 */
public interface DocumentService {

    Map<String,Object> getTableFromUrl(String swaggerUrl);

    Map<String, Object> parseSwagger2JsonForTableFrom(String jsonStr);

    Map<String, Object> getTableFromJsonFile(MultipartFile jsonFile);

    String processForContent(Map<String, Object> asMap);

    Map<String, Object> getFromSwagger3Url(String url);

    Map<String, Object> getTableFromSwagger3JsonFile(MultipartFile jsonFile);

    Map<String, Object> parseSwagger3JsonForTableFrom(String jsonStr);
}
