package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Regions;
import org.example.database.entity.RegionStudentCount;
import org.example.database.service.RegionsService;
import org.example.database.mapper.RegionsMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_regions】的数据库操作Service实现
* @createDate 2025-06-28 22:15:57
*/
@Service
public class RegionsServiceImpl extends ServiceImpl<RegionsMapper, Regions>
    implements RegionsService{

    @Override
    public IPage<RegionStudentCount> selectRegionStudentCountByPage(IPage<RegionStudentCount> page, String regionName) {
        return baseMapper.selectRegionStudentCountByPage(page, regionName);
    }

}
