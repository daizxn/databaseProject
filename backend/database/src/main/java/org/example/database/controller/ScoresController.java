package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Scores;
import org.example.database.service.ScoresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoresController {
    @Resource
    private ScoresService scoresService;

    /**
     * 插入
     *
     * @param scores 信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Scores scores) {
        return scoresService.save(scores) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
    }

    /**
     * @param Id 删除对象的id
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteById/{Id}")
    @ResponseBody
    public Result deleteById(@PathVariable Integer Id) {
        if (Id == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return scoresService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param Ids 删除对象的Id列表
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteBatch")
    @ResponseBody
    public Result deleteBatch(@RequestBody List<Integer> Ids) {
        if (Ids == null || Ids.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return scoresService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PostMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Scores department) {
        return scoresService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Scores> scores) {
        return scoresService.updateBatchById(scores) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Scores department = scoresService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Scores> scoresList = scoresService.list();
        return scoresList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(scoresList);
    }


    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Scores scores,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Scores> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Scores::getStudentNumber, scores.getStudentNumber());
        queryWrapper.eq(Scores::getTeacherNumber, scores.getTeacherNumber());
        queryWrapper.eq(Scores::getCourseNumber, scores.getCourseNumber());
        IPage<Scores> page =new Page<>(pageNum,pageSize);
        IPage<Scores> scoresPage = scoresService.page(page, queryWrapper);
        if (scoresPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(scoresPage);
        }
    }
}
