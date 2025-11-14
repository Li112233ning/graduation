package com.agileboot.domain.home.tag.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author agileboot
 */
@Getter
@Setter
@TableName("home_tag")
@Schema(description = "标签实体")
public class HomeTagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "标签ID")
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Long tagId;

    @Schema(description = "标签名称")
    @TableField("tag_name")
    private String tagName;

    @Schema(description = "排序号")
    @TableField("sort_order")
    private Integer sortOrder;

    @Schema(description = "状态（0禁用 1启用）")
    @TableField("status")
    private Integer status;
}