package com.example.springboot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Log;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.INoticeService;
import com.example.springboot.entity.Notice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 吕宏博
 * @Date: 2024--02--25--14:07
 * @Description:
 */

@Api(value = "menu接口", tags = "公告管理相关的接口")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    /**
     * 新增/修改
     * Notice公告信息
     * */
    @ApiOperation(value = "根据id修改积分等级")
    @PostMapping("/save")
    public Result save(@RequestBody Notice notice) {
        //公告名重复问题
        boolean  b= noticeService.saveOrUpdate(notice);
        if (b){
            return Result.success(200,"保存公告信息成功",notice); // 使用无参的 success() 方法
        }else
        {
            return  Result.error();
        }
    }

    /**查询接口
     * 查询公告所有
     * @return
     */
    @ApiOperation(value = "用于指定查询条件type")
    @PostMapping("/findAll")
    public Result findAll(@RequestParam(name = "type",defaultValue = "")String type){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(200,"查询Notice中icon成功",noticeService.list(queryWrapper));
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

        boolean delete = noticeService.removeById(id);
        if(delete)
        {
            return Result.success(200,"公告信息删除成功");
        } else
        {
            return Result.error(405,"公告信息删除失败");
        }
    }
    /***
     *id批量删除角色
     * **/
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "根据id修改积分等级")
    public Result deleteBatch(@RequestBody List<Integer> idList) {


        boolean delete = noticeService.removeByIds(idList);
        if(delete)
        {

            return Result.success(200, "删除公告信息成功");
        } else
        {
            return Result.error(405, "删除公告信息失败");
        }
    }

    /**分页接口
     * 公告数据分页接口
     * @param pageNum
     * @param pageSize
     * @param user
     * @param title
     * @return
     */

    @GetMapping("/findPage")
    @ApiOperation(value = "Notice分页方法接口")
   // @Log(record ="查询公告分页",type = "公告查询方法")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "title",defaultValue = "") String title,
                           @RequestParam(name = "user",defaultValue = "") String user
    ) {
        Page<Notice> page = new Page(pageNum,pageSize);
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (StringUtils.isNotEmpty(user)) {
            queryWrapper.like("user", user);
        }
        IPage<Notice> rolePage = noticeService.page(page, queryWrapper);
        return Result.success(200,"公告信息查询成功",rolePage);
    }
}
