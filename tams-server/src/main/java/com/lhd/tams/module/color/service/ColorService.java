package com.lhd.tams.module.color.service;

import com.lhd.tams.module.color.model.vo.ColorListVO;

import java.util.List;

/**
 * @author lhd
 */
public interface ColorService {

    /**
     * 列表
     * @return
     */
    List<ColorListVO> getEffectiveList();
}
