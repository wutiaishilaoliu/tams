package com.lhd.tams.module.teacher.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教师新增参数")
@Data
public class TeacherSaveDTO {

    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String username;

    @Schema(description = "登录密码")
    private String password;
}
