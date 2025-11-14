<template>
  <div class="min-h-screen bg-gradient-to-b from-red-50 via-pink-50 to-orange-50">
    <AnimatedBackground />

    <!-- 头部 -->
    <header class="sticky top-0 z-50 bg-white/90 backdrop-blur-md border-b border-gray-200/50 shadow-sm">
      <div class="container mx-auto px-4">
        <div class="flex items-center justify-between h-16">
          <div class="flex items-center gap-4">
            <Button
              variant="ghost"
              size="icon"
              @click="goBack"
              class="text-gray-600 hover:text-gray-900"
            >
              <ArrowLeft class="h-5 w-5" />
            </Button>
            <div>
              <h1 class="text-xl font-bold text-gray-900">{{ pageTitle }}</h1>
              <p class="text-xs text-gray-500">共 {{ totalCount }} {{ pageType === 'influencers' ? '位达人' : '项内容' }}</p>
            </div>
          </div>
          <router-link to="/" class="flex items-center gap-2 group">
            <span class="font-bold text-xl tracking-tight text-gray-900 group-hover:text-red-500 transition-colors duration-300">
              新媒体内容平台
            </span>
          </router-link>
        </div>
      </div>
    </header>

    <div class="container mx-auto px-4 py-8">


      <!-- 优质达人展示 -->
      <div v-if="pageType === 'influencers'">
        <div v-if="allInfluencers.length === 0" class="text-center py-12">
          <div class="text-gray-500">
            <p class="text-lg mb-2">暂无优质达人数据</p>
            <p class="text-sm">请检查网络连接或稍后重试</p>
          </div>
        </div>
        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          <div
            v-for="influencer in allInfluencers"
            :key="influencer.id"
            class="group relative bg-white/80 backdrop-blur-sm border border-gray-200/50 rounded-lg overflow-hidden transition-all duration-300 group-hover:border-red-500 group-hover:shadow-lg group-hover:shadow-red-500/10 cursor-pointer"
            @click="handleInfluencerClick(influencer)"
          >
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
            <p class="text-sm text-gray-600 mb-2">{{ influencer.field }}</p>
          </div>
          </div>
        </div>
      </div>

      <!-- 内容展示 -->
      <template v-else>
        <!-- 筛选和排序 -->
        <div class="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
        <div class="flex flex-wrap gap-2 items-center">
<!--          <span class="text-sm font-medium text-gray-500 mr-1">标签:</span>-->
<!--          <Badge-->
<!--            v-for="(category, index) in filterCategories"-->
<!--            :key="index"-->
<!--            :class="[-->
<!--              'cursor-pointer transition-all duration-300 px-3 py-1.5 text-sm font-medium',-->
<!--              selectedCategory === category.id-->
<!--                ? 'bg-gradient-to-r from-red-500 to-pink-500 text-white border-transparent shadow-md shadow-red-500/20'-->
<!--                : 'bg-white text-gray-700 border border-gray-300 hover:bg-red-50 hover:border-red-500 hover:text-red-500'-->
<!--            ]"-->
<!--            @click="selectedCategory = category.id"-->
<!--          >-->
<!--            {{ category.name }}-->
<!--          </Badge>-->
        </div>
        <div class="flex items-center gap-2">
          <span class="text-sm font-medium text-gray-500">排序:</span>
          <Button
            variant="outline"
            size="sm"
            :class="[
              'transition-all duration-300',
              sortBy === 'latest'
                ? 'bg-red-50 border-red-500 text-red-500'
                : 'border-gray-300 text-gray-700 hover:border-gray-400'
            ]"
            @click="sortBy = 'latest'"
          >
            <Clock class="h-4 w-4 mr-1" />
            最新
          </Button>
          <Button
            variant="outline"
            size="sm"
            :class="[
              'transition-all duration-300',
              sortBy === 'popular'
                ? 'bg-red-50 border-red-500 text-red-500'
                : 'border-gray-300 text-gray-700 hover:border-gray-400'
            ]"
            @click="sortBy = 'popular'"
          >
            <TrendingUp class="h-4 w-4 mr-1" />
            最热
          </Button>
        </div>
      </div>

      <!-- 交互式内容选择器 - 展示排序后的前6个 -->
      <div v-if="topSixContents.length > 0" class="mb-8">
        <ContentSelector 
          :key="`${sortBy}-${selectedCategory}`"
          :contents="topSixContents" 
          :initial-active="topSixContents[0].id"
          @view-detail="openDetail"
        />
      </div>
      
      <!-- 提示文本 -->
      <div v-if="topSixContents.length > 0" class="mb-6 text-center">
        <div class="inline-flex items-center gap-2 px-4 py-2 bg-gradient-to-r from-red-50 to-pink-50 rounded-full border border-red-200/50">
          <TrendingUp v-if="sortBy === 'popular'" class="h-4 w-4 text-red-500" />
          <Clock v-else class="h-4 w-4 text-red-500" />
          <span class="text-sm font-medium text-gray-700">
            <span class="text-red-500">{{ sortBy === 'popular' ? '最热' : '最新' }}</span> 
            TOP 6 精选内容
          </span>
        </div>
      </div>

      <!-- 传统网格视图 -->
      <div class="mt-12">
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-2xl font-bold flex items-center text-gray-900">
            <Grid class="mr-2 h-5 w-5 text-red-500" />
            全部内容
          </h2>
          <div class="flex gap-2">
            <Button
              variant="outline"
              size="icon"
              :class="[
                'transition-all duration-300',
                viewMode === 'grid'
                  ? 'bg-red-50 border-red-500 text-red-500'
                  : 'border-gray-300 text-gray-700'
              ]"
              @click="viewMode = 'grid'"
            >
              <Grid class="h-4 w-4" />
            </Button>
            <Button
              variant="outline"
              size="icon"
              :class="[
                'transition-all duration-300',
                viewMode === 'list'
                  ? 'bg-red-50 border-red-500 text-red-500'
                  : 'border-gray-300 text-gray-700'
              ]"
              @click="viewMode = 'list'"
            >
              <List class="h-4 w-4" />
            </Button>
          </div>
        </div>

        <!-- 网格视图 -->
        <div
          v-if="viewMode === 'grid'"
          class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6"
        >
          <div 
            v-for="content in paginatedContents" 
            :key="content.id"
            @click="openDetail(content)"
            class="cursor-pointer"
          >
            <GameCard :game="content" />
          </div>
        </div>

        <!-- 列表视图 -->
        <div v-else class="space-y-4">
          <div
            v-for="content in paginatedContents"
            :key="content.id"
            class="bg-white/80 backdrop-blur-sm border border-gray-200/50 rounded-lg p-4 flex gap-4 items-center hover:border-red-500 hover:shadow-lg hover:shadow-red-500/10 transition-all duration-300"
          >
            <img
              :src="content.image"
              :alt="content.title"
              class="w-32 h-20 object-cover rounded-lg"
            />
            <div class="flex-1">
              <h3 class="font-semibold text-gray-900 mb-1">{{ content.title }}</h3>
              <div class="flex flex-wrap gap-1 mb-2">
                <Badge
                  v-for="(tag, i) in content.tags"
                  :key="i"
                  variant="outline"
                  class="text-xs border-gray-300 text-gray-600"
                >
                  {{ tag }}
                </Badge>
              </div>
              <div class="flex items-center gap-4 text-sm text-gray-500">
                <span class="flex items-center gap-1">
                  <Heart class="h-4 w-4 text-red-500" />
                  {{ content.likes }}
                </span>
                <span class="flex items-center gap-1">
                  <MessageCircle class="h-4 w-4" />
                  {{ content.comments }}
                </span>
                <span class="flex items-center gap-1">
                  <Eye class="h-4 w-4" />
                  {{ content.players }}
                </span>
              </div>
            </div>
            <Button
              @click="openDetail(content)"
              class="bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white"
            >
              查看详情
            </Button>
          </div>
        </div>

        <!-- 分页 -->
        <div class="mt-8 flex justify-center gap-2">
          <Button
            variant="outline"
            size="sm"
            :disabled="currentPage === 1"
            @click="currentPage--"
            class="border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500 disabled:opacity-50"
          >
            <ChevronLeft class="h-4 w-4" />
          </Button>
          <Button
            v-for="page in totalPages"
            :key="page"
            variant="outline"
            size="sm"
            :class="[
              'transition-all duration-300',
              currentPage === page
                ? 'bg-red-500 border-red-500 text-white'
                : 'border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500'
            ]"
            @click="currentPage = page"
          >
            {{ page }}
          </Button>
          <Button
            variant="outline"
            size="sm"
            :disabled="currentPage === totalPages"
            @click="currentPage++"
            class="border-gray-300 text-gray-700 hover:bg-red-50 hover:border-red-500 hover:text-red-500 disabled:opacity-50"
          >
            <ChevronRight class="h-4 w-4" />
          </Button>
        </div>
      </div>
      </template>
    </div>

    <!-- 详情弹窗 -->
    <ContentDetailDialog
      :is-open="showDetailDialog"
      :content="selectedContent"
      @close="showDetailDialog = false"
      @view-influencer="handleViewInfluencerFromDetail"
    />

    <!-- 优质达人主页弹窗 -->
    <InfluencerProfileDialog
      :is-open="showInfluencerProfile"
      :influencer="selectedInfluencer"
      @close="showInfluencerProfile = false"
      @view-content="openDetail"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  ArrowLeft,
  Clock,
  TrendingUp,
  Grid,
  List,
  ChevronLeft,
  ChevronRight,
  Heart,
  MessageCircle,
  Eye
} from 'lucide-vue-next'
import Button from '@/components/ui/Button.vue'
import Badge from '@/components/ui/Badge.vue'
import AnimatedBackground from '@/components/AnimatedBackground.vue'
import ContentSelector from '@/components/ContentSelector.vue'
import GameCard from '@/components/GameCard.vue'
import ContentDetailDialog from '@/components/ContentDetailDialog.vue'
import InfluencerProfileDialog from '@/components/InfluencerProfileDialog.vue'
import { getFeaturedContentList, getLatestContentList, getHomeContentList, getInfluencerList } from '@/api/home'

const route = useRoute()
const router = useRouter()

// 页面类型：featured, latest, influencers
const pageType = ref(route.query.type as string || '')
const currentPlatform = ref(route.query.platform as string || '')
const currentTag = ref(route.query.tag as string || '')
const currentCategory = ref(route.query.category as string || '')

const pageTitle = computed(() => {
  console.log('计算页面标题，当前类型:', pageType.value)
  if (currentPlatform.value) {
    return `${currentPlatform.value} 平台内容`
  }
  if (currentTag.value) {
    return `#${currentTag.value} 标签内容`
  }
  if (currentCategory.value) {
    return `${currentCategory.value} 分类内容`
  }
  switch (pageType.value) {
    case 'featured':
      return '精选内容'
    case 'latest':
      return '最新发布'
    case '热门榜单':
      return '热门榜单'
    case 'influencers':
      console.log('识别为优质达人页面')
      return '优质达人'
    default:
      return '全部内容'
  }
})

// 数据
const allContents = ref<any[]>([])
const allInfluencers = ref<any[]>([])
const selectedCategory = ref('all')
const sortBy = ref('latest')
const viewMode = ref<'grid' | 'list'>('grid')
const currentPage = ref(1)
const itemsPerPage = 12

// 详情弹窗
const showDetailDialog = ref(false)
const selectedContent = ref<any>(null)
const showInfluencerProfile = ref(false)
const selectedInfluencer = ref<any>(null)

// 打开详情
const openDetail = (content: any) => {
  selectedContent.value = {
    ...content,
    description: content.description || '这是一篇精彩的内容，包含了丰富的信息和独特的见解。作者用心创作，为读者带来了优质的阅读体验。内容涵盖了多个方面，从不同角度进行了深入的分析和探讨。',
    publishTime: content.publishTime || '2024-01-15 10:30',
    shares: content.shares || '1.2万',
    category: content.category || '热门',
    author: content.author || {
      name: '优质创作者',
      avatar: '',
      followers: '10万+'
    }
  }
  showDetailDialog.value = true
}

// // 筛选分类
// const filterCategories = ref([
//   { id: 'all', name: '全部' },
//   { id: 'video', name: '视频' },
//   { id: 'article', name: '文章' },
//   { id: 'image', name: '图片' },
//   { id: 'live', name: '直播' },
// ])

// 加载数据
const loadData = async () => {
  console.log('开始加载数据，当前页面类型:', pageType.value)
  console.log('路由参数:', route.query)

  // 如果是优质达人页面，加载达人列表
  if (pageType.value === 'influencers') {
    console.log('加载优质达人列表...')
    try {
      const response = await getInfluencerList()
      console.log('达人列表API响应:', response)

      if (response.data && Array.isArray(response.data)) {
        console.log('达人数据条数:', response.data.length)
        console.log('第一条达人数据:', response.data[0])

        allInfluencers.value = response.data.map((item: any) => {
          console.log('处理达人数据:', item)
          return {
            id: item.id || item.influencerId,
            name: item.name,
            platform: item.platform,
            followers: item.followers,
            field: item.field,
            image: item.image,
            description: item.description,
          }
        })
        console.log('处理后的达人列表:', allInfluencers.value)
      } else {
        console.log('达人数据格式错误或为空')
        allInfluencers.value = []
      }
    } catch (error) {
      console.error('加载达人列表失败:', error)
      allInfluencers.value = []
    }
    return
  }

  let response

  // 根据路由参数构建请求
  const params: {
    type?: string
    category?: string
    platform?: string
    tag?: string
  } = {}

  if (pageType.value) {
    params.type = pageType.value
  }
  if (currentPlatform.value) {
    params.platform = currentPlatform.value
  }
  if (currentTag.value) {
    params.tag = currentTag.value
  }
  if (currentCategory.value) {
    params.category = currentCategory.value
  }

  // 调用通用的获取内容列表API
  console.log('请求参数:', params)
  response = await getHomeContentList(params.type, params.category, params.platform, params.tag)
  console.log('后端返回的数据:', response)

  if (response.data && Array.isArray(response.data)) {
    console.log('数据条数:', response.data.length)
    console.log('第一条数据示例:', response.data[0])
    
    // 直接使用后端返回的数据，不进行复制
    // 使用 Map 来去重，确保每个 ID 只出现一次
    const contentMap = new Map<number, any>()
    
    response.data.forEach((item: any, index: number) => {
      const id = item.id || (index + 1)
      // 如果ID已存在，跳过（避免重复）
      if (!contentMap.has(id)) {
        contentMap.set(id, {
          id: id,
          title: item.title,
          image: item.image || '', // 后端ContentDTO返回的字段名是image
          tags: Array.isArray(item.tags) ? item.tags : (item.tags || []), // 确保tags是数组
          likes: item.likes || '0', // 后端返回的字段名是likes
          comments: item.comments || '0', // 后端返回的字段名是comments
          players: item.players || '0', // 后端返回的字段名是players
          description: item.description || '',
          publishTime: item.publishTime, // 后端返回的字段名是publishTime
          platform: item.platform || '',
          influencerId: item.influencerId, // 达人ID，用于点击头像跳转
        })
      }
    })
    
    // 将 Map 转换为数组
    allContents.value = Array.from(contentMap.values())
    console.log('处理后的数据条数:', allContents.value.length)
  } else {
    // 如果没有数据，设置为空数组
    allContents.value = []
  }
}

// 过滤和排序后的内容
const filteredContents = computed(() => {
  let result = [...allContents.value]

  // 筛选
  if (selectedCategory.value !== 'all') {
    // 这里可以根据实际的分类字段进行筛选
    // result = result.filter(item => item.category === selectedCategory.value)
  }

  // 排序
  if (sortBy.value === 'popular') {
    result.sort((a, b) => {
      const likesA = parseInt(a.likes.replace(/[^\d]/g, '')) || 0
      const likesB = parseInt(b.likes.replace(/[^\d]/g, '')) || 0
      return likesB - likesA
    })
  }

  return result
})

// 前6个内容用于ContentSelector展示
const topSixContents = computed(() => {
  return filteredContents.value.slice(0, 6)
})

// 分页后的内容
const paginatedContents = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filteredContents.value.slice(start, end)
})

// 总页数
const totalPages = computed(() => {
  return Math.ceil(filteredContents.value.length / itemsPerPage)
})

// 总数量
const totalCount = computed(() => {
  if (pageType.value === 'influencers') {
    return allInfluencers.value.length
  }
  return filteredContents.value.length
})

// 点击达人
const handleInfluencerClick = (influencer: any) => {
  selectedInfluencer.value = influencer
  showInfluencerProfile.value = true
}

// 从详情页查看达人主页
const handleViewInfluencerFromDetail = async (influencerId: number) => {
  // 从达人列表中查找对应的达人
  let influencer = allInfluencers.value.find(inf => inf.id === influencerId)

  // 如果当前页面不是达人列表页，或者找不到达人，则需要加载达人数据
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

// 返回上一页
const goBack = () => {
  router.back()
}

onMounted(() => {
  console.log('ViewAllPage组件挂载完成')
  console.log('当前路由:', route.fullPath)
  console.log('查询参数:', route.query)
  console.log('页面类型:', pageType.value)
  loadData()
})

watch(
  () => route.query,
  () => {
    console.log('路由参数变化:', route.query)
    pageType.value = route.query.type as string || ''
    currentPlatform.value = route.query.platform as string || ''
    currentTag.value = route.query.tag as string || ''
    currentCategory.value = route.query.category as string || ''
    console.log('更新后的页面类型:', pageType.value)
    loadData()
  }
)

</script>

