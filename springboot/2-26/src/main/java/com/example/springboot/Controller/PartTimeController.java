package com.example.springboot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.IPartTimeService;
import com.example.springboot.entity.PartTimeJob;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--05--9:29
 * @Description:
 */
@ApiModel(value="工作对象", description="工作方法")
@RestController
@RequestMapping("/job")
public class PartTimeController {
    @Autowired
    private IPartTimeService partTimeService;
    /**
     * 新增/修改
     * partTimeJob工作消息信息
     * */
    @ApiOperation(value = "根据id修改积分等级")
    @PostMapping("/save")
    public Result save(@RequestBody PartTimeJob partTimeJob) {
        //字典名重复问题
        boolean  b= partTimeService.saveOrUpdate(partTimeJob);
        if (b){
            return Result.success(200,"保存字典信息成功",partTimeJob); // 使用无参的 success() 方法
        }else
        {
            return  Result.error();
        }
    }

    /**查询接口
     * 查询工作消息所有
     * @return
     */
    @ApiOperation(value = "用于指定查询条件type")
    @PostMapping("/findAll")
    public Result findAll(){
        return Result.success(200,"查询partTimeJob中icon成功",partTimeService.list());
    }

    /***删除接口，根据id进行删除
     *单个工作消息删除
     * @param id
     * @return
     * 创建一个名为 queryWrapper 的 QueryWrapper 对象，用于构建查询条件。
     * 使用 queryWrapper.eq("id", id) 方法设置查询条件，即根据 id 字段等于传入的 id 值进行查询。
     * 调用 userService.list(queryWrapper) 方法执行查询，并将结果存储在 list 变量中。
     * 如果查询结果为空列表（即没有找到匹配的记录），则返回一个带有错误信息的 Result 对象，表示工作消息信息已经被使用，无法删除。
     * 调用 roleService.removeById(id) 方法删除指定的工作消息信息。如果删除成功，返回一个带有成功信息的 Result 对象。
     * 如果删除失败，则返回一个默认的错误 Result 对象。
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id修改积分等级")
    public Result delete(@PathVariable Integer id) {

        boolean delete = partTimeService.removeById(id);
        if(delete)
        {
            return Result.success(200,"字典信息删除成功");
        } else
        {
            return Result.error(405,"字典信息删除失败");
        }
    }
    /***
     *id批量删除工作消息
     * **/
    @PostMapping("/deleteBatch")
    @ApiOperation(value = "根据id修改积分等级")
    public Result deleteBatch(@RequestBody List<Integer> idList) {
        int successCount = 0; // 统计删除成功的条数

        boolean delete = partTimeService.removeByIds(idList);
        if(delete)
        {
            successCount = idList.size(); // 设置成功删除的条数为传递的id列表的长度
            return Result.success(200, "删除字典信息成功",successCount);
        } else
        {
            return Result.error(405, "删除字典信息失败");
        }
    }

    /**分页接口
     * 工作消息数据分页接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/findPage")
    @ApiOperation(value = "partTimeJob分页方法接口")
    //@Log(record ="查询字典分页",type = "字典查询方法")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(name = "name",defaultValue = "") String name
    ) {
        Page<PartTimeJob> page = new Page(pageNum,pageSize);
        QueryWrapper<PartTimeJob> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        IPage<PartTimeJob> rolePage = partTimeService.page(page, queryWrapper);
        return Result.success(200,"字典信息查询成功",rolePage);
    }
    /***
     * 根据关键字查询
     *
     */
    @ApiOperation(value = "根据工作表中关键字查询方法接口")
    @GetMapping("/title")
    public PartTimeJob findByTitle(@PathVariable String title) {
        QueryWrapper<PartTimeJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        return partTimeService.getOne(queryWrapper);
    }

//    @ApiOperation(value = "根据工作表中关键字查询方法接口")
//    @GetMapping("/job")
//    public PartTimeJob findByTitle(@RequestParam String title) {
//        QueryWrapper<PartTimeJob> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("title", title);
//        return partTimeService.getOne(queryWrapper);
//    }

    /***
     * 根据地点关键字查询
     *
     */
    @ApiOperation(value = "根据工作表中关键字查询方法接口")
    @GetMapping("/location/{location}")
    public PartTimeJob findByLocation(@PathVariable String location) {
        QueryWrapper<PartTimeJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("location",location);
        return partTimeService.getOne(queryWrapper);
    }

    /***
     * 根据薪资关键字查询
     *
     */
    @ApiOperation(value = "根据薪资查询兼职工作")
    @GetMapping("/salary/{salary}")
    public PartTimeJob findBySalary(@PathVariable Long salary) {
        QueryWrapper<PartTimeJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("salary", salary);
        return partTimeService.getOne(queryWrapper);
    }
}
