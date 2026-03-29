package com.lhd.tams.module.coursescheduling.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lhd.tams.module.coursescheduling.model.data.CourseSchedulingDO;
import com.lhd.tams.module.coursescheduling.model.vo.ClassroomUsageReportVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingExportVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingReportVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public interface CourseSchedulingMapper extends BaseMapper<CourseSchedulingDO> {

    /**
     * 列表
     * @param queryWrapper
     * @return
     */
    List<CourseSchedulingListVO> selectCourseSchedulingList(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    /**
     * 详情
     * @param id
     * @return
     */
    CourseSchedulingListVO selectCourseSchedulingById(Long id);

    /**
     * 数量
     * @param queryWrapper
     * @return
     */
    List<Map<String, String>> selectCourseSchedulingCourseCount(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    /**
     * 日期范围
     * @param dateList
     * @param classroomId
     * @return
     */
    List<String> selectTimePeriodByDateRange(@Param("dateList") List<LocalDate> dateList,
                                             @Param("classroomId") Long classroomId,
                                             @Param("teacherId") Long teacherId,
                                             @Param("classId") Long classId);

    /**
     * 日期范围
     * @param startDate
     * @param endDate
     * @param classroomId
     * @return
     */
    List<CourseSchedulingExportVO> selectByDateRange(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate,
                                                     @Param("classroomId") Long classroomId,
                                                     @Param("teacherId") Long teacherId,
                                                     @Param("classId") Long classId);

    /**
     * 教师数量
     * @param startDate
     * @param endDate
     * @return
     */
    List<CourseSchedulingReportVO> selectReportTeacherCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 课程数量
     * @param startDate
     * @param endDate
     * @return
     */
    List<CourseSchedulingReportVO> selectReportCourseCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 教室使用情况
     * @param startDate
     * @param endDate
     * @return
     */
    List<ClassroomUsageReportVO> selectReportClassroomUsage(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 根据班级ID查询排课列表（通过关联表）
     * @param classId 班级ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    List<CourseSchedulingListVO> selectCourseSchedulingListByClassId(@Param("classId") Long classId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
