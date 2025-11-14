-- 稿件标签关联表
CREATE TABLE IF NOT EXISTS home_content_tag (
    id          bigint auto_increment comment '主键ID'
        primary key,
    content_id  bigint                   not null comment '内容ID',
    tag_id      bigint                   not null comment '标签ID',
    create_time datetime                 null comment '创建时间',
    deleted     tinyint(1)   default 0   not null comment '逻辑删除',
    unique key uk_content_tag (content_id, tag_id),
    index idx_content_id (content_id),
    index idx_tag_id (tag_id)
) comment '稿件标签关联表';