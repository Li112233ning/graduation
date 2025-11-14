-- 为 home_content 表添加达人ID字段和审核状态字段
ALTER TABLE home_content 
ADD COLUMN influencer_id bigint NULL COMMENT '所属达人ID' AFTER platform,
ADD COLUMN audit_status smallint DEFAULT 0 NOT NULL COMMENT '审核状态（0待审核 1审核通过 2审核拒绝）' AFTER status,
ADD COLUMN audit_remark varchar(512) NULL COMMENT '审核备注' AFTER audit_status;

-- 更新现有数据，为部分稿件设置达人ID（示例）
UPDATE home_content SET influencer_id = 1 WHERE content_id IN (1, 4);
UPDATE home_content SET influencer_id = 2 WHERE content_id IN (2, 6);
UPDATE home_content SET influencer_id = 3 WHERE content_id IN (3, 7);

-- 更新现有数据，设置审核状态（示例：部分通过，部分待审核）
UPDATE home_content SET audit_status = 1 WHERE content_id IN (1, 2, 3, 4);
UPDATE home_content SET audit_status = 0 WHERE content_id IN (5, 6, 7, 8);

