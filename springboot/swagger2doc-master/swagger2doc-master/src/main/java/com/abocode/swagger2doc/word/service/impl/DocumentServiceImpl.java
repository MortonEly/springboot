package com.abocode.swagger2doc.word.service.impl;

import com.abocode.swagger2doc.utils.JsonUtils;
import com.abocode.swagger2doc.word.model.*;
import com.abocode.swagger2doc.word.service.Swagger2ApiParser;
import com.abocode.swagger2doc.word.service.Swagger3ApiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.abocode.swagger2doc.word.service.DocumentService;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XiuYin.Cui
 **/
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Swagger2ApiParser swagger2ApiParser;
    @Autowired
    private Swagger3ApiParser swagger3ApiParser;


    @Override
    public Map<String, Object> getTableFromUrl(String swaggerUrl) {
        String jsonStr = restTemplate.getForObject(swaggerUrl, String.class);
        return parseSwagger2JsonForTableFrom(jsonStr);
    }

    @Override
    public Map<String, Object> getFromSwagger3Url(String swaggerUrl) {
        String jsonStr = restTemplate.getForObject(swaggerUrl, String.class);
        return parseSwagger3JsonForTableFrom(jsonStr);
    }

    @Override
    public Map<String, Object> getTableFromSwagger3JsonFile(MultipartFile jsonFile) {
        try {
            String jsonStr = new String(jsonFile.getBytes(), StandardCharsets.UTF_8);
            return parseSwagger3JsonForTableFrom(jsonStr);
        } catch (Exception e) {
            log.error("parse error", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> parseSwagger3JsonForTableFrom(String jsonStr) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = JsonUtils.readValue(jsonStr, HashMap.class);
        TreeMap<String, List<Table>> treeMap = getTreeMap(map);
        resultMap.put("tableMap", treeMap);
        resultMap.put("info", map.get("info"));
        return resultMap;
    }

    private TreeMap<String, List<Table>> getTreeMap(Map<String, Object> map) {

        Map<String, Attribute> definitionMap = swagger3ApiParser.parseOpenApiDefinitions(map);
        //解析paths
        Map<String, Map<String, Object>> paths = (Map<String, Map<String, Object>>) map.get("paths");
        List<Table> result = swagger3ApiParser.buildDefinitionsAndProcessByPath(paths, definitionMap);
        Map<String, List<Table>> tableMap = result.stream().parallel().collect(Collectors.groupingBy(Table::getTitle));
        return new TreeMap<>(tableMap);
    }


    @Override
    public Map<String, Object> parseSwagger2JsonForTableFrom(String jsonStr) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = JsonUtils.readValue(jsonStr, HashMap.class);
        List<Table> result = swagger2ApiParser.getResult(map);
        Map<String, List<Table>> tableMap = result.stream().parallel().collect(Collectors.groupingBy(Table::getTitle));
        resultMap.put("tableMap", new TreeMap<>(tableMap));
        resultMap.put("info", map.get("info"));
        return resultMap;
    }

    @Override
    public Map<String, Object> getTableFromJsonFile(MultipartFile jsonFile) {
        try {
            String jsonStr = new String(jsonFile.getBytes(), StandardCharsets.UTF_8);
            return parseSwagger2JsonForTableFrom(jsonStr);
        } catch (Exception e) {
            log.error("parse error", e);
        }
        return null;
    }

    @Override
    public String processForContent(Map<String, Object> asMap) {
        Context context = new Context();
        context.setVariables(asMap);
        return springTemplateEngine.process("word", context);
    }
}
