package com.lhd.tams.module.classroom.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教室列表返回结果")
@Data
public class ClassroomListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "停启用状态")
    private Integer enableState;

    @Schema(description = "类型: 1普通教室 2多媒体教室 3实验室 4操场")
    private Integer type;

    @Schema(description = "容量(人数)")
    private Integer capacity;

    @Schema(description = "可用时间段")
    private String availableTime;
}
