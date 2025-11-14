<template>
  <Teleport to="body">
    <!-- 背景遮罩 -->
    <Transition name="fade">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm"
        @click="handleClose"
      ></div>
    </Transition>

    <!-- 达人主页弹窗 -->
    <Transition name="modal">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
        @click.self="handleClose"
      >
        <div
          class="relative w-full max-w-4xl bg-white rounded-2xl shadow-2xl overflow-hidden max-h-[90vh] overflow-y-auto"
          @click.stop
        >
          <!-- 关闭按钮 -->
          <button
            @click="handleClose"
            class="absolute top-4 right-4 z-10 w-10 h-10 flex items-center justify-center rounded-full bg-white/80 backdrop-blur-sm hover:bg-white text-gray-600 hover:text-gray-900 transition-all duration-300 shadow-lg"
          >
            <X class="h-5 w-5" />
          </button>

          <!-- 头部背景 -->
          <div class="relative h-48 bg-gradient-to-br from-red-500 to-pink-500">
            <img
              v-if="influencer?.image"
              :src="influencer.image"
              :alt="influencer.name"
              class="w-full h-full object-cover opacity-30"
            />
          </div>

          <!-- 内容区域 -->
          <div class="relative px-8 pb-8">
            <!-- 头像和基本信息 -->
            <div class="flex flex-col md:flex-row gap-6 -mt-16 mb-6">
              <Avatar class="h-32 w-32 border-4 border-white shadow-xl flex-shrink-0">
                <AvatarImage v-if="influencer?.image" :src="influencer.image" :alt="influencer?.name" />
                <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-white text-3xl">
                  {{ influencer?.name?.substring(0, 2) || '达人' }}
                </AvatarFallback>
              </Avatar>

              <div class="flex-1 mt-16 md:mt-0">
                <div class="flex items-start justify-between mb-4">
                  <div>
                    <h2 class="text-3xl font-bold text-gray-900 mb-2">{{ influencer?.name }}</h2>
                    <p class="text-gray-600 mb-2">{{ influencer?.field }}</p>
                    <div class="flex items-center gap-2">
                      <Badge class="bg-gradient-to-r from-red-500 to-pink-500 text-white">
                        {{ influencer?.platform }}
                      </Badge>
                      <span class="text-sm text-gray-500">{{ influencer?.followers }} 粉丝</span>
                    </div>
                  </div>
                  <Button class="bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white">
                    <UserPlus class="h-4 w-4 mr-2" />
                    关注
                  </Button>
                </div>

                <!-- 统计信息 -->
                <div class="grid grid-cols-4 gap-4 p-4 bg-gray-50 rounded-lg">
                  <div class="text-center">
                    <div class="text-2xl font-bold text-gray-900">128</div>
                    <div class="text-xs text-gray-500">作品</div>
                  </div>
                  <div class="text-center">
                    <div class="text-2xl font-bold text-gray-900">{{ influencer?.followers }}</div>
                    <div class="text-xs text-gray-500">粉丝</div>
                  </div>
                  <div class="text-center">
                    <div class="text-2xl font-bold text-gray-900">256</div>
                    <div class="text-xs text-gray-500">关注</div>
                  </div>
                  <div class="text-center">
                    <div class="text-2xl font-bold text-gray-900">1.2万</div>
                    <div class="text-xs text-gray-500">获赞</div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 简介 -->
            <div class="mb-6 p-4 bg-gray-50 rounded-lg">
              <h3 class="text-sm font-medium text-gray-900 mb-2">个人简介</h3>
              <p class="text-sm text-gray-600 leading-relaxed">
                {{ influencer?.description || '这是一位优质创作者，专注于创作高质量内容，为粉丝带来精彩的作品和独特的视角。' }}
              </p>
            </div>

            <!-- 标签页 -->
            <div class="mb-6">
              <div class="flex gap-4 border-b border-gray-200">
                <button
                  v-for="tab in tabs"
                  :key="tab.id"
                  @click="activeTab = tab.id"
                  :class="[
                    'px-4 py-2 text-sm font-medium transition-all duration-300 relative',
                    activeTab === tab.id
                      ? 'text-red-500'
                      : 'text-gray-600 hover:text-gray-900'
                  ]"
                >
                  {{ tab.label }}
                  <div
                    v-if="activeTab === tab.id"
                    class="absolute bottom-0 left-0 right-0 h-0.5 bg-red-500"
                  ></div>
                </button>
              </div>
            </div>

            <!-- 作品列表 -->
            <div v-if="activeTab === 'works'">
              <div v-if="loading" class="text-center py-8 text-gray-500">加载中...</div>
              <div v-else-if="works.length === 0" class="text-center py-8 text-gray-500">暂无作品</div>
              <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                <div
                  v-for="work in works"
                  :key="work.id"
                  @click="handleWorkClick(work)"
                  class="group relative aspect-video rounded-lg overflow-hidden cursor-pointer"
                >
                  <img
                    :src="work.image"
                    :alt="work.title"
                    class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
                  />
                  <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex flex-col justify-end p-4">
                    <h4 class="text-white font-medium text-sm mb-1">{{ work.title }}</h4>
                    <div class="flex items-center gap-3 text-white/80 text-xs">
                      <span class="flex items-center gap-1">
                        <Heart class="h-3 w-3" />
                        {{ work.likes }}
                      </span>
                      <span class="flex items-center gap-1">
                        <MessageCircle class="h-3 w-3" />
                        {{ work.comments }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 动态列表 -->
            <div v-if="activeTab === 'moments'" class="space-y-4">
              <div
                v-for="moment in mockMoments"
                :key="moment.id"
                class="p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <div class="flex items-start gap-3">
                  <Avatar class="h-10 w-10">
                    <AvatarImage :src="influencer?.image" :alt="influencer?.name" />
                    <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-white text-sm">
                      {{ influencer?.name?.substring(0, 2) }}
                    </AvatarFallback>
                  </Avatar>
                  <div class="flex-1">
                    <div class="flex items-center gap-2 mb-1">
                      <span class="font-medium text-gray-900">{{ influencer?.name }}</span>
                      <span class="text-xs text-gray-500">{{ moment.time }}</span>
                    </div>
                    <p class="text-sm text-gray-700 mb-2">{{ moment.content }}</p>
                    <div class="flex items-center gap-4 text-xs text-gray-500">
                      <button class="flex items-center gap-1 hover:text-red-500 transition-colors">
                        <Heart class="h-3 w-3" />
                        {{ moment.likes }}
                      </button>
                      <button class="flex items-center gap-1 hover:text-red-500 transition-colors">
                        <MessageCircle class="h-3 w-3" />
                        {{ moment.comments }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { X, UserPlus, Heart, MessageCircle } from 'lucide-vue-next'
import Avatar from '@/components/ui/Avatar.vue'
import AvatarImage from '@/components/ui/AvatarImage.vue'
import AvatarFallback from '@/components/ui/AvatarFallback.vue'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import { getContentListByInfluencerId } from '@/api/home'

interface Influencer {
  id?: number
  name: string
  platform: string
  followers: string
  field: string
  image: string
  description?: string
}

const props = defineProps<{
  isOpen: boolean
  influencer: Influencer | null
}>()

const emit = defineEmits<{
  close: []
  viewContent: [content: any]
}>()

const activeTab = ref('works')

const tabs = [
  { id: 'works', label: '作品' },
  { id: 'moments', label: '动态' },
]

// 作品数据
const works = ref<any[]>([])
const loading = ref(false)

// 加载达人稿件
const loadWorks = async () => {
  if (!props.influencer?.id) {
    works.value = []
    return
  }
  
  loading.value = true
  try {
    const response = await getContentListByInfluencerId(props.influencer.id)
    if (response.data && Array.isArray(response.data)) {
      works.value = response.data.map((item: any) => ({
        id: item.id || item.contentId,
        title: item.title,
        image: item.image || item.imageUrl,
        likes: item.likes || item.likesCount || '0',
        comments: item.comments || item.commentsCount || '0',
        content: item
      }))
    } else {
      works.value = []
    }
  } catch (error) {
    console.error('加载达人稿件失败:', error)
    works.value = []
  } finally {
    loading.value = false
  }
}

// 监听弹窗打开和达人变化
watch([() => props.isOpen, () => props.influencer?.id], ([isOpen, influencerId]) => {
  if (isOpen && influencerId) {
    loadWorks()
  }
})

// 点击作品
const handleWorkClick = (work: any) => {
  emit('viewContent', work.content)
  emit('close')
}

// 模拟动态数据
const mockMoments = ref([
  {
    id: 1,
    content: '今天分享一个新的创作技巧，希望对大家有帮助！',
    time: '2小时前',
    likes: '328',
    comments: '45'
  },
  {
    id: 2,
    content: '感谢大家的支持，新作品即将发布，敬请期待！',
    time: '1天前',
    likes: '856',
    comments: '128'
  },
  {
    id: 3,
    content: '参加了一个很棒的活动，收获满满！',
    time: '3天前',
    likes: '542',
    comments: '67'
  },
])

// 关闭弹窗
const handleClose = () => {
  emit('close')
}
</script>

<style scoped>
/* 背景遮罩动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 弹窗动画 */
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
  transform: scale(0.95) translateY(10px);
}
</style>