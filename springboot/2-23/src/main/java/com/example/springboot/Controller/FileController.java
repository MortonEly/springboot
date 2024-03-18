package com.example.springboot.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Log;
import com.example.springboot.Common.NoAuth;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.IFileService;
import com.example.springboot.entity.FilesClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: LENOVO
 * @Date: 2024/02/22/16:09
 * @Description:
 */
@ApiModel(value="file字典对象", description="file字典方法")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService fileService;

    @Value("${files.upload.path}")
    private String fileUploadPath;
    /**
     * 新增/修改
     * 上传文件方法接口
     * */
    @ApiOperation(value = "根据id修改积分等级")
    @PostMapping("/save")
    public Result save(@RequestParam MultipartFile file, HttpServletResponse response) throws IOException {

        String url = null;//url
        String md5 = DigestUtil.md5Hex(file.getBytes());//md5
        List<FilesClass> ExistFile = fileService.getByMD54(md5);
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        long size = file.getSize();
        String uuid = UUID.randomUUID().toString();
        String fileUUID = uuid + "." + type;
        File uploadParentFile = new File(fileUploadPath);
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        File uploadFile = new File(fileUploadPath); // 初始化为空对象
        if (CollectionUtils.isNotEmpty(ExistFile)) {
            url = ExistFile.get(0).getUrl();
        } else {
            url = "http://localhost:9090/file/" + fileUUID;
            uploadFile = new File(uploadParentFile, fileUUID); // 创建新的上传文件对象// 创建新的上传文件对象
        }
        if (uploadFile != null) {
            file.transferTo(uploadFile);
        }
        // 存储到数据库
        FilesClass filesClass = new FilesClass();
        filesClass.setName(URLEncoder.encode(originalFilename, StandardCharsets.UTF_8.toString()));
        filesClass.setSize(size / 1024);
        filesClass.setType(type);
        filesClass.setUrl(url);
        filesClass.setMd5(md5);
        fileService.save(filesClass);
        // 进行数据库存储操作

        // 返回文件名前进行URL解码
        response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLDecoder.decode(originalFilename, StandardCharsets.UTF_8.toString()));

        return Result.success(200,"保存成功",url);
    }
//        String url = null; // url
//        String md5 = DigestUtil.md5Hex(file.getBytes()); // md5
//        List<FilesClass> ExistFile = fileService.getByMD54(md5);
//        String originalFilename = file.getOriginalFilename();
//        String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//        long size = file.getSize();
//        File uploadParentFile = new File(fileUploadPath);
//        if (!uploadParentFile.exists()) {
//            uploadParentFile.mkdirs();
//        }
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String fileUUID = uuid + "." + type;
//
//        // 创建存储文件的文件夹
//        File typeFolder = new File(uploadParentFile, type.toUpperCase());
//        if (!typeFolder.exists()) {
//            typeFolder.mkdirs();
//        }
//
//        File uploadFile = new File(typeFolder, fileUUID); // 文件保存路径改为对应后缀名文件夹中
//        if (!uploadFile.exists()) {
//            file.transferTo(uploadFile); // 如果文件不存在，则进行保存
//        }
//
//        if (CollectionUtils.isNotEmpty(ExistFile)) {
//            url = ExistFile.get(0).getUrl();
//        } else {
//            //todo
//          //  url = "http://localhost:9090/file/"+fileUploadPath+"/file/" + type.toUpperCase() + "/" + fileUUID;
//            url = fileUploadPath + type.toUpperCase() + "/" + fileUUID;
//        }
//
//        // 存储到数据库
//        FilesClass filesClass = new FilesClass();
//        filesClass.setName(URLEncoder.encode(originalFilename, "UTF-8"));
//        filesClass.setSize(size / 1024);
//        filesClass.setType(type);
//        filesClass.setUrl(url);
//        filesClass.setMd5(md5);
//        fileService.save(filesClass);
//
//        // 返回文件名前进行URL解码
//        response.addHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLDecoder.decode(originalFilename, "UTF-8"));
//
//        return Result.success(200, "文件保存成功", url);
//    }


    /**
     * 下载文件
     * @param
     */
    @GetMapping("/download/{fileUUID}")
    @NoAuth
    public void download(@PathVariable String fileUUID, HttpServletResponse response, HttpServletRequest request) {
        File downloadFile = new File(fileUploadPath + fileUUID);
        try {
            FileInputStream fileInputStream = new FileInputStream(downloadFile);
            // 设置输出流的格式
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
            //作用是使客户端浏览器区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
            response.setContentType("application/octet-stream");  //.*（ 二进制流，不知道下载文件类型）
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        // 根据 fileUUID 查询数据库，获取文件信息
//        FilesClass filesClass = new FilesClass();
//        if (filesClass == null) {
//            // 文件不存在，返回错误信息
//            // TODO: 返回文件不存在的错误信息
//            return;}
//
//        File downloadFile = new File(fileUploadPath + fileUUID);
//        String filename = filesClass.getName();
//        try {
//            FileInputStream fileInputStream = new FileInputStream(downloadFile);
//            // 设置输出流的格式
//            response.setCharacterEncoding("UTF-8");
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setContentType("application/octet-stream");
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            filename = filenameEncoding(filename, request);
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//
//            ServletOutputStream outputStream = response.getOutputStream();
//            int len = 0;
//            byte[] bytes = new byte[1024];
//            while((len = fileInputStream.read(bytes)) != -1){
//                outputStream.write(bytes,0,len);
//                outputStream.flush();
//            }
//            outputStream.flush();
//            outputStream.close();
//            fileInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private String filenameEncoding(String filename, HttpServletRequest request) throws UnsupportedEncodingException {
//// 获得请求头中的User-Agent
//        String agent = request.getHeader("User-Agent");
//        // 根据不同的客户端进行不同的编码
//
//        if (agent.contains("MSIE")) {
//            // IE浏览器
//            filename = URLEncoder.encode(filename, "utf-8");
//        } else if (agent.contains("Firefox")) {
//            // 火狐浏览器
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
//        } else {
//            // 其它浏览器
//            filename = URLEncoder.encode(filename, "utf-8");
//        }
//        return filename;
//    }

    /**查询接口
     * 查询菜单所有
     * @return
     */
    @ApiOperation(value = "用于指定查询条件type")
    @PostMapping("/findAll")
    public Result findAll(@RequestParam(name = "type",defaultValue = "")String type){
        QueryWrapper<FilesClass> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(200,"查询dict中icon成功",fileService.list(queryWrapper));
    }

    /***删除接口，根据id进行删除
     *单个用户删除
     * @param id
     * @return
     * 创建一个名为 queryWrapper 的 QueryWrapper 对象，用于构建查询条件。
     * 使用 queryWrapper.eq("id", id) 方法设置查询条件，即根据 id 字段等于传入的 id 值进行查询。
     * 调用 userService.list(queryWrapper) 方法执行查询，并将结果存储在 list 变量中。
     * 如果查询结果为空列表（即没有找到匹配的记录），则返回一个带有错误信息的 Result 对象，表示角色信息已经被使用，无法删除。
     * 调用 roleService.removeById(id) 方法删除指定的角色信息。如果删除成功，返回一个带有成功信息的 Result 对象。
     * 如果删除失败，则返回一个默认的错误 Result 对象。
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id修改积分等级")
    public Result delete(@PathVariable Integer id) {
        FilesClass filesClass = fileService.getById(id);
        filesClass.setIsDelete(1);
        boolean delete = fileService.updateById(filesClass);
        if(delete)
        {
            return Result.success(200,"字典信息删除成功");
        } else
        {
            return Result.error(405,"字典信息删除失败");
        }
    }
    /***
     *id批量删除角色
     * **/
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "根据id修改积分等级")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
       for (Integer id:idList){
           FilesClass filesClass = fileService.getById(id);
           filesClass.setIsDelete(1);
           fileService.updateById(filesClass);
       }

        boolean delete = fileService.removeByIds(idList);
        if(delete)
        {
            return Result.success(200, "删除字典信息成功");
        } else
        {
            return Result.error(405, "删除字典信息失败");
        }
    }

    /***
     *文件状态改变
     * **/
    @PostMapping("/updateEnable")
    @ApiOperation(value = "文件状态改变")
    public Result updateEnable(@RequestBody FilesClass filesClass) {
        boolean delete = fileService.updateById(filesClass);
        if(delete)
        {
            return Result.success(200, "删除字典信息成功");
        } else
        {
            return Result.error(405, "删除字典信息失败");
        }
    }

    /**分页接口
     * 菜单数据分页接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/findPage")
    @ApiOperation(value = "dict分页方法接口")
    @Log(record ="查询文件分页",type = "文件查询方法")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name
    ) {
        Page<FilesClass> page = new Page(pageNum,pageSize);
        QueryWrapper<FilesClass> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        IPage<FilesClass> filePage = fileService.page(page, queryWrapper);
        return Result.success(200,"字典信息查询成功",filePage);
    }
}
