package com.agileboot.domain.home.content.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 稿件标签关联实体
 *
 * @author agileboot
 */
@Data
@TableName("home_content_tag")
@Schema(description = "稿件标签关联实体")
public class HomeContentTagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "内容ID")
    @TableField("content_id")
    private Long contentId;

    @Schema(description = "标签ID")
    @TableField("tag_id")
    private Long tagId;
}