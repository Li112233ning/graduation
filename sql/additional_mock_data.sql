-- 增加更多的模拟数据

-- 增加更多标签数据
INSERT INTO home_tag (tag_id, tag_name, sort_order, status, create_time, update_time, creator_id, updater_id, deleted) VALUES
(11, '生活分享', 11, 1, NOW(), NOW(), 1, 1, 0),
(12, '职场经验', 12, 1, NOW(), NOW(), 1, 1, 0),
(13, '学习笔记', 13, 1, NOW(), NOW(), 1, 1, 0),
(14, '亲子教育', 14, 1, NOW(), NOW(), 1, 1, 0),
(15, '健康养生', 15, 1, NOW(), NOW(), 1, 1, 0),
(16, '家居装修', 16, 1, NOW(), NOW(), 1, 1, 0),
(17, '宠物日常', 17, 1, NOW(), NOW(), 1, 1, 0),
(18, '摄影技巧', 18, 1, NOW(), NOW(), 1, 1, 0),
(19, '音乐推荐', 19, 1, NOW(), NOW(), 1, 1, 0),
(20, '电影影评', 20, 1, NOW(), NOW(), 1, 1, 0);

-- 增加更多美食类内容数据
INSERT INTO home_content (content_id, title, category_id, image_url, images, likes_count, comments_count, views_count, shares_count, content_type, platform, description, status, influencer_id, audit_status, audit_remark, creator_id, create_time, updater_id, update_time, deleted) VALUES
-- 美食类内容
(21, '川菜正宗做法 | 麻婆豆腐家庭版教程', 1, 'https://images.unsplash.com/photo-1583224994076-ae951d019af7?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1583224994076-ae951d019af7?w=600&h=300&fit=crop"]', 156000, 42000, 234000, 28000, 1, '抖音', '分享麻婆豆腐的正宗做法，简单易学，在家也能做出餐厅味道。', 1, 7, 1, '', 1, NOW(), null, null, 0),

(22, '烘焙入门 | 零失败的戚风蛋糕配方', 1, 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=600&h=300&fit=crop"]', 189000, 58000, 312000, 35000, 1, '小红书', '详细分享戚风蛋糕的制作过程，包括注意事项和技巧。', 1, 7, 1, '', 1, NOW(), null, null, 0),

(23, '街头小吃 | 夜市最火的铁板鱿鱼做法', 1, 'https://images.unsplash.com/photo-1555126634-323283e090fa?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1555126634-323283e090fa?w=600&h=300&fit=crop"]', 134000, 36000, 198000, 24000, 2, '快手', '复刻夜市铁板鱿鱼的做法，调料配比大公开。', 1, 7, 1, '', 1, NOW(), null, null, 0),

(24, '地方特色 | 广式早茶虾饺制作教程', 1, 'https://images.unsplash.com/photo-1563245372-f21724e3856d?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1563245372-f21724e3856d?w=600&h=300&fit=crop"]', 167000, 49000, 245000, 31000, 1, 'B站', '详细介绍广式虾饺的制作方法，从和面到包馅都有讲解。', 1, 7, 1, '', 1, NOW(), null, null, 0),

(25, '饮品制作 | 夏日清爽西瓜汁的做法', 1, 'https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=600&h=300&fit=crop"]', 98000, 27000, 156000, 19000, 2, '抖音', '分享夏日清爽西瓜汁的制作方法，简单又好喝。', 1, 7, 1, '', 1, NOW(), null, null, 0);

-- 增加更多分类数据
INSERT INTO home_category (category_id, category_name, icon_name, color_class, sort_order, status, creator_id, create_time, updater_id, update_time, deleted) VALUES
(7, '职场', 'Briefcase', 'from-blue-500 to-cyan-500', 7, 1, 1, NOW(), null, null, 0),
(8, '教育', 'GraduationCap', 'from-green-500 to-teal-500', 8, 1, 1, NOW(), null, null, 0),
(9, '健康', 'Heart', 'from-red-500 to-pink-500', 9, 1, 1, NOW(), null, null, 0),
(10, '家居', 'Home', 'from-orange-500 to-yellow-500', 10, 1, 1, NOW(), null, null, 0),
(11, '宠物', 'PawPrint', 'from-purple-500 to-indigo-500', 11, 1, 1, NOW(), null, null, 0),
(12, '摄影', 'Camera', 'from-gray-500 to-slate-500', 12, 1, 1, NOW(), null, null, 0),
(13, '音乐', 'Music', 'from-pink-500 to-rose-500', 13, 1, 1, NOW(), null, null, 0),
(14, '影视', 'Film', 'from-indigo-500 to-blue-500', 14, 1, 1, NOW(), null, null, 0);

-- 增加更多优质达人数据
INSERT INTO home_influencer (influencer_id, name, platform, followers, field, image_url, sort_order, status, creator_id, create_time, updater_id, update_time, deleted) VALUES
(4, '职场导师小李', '知乎', '32万', '职场经验分享', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600&h=300&fit=crop', 4, 1, 1, NOW(), null, null, 0),
(5, '育儿专家王老师', '小红书', '76万', '亲子教育', 'https://images.unsplash.com/photo-1581382575275-97901c2635b7?w=600&h=300&fit=crop', 5, 1, 1, NOW(), null, null, 0),
(6, '健身教练Tony', '抖音', '158万', '健身训练', 'https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=600&h=300&fit=crop', 6, 1, 1, NOW(), null, null, 0),
(7, '美食博主阿强', 'B站', '95万', '美食制作', 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=600&h=300&fit=crop', 7, 1, 1, NOW(), null, null, 0),
(8, '摄影师小张', '微博', '43万', '摄影技巧', 'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop', 8, 1, 1, NOW(), null, null, 0);

-- 增加更多精选内容数据
INSERT INTO home_content (content_id, title, category_id, image_url, images, likes_count, comments_count, views_count, shares_count, content_type, platform, description, status, influencer_id, audit_status, audit_remark, creator_id, create_time, updater_id, update_time, deleted) VALUES
-- 生活分享类内容
(9, '我的极简生活 | 断舍离一年的感悟', 7, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=300&fit=crop"]', 67000, 18000, 98000, 12000, 1, '小红书', '分享我一年来践行极简主义的心得体会，从物质到精神的全面转变。', 1, 4, 1, '', 1, NOW(), null, null, 0),

(10, '职场新人必看 | 5个快速成长秘诀', 7, 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=600&h=300&fit=crop"]', 89000, 25000, 156000, 18000, 1, '知乎', '总结我在职场前三年学到的宝贵经验，帮助职场新人少走弯路。', 1, 4, 1, '', 1, NOW(), null, null, 0),

-- 教育类内容
(11, '高效学习方法 | 如何一年读100本书', 8, 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=600&h=300&fit=crop"]', 123000, 42000, 234000, 28000, 1, 'B站', '分享我的阅读方法和技巧，帮助大家在忙碌的生活中也能高效阅读。', 1, null, 1, '', 1, NOW(), null, null, 0),

(12, '亲子沟通技巧 | 如何与青春期孩子相处', 8, 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1511895426328-dc8714191300?w=600&h=300&fit=crop"]', 156000, 38000, 189000, 22000, 1, '小红书', '分享我与青春期孩子相处的经验，帮助家长建立良好的亲子关系。', 1, 5, 1, '', 1, NOW(), null, null, 0),

-- 健康类内容
(13, '30天健身挑战 | 从宅男到肌肉男的蜕变', 9, 'https://images.unsplash.com/photo-1583454110551-21f2fa2afe61?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1583454110551-21f2fa2afe61?w=600&h=300&fit=crop"]', 234000, 67000, 345000, 45000, 1, '抖音', '记录我30天健身的完整过程，分享训练计划和饮食安排。', 1, 6, 1, '', 1, NOW(), null, null, 0),

(14, '健康饮食 | 一周减脂餐详细教程', 9, 'https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=600&h=300&fit=crop"]', 187000, 52000, 267000, 31000, 1, '小红书', '分享我制作减脂餐的经验，营养搭配合理，效果显著。', 1, 6, 1, '', 1, NOW(), null, null, 0),

-- 家居类内容
(15, '小户型装修 | 30平米住出大空间感', 10, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600&h=300&fit=crop"]', 145000, 41000, 198000, 26000, 1, '知乎', '分享我的小户型装修经验，如何让有限的空间发挥最大的效用。', 1, null, 1, '', 1, NOW(), null, null, 0),

-- 宠物类内容
(16, '新手养猫指南 | 从领养到照顾全攻略', 11, 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=600&h=300&fit=crop"]', 198000, 58000, 289000, 37000, 1, 'B站', '分享我从领养猫咪到日常照顾的完整经验，帮助新手铲屎官。', 1, null, 1, '', 1, NOW(), null, null, 0),

-- 摄影类内容
(17, '摄影入门 | 如何用手机拍出大片感', 12, 'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop"]', 167000, 49000, 234000, 29000, 1, '微博', '分享手机摄影的技巧和心得，让大家都能拍出好看的照片。', 1, 8, 1, '', 1, NOW(), null, null, 0),

-- 音乐类内容
(18, '私藏歌单分享 | 适合深夜听的治愈系音乐', 13, 'https://images.unsplash.com/photo-1493225457124-a3fb160957de?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1493225457124-a3fb160957de?w=600&h=300&fit=crop"]', 134000, 36000, 187000, 24000, 1, '小红书', '分享我私藏的治愈系音乐歌单，适合在深夜静静聆听。', 1, null, 1, '', 1, NOW(), null, null, 0),

-- 影视类内容
(19, '经典电影推荐 | 人生必看的10部电影', 14, 'https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=600&h=300&fit=crop"]', 212000, 73000, 321000, 42000, 1, '知乎', '推荐我心中的10部经典电影，每一部都值得反复观看。', 1, null, 1, '', 1, NOW(), null, null, 0),

(20, '热剧解析 | 最近大火的电视剧深度解读', 14, 'https://images.unsplash.com/photo-1594909122845-11baa439b7bf?w=600&h=300&fit=crop', '["https://images.unsplash.com/photo-1594909122845-11baa439b7bf?w=600&h=300&fit=crop"]', 178000, 54000, 267000, 33000, 2, '微博', '深度解读最近热播的电视剧，分析剧情和人物关系。', 1, null, 1, '', 1, NOW(), null, null, 0);

-- 增加更多轮播图数据
INSERT INTO home_banner (banner_id, title, description, image_url, link_url, sort_order, status, creator_id, create_time, updater_id, update_time, deleted) VALUES
(4, '职场进阶 | 从菜鸟到高管的晋升之路', '分享职场晋升的实用经验和技巧，帮助你在职场上快速成长', 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=1400&h=600&fit=crop', '', 4, 1, 1, NOW(), null, null, 0),
(5, '健康生活 | 科学饮食与运动指南', '科学的饮食和运动建议，帮助你建立健康的生活方式', 'https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=1400&h=600&fit=crop', '', 5, 1, 1, NOW(), null, null, 0),
(6, '亲子时光 | 陪伴孩子成长的温馨时刻', '记录与孩子相处的美好时光，分享亲子教育的经验和感悟', 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=1400&h=600&fit=crop', '', 6, 1, 1, NOW(), null, null, 0);

-- 为内容添加标签关联（多对多关系）
INSERT INTO home_content_tag (content_id, tag_id, create_time, deleted) VALUES
-- 生活分享类内容的标签
(9, 11, NOW(), 0),
(9, 15, NOW(), 0),

-- 职场类内容的标签
(10, 12, NOW(), 0),
(10, 13, NOW(), 0),

-- 教育类内容的标签
(11, 13, NOW(), 0),
(11, 8, NOW(), 0),

(12, 14, NOW(), 0),
(12, 8, NOW(), 0),

-- 健康类内容的标签
(13, 15, NOW(), 0),
(13, 6, NOW(), 0),

(14, 15, NOW(), 0),
(14, 1, NOW(), 0),

-- 家居类内容的标签
(15, 16, NOW(), 0),
(15, 11, NOW(), 0),

-- 宠物类内容的标签
(16, 17, NOW(), 0),
(16, 11, NOW(), 0),

-- 摄影类内容的标签
(17, 18, NOW(), 0),
(17, 5, NOW(), 0),

-- 音乐类内容的标签
(18, 19, NOW(), 0),
(18, 11, NOW(), 0),

-- 影视类内容的标签
(19, 20, NOW(), 0),
(19, 11, NOW(), 0),

(20, 20, NOW(), 0),
(20, 3, NOW(), 0);

-- 为美食内容添加标签关联
INSERT INTO home_content_tag (content_id, tag_id, create_time, deleted) VALUES
-- 川菜麻婆豆腐的标签
(21, 1, NOW(), 0),
(21, 11, NOW(), 0),
(21, 15, NOW(), 0),

-- 烘焙戚风蛋糕的标签
(22, 1, NOW(), 0),
(22, 11, NOW(), 0),
(22, 15, NOW(), 0),

-- 铁板鱿鱼的标签
(23, 1, NOW(), 0),
(23, 11, NOW(), 0),

-- 广式虾饺的标签
(24, 1, NOW(), 0),
(24, 11, NOW(), 0),

-- 西瓜汁的标签
(25, 1, NOW(), 0),
(25, 11, NOW(), 0),
(25, 15, NOW(), 0);