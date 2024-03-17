package com.example.springboot.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.Common.Result;
import com.example.springboot.Service.IMessageService;
import com.example.springboot.Service.IUserService;
import com.example.springboot.entity.Message;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @Auther: 吕宏博
 * @Date: 2024--03--02--15:40
 * @Description:
 */

@ApiModel(value="message留言对象", description="message留言方法")
@RestController
@RequestMapping("/message")
public class MessageController {
        @Autowired
        private IMessageService messageService;
        @Autowired
        private IUserService userService;
        /**
         * 新增/修改
         * message留言信息
         * */
        @ApiOperation(value = "保存留言信息")
        @PostMapping("/save")
        public Result save(@RequestBody Message message) {
                //Message.setUsername(getUser().getUsername);
                //留言名重复问题
                boolean  b= messageService.saveOrUpdate(message);
                if (b){
                        return Result.success(200,"保存留言信息成功",message); // 使用无参的 success() 方法
                }else
                {
                        return  Result.error();
                }
        }

        /**查询接口
         * 查询留言所有
         * @return
         */
        @ApiOperation(value = "用于指定查询条件type和展示所有留言信息")
        @PostMapping("/findAll")
        public Result findAll(@RequestParam(name = "type",defaultValue = "")String type){
                QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
                if (StringUtils.isNotEmpty(type)){
                        queryWrapper.eq("type",type);
                }
                return Result.success(200,"查询message中icon成功",messageService.list(queryWrapper));
        }

        /***删除接口，根据id进行删除
         *单个用户删除
         * @param id
         * @return
         * 创建一个名为 queryWrapper 的 QueryWrapper 对象，用于构建查询条件。
         * 使用 queryWrapper.eq("id", id) 方法设置查询条件，即根据 id 字段等于传入的 id 值进行查询。
         * 调用 userService.list(queryWrapper) 方法执行查询，并将结果存储在 list 变量中。
         * 如果查询结果为空列表（即没有找到匹配的记录），则返回一个带有错误信息的 Result 对象，表示留言信息已经被使用，无法删除。
         * 调用 roleService.removeById(id) 方法删除指定的留言信息。如果删除成功，返回一个带有成功信息的 Result 对象。
         * 如果删除失败，则返回一个默认的错误 Result 对象。
         */
        @PostMapping("/deleteById/{id}")
        @ApiOperation(value = "根据id删除留言信息")
        public Result delete(@PathVariable Integer id) {

                boolean delete = messageService.removeById(id);
                if(delete)
                {
                        return Result.success(200,"留言信息删除成功");
                } else
                {
                        return Result.error(405,"留言信息删除失败");
                }
        }
        /***
         *id批量删除留言
         * **/
        @PostMapping("/deleteBatch")
        @ApiOperation(value = "根据id批量删除留言信息")
        public Result deleteBatch(@RequestBody List<Integer> idList) {


                boolean delete = messageService.removeByIds(idList);
                if(delete)
                {
                        return Result.success(200, "删除留言信息成功");
                } else
                {
                        return Result.error(405, "删除留言信息失败");
                }
        }

        /**分页接口
         * 留言数据分页接口
         * @param pageNum
         * @param pageSize
         * @param name
         * @return
         */

        @GetMapping("/findPage")
        @ApiOperation(value = "message分页方法接口")
        //@Log(record ="查询留言分页",type = "留言查询方法")
        public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(name = "name",defaultValue = "") String name
        ) {
                Page<Message> page = new Page(pageNum,pageSize);
                QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
                if (StringUtils.isNotEmpty(name)) {
                        queryWrapper.like("name", name);
                }
                IPage<Message> rolePage = messageService.page(page, queryWrapper);
                return Result.success(200,"留言信息查询成功",rolePage);
        }
        /***追加评论方法
         *
         */
        @GetMapping("/tree")
        @ApiOperation(value = "获取留言树")
        public Result<List<Message>> getMessageTree() {
                List<Message> messagesTree = messageService.processMessagesToTree();
                return Result.success(messagesTree);
        }


 }
