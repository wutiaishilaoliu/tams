package com.lhd.tams.module.coursescheduling.model.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author lhd
 */
@Data
@TableName("t_course_scheduling")
public class CourseSchedulingDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教室id
     */
    private Long classroomId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 上课时间
     */
    private LocalTime attendTime;

    /**
     * 下课时间
     */
    private LocalTime finishTime;

    /**
     * 班级id列表（临时字段，不保存到数据库）
     */
    @TableField(exist = false)
    private List<Long> classIdList;
}
