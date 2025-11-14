package com.agileboot.domain.home.platform.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 内容平台表
 * </p>
 *
 * @author agileboot
 */
@Data
@TableName("home_platform")
@Schema(description = "内容平台实体")
public class HomePlatformEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "平台ID")
    @TableId(value = "platform_id", type = IdType.AUTO)
    private Long platformId;

    @Schema(description = "平台名称")
    @TableField("platform_name")
    private String platformName;

    @Schema(description = "排序顺序")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态（0停用 1启用）")
    @TableField("status")
    private Integer status;
}

