-- 删除冗余字段的SQL脚本
-- 由于已经有专门的标签关联表，删除直接存储在内容表中的标签字段

-- 1. 删除 home_content 表中的 tags 字段
ALTER TABLE home_content DROP COLUMN IF EXISTS tags;

-- 2. 删除 home_banner 表中的 tags 字段
ALTER TABLE home_banner DROP COLUMN IF EXISTS tags;

-- 3. 如果存在的话，删除其他表中类似的冗余标签字段
-- ALTER TABLE other_table DROP COLUMN IF EXISTS tags;

-- 4. 更新现有数据，确保标签关联表中的数据完整性
-- 这部分将在代码层面处理，通过标签关联表来管理标签关系

-- 5. 可以添加一些清理脚本，删除孤立的无用数据
-- DELETE FROM home_tag WHERE tag_id NOT IN (SELECT DISTINCT tag_id FROM home_content_tag);

-- 6. 添加索引优化查询性能
CREATE INDEX IF NOT EXISTS idx_content_tag_content_id ON home_content_tag(content_id);
CREATE INDEX IF NOT EXISTS idx_content_tag_tag_id ON home_content_tag(tag_id);