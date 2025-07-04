package org.example.database.service;

import org.example.database.entity.Regions;
import org.example.database.entity.RegionStudentCount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author daizxn
* @description 针对表【daizx_regions】的数据库操作Service
* @createDate 2025-06-28 22:15:57
*/
public interface RegionsService extends IService<Regions> {

    /**
     * 分页查询地区信息并统计每个地区的学生数
     * @param page 分页对象
     * @param regionName 地区名称（可选过滤条件）
     * @return 地区学生统计信息的分页结果
     */
    IPage<RegionStudentCount> selectRegionStudentCountByPage(IPage<RegionStudentCount> page, String regionName);

}
