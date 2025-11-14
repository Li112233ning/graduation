-- 检查所有分类数据，包括逻辑删除状态
SELECT category_id, category_name, icon_name, sort_order, status, deleted, create_time
FROM home_category
ORDER BY category_id;

-- 检查是否有重复的分类名称
SELECT category_name, COUNT(*) as count
FROM home_category
WHERE deleted = 0
GROUP BY category_name
HAVING COUNT(*) > 1;

-- 检查美食分类的具体情况
SELECT * FROM home_category
WHERE category_name = '美食';