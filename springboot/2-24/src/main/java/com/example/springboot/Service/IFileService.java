package com.example.springboot.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.FilesClass;

import java.io.File;
import java.util.List;

/**
 * @Auther: LENOVO
 * @Date: 2024/02/22/16:06
 * @Description:
 */
public interface IFileService extends IService<FilesClass> {

    List<FilesClass> getByMD54(String md5);
}
