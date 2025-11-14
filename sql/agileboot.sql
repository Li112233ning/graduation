/*
 Navicat Premium Dump SQL

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80017 (8.0.17)
 Source Host           : localhost:3306
 Source Schema         : agileboot

 Target Server Type    : MySQL
 Target Server Version : 80017 (8.0.17)
 File Encoding         : 65001

 Date: 14/11/2025 16:25:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content_submission
-- ----------------------------
DROP TABLE IF EXISTS `content_submission`;
CREATE TABLE `content_submission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '投稿用户ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '正文',
  `cover_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图URL',
  `platform_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '平台类型：douyin/xiaohongshu/weibo/other',
  `scheduled_publish_time` datetime NULL DEFAULT NULL COMMENT '预计发布时间',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0-草稿，1-待审核，2-已通过，3-已驳回，4-已发布',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `published_at` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `auditor_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_platform_type`(`platform_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_submission
-- ----------------------------

-- ----------------------------
-- Table structure for home_banner
-- ----------------------------
DROP TABLE IF EXISTS `home_banner`;
CREATE TABLE `home_banner`  (
  `banner_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片URL',
  `link_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`banner_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页轮播图表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_banner
-- ----------------------------
INSERT INTO `home_banner` VALUES (1, '西藏之旅 | 川藏线自驾完整攻略', '分享我的川藏线自驾全记录，从成都出发，一路美景，所有必备攻略和注意事项都在这里。', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1400&h=600&fit=crop', '', 1, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_banner` VALUES (2, '夏日清爽妆容教程 | 持久不脱妆秘诀', '夏天来了，分享我的清爽妆容技巧，让你一整天保持完美妆容，不脱妆不花妆。', 'https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=1400&h=600&fit=crop', '', 2, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_banner` VALUES (3, '科技测评 | MacBook Pro M3 深度体验', '最新款 MacBook Pro M3 芯片全面评测，性能、续航、屏幕表现一次性告诉你。', 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=1400&h=600&fit=crop', '', 3, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_banner` VALUES (4, '职场进阶 | 从菜鸟到高管的晋升之路', '分享职场晋升的实用经验和技巧，帮助你在职场上快速成长', 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=1400&h=600&fit=crop', '', 4, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_banner` VALUES (5, '健康生活 | 科学饮食与运动指南', '科学的饮食和运动建议，帮助你建立健康的生活方式', 'https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=1400&h=600&fit=crop', '', 5, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_banner` VALUES (6, '亲子时光 | 陪伴孩子成长的温馨时刻', '记录与孩子相处的美好时光，分享亲子教育的经验和感悟', 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=1400&h=600&fit=crop', '', 6, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_category
-- ----------------------------
DROP TABLE IF EXISTS `home_category`;
CREATE TABLE `home_category`  (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `icon_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标名称',
  `color_class` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '颜色样式类',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_category
-- ----------------------------
INSERT INTO `home_category` VALUES (1, '美食', 'Zap', 'from-red-500 to-pink-500', 1, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (2, '旅游', 'Gamepad2', 'from-pink-500 to-rose-500', 2, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (3, '时尚', 'Award', 'from-rose-500 to-red-500', 3, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (4, '美妆', 'ChevronRight', 'from-orange-500 to-red-500', 4, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (5, '科技', 'Tag', 'from-red-400 to-pink-400', 5, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (6, '运动', 'Trophy', 'from-pink-400 to-rose-400', 6, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (7, '职场', 'Briefcase', 'from-blue-500 to-cyan-500', 7, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (8, '教育', 'GraduationCap', 'from-green-500 to-teal-500', 8, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (9, '健康', 'Heart', 'from-red-500 to-pink-500', 9, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (10, '家居', 'Home', 'from-orange-500 to-yellow-500', 10, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (11, '宠物', 'PawPrint', 'from-purple-500 to-indigo-500', 11, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (12, '摄影', 'Camera', 'from-gray-500 to-slate-500', 12, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (13, '音乐', 'Music', 'from-pink-500 to-rose-500', 13, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_category` VALUES (14, '影视', 'Film', 'from-indigo-500 to-blue-500', 14, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_comment
-- ----------------------------
DROP TABLE IF EXISTS `home_comment`;
CREATE TABLE `home_comment`  (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `content_id` bigint(20) NOT NULL COMMENT '内容ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论ID（用于回复）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `likes_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_comment
-- ----------------------------
INSERT INTO `home_comment` VALUES (1, 2, 4, NULL, '1', 0, 1, 4, '2025-11-12 11:31:23', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (2, 2, 4, NULL, '@admin1 nihoa', 0, 1, 4, '2025-11-12 11:35:19', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (3, 2, 4, NULL, '1', 1, 1, 4, '2025-11-12 11:45:52', 4, '2025-11-12 11:49:31', 0);
INSERT INTO `home_comment` VALUES (4, 2, 4, NULL, '@admin1 nh', 0, 1, 4, '2025-11-12 11:46:03', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (5, 2, 4, NULL, '@admin1 111', 0, 1, 4, '2025-11-12 11:51:45', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (6, 1, 4, NULL, '1', 0, 1, 4, '2025-11-12 13:14:13', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (7, 1, 4, 6, '@admin1 111', 0, 1, 4, '2025-11-12 13:14:17', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (8, 19, 4, NULL, '1', 0, 1, 4, '2025-11-13 17:31:35', NULL, NULL, 0);
INSERT INTO `home_comment` VALUES (9, 19, 4, 8, '@admin1 2', 0, 1, 4, '2025-11-13 17:31:42', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `home_comment_like`;
CREATE TABLE `home_comment_like`  (
  `like_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0取消 1有效）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`like_id`) USING BTREE,
  UNIQUE INDEX `uk_comment_user`(`comment_id` ASC, `user_id` ASC) USING BTREE,
  INDEX `idx_comment_id`(`comment_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_comment_like
-- ----------------------------
INSERT INTO `home_comment_like` VALUES (1, 3, 4, 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_content
-- ----------------------------
DROP TABLE IF EXISTS `home_content`;
CREATE TABLE `home_content`  (
  `content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类ID',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '封面图片URL',
  `images` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片列表（JSON数组）',
  `likes_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comments_count` int(11) NOT NULL DEFAULT 0 COMMENT '评论数',
  `views_count` int(11) NOT NULL DEFAULT 0 COMMENT '浏览数',
  `shares_count` int(11) NOT NULL DEFAULT 0 COMMENT '分享数',
  `content_type` smallint(6) NOT NULL DEFAULT 1 COMMENT '内容类型（1精选 2最新 3热门）',
  `platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布平台',
  `influencer_id` bigint(20) NULL DEFAULT NULL COMMENT '所属达人ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容描述',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `audit_status` smallint(6) NOT NULL DEFAULT 0 COMMENT '审核状态（0待审核 1审核通过 2审核拒绝）',
  `audit_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核备注',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`content_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容稿件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_content
-- ----------------------------
INSERT INTO `home_content` VALUES (1, '探秘成都美食 | 10家必打卡的地道小吃', 1, 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=600&h=300&fit=crop\"]', 86001, 23002, 125006, 0, 1, '小红书', 1, NULL, 1, 1, NULL, 1, '2025-11-09 15:52:29', 4, '2025-11-13 17:11:29', 0);
INSERT INTO `home_content` VALUES (2, '夏日穿搭指南 | 5套清爽搭配分享', 3, 'https://images.unsplash.com/photo-1490481651871-ab68de25d43d?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1490481651871-ab68de25d43d?w=600&h=300&fit=crop\"]', 52001, 18005, 89028, 0, 1, '抖音', 2, NULL, 1, 1, NULL, 1, '2025-11-09 15:52:29', 4, '2025-11-13 17:11:10', 0);
INSERT INTO `home_content` VALUES (3, '科技评测 | iPhone 15 Pro 深度体验', 5, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&h=300&fit=crop\"]', 98000, 32000, 152003, 0, 1, 'B站', 3, NULL, 1, 1, NULL, 1, '2025-11-09 15:52:29', 4, '2025-11-13 11:37:10', 0);
INSERT INTO `home_content` VALUES (4, '健身打卡 | 30天瘦身计划分享', 6, 'https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=600&h=300&fit=crop\"]', 43000, 12000, 68004, 0, 1, '小红书', 1, NULL, 1, 1, NULL, 1, '2025-11-09 15:52:29', 4, '2025-11-12 13:33:51', 0);
INSERT INTO `home_content` VALUES (5, '新疆自驾游 | 独库公路全攻略', 2, 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&h=300&fit=crop\"]', 156000, 48000, 254000, 0, 2, '微博', 1, NULL, 1, 0, NULL, 1, '2025-11-09 15:52:29', NULL, '2025-11-12 13:37:23', 0);
INSERT INTO `home_content` VALUES (6, '护肤心得 | 敏感肌修复历程分享', 4, 'https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=600&h=300&fit=crop\"]', 123000, 35000, 182001, 0, 2, '小红书', 2, NULL, 1, 0, NULL, 1, '2025-11-09 15:52:29', 4, '2025-11-12 13:48:39', 0);
INSERT INTO `home_content` VALUES (7, '数码开箱 | 最新款游戏笔记本评测', 5, 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&h=300&fit=crop\"]', 187000, 52000, 221000, 0, 2, '抖音', 3, NULL, 1, 0, NULL, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (8, '家常菜谱 | 10分钟快手早餐合集', 1, 'https://images.unsplash.com/photo-1484723091739-30a097e8f929?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1484723091739-30a097e8f929?w=600&h=300&fit=crop\"]', 228000, 69000, 356001, 0, 2, '快手', 2, NULL, 1, 1, NULL, 1, '2025-11-09 15:52:29', NULL, '2025-11-13 17:30:49', 0);
INSERT INTO `home_content` VALUES (9, '我的极简生活 | 断舍离一年的感悟', 7, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=300&fit=crop\"]', 67000, 18000, 98011, 12000, 1, '小红书', 4, '分享我一年来践行极简主义的心得体会，从物质到精神的全面转变。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, '2025-11-13 17:25:07', 0);
INSERT INTO `home_content` VALUES (10, '职场新人必看 | 5个快速成长秘诀', 7, 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=600&h=300&fit=crop\"]', 89000, 25000, 156022, 18000, 1, '知乎', 4, '总结我在职场前三年学到的宝贵经验，帮助职场新人少走弯路。', 1, 1, '', 1, '2025-11-13 11:41:46', 4, '2025-11-13 17:32:09', 0);
INSERT INTO `home_content` VALUES (11, '高效学习方法 | 如何一年读100本书', 8, 'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=600&h=300&fit=crop\"]', 123000, 42000, 234006, 28000, 1, 'B站', 3, '分享我的阅读方法和技巧，帮助大家在忙碌的生活中也能高效阅读。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, '2025-11-13 17:28:43', 0);
INSERT INTO `home_content` VALUES (12, '亲子沟通技巧 | 如何与青春期孩子相处', 8, 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1511895426328-dc8714191300?w=600&h=300&fit=crop\"]', 156000, 38000, 189000, 22000, 1, '小红书', 5, '分享我与青春期孩子相处的经验，帮助家长建立良好的亲子关系。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (13, '30天健身挑战 | 从宅男到肌肉男的蜕变', 9, 'https://images.unsplash.com/photo-1583454110551-21f2fa2afe61?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1583454110551-21f2fa2afe61?w=600&h=300&fit=crop\"]', 234000, 67000, 345001, 45000, 1, '抖音', 6, '记录我30天健身的完整过程，分享训练计划和饮食安排。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, '2025-11-13 13:13:15', 0);
INSERT INTO `home_content` VALUES (14, '健康饮食 | 一周减脂餐详细教程', 9, 'https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1490645935967-10de6ba17071?w=600&h=300&fit=crop\"]', 117000, 52000, 267000, 31000, 1, '小红书', 6, '分享我制作减脂餐的经验，营养搭配合理，效果显著。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (15, '小户型装修 | 30平米住出大空间感', 10, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600&h=300&fit=crop\"]', 145000, 41000, 198000, 26000, 1, '知乎', 4, '分享我的小户型装修经验，如何让有限的空间发挥最大的效用。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (16, '新手养猫指南 | 从领养到照顾全攻略', 11, 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=600&h=300&fit=crop\"]', 198000, 58000, 289001, 37000, 1, 'B站', 1, '分享我从领养猫咪到日常照顾的完整经验，帮助新手铲屎官。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, '2025-11-13 17:24:34', 0);
INSERT INTO `home_content` VALUES (17, '摄影入门 | 如何用手机拍出大片感', 12, 'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop\"]', 167000, 49000, 234000, 29000, 1, '微博', 8, '分享手机摄影的技巧和心得，让大家都能拍出好看的照片。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (18, '私藏歌单分享 | 适合深夜听的治愈系音乐', 13, 'https://images.unsplash.com/photo-1493225457124-a3fb160957de?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1493225457124-a3fb160957de?w=600&h=300&fit=crop\"]', 134000, 36000, 187000, 24000, 1, '小红书', 5, '分享我私藏的治愈系音乐歌单，适合在深夜静静聆听。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_content` VALUES (19, '经典电影推荐 | 人生必看的10部电影', 14, 'https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=600&h=300&fit=crop\"]', 212001, 73002, 321002, 42000, 1, '知乎', 6, '推荐我心中的10部经典电影，每一部都值得反复观看。', 1, 1, '', 1, '2025-11-13 11:41:46', 4, '2025-11-13 17:31:42', 0);
INSERT INTO `home_content` VALUES (20, '热剧解析 | 最近大火的电视剧深度解读', 14, 'https://images.unsplash.com/photo-1594909122845-11baa439b7bf?w=600&h=300&fit=crop', '[\"https://images.unsplash.com/photo-1594909122845-11baa439b7bf?w=600&h=300&fit=crop\"]', 178000, 54000, 267000, 33000, 2, '微博', 7, '深度解读最近热播的电视剧，分析剧情和人物关系。', 1, 1, '', 1, '2025-11-13 11:41:46', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `home_content_tag`;
CREATE TABLE `home_content_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content_id` bigint(20) NOT NULL COMMENT '内容ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_tag`(`content_id` ASC, `tag_id` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '稿件标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_content_tag
-- ----------------------------
INSERT INTO `home_content_tag` VALUES (1, 9, 11, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (2, 9, 15, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (3, 10, 12, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (4, 10, 13, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (5, 11, 13, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (6, 11, 8, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (7, 12, 14, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (8, 12, 8, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (9, 13, 15, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (10, 13, 6, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (11, 14, 15, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (12, 14, 1, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (13, 15, 16, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (14, 15, 11, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (15, 16, 17, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (16, 16, 11, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (17, 17, 18, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (18, 17, 5, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (19, 18, 19, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (20, 18, 11, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (21, 19, 20, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (22, 19, 11, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (23, 20, 20, '2025-11-13 11:41:46', 0);
INSERT INTO `home_content_tag` VALUES (24, 20, 3, '2025-11-13 11:41:46', 0);

-- ----------------------------
-- Table structure for home_influencer
-- ----------------------------
DROP TABLE IF EXISTS `home_influencer`;
CREATE TABLE `home_influencer`  (
  `influencer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '达人ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '达人名称',
  `platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属平台',
  `followers` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '粉丝数量',
  `field` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业领域',
  `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '封面图片URL',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`influencer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优质达人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_influencer
-- ----------------------------
INSERT INTO `home_influencer` VALUES (1, '美食探店小达人', '小红书', '58万', '美食探店', 'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=600&h=300&fit=crop', 1, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (2, '时尚穿搭师', '抖音', '125万', '时尚穿搭', 'https://images.unsplash.com/photo-1483985988355-763728e1935b?w=600&h=300&fit=crop', 2, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (3, '数码科技君', 'B站', '89万', '数码评测', 'https://images.unsplash.com/photo-1518770660439-4636190af475?w=600&h=300&fit=crop', 3, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (4, '职场导师小李', '知乎', '32万', '职场经验分享', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600&h=300&fit=crop', 4, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (5, '育儿专家王老师', '小红书', '76万', '亲子教育', 'https://images.unsplash.com/photo-1581382575275-97901c2635b7?w=600&h=300&fit=crop', 5, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (6, '健身教练Tony', '抖音', '158万', '健身训练', 'https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=600&h=300&fit=crop', 6, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (7, '美食博主阿强', 'B站', '95万', '美食制作', 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=600&h=300&fit=crop', 7, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);
INSERT INTO `home_influencer` VALUES (8, '摄影师小张', '微博', '43万', '摄影技巧', 'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=600&h=300&fit=crop', 8, 1, 1, '2025-11-13 11:41:46', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_platform
-- ----------------------------
DROP TABLE IF EXISTS `home_platform`;
CREATE TABLE `home_platform`  (
  `platform_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台ID',
  `platform_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '平台名称',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`platform_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容平台表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_platform
-- ----------------------------
INSERT INTO `home_platform` VALUES (1, '小红书', 1, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_platform` VALUES (2, '抖音', 2, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_platform` VALUES (3, '微博', 3, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_platform` VALUES (4, 'B站', 4, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_platform` VALUES (5, '快手', 5, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);
INSERT INTO `home_platform` VALUES (6, '知乎', 6, 1, 1, '2025-11-09 15:52:29', NULL, NULL, 0);

-- ----------------------------
-- Table structure for home_tag
-- ----------------------------
DROP TABLE IF EXISTS `home_tag`;
CREATE TABLE `home_tag`  (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0禁用 1启用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`tag_id`) USING BTREE,
  UNIQUE INDEX `uk_tag_name`(`tag_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_tag
-- ----------------------------
INSERT INTO `home_tag` VALUES (11, '生活分享', 11, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (12, '职场经验', 12, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (13, '学习笔记', 13, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (14, '亲子教育', 14, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (15, '健康养生', 15, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (16, '家居装修', 16, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (17, '宠物日常', 17, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (18, '摄影技巧', 18, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (19, '音乐推荐', 19, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);
INSERT INTO `home_tag` VALUES (20, '电影影评', 20, 1, '2025-11-13 11:41:45', '2025-11-13 11:41:45', 1, 1, 0);

-- ----------------------------
-- Table structure for home_user_interaction
-- ----------------------------
DROP TABLE IF EXISTS `home_user_interaction`;
CREATE TABLE `home_user_interaction`  (
  `interaction_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '互动ID',
  `content_id` bigint(20) NOT NULL COMMENT '内容ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `interaction_type` smallint(6) NOT NULL COMMENT '互动类型（1点赞 2收藏）',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0取消 1有效）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`interaction_id`) USING BTREE,
  UNIQUE INDEX `uk_content_user_type`(`content_id` ASC, `user_id` ASC, `interaction_type` ASC) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户互动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_user_interaction
-- ----------------------------
INSERT INTO `home_user_interaction` VALUES (1, 2, 4, 1, 1, NULL, NULL, 0);
INSERT INTO `home_user_interaction` VALUES (2, 2, 4, 2, 1, NULL, NULL, 0);
INSERT INTO `home_user_interaction` VALUES (3, 1, 4, 1, 1, NULL, NULL, 0);
INSERT INTO `home_user_interaction` VALUES (4, 1, 4, 2, 1, NULL, NULL, 0);
INSERT INTO `home_user_interaction` VALUES (5, 19, 4, 1, 1, NULL, NULL, 0);
INSERT INTO `home_user_interaction` VALUES (6, 19, 4, 2, 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for influencer_application
-- ----------------------------
DROP TABLE IF EXISTS `influencer_application`;
CREATE TABLE `influencer_application`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `platform_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '平台账号',
  `fans_count` int(11) NOT NULL COMMENT '粉丝量',
  `content_field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容领域',
  `portfolio_links` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '代表作品链接（JSON数组）',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态：0-待审核，1-已通过，2-已驳回',
  `reject_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `approved_at` datetime NULL DEFAULT NULL COMMENT '审核通过时间',
  `auditor_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of influencer_application
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '配置名称',
  `config_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '配置键名',
  `config_options` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '可选的选项',
  `config_value` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '配置值',
  `is_allow_change` tinyint(1) NOT NULL COMMENT '是否允许修改',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`config_id`) USING BTREE,
  UNIQUE INDEX `config_key_uniq_idx`(`config_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', '[\"skin-blue\",\"skin-green\",\"skin-purple\",\"skin-red\",\"skin-yellow\"]', 'skin-blue', 1, NULL, NULL, '2022-08-28 22:12:19', '2022-05-21 08:30:55', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 0);
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '', '123456', 1, NULL, 1, '2023-07-20 14:42:08', '2022-05-21 08:30:55', '初始化密码 123456', 0);
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', '[\"theme-dark\",\"theme-light\"]', 'theme-dark', 1, NULL, NULL, '2022-08-28 22:12:15', '2022-08-20 08:30:55', '深色主题theme-dark，浅色主题theme-light', 0);
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaOnOff', '[\"true\",\"false\"]', 'false', 0, NULL, 1, '2023-07-20 14:39:36', '2022-05-21 08:30:55', '是否开启验证码功能（true开启，false关闭）', 0);
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', '[\"true\",\"false\"]', 'true', 0, NULL, 1, '2022-10-05 22:18:57', '2022-05-21 08:30:55', '是否开启注册用户功能（true开启，false关闭）', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祖级列表',
  `dept_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(11) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_id` bigint(20) NULL DEFAULT NULL,
  `leader_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '部门状态（0停用 1启用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '0', 'AgileBoot科技', 0, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (2, 1, '0,1', '深圳总公司', 1, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (3, 1, '0,1', '长沙分公司', 2, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (4, 2, '0,1,2', '研发部门', 1, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (5, 2, '0,1,2', '市场部门', 2, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 0, NULL, '2022-05-21 08:30:54', 1, '2023-07-20 22:46:41', 0);
INSERT INTO `sys_dept` VALUES (6, 2, '0,1,2', '测试部门', 3, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (7, 2, '0,1,2', '财务部门', 4, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (8, 2, '0,1,2', '运维部门', 5, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (9, 3, '0,1,3', '市场部!', 1, NULL, 'valarchie!!', '15888188888', 'valarc1hie@163.com', 0, NULL, '2022-05-21 08:30:54', 1, '2023-07-20 22:33:31', 0);
INSERT INTO `sys_dept` VALUES (10, 3, '0,1,3', '财务部门', 2, NULL, 'valarchie', '15888888888', 'valarchie@163.com', 0, NULL, '2022-05-21 08:30:54', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_login_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_info`;
CREATE TABLE `sys_login_info`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `ip_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `operation_system` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作系统',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '登录状态（1成功 0失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 438 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_info
-- ----------------------------
INSERT INTO `sys_login_info` VALUES (415, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Mac OS X', 1, '登录成功', '2023-06-29 22:49:37', 0);
INSERT INTO `sys_login_info` VALUES (416, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Mac OS X', 1, '登录成功', '2023-07-02 22:12:30', 0);
INSERT INTO `sys_login_info` VALUES (417, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Mac OS X', 0, '验证码过期', '2023-07-02 22:16:06', 0);
INSERT INTO `sys_login_info` VALUES (418, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 09:39:29', 0);
INSERT INTO `sys_login_info` VALUES (419, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 09:41:05', 0);
INSERT INTO `sys_login_info` VALUES (420, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 09:42:58', 0);
INSERT INTO `sys_login_info` VALUES (421, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 09:43:28', 0);
INSERT INTO `sys_login_info` VALUES (422, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 09:45:02', 0);
INSERT INTO `sys_login_info` VALUES (423, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 10:58:16', 0);
INSERT INTO `sys_login_info` VALUES (424, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 11:06:37', 0);
INSERT INTO `sys_login_info` VALUES (425, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 11:22:20', 0);
INSERT INTO `sys_login_info` VALUES (426, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-12 13:14:04', 0);
INSERT INTO `sys_login_info` VALUES (427, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 0, '用户不存在/密码错误', '2025-11-13 08:34:00', 0);
INSERT INTO `sys_login_info` VALUES (428, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 08:34:11', 0);
INSERT INTO `sys_login_info` VALUES (429, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 08:43:42', 0);
INSERT INTO `sys_login_info` VALUES (430, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 09:30:30', 0);
INSERT INTO `sys_login_info` VALUES (431, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 09:38:15', 0);
INSERT INTO `sys_login_info` VALUES (432, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 09:44:43', 0);
INSERT INTO `sys_login_info` VALUES (433, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 09:47:39', 0);
INSERT INTO `sys_login_info` VALUES (434, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 10:15:48', 0);
INSERT INTO `sys_login_info` VALUES (435, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 10:42:07', 0);
INSERT INTO `sys_login_info` VALUES (436, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 11:01:19', 0);
INSERT INTO `sys_login_info` VALUES (437, 'admin1', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome 14', 'Windows 10', 1, '登录成功', '2025-11-13 17:31:14', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `menu_type` smallint(6) NOT NULL DEFAULT 0 COMMENT '菜单的类型(1为普通菜单2为目录3为内嵌iFrame4为外链跳转)',
  `router_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '路由名称（需保持和前端对应的vue文件中的name保持一致defineOptions方法中设置的name）',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径（对应前端项目view文件夹中的路径）',
  `is_button` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否按钮',
  `permission` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `meta_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '{}' COMMENT '路由元信息（前端根据这个信息进行逻辑处理）',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '菜单状态（1启用 0停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 2, '', 0, '/system', 0, '', '{\"title\":\"系统管理\",\"icon\":\"ep:management\",\"showParent\":true,\"rank\":1}', 1, '系统管理目录', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:08:50', 0);
INSERT INTO `sys_menu` VALUES (2, '系统监控', 2, '', 0, '/monitor', 0, '', '{\"title\":\"系统监控\",\"icon\":\"ep:monitor\",\"showParent\":true,\"rank\":3}', 1, '系统监控目录', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:09:15', 0);
INSERT INTO `sys_menu` VALUES (3, '系统工具', 2, '', 0, '/tool', 0, '', '{\"title\":\"系统工具\",\"icon\":\"ep:tools\",\"showParent\":true,\"rank\":2}', 0, '系统工具目录', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:36:18', 0);
INSERT INTO `sys_menu` VALUES (4, 'AgileBoot官网', 3, 'AgileBootguanwangIframeRouter', 0, '/AgileBootguanwangIframeLink', 0, '', '{\"title\":\"AgileBoot官网\",\"icon\":\"ep:link\",\"showParent\":true,\"frameSrc\":\"https://element-plus.org/zh-CN/\",\"rank\":8}', 0, 'Agileboot官网地址', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:36:01', 0);
INSERT INTO `sys_menu` VALUES (5, '用户管理', 1, 'SystemUser', 1, '/system/user/index', 0, 'system:user:list', '{\"title\":\"用户管理\",\"icon\":\"ep:user-filled\",\"showParent\":true}', 1, '用户管理菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:16:13', 0);
INSERT INTO `sys_menu` VALUES (6, '角色管理', 1, 'SystemRole', 1, '/system/role/index', 0, 'system:role:list', '{\"title\":\"角色管理\",\"icon\":\"ep:user\",\"showParent\":true}', 1, '角色管理菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:16:23', 0);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', 1, 'MenuManagement', 1, '/system/menu/index', 0, 'system:menu:list', '{\"title\":\"菜单管理\",\"icon\":\"ep:menu\",\"showParent\":true}', 1, '菜单管理菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:15:41', 0);
INSERT INTO `sys_menu` VALUES (8, '部门管理', 1, 'Department', 1, '/system/dept/index', 0, 'system:dept:list', '{\"title\":\"部门管理\",\"icon\":\"fa-solid:code-branch\",\"showParent\":true}', 0, '部门管理菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:37:33', 0);
INSERT INTO `sys_menu` VALUES (9, '岗位管理', 1, 'Post', 1, '/system/post/index', 0, 'system:post:list', '{\"title\":\"岗位管理\",\"icon\":\"ep:postcard\",\"showLink\":false,\"showParent\":true}', 0, '岗位管理菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:37:44', 0);
INSERT INTO `sys_menu` VALUES (10, '参数设置', 1, 'Config', 1, '/system/config/index', 0, 'system:config:list', '{\"title\":\"参数设置\",\"icon\":\"ep:setting\",\"showLink\":true,\"showParent\":true}', 0, '参数设置菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:38:10', 0);
INSERT INTO `sys_menu` VALUES (11, '通知公告', 1, 'SystemNotice', 1, '/system/notice/index', 0, 'system:notice:list', '{\"title\":\"通知公告\",\"icon\":\"ep:notification\",\"showParent\":true}', 0, '通知公告菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:38:19', 0);
INSERT INTO `sys_menu` VALUES (12, '日志管理', 1, 'LogManagement', 1, '/system/logd', 0, '', '{\"title\":\"日志管理\",\"icon\":\"ep:document\",\"showParent\":true}', 1, '日志管理菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:14:47', 0);
INSERT INTO `sys_menu` VALUES (13, '在线用户', 1, 'OnlineUser', 2, '/system/monitor/onlineUser/index', 0, 'monitor:online:list', '{\"title\":\"在线用户\",\"icon\":\"fa-solid:users\",\"showParent\":true}', 1, '在线用户菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:13:13', 0);
INSERT INTO `sys_menu` VALUES (14, '数据监控', 1, 'DataMonitor', 2, '/system/monitor/druid/index', 0, 'monitor:druid:list', '{\"title\":\"数据监控\",\"icon\":\"fa:database\",\"showParent\":true,\"frameSrc\":\"/druid/login.html\",\"isFrameSrcInternal\":true}', 0, '数据监控菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:36:56', 0);
INSERT INTO `sys_menu` VALUES (15, '服务监控', 1, 'ServerInfo', 2, '/system/monitor/server/index', 0, 'monitor:server:list', '{\"title\":\"服务监控\",\"icon\":\"fa:server\",\"showParent\":true}', 0, '服务监控菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:37:01', 0);
INSERT INTO `sys_menu` VALUES (16, '缓存监控', 1, 'CacheInfo', 2, '/system/monitor/cache/index', 0, 'monitor:cache:list', '{\"title\":\"缓存监控\",\"icon\":\"ep:reading\",\"showParent\":true}', 0, '缓存监控菜单', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:37:05', 0);
INSERT INTO `sys_menu` VALUES (17, '系统接口', 1, 'SystemAPI', 3, '/tool/swagger/index', 0, 'tool:swagger:list', '{\"title\":\"系统接口\",\"icon\":\"ep:document-remove\",\"showParent\":true,\"frameSrc\":\"/swagger-ui/index.html\",\"isFrameSrcInternal\":true}', 1, '系统接口菜单', 0, '2022-05-21 08:30:54', 1, '2023-08-14 23:14:01', 0);
INSERT INTO `sys_menu` VALUES (18, '操作日志', 1, 'OperationLog', 12, '/system/log/operationLog/index', 0, 'monitor:operlog:list', '{\"title\":\"操作日志\"}', 1, '操作日志菜单', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, '登录日志', 1, 'LoginLog', 12, '/system/log/loginLog/index', 0, 'monitor:logininfor:list', '{\"title\":\"登录日志\"}', 1, '登录日志菜单', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, '用户查询', 0, ' ', 5, '', 1, 'system:user:query', '{\"title\":\"用户查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, '用户新增', 0, ' ', 5, '', 1, 'system:user:add', '{\"title\":\"用户新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, '用户修改', 0, ' ', 5, '', 1, 'system:user:edit', '{\"title\":\"用户修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, '用户删除', 0, ' ', 5, '', 1, 'system:user:remove', '{\"title\":\"用户删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, '用户导出', 0, ' ', 5, '', 1, 'system:user:export', '{\"title\":\"用户导出\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, '用户导入', 0, ' ', 5, '', 1, 'system:user:import', '{\"title\":\"用户导入\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, '重置密码', 0, ' ', 5, '', 1, 'system:user:resetPwd', '{\"title\":\"重置密码\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, '角色查询', 0, ' ', 6, '', 1, 'system:role:query', '{\"title\":\"角色查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (28, '角色新增', 0, ' ', 6, '', 1, 'system:role:add', '{\"title\":\"角色新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (29, '角色修改', 0, ' ', 6, '', 1, 'system:role:edit', '{\"title\":\"角色修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30, '角色删除', 0, ' ', 6, '', 1, 'system:role:remove', '{\"title\":\"角色删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (31, '角色导出', 0, ' ', 6, '', 1, 'system:role:export', '{\"title\":\"角色导出\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (32, '菜单查询', 0, ' ', 7, '', 1, 'system:menu:query', '{\"title\":\"菜单查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (33, '菜单新增', 0, ' ', 7, '', 1, 'system:menu:add', '{\"title\":\"菜单新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (34, '菜单修改', 0, ' ', 7, '', 1, 'system:menu:edit', '{\"title\":\"菜单修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, '菜单删除', 0, ' ', 7, '', 1, 'system:menu:remove', '{\"title\":\"菜单删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (36, '部门查询', 0, ' ', 8, '', 1, 'system:dept:query', '{\"title\":\"部门查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (37, '部门新增', 0, ' ', 8, '', 1, 'system:dept:add', '{\"title\":\"部门新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (38, '部门修改', 0, ' ', 8, '', 1, 'system:dept:edit', '{\"title\":\"部门修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (39, '部门删除', 0, ' ', 8, '', 1, 'system:dept:remove', '{\"title\":\"部门删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40, '岗位查询', 0, ' ', 9, '', 1, 'system:post:query', '{\"title\":\"岗位查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (41, '岗位新增', 0, ' ', 9, '', 1, 'system:post:add', '{\"title\":\"岗位新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (42, '岗位修改', 0, ' ', 9, '', 1, 'system:post:edit', '{\"title\":\"岗位修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (43, '岗位删除', 0, ' ', 9, '', 1, 'system:post:remove', '{\"title\":\"岗位删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (44, '岗位导出', 0, ' ', 9, '', 1, 'system:post:export', '{\"title\":\"岗位导出\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (45, '参数查询', 0, ' ', 10, '', 1, 'system:config:query', '{\"title\":\"参数查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (46, '参数新增', 0, ' ', 10, '', 1, 'system:config:add', '{\"title\":\"参数新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (47, '参数修改', 0, ' ', 10, '', 1, 'system:config:edit', '{\"title\":\"参数修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (48, '参数删除', 0, ' ', 10, '', 1, 'system:config:remove', '{\"title\":\"参数删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (49, '参数导出', 0, ' ', 10, '', 1, 'system:config:export', '{\"title\":\"参数导出\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (50, '公告查询', 0, ' ', 11, '', 1, 'system:notice:query', '{\"title\":\"公告查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (51, '公告新增', 0, ' ', 11, '', 1, 'system:notice:add', '{\"title\":\"公告新增\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (52, '公告修改', 0, ' ', 11, '', 1, 'system:notice:edit', '{\"title\":\"公告修改\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (53, '公告删除', 0, ' ', 11, '', 1, 'system:notice:remove', '{\"title\":\"公告删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (54, '操作查询', 0, ' ', 18, '', 1, 'monitor:operlog:query', '{\"title\":\"操作查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (55, '操作删除', 0, ' ', 18, '', 1, 'monitor:operlog:remove', '{\"title\":\"操作删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (56, '日志导出', 0, ' ', 18, '', 1, 'monitor:operlog:export', '{\"title\":\"日志导出\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (57, '登录查询', 0, ' ', 19, '', 1, 'monitor:logininfor:query', '{\"title\":\"登录查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (58, '登录删除', 0, ' ', 19, '', 1, 'monitor:logininfor:remove', '{\"title\":\"登录删除\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (59, '日志导出', 0, ' ', 19, '', 1, 'monitor:logininfor:export', '{\"title\":\"日志导出\",\"rank\":22}', 1, '', 0, '2022-05-21 08:30:54', 1, '2023-07-22 17:02:28', 0);
INSERT INTO `sys_menu` VALUES (60, '在线查询', 0, ' ', 13, '', 1, 'monitor:online:query', '{\"title\":\"在线查询\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (61, '批量强退', 0, ' ', 13, '', 1, 'monitor:online:batchLogout', '{\"title\":\"批量强退\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (62, '单条强退', 0, ' ', 13, '', 1, 'monitor:online:forceLogout', '{\"title\":\"单条强退\"}', 1, '', 0, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (63, 'AgileBoot Github地址', 4, 'https://github.com/valarchie/AgileBoot-Back-End', 0, '/external', 0, '', '{\"title\":\"AgileBoot Github地址\",\"icon\":\"fa-solid:external-link-alt\",\"showParent\":true,\"rank\":9}', 0, 'Agileboot github地址', 0, '2022-05-21 08:30:54', 1, '2025-11-13 08:36:05', 0);
INSERT INTO `sys_menu` VALUES (64, '首页', 2, '', 0, '/global', 0, '121212', '{\"title\":\"首页\",\"showParent\":true,\"rank\":3}', 1, '', 1, '2023-07-24 22:36:03', 1, '2023-07-24 22:38:37', 1);
INSERT INTO `sys_menu` VALUES (65, '个人中心', 1, 'PersonalCenter', 2053, '/system/user/profile', 0, '434sdf', '{\"title\":\"个人中心\",\"showParent\":true,\"rank\":3}', 1, '', 1, '2023-07-24 22:36:55', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `notice_type` smallint(6) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '公告内容',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '公告状态（1正常 0关闭）',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 AgileBoot新版本发布啦', 2, '新版本内容~~~~~~~~~~', 1, 1, '2022-05-21 08:30:55', 1, '2022-08-29 20:12:37', '管理员', 0);
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 AgileBoot系统凌晨维护', 1, '维护内容', 1, 1, '2022-05-21 08:30:55', NULL, NULL, '管理员', 0);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `operation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `business_type` smallint(6) NOT NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `request_method` smallint(6) NOT NULL DEFAULT 0 COMMENT '请求方式',
  `request_module` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '请求模块',
  `request_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '请求URL',
  `called_method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '调用方法',
  `operator_type` smallint(6) NOT NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `user_id` bigint(20) NULL DEFAULT 0 COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  `operator_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员ip',
  `operator_location` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  `dept_id` bigint(20) NULL DEFAULT 0 COMMENT '部门ID',
  `dept_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `operation_param` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  `operation_result` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '操作状态（1正常 0异常）',
  `error_stack` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`operation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 574 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
INSERT INTO `sys_operation_log` VALUES (561, 1, 2, '菜单管理', '/system/menus', 'com.agileboot.admin.controller.system.SysMenuController.add()', 1, 0, 'admin', '127.0.0.1', '内网IP', 0, NULL, '{\"menuName\":\"\",\"permission\":\"\",\"parentId\":2035,\"path\":\"\",\"isButton\":false,\"routerName\":\"\",\"meta\":{\"showParent\":true,\"rank\":0},\"status\":1},', '', 1, '', '2023-07-22 17:06:57', 0);
INSERT INTO `sys_operation_log` VALUES (562, 3, 4, '菜单管理', '/system/menus/4', 'com.agileboot.admin.controller.system.SysMenuController.remove()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=4}', '', 0, '菜单已分配给角色，不允许', '2025-11-13 08:35:37', 0);
INSERT INTO `sys_operation_log` VALUES (563, 2, 3, '菜单管理', '/system/menus/4', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=4}', '', 1, '', '2025-11-13 08:36:01', 0);
INSERT INTO `sys_operation_log` VALUES (564, 2, 3, '菜单管理', '/system/menus/63', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=63}', '', 1, '', '2025-11-13 08:36:05', 0);
INSERT INTO `sys_operation_log` VALUES (565, 2, 3, '菜单管理', '/system/menus/3', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=3}', '', 1, '', '2025-11-13 08:36:18', 0);
INSERT INTO `sys_operation_log` VALUES (566, 2, 3, '菜单管理', '/system/menus/14', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=14}', '', 1, '', '2025-11-13 08:36:56', 0);
INSERT INTO `sys_operation_log` VALUES (567, 2, 3, '菜单管理', '/system/menus/15', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=15}', '', 1, '', '2025-11-13 08:37:01', 0);
INSERT INTO `sys_operation_log` VALUES (568, 2, 3, '菜单管理', '/system/menus/16', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=16}', '', 1, '', '2025-11-13 08:37:05', 0);
INSERT INTO `sys_operation_log` VALUES (569, 2, 3, '菜单管理', '/system/menus/8', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=8}', '', 1, '', '2025-11-13 08:37:33', 0);
INSERT INTO `sys_operation_log` VALUES (570, 2, 3, '菜单管理', '/system/menus/9', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=9}', '', 1, '', '2025-11-13 08:37:44', 0);
INSERT INTO `sys_operation_log` VALUES (571, 2, 3, '菜单管理', '/system/menus/10', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=10}', '', 1, '', '2025-11-13 08:37:52', 0);
INSERT INTO `sys_operation_log` VALUES (572, 2, 3, '菜单管理', '/system/menus/10', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=10}', '', 1, '', '2025-11-13 08:38:10', 0);
INSERT INTO `sys_operation_log` VALUES (573, 2, 3, '菜单管理', '/system/menus/11', 'com.agileboot.admin.controller.system.SysMenuController.edit()', 1, 0, 'admin', '0:0:0:0:0:0:0:1', '内网IP', 0, NULL, '{menuId=11}', '', 1, '', '2025-11-13 08:38:19', 0);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` smallint(6) NOT NULL COMMENT '状态（1正常 0停用）',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 1, '', NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, 1, '', NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, 1, '', NULL, '2022-05-21 08:30:54', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 5, 0, '', NULL, '2022-05-21 08:30:54', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` smallint(6) NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3: 本部门数据权限 4: 本部门及以下数据权限 5: 本人权限）',
  `dept_id_set` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '角色所拥有的部门数据权限',
  `status` smallint(6) NOT NULL COMMENT '角色状态（1正常 0停用）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, 1, '', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, '超级管理员', 0);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 3, 2, '', 1, NULL, '2022-05-21 08:30:54', NULL, NULL, '普通角色', 0);
INSERT INTO `sys_role` VALUES (3, '闲置角色', 'unused', 4, 2, '', 0, NULL, '2022-05-21 08:30:54', NULL, NULL, '未使用的角色', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 25);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 27);
INSERT INTO `sys_role_menu` VALUES (2, 28);
INSERT INTO `sys_role_menu` VALUES (2, 29);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 35);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 42);
INSERT INTO `sys_role_menu` VALUES (2, 43);
INSERT INTO `sys_role_menu` VALUES (2, 44);
INSERT INTO `sys_role_menu` VALUES (2, 45);
INSERT INTO `sys_role_menu` VALUES (2, 46);
INSERT INTO `sys_role_menu` VALUES (2, 47);
INSERT INTO `sys_role_menu` VALUES (2, 48);
INSERT INTO `sys_role_menu` VALUES (2, 49);
INSERT INTO `sys_role_menu` VALUES (2, 50);
INSERT INTO `sys_role_menu` VALUES (2, 51);
INSERT INTO `sys_role_menu` VALUES (2, 52);
INSERT INTO `sys_role_menu` VALUES (2, 53);
INSERT INTO `sys_role_menu` VALUES (2, 54);
INSERT INTO `sys_role_menu` VALUES (2, 55);
INSERT INTO `sys_role_menu` VALUES (2, 56);
INSERT INTO `sys_role_menu` VALUES (2, 57);
INSERT INTO `sys_role_menu` VALUES (2, 58);
INSERT INTO `sys_role_menu` VALUES (2, 59);
INSERT INTO `sys_role_menu` VALUES (2, 60);
INSERT INTO `sys_role_menu` VALUES (2, 61);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (111, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `post_id` bigint(20) NULL DEFAULT NULL COMMENT '职位id',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_type` smallint(6) NULL DEFAULT 0 COMMENT '用户类型（00系统用户）',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` smallint(6) NULL DEFAULT 0 COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '帐号状态（1正常 2停用 3冻结）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `is_admin` tinyint(1) NOT NULL DEFAULT 0 COMMENT '超级管理员标志（1是，0否）',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, 1, 4, 'admin', 'valarchie1', 0, 'agileboot@163.com', '15888888883', 0, '/profile/avatar/20230725164110_blob_6b7a989b1cdd4dd396665d2cfd2addc5.png', '$2a$10$o55UFZAtyWnDpRV6dvQe8.c/MjlFacC49ASj2usNXm9BY74SYI/uG', 1, '0:0:0:0:0:0:0:1', '2025-11-13 11:01:21', 1, NULL, '2022-05-21 08:30:54', 1, '2025-11-13 11:01:21', '管理员', 0);
INSERT INTO `sys_user` VALUES (2, 2, 2, 5, 'ag1', 'valarchie2', 0, 'agileboot1@qq.com', '15666666666', 1, '/profile/avatar/20230725114818_avatar_b5bf400732bb43369b4df58802049b22.png', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 1, '127.0.0.1', '2022-05-21 08:30:54', 0, NULL, '2022-05-21 08:30:54', NULL, NULL, '测试员1', 0);
INSERT INTO `sys_user` VALUES (3, 2, 0, 5, 'ag2', 'valarchie3', 0, 'agileboot2@qq.com', '15666666667', 1, '/profile/avatar/20230725114818_avatar_b5bf400732bb43369b4df58802049b22.png', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 1, '127.0.0.1', '2022-05-21 08:30:54', 0, NULL, '2022-05-21 08:30:54', NULL, NULL, '测试员2', 0);
INSERT INTO `sys_user` VALUES (4, NULL, 2, NULL, 'admin1', 'admin1', 0, '', '', 0, '', '$2a$10$e.Jwu1QRBDXMV.21gI9Yk.SK2Z4e6pcNwwZK9JNIcZpFFeIh7EgMW', 1, '0:0:0:0:0:0:0:1', '2025-11-13 17:31:25', 0, NULL, '2025-11-12 09:33:20', 4, '2025-11-13 17:31:25', NULL, 0);

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow`  (
  `follow_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  `follower_id` bigint(20) NOT NULL COMMENT '关注者ID（粉丝）',
  `followee_id` bigint(20) NOT NULL COMMENT '被关注者ID（博主）',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态（0取消 1有效）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`follow_id`) USING BTREE,
  UNIQUE INDEX `uk_follower_followee`(`follower_id` ASC, `followee_id` ASC) USING BTREE,
  INDEX `idx_follower_id`(`follower_id` ASC) USING BTREE,
  INDEX `idx_followee_id`(`followee_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_follow
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
