package com.sky.controller.admin;

/*
 * @Auther:fz
 * @Date:2025/7/31
 * @Description:
 */

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理接口")
public class SetMealController {

    @Autowired
    SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> insert(@RequestBody SetmealDTO setmealDTO){
        setmealService.insert(setmealDTO);
        return Result.success("新增套餐成功");
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult>page(SetmealPageQueryDTO setmealPageQueryDTO){
         PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
         return Result.success(pageResult);
    }


    @DeleteMapping
    @ApiOperation("批量删除")
    public Result<String> delete(@RequestParam List<Long> ids){
        setmealService.delete(ids);
        return Result.success("删除成功");
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success("修改成功");
    }


    @PostMapping("/status/{status}")
    @ApiOperation("批量停售/起售")
    public Result<String> updateStatus(@PathVariable Integer status,Long id){
        setmealService.updateStatus(status,id);
        return Result.success("修改成功");

    }
}
