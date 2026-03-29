package com.lhd.tams.module.classroom.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教室新增参数")
@Data
public class ClassroomSaveDTO {

    @NotEmpty(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型: 1普通教室 2多媒体教室 3实验室 4操场")
    private Integer type;

    @NotNull(message = "容量不能为空")
    @Schema(description = "容量(人数)")
    private Integer capacity;

    @Schema(description = "可用时间段(格式: 08:00-12:00,14:00-18:00)")
    private String availableTime;
}
