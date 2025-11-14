<template>
  <div class="min-h-screen bg-gradient-to-b from-red-50 via-pink-50 to-orange-50 text-gray-900">
    <AnimatedBackground />

    <!-- 头部 -->
    <header class="sticky top-0 z-50 bg-white/90 backdrop-blur-md border-b border-gray-200/50 shadow-sm">
      <div class="container mx-auto px-4">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center gap-8">
            <router-link to="/" class="flex items-center gap-2 group">
<!--              <div class="relative w-8 h-8 overflow-hidden rounded-full bg-gradient-to-br from-red-500 to-pink-500 group-hover:from-red-400 group-hover:to-pink-400 transition-all duration-300">-->
<!--                <img-->
<!--                  src="https://images.unsplash.com/photo-1511512578047-dfb367046420?w=32&h=32&fit=crop"-->
<!--                  alt="Steam Logo"-->
<!--                  class="w-8 h-8 scale-90 group-hover:scale-100 transition-transform duration-300"-->
<!--                />-->
<!--              </div>-->
              <span class="font-bold text-xl tracking-tight text-gray-900 group-hover:text-red-500 transition-colors duration-300">
                新媒体内容平台
              </span>
            </router-link>
            <nav class="hidden md:flex items-center space-x-6">
              <router-link to="/" class="text-gray-900 hover:text-red-500 font-medium transition-colors duration-300 relative after:absolute after:bottom-0 after:left-0 after:h-0.5 after:w-0 hover:after:w-full after:bg-red-500 after:transition-all after:duration-300">
                首页
              </router-link>
              <router-link to="/content-management" v-if="influencerStore.isInfluencer" class="text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300 relative after:absolute after:bottom-0 after:left-0 after:h-0.5 after:w-0 hover:after:w-full after:bg-red-500 after:transition-all after:duration-300">
                我的稿件
              </router-link>
              <router-link to="/influencer-application" v-if="!influencerStore.isInfluencer && userStore.isLoggedIn" class="text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300 relative after:absolute after:bottom-0 after:left-0 after:h-0.5 after:w-0 hover:after:w-full after:bg-red-500 after:transition-all after:duration-300">
                达人入驻
              </router-link>
            </nav>
          </div>
          <div class="flex items-center gap-4">
            <div class="relative hidden md:block w-64 group">
              <Search class="absolute left-2 top-2.5 h-4 w-4 text-gray-400 group-focus-within:text-red-500 transition-colors duration-300" />
              <Input
                placeholder="搜索内容、达人..."
                class="pl-8 bg-gray-50 border-gray-200 focus-visible:border-red-500 focus-visible:ring-red-500/20 transition-all duration-300"
              />
            </div>
            <SearchDialog />
            <Button variant="ghost" size="icon" class="text-gray-600 hover:text-gray-900 relative group">
              <Bell class="h-5 w-5 group-hover:scale-110 transition-transform duration-300" />
              <span class="absolute -top-1 -right-1 flex h-4 w-4 items-center justify-center rounded-full bg-red-500 text-[10px] font-medium text-white">
                3
              </span>
            </Button>
<!--            <Button variant="ghost" size="icon" class="text-gray-600 hover:text-gray-900 group">-->
<!--              <Heart class="h-5 w-5 group-hover:scale-110 group-hover:text-rose-500 transition-all duration-300" />-->
<!--            </Button>-->
<!--            <ThemeToggle />-->
            <!-- 用户头像/登录按钮 -->
            <div class="relative group">
              <button
                @click="toggleUserMenu"
                class="flex items-center gap-2 cursor-pointer"
              >
                <Avatar class="h-8 w-8 border border-gray-200 ring-2 ring-red-500/20 hover:ring-red-500/50 transition-all duration-300">
                  <AvatarImage :src="userStore.userAvatar" :alt="userStore.displayName" />
                  <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-white text-xs">
                    {{ userStore.displayName.substring(0, 2) }}
                  </AvatarFallback>
                </Avatar>
                <ChevronDown v-if="userStore.isLoggedIn" class="h-4 w-4 text-gray-600 transition-transform duration-300" :class="{ 'rotate-180': showUserMenu }" />
              </button>

              <!-- 用户菜单 -->
              <div
                v-if="showUserMenu && userStore.isLoggedIn"
                class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-200 py-2 z-50"
              >
                <div class="px-4 py-2 border-b border-gray-100">
                  <p class="font-medium text-gray-900">{{ userStore.displayName }}</p>
                  <p class="text-xs text-gray-500">{{ userStore.userInfo?.username }}</p>
                </div>
                <button
                  @click="$router.push('/personal-center'); showUserMenu = false"
                  class="flex items-center gap-2 w-full px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 transition-colors"
                >
                  <User class="h-4 w-4" />
                  个人中心
                </button>
<!--                <a href="#" class="flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 transition-colors">-->
<!--                  <Settings class="h-4 w-4" />-->
<!--                  设置-->
<!--                </a>-->
                <button
                  @click="handleLogout"
                  class="flex items-center gap-2 w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50 transition-colors"
                >
                  <LogOut class="h-4 w-4" />
                  退出登录
                </button>
              </div>
            </div>
            <MobileMenu />
          </div>
        </div>
      </div>
    </header>

    <div class="container mx-auto px-4 py-6">
      <div class="flex flex-col md:flex-row gap-6">
        <!-- 侧边栏 -->
        <aside class="hidden md:block w-56 shrink-0">
          <div class="space-y-6 sticky top-24">
            <div class="bg-white/80 backdrop-blur-sm rounded-xl p-4 border border-gray-200/50 shadow-sm">
              <h3 class="font-medium text-red-500 mb-3 flex items-center">
                <Flame class="mr-2 h-4 w-4" />
                发现
              </h3>
              <ul class="space-y-2">
                <li>
                  <a href="#latest-content" class="flex items-center text-gray-900 hover:text-red-500 transition-colors duration-300 group">
                    <Zap class="mr-2 h-4 w-4 group-hover:scale-110 transition-transform duration-300" />
                    <span>最新内容</span>
                  </a>
                </li>
                <li>
                  <a href="#hot-content" class="flex items-center text-gray-600 hover:text-gray-900 transition-colors duration-300 group">
                    <Award class="mr-2 h-4 w-4 group-hover:scale-110 transition-transform duration-300" />
                    <span>热门榜单</span>
                  </a>
                </li>
<!--                <li>-->
<!--                  <a href="#" class="flex items-center text-gray-600 hover:text-gray-900 transition-colors duration-300 group">-->
<!--                    <Clock class="mr-2 h-4 w-4 group-hover:scale-110 transition-transform duration-300" />-->
<!--                    <span>即将发布</span>-->
<!--                  </a>-->
<!--                </li>-->
                <li>
                  <a href="#influencers" class="flex items-center text-gray-600 hover:text-gray-900 transition-colors duration-300 group">
                    <TrendingUp class="mr-2 h-4 w-4 group-hover:scale-110 transition-transform duration-300" />
                    <span>优质达人</span>
                  </a>
                </li>
              </ul>
            </div>

            <div class="bg-white/80 backdrop-blur-sm rounded-xl p-4 border border-gray-200/50 shadow-sm">
              <h3 class="font-medium text-red-500 mb-3 flex items-center">
                <Tag class="mr-2 h-4 w-4" />
                平台
              </h3>
              <ul class="space-y-2">
                <li v-for="(platform, index) in platforms" :key="index">
                  <router-link :to="`/view-all?platform=${platform.name}`" class="flex items-center justify-between text-gray-600 hover:text-gray-900 transition-colors duration-300 group">
                    <span>{{ platform.name }}</span>
                    <ChevronRight class="h-4 w-4 group-hover:translate-x-1 transition-transform duration-300" />
                  </router-link>
                </li>
              </ul>
            </div>

            <div class="bg-white/80 backdrop-blur-sm rounded-xl p-4 border border-gray-200/50 shadow-sm">
              <h3 class="font-medium text-red-500 mb-3 flex items-center">
                <TrendingUp class="mr-2 h-4 w-4" />
                热门分类
              </h3>
              <div class="flex flex-wrap gap-2">
                <router-link
                  v-for="(category, index) in categories.slice(0, 6)"
                  :key="index"                  :to="`/view-all?category=${category.name}`"
                >                  <Badge
                    class="bg-red-50 text-red-600 hover:bg-red-500 hover:text-white transition-colors duration-300 cursor-pointer"
                  >
                    #{{ category.name }}
                  </Badge>
                </router-link>
              </div>
            </div>
          </div>
        </aside>

        <!-- 主内容 -->
        <main class="flex-1">
          <!-- 主横幅轮播 -->
          <section class="mb-10">
            <HeroCarousel />
          </section>

          <!-- 精选与推荐 -->
          <section id="featured-content" class="mb-10 scroll-mt-20">
            <div class="flex items-center justify-between mb-6">
              <h2 class="text-2xl font-bold flex items-center text-gray-900">
                <Sparkles class="mr-2 h-5 w-5 text-red-500" />
                精选内容
              </h2>
              <router-link to="/view-all?type=featured">
                <Button
                  variant="outline"
                  class="border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500 transition-all duration-300"
                >
                  查看全部
                </Button>
              </router-link>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              <GameCard v-for="(game, index) in featuredGames.slice(0, 4)" :key="index" :game="game" @click="handleContentClick" />
            </div>
          </section>

          <!-- 按分类浏览 -->
          <section id="categories" class="mb-10 scroll-mt-20">
            <div class="flex items-center justify-between mb-6">
              <h2 class="text-2xl font-bold flex items-center text-gray-900">
                <Tag class="mr-2 h-5 w-5 text-red-500" />
                分类
              </h2>
            </div>
            <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4">
              <CategoryCard v-for="(category, index) in categories.slice(0, 6)" :key="index" :category="category" />
            </div>
          </section>

          <!-- 最近更新 -->
          <section id="latest-content" class="mb-10 scroll-mt-20">
            <div class="flex items-center justify-between mb-6">
              <h2 class="text-2xl font-bold flex items-center text-gray-900">
                <Clock class="mr-2 h-5 w-5 text-red-500" />
                最新发布
              </h2>
              <router-link to="/view-all?type=latest">
                <Button
                  variant="outline"
                  class="border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500 transition-all duration-300"
                >
                  查看全部
                </Button>
              </router-link>
            </div>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              <GameCard v-for="(game, index) in updatedGames.slice(0, 4)" :key="index" :game="game" @click="handleContentClick" />
            </div>
          </section>

          <!-- 优质达人 -->
          <section id="influencers" class="mb-10 scroll-mt-20">
            <div class="flex items-center justify-between mb-6">
              <h2 class="text-2xl font-bold flex items-center text-gray-900">
                <Flame class="mr-2 h-5 w-5 text-red-500" />
                优质达人
              </h2>
              <Button
                variant="outline"
                class="border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500 transition-all duration-300"
                @click="$router.push('/view-all?type=influencers')"
              >
                查看全部
              </Button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              <div v-for="(influencer, index) in influencers.slice(0, 3)" :key="index" class="group relative">
                <div class="bg-white/80 backdrop-blur-sm border border-gray-200/50 rounded-lg overflow-hidden transition-all duration-300 group-hover:border-red-500 group-hover:shadow-lg group-hover:shadow-red-500/10">
                  <div class="aspect-video relative overflow-hidden">
                    <img
                      :src="influencer.image"
                      :alt="influencer.name"
                      class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
                    />
                    <div class="absolute top-2 left-2 flex items-center gap-2">
                      <Badge class="bg-gradient-to-r from-red-500 to-pink-500 text-white">
                        {{ influencer.platform }}
                      </Badge>
                      <Badge class="bg-white/80 backdrop-blur-sm text-gray-900">{{ influencer.followers }} 粉丝</Badge>
                    </div>
                    <div class="absolute inset-0 bg-gradient-to-t from-gray-900 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-end">
                      <Button
                        @click.stop="handleInfluencerClick(influencer)"
                        class="m-3 bg-red-500 hover:bg-red-600 text-white transition-colors duration-300"
                      >
                        查看主页
                      </Button>
                    </div>
                  </div>
                  <div class="p-4">
                    <h3 class="font-semibold mb-1 text-gray-900 group-hover:text-red-500 transition-colors duration-300">
                      {{ influencer.name }}
                    </h3>
                    <div class="flex items-center gap-2">
                      <Avatar class="h-6 w-6 border border-gray-200">
                        <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-xs">
                          {{ influencer.name.substring(0, 2) }}
                        </AvatarFallback>
                      </Avatar>
                      <span class="text-sm text-gray-600">{{ influencer.field }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
      </div>
    </div>

    <!-- 登录对话框 -->
    <LoginDialog 
      :is-open="showLoginDialog" 
      @close="showLoginDialog = false"
      @success="showLoginDialog = false"
    />

    <!-- 内容详情弹窗 -->
    <ContentDetailDialog
      :is-open="showContentDetail"
      :content="selectedContent"
      @close="handleCloseDetail"
      @view-influencer="handleViewInfluencerFromDetail"
    />

    <!-- 个人中心弹窗 -->
    <ProfileDialog
      :is-open="showProfileDialog"
      @close="showProfileDialog = false"
    />

    <!-- 优质达人主页弹窗 -->
    <InfluencerProfileDialog
      :is-open="showInfluencerProfile"
      :influencer="selectedInfluencer"
      @close="showInfluencerProfile = false"
      @view-content="handleContentClick"
    />
  </div>
</template>

<script setup lang="ts">
import { h, ref, onMounted, onBeforeUnmount } from 'vue'
import {
  Search,
  Heart,
  Bell,
  ChevronRight,
  Zap,
  Clock,
  Award,
  Tag,
  Flame,
  TrendingUp,
  Trophy,
  Sparkles,
  Gamepad2,
  User,
  Settings,
  LogOut,
  ChevronDown
} from 'lucide-vue-next'

import Button from '@/components/ui/Button.vue'
import Input from '@/components/ui/Input.vue'
import Badge from '@/components/ui/Badge.vue'
import Avatar from '@/components/ui/Avatar.vue'
import AvatarFallback from '@/components/ui/AvatarFallback.vue'
import AvatarImage from '@/components/ui/AvatarImage.vue'
import SearchDialog from '@/components/SearchDialog.vue'
import MobileMenu from '@/components/MobileMenu.vue'
import ThemeToggle from '@/components/ThemeToggle.vue'
import AnimatedBackground from '@/components/AnimatedBackground.vue'
import HeroCarousel from '@/components/HeroCarousel.vue'
import GameCard from '@/components/GameCard.vue'
import CategoryCard from '@/components/CategoryCard.vue'
import LoginDialog from '@/components/LoginDialog.vue'
import ContentDetailDialog from '@/components/ContentDetailDialog.vue'
import ProfileDialog from '@/components/ProfileDialog.vue'
import InfluencerProfileDialog from '@/components/InfluencerProfileDialog.vue'
import { getCategoryList, getFeaturedContentList, getLatestContentList, getInfluencerList, getPlatformList } from '@/api/home'
import { useUserStore } from '@/stores/user'
import { useInfluencerStore } from '@/stores/influencer'

// 图标映射
const iconMap: Record<string, any> = {
  'Zap': Zap,
  'Gamepad2': Gamepad2,
  'Award': Award,
  'ChevronRight': ChevronRight,
  'Tag': Tag,
  'Trophy': Trophy,
}

// Tailwind颜色映射
const colorMap: Record<string, string> = {
  'red-500': '#ef4444',
  'pink-500': '#ec4899',
  'rose-500': '#f43f5e',
  'orange-500': '#f97316',
  'red-400': '#f87171',
  'pink-400': '#f472b6',
  'rose-400': '#fb7185',
}

// 解析Tailwind渐变类名为实际颜色
const parseGradientColors = (colorClass: string): { fromColor: string, toColor: string } => {
  // colorClass格式: "from-red-500 to-pink-500"
  const fromMatch = colorClass.match(/from-(\w+-\d+)/)
  const toMatch = colorClass.match(/to-(\w+-\d+)/)
  
  const fromColor = fromMatch ? colorMap[fromMatch[1]] || '#ef4444' : '#ef4444'
  const toColor = toMatch ? colorMap[toMatch[1]] || '#ec4899' : '#ec4899'
  
  return { fromColor, toColor }
}

// 用户Store
const userStore = useUserStore()
const influencerStore = useInfluencerStore()

// UI状态
const showUserMenu = ref(false)
const showLoginDialog = ref(false)
const showContentDetail = ref(false)
const showProfileDialog = ref(false)
const showInfluencerProfile = ref(false)
const selectedContent = ref<any>(null)
const selectedInfluencer = ref<any>(null)

// 内容稿件数据
const featuredGames = ref<any[]>([])
const categories = ref<any[]>([])
const updatedGames = ref<any[]>([])
const influencers = ref<any[]>([])
const platforms = ref<any[]>([])

// 处理内容卡片点击
const handleContentClick = (game: any) => {
  selectedContent.value = game
  showContentDetail.value = true
}

// 关闭详情弹窗
const handleCloseDetail = () => {
  showContentDetail.value = false
  selectedContent.value = null
}

// 加载所有数据
const loadData = async () => {
  console.log('开始加载首页数据...')
  
  // 加载分类
  console.log('正在加载分类...')
  const categoriesRes = await getCategoryList()
  console.log('分类原始数据:', categoriesRes)
  console.log('分类data:', categoriesRes.data)

  if (categoriesRes.data && Array.isArray(categoriesRes.data)) {
    console.log('分类数量:', categoriesRes.data.length)
    categories.value = categoriesRes.data.map((cat: any) => {
      console.log('处理分类:', cat)
      const colors = parseGradientColors(cat.color || 'from-red-500 to-pink-500')
      return {
        name: cat.name,
        icon: h(iconMap[cat.icon] || Zap, { class: 'h-8 w-8' }),
        fromColor: colors.fromColor,
        toColor: colors.toColor,
      }
    })
    console.log('处理后的分类:', categories.value)
    console.log('美食分类数量:', categories.value.filter(cat => cat.name === '美食').length)
  } else {
    console.error('分类数据格式错误:', categoriesRes)
  }

  // 加载精选内容
  console.log('正在加载精选内容...')
  const featuredRes = await getFeaturedContentList()
  console.log('精选内容原始数据:', featuredRes)
  
  if (featuredRes.data && Array.isArray(featuredRes.data)) {
    featuredGames.value = featuredRes.data.map((item: any) => ({
      id: item.id,
      title: item.title,
      image: item.image,
      images: item.images,
      tags: item.tags,
      likes: item.likes,
      comments: item.comments,
      players: item.players,
      platform: item.platform,
      description: item.description,
      publishTime: item.publishTime,
      influencerId: item.influencerId,
      author: item.author,
    }))
    console.log('处理后的精选内容:', featuredGames.value)
  } else {
    console.error('精选内容数据格式错误:', featuredRes)
  }

  // 加载最新内容
  console.log('正在加载最新内容...')
  const latestRes = await getLatestContentList()
  console.log('最新内容原始数据:', latestRes)
  
  if (latestRes.data && Array.isArray(latestRes.data)) {
    updatedGames.value = latestRes.data.map((item: any) => ({
      id: item.id,
      title: item.title,
      update: item.update || '最新发布',
      image: item.image,
      images: item.images,
      tags: item.tags,
      likes: item.likes,
      comments: item.comments,
      players: item.players,
      platform: item.platform,
      description: item.description,
      publishTime: item.publishTime,
      influencerId: item.influencerId,
      author: item.author,
    }))
    console.log('处理后的最新内容:', updatedGames.value)
  } else {
    console.error('最新内容数据格式错误:', latestRes)
  }

  // 加载优质达人
  console.log('正在加载优质达人...')
  const influencersRes = await getInfluencerList()
  console.log('优质达人原始数据:', influencersRes)
  
  if (influencersRes.data && Array.isArray(influencersRes.data)) {
    influencers.value = influencersRes.data.map((item: any) => ({
      id: item.id || item.influencerId,
      name: item.name,
      platform: item.platform,
      followers: item.followers,
      field: item.field,
      image: item.image,
      description: item.description,
    }))
    console.log('处理后的优质达人:', influencers.value)
  } else {
    console.error('优质达人数据格式错误:', influencersRes)
  }

  // 加载平台
  console.log('正在加载平台...')
  const platformsRes = await getPlatformList()
  console.log('平台原始数据:', platformsRes)
  
  if (platformsRes.data && Array.isArray(platformsRes.data)) {
    platforms.value = platformsRes.data.map((item: any) => ({
      name: item.name,
    }))
    console.log('处理后的平台:', platforms.value)
  } else {
    console.error('平台数据格式错误:', platformsRes)
  }
  
  console.log('数据加载完成')
}

// 切换用户菜单
const toggleUserMenu = () => {
  if (!userStore.isLoggedIn) {
    showLoginDialog.value = true
  } else {
    showUserMenu.value = !showUserMenu.value
  }
}

// 退出登录
const handleLogout = () => {
  userStore.logout()
  showUserMenu.value = false
}

// 打开达人主页
const handleInfluencerClick = (influencer: any) => {
  selectedInfluencer.value = influencer
  showInfluencerProfile.value = true
}

// 从详情页查看达人主页
const handleViewInfluencerFromDetail = async (influencerId: number) => {
  // 从达人列表中查找对应的达人
  let influencer = influencers.value.find(inf => inf.id === influencerId)

  // 如果找不到达人，则需要加载达人数据
  if (!influencer) {
    try {
      const response = await getInfluencerList()
      if (response.data && Array.isArray(response.data)) {
        influencer = response.data.find((item: any) => (item.id || item.influencerId) === influencerId)
        if (influencer) {
          influencer = {
            id: influencer.id || influencer.influencerId,
            name: influencer.name,
            platform: influencer.platform,
            followers: influencer.followers,
            field: influencer.field,
            image: influencer.image,
            description: influencer.description,
          }
        }
      }
    } catch (error) {
      console.error('加载达人信息失败:', error)
    }
  }

  if (influencer) {
    selectedInfluencer.value = influencer
    showInfluencerProfile.value = true
  }
}

// 点击外部关闭菜单
const handleClickOutside = (event: MouseEvent) => {
  const target = event.target as HTMLElement
  if (!target.closest('.relative.group')) {
    showUserMenu.value = false
  }
}

onMounted(async () => {
  // 初始化用户信息
  userStore.initUserInfo()
  
  // 检查达人角色
  if (userStore.isLoggedIn) {
    await influencerStore.checkInfluencerRole()
  }
  
  // 加载页面数据
  loadData()
  
  // 添加全局点击事件监听
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>


