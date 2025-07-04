package org.example.database.mapper;

import org.example.database.entity.Regions;
import org.example.database.entity.RegionStudentCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_regions】的数据库操作Mapper
* @createDate 2025-06-28 22:15:57
* @Entity org.example.database.entity.Regions
*/
public interface RegionsMapper extends BaseMapper<Regions> {

    /**
     * 分页查询地区信息并统计每个地区的学生数
     * @param page 分页对象
     * @param regionName 地区名称（可选过滤条件）
     * @return 地区学生统计信息的分页结果
     */
    IPage<RegionStudentCount> selectRegionStudentCountByPage(IPage<RegionStudentCount> page, @Param("regionName") String regionName);

}
