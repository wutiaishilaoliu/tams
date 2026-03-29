package com.lhd.tams.module.color.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.tams.module.color.dao.ColorMapper;
import com.lhd.tams.module.color.model.data.ColorDO;
import com.lhd.tams.module.color.model.vo.ColorListVO;
import com.lhd.tams.module.color.service.ColorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lhd
 */
@Service
public class ColorServiceImpl extends ServiceImpl<ColorMapper, ColorDO> implements ColorService {

    @Override
    public List<ColorListVO> getEffectiveList() {

        // 直接返回所有颜色，按值排序
        // 原逻辑排除了已经被课程使用的颜色，可能导致在演示环境或数据较少时下拉列表为空
        LambdaQueryWrapper<ColorDO> queryWrapper = Wrappers.<ColorDO>lambdaQuery()
                .orderByAsc(ColorDO::getValue);

        List<ColorDO> colorDOList = list(queryWrapper);
        return colorDOList.stream().map(colorDO -> {
            ColorListVO vo = new ColorListVO();
            vo.setId(colorDO.getId());
            vo.setName(colorDO.getName());
            vo.setValue(colorDO.getValue());
            return vo;
        }).collect(java.util.stream.Collectors.toList());
    }
}
