package com.abocode.swagger2doc.word.web;

import com.abocode.swagger2doc.word.service.DocumentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by XiuYin.Cui on 2018/1/11.
 */
@Controller
@Api(tags = "Word API")
@RequestMapping("/v2")
public class Swagger2Controller {
    private String fileName = "Swagger2Word";
    @Autowired
    private DocumentService documentService;

    /**
     * 将 swagger 文档一键下载为 doc 文档
     *
     * @param model
     * @param url      需要转换成 word 文档的资源地址
     * @param response
     */
    @ApiOperation(value = "将 swagger json url地址转换成 word文档并下载")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功。")})
    @GetMapping(value = "/urlToWord")
    public void word(Model model, @ApiParam(value = "资源地址", example = "http://127.0.0.1:8089/v2/api-docs") @RequestParam String url, HttpServletResponse response) {
        Map<String, Object> result = documentService.getTableFromUrl(url);
        model.addAttribute("url", url);
        model.addAllAttributes(result);
        String content = documentService.processForContent(model.asMap());
        writeContentToResponse(content, response);
    }


    /**
     * 将 swagger json文件转换成 word文档并下载
     *
     * @param model
     * @param jsonFile 需要转换成 word 文档的swagger json文件
     * @param response
     * @return
     */
    @ApiOperation(value = "将 swagger json文件转换成 word文档并下载")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功。")})
    @PostMapping(value = "/fileToWord")
    public void getWord(Model model, @ApiParam("swagger json file") @Valid @RequestPart("jsonFile") MultipartFile jsonFile, HttpServletResponse response) {
        Map<String, Object> result = documentService.getTableFromJsonFile(jsonFile);
        fileName = jsonFile.getOriginalFilename();
        if (fileName != null) {
            fileName = fileName.replaceAll(".json", "");
        } else {
            fileName = "toWord";
        }
        model.addAttribute("url", "http://");
        model.addAllAttributes(result);
        String content = documentService.processForContent(model.asMap());
        writeContentToResponse(content, response);
    }

    /**
     * 将 swagger json字符串转换成 word文档并下载
     *
     * @param model
     * @param jsonStr  需要转换成 word 文档的swagger json字符串
     * @param response
     * @return
     */
    @ApiOperation(value = "将 swagger json字符串转换成 word文档并下载")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功。")})
    @PostMapping(value = "/strToWord")
    public void getWord(Model model, @ApiParam("swagger json string") @Valid @RequestBody String jsonStr, HttpServletResponse response) {
        Map<String, Object> result = documentService.parseSwagger2JsonForTableFrom(jsonStr);
        model.addAttribute("url", "http://");
        model.addAllAttributes(result);
        String content = documentService.processForContent(model.asMap());
        writeContentToResponse(content, response);
    }

    private void writeContentToResponse(String content, HttpServletResponse response) {
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName.concat(System.currentTimeMillis() + "") + ".doc", "utf-8"));
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
