export default {
  path: "/content",
  redirect: "/content/article",
  meta: {
    icon: "ep:document",
    title: "内容管理",
    rank: 4
  },
  children: [
    {
      path: "/content/article",
      name: "ContentArticle",
      component: () => import("@/views/content/article/index.vue"),
      meta: {
        title: "稿件管理"
      }
    },
    {
      path: "/content/tag",
      name: "ContentTag",
      component: () => import("@/views/content/tag/index.vue"),
      meta: {
        title: "标签管理"
      }
    },
    {
      path: "/content/platform",
      name: "ContentPlatform",
      component: () => import("@/views/content/platform/index.vue"),
      meta: {
        title: "平台管理"
      }
    },
    {
      path: "/content/influencer",
      name: "ContentInfluencer",
      component: () => import("@/views/content/influencer/index.vue"),
      meta: {
        title: "达人管理"
      }
    },
    {
      path: "/content/banner",
      name: "ContentBanner",
      component: () => import("@/views/content/banner/index.vue"),
      meta: {
        title: "轮播图管理"
      }
    }
  ]
} as RouteConfigsTable;