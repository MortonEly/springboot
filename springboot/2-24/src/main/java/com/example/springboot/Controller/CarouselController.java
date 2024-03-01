package com.example.springboot.Controller;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--01--10:03
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.ICarouselService;
import com.example.springboot.entity.Carousel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiModel(value="carousel轮播图对象", description="carousel轮播图方法")
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private ICarouselService carouselService;
    /**
     * 新增/修改
     * carousel菜单信息
     * */
    @ApiOperation(value = "根据id修改积分等级")
    @PostMapping("/save")
    public Result save(@RequestBody Carousel carousel) {
        //轮播图名重复问题
        boolean  b= carouselService.saveOrUpdate(carousel);
        if (b){
            return Result.success(200,"保存轮播图信息成功",carousel); // 使用无参的 success() 方法
        }else
        {
            return  Result.error();
        }
    }

    /**查询接口
     * 查询菜单所有
     * @return
     */
    @ApiOperation(value = "用于指定查询条件type")
    @PostMapping("/findAll")
    public Result findAll(@RequestParam(name = "type",defaultValue = "")String type){
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(type)){
            queryWrapper.eq("type",type);
        }
        return Result.success(200,"查询carousel中icon成功",carouselService.list(queryWrapper));
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

        boolean delete = carouselService.removeById(id);
        if(delete)
        {
            return Result.success(200,"轮播图信息删除成功");
        } else
        {
            return Result.error(405,"轮播图信息删除失败");
        }
    }
    /***
     *id批量删除角色
     * **/
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "根据id修改积分等级")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
        int successCount = 0; // 统计删除成功的条数

        boolean delete = carouselService.removeByIds(idList);
        if(delete)
        {
            successCount = idList.size(); // 设置成功删除的条数为传递的id列表的长度
            return Result.success(200, "删除轮播图信息成功",successCount);
        } else
        {
            return Result.error(405, "删除轮播图信息失败");
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
    @ApiOperation(value = "carousel分页方法接口")
   // @Log(record ="查询轮播图分页",type = "轮播图查询方法")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name
    ) {
        Page<Carousel> page = new Page(pageNum,pageSize);
        QueryWrapper<Carousel> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        IPage<Carousel> carouselIPage = carouselService.page(page, queryWrapper);
        return Result.success(200,"轮播图信息查询成功",carouselIPage);
    }
}
