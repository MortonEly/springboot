package com.example.springboot.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Mapper.FileMapper;
import com.example.springboot.Service.IFileService;
import com.example.springboot.entity.FilesClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @Auther: LENOVO
 * @Date: 2024/02/22/16:06
 * @Description:
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FilesClass> implements IFileService {
    @Autowired
    private FileMapper fileMapper;
    // 根据文件MD5查询文件信息
    @Override
    public List<FilesClass> getByMD54(String md5) {
        QueryWrapper<FilesClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",0);
        queryWrapper.eq("md5",md5);
       return this.baseMapper.selectList(queryWrapper);

    }
}
