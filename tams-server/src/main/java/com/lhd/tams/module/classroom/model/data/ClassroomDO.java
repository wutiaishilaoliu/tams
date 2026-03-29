package com.lhd.tams.module.classroom.model.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lhd
 */
@Data
@TableName("t_classroom")
public class ClassroomDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 停启用状态
     */
    private Integer enableState;

    /**
     * 类型: 1普通教室 2多媒体教室 3实验室 4操场
     */
    private Integer type;

    /**
     * 容量(人数)
     */
    private Integer capacity;

    /**
     * 可用时间段(格式: 08:00-12:00,14:00-18:00)
     */
    private String availableTime;
}
