package com.example.springboot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.FilesClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @Auther: 吕宏博
 * @Date: 2024--03--01--10:05
 * @Description:    文件
 */
@Mapper
public interface FileMapper extends BaseMapper <FilesClass>{
  //  int updateBatchById(List<FilesClass> files);

    // 保存文件
    void save(FilesClass filesClass);

    // 根据文件MD5查询文件信息
    List<FilesClass> getByMD54(String md5);

    // 根据UUID查询文件信息
    FilesClass getByUUID(String uuid);
}
