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

    <!-- 居中弹窗 -->
    <Transition name="modal">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
        @click.self="handleClose"
      >
        <div
          class="relative w-full max-w-2xl max-h-[90vh] bg-white rounded-2xl shadow-2xl overflow-hidden flex flex-col"
          @click.stop
        >
        <!-- 关闭按钮 -->
        <button
          @click="handleClose"
          class="absolute top-4 right-4 z-10 w-10 h-10 flex items-center justify-center rounded-full bg-gray-100 hover:bg-gray-200 text-gray-600 hover:text-gray-900 transition-all duration-300"
        >
          <X class="h-5 w-5" />
        </button>

        <!-- 滚动容器 -->
        <div class="flex-1 overflow-y-auto custom-scrollbar">
          <!-- 顶部作者信息 -->
          <div class="p-4 border-b border-gray-100">
            <div class="flex items-center justify-between">
              <div class="flex items-center gap-3">
                <Avatar
                  @click="handleAvatarClick"
                  :class="[
                    'h-11 w-11 border-2 border-gray-100',
                    displayContent?.influencerId ? 'cursor-pointer hover:border-red-500 transition-colors' : ''
                  ]"
                >
                  <AvatarImage :src="displayContent?.author?.avatar" :alt="displayContent?.author?.name" />
                  <AvatarFallback class="bg-gradient-to-br from-red-500 to-pink-500 text-white text-sm">
                    {{ displayContent?.author?.name?.substring(0, 2) }}
                  </AvatarFallback>
                </Avatar>
<!--                <div>-->
<!--                  <h3 class="font-medium text-gray-900 text-sm">{{ displayContent?.author?.name }}</h3>-->
<!--                  <p class="text-xs text-gray-500">{{ displayContent?.author?.followers }} 粉丝</p>-->
<!--                </div>-->
              </div>
<!--              <Button size="sm" class="bg-red-500 hover:bg-red-600 text-white rounded-full px-6 mr-12">-->
<!--                关注-->
<!--              </Button>-->
            </div>
          </div>

          <!-- 多图片轮播 -->
          <div class="relative" v-if="contentImages && contentImages.length > 0">
            <div class="relative overflow-hidden" style="max-height: 300px;">
              <img
                :src="contentImages[currentImageIndex]"
                :alt="displayContent?.title"
                class="w-full object-cover transition-opacity duration-300"
                style="max-height: 300px;"
              />
              <!-- 图片切换按钮 -->
              <button
                v-if="contentImages.length > 1"
                @click="previousImage"
                class="absolute left-4 top-1/2 -translate-y-1/2 bg-black/50 hover:bg-black/70 text-white p-2 rounded-full transition-all duration-300 z-10"
              >
                <ChevronLeft class="h-5 w-5" />
              </button>
              <button
                v-if="contentImages.length > 1"
                @click="nextImage"
                class="absolute right-4 top-1/2 -translate-y-1/2 bg-black/50 hover:bg-black/70 text-white p-2 rounded-full transition-all duration-300 z-10"
              >
                <ChevronRight class="h-5 w-5" />
              </button>
              <!-- 图片指示器 -->
              <div
                v-if="contentImages.length > 1"
                class="absolute bottom-4 left-1/2 -translate-x-1/2 flex gap-2 z-10"
              >
                <div
                  v-for="(img, index) in contentImages"
                  :key="index"
                  :class="[
                    'w-2 h-2 rounded-full transition-all duration-300',
                    currentImageIndex === index ? 'bg-white' : 'bg-white/50'
                  ]"
                ></div>
              </div>
            </div>
          </div>

          <!-- 内容区域 -->
          <div class="p-4">
            <!-- 标题 -->
            <h1 class="text-lg font-bold text-gray-900 mb-3 leading-tight">{{ displayContent?.title }}</h1>

            <!-- 内容描述 -->
            <p v-if="displayContent?.description" class="text-sm text-gray-700 leading-relaxed mb-4">
              {{ displayContent.description }}
            </p>

            <!-- 标签 -->
            <div class="flex flex-wrap gap-2 mb-6">
              <Badge
                v-for="(tag, i) in displayContent?.tags"
                :key="i"
                class="bg-gray-100 text-gray-700 text-xs px-2 py-1 rounded"
              >
                #{{ tag }}
              </Badge>
            </div>

            <!-- 发布时间 -->
            <div class="flex items-center gap-2 text-xs text-gray-400 mb-6">
              <Clock class="h-3 w-3" />
              <span v-if="displayContent?.publishTime">{{ formatTime(displayContent.publishTime) }}</span>
              <span v-if="displayContent?.platform" class="mx-1">·</span>
              <span v-if="displayContent?.platform">{{ displayContent.platform }}</span>
            </div>

            <!-- 互动数据 -->
<!--            <div class="flex items-center gap-6 py-4 border-y border-gray-100 mb-6">-->
<!--              <div class="flex items-center gap-2 text-sm">-->
<!--                <Heart class="h-4 w-4 text-gray-400" />-->
<!--                <span class="text-gray-600">{{ displayContent?.likes || '0' }}</span>-->
<!--              </div>-->
<!--              <div class="flex items-center gap-2 text-sm">-->
<!--                <MessageCircle class="h-4 w-4 text-gray-400" />-->
<!--                <span class="text-gray-600">{{ displayContent?.comments || '0' }}</span>-->
<!--              </div>-->
<!--              <div class="flex items-center gap-2 text-sm">-->
<!--                <Eye class="h-4 w-4 text-gray-400" />-->
<!--                <span class="text-gray-600">{{ displayContent?.players || '0' }}</span>-->
<!--              </div>-->
<!--&lt;!&ndash;              <div v-if="displayContent?.shares" class="flex items-center gap-2 text-sm">&ndash;&gt;-->
<!--&lt;!&ndash;                <Share2 class="h-4 w-4 text-gray-400" />&ndash;&gt;-->
<!--&lt;!&ndash;                <span class="text-gray-600">{{ displayContent.shares }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;              </div>&ndash;&gt;-->
<!--            </div>-->

            <!-- 评论区标题 -->
            <div class="mb-4">
              <h3 class="text-sm font-medium text-gray-900">共 {{ comments.length }} 条评论</h3>
            </div>

            <!-- 评论列表 -->
            <div class="space-y-4 mb-20">
              <div v-if="comments.length === 0" class="text-center py-8 text-gray-400 text-sm">
                暂无评论，快来抢沙发吧~
              </div>
              <div v-for="comment in comments" :key="comment.id" class="flex gap-3">
                <Avatar class="h-8 w-8">
                  <AvatarImage v-if="comment.userAvatar" :src="comment.userAvatar" :alt="comment.userName" />
                  <AvatarFallback class="bg-gray-200 text-gray-600 text-xs">
                    {{ comment.userName?.substring(0, 2) || '用户' }}
                  </AvatarFallback>
                </Avatar>
                <div class="flex-1">
                  <div class="flex items-center gap-2 mb-1">
                    <span class="text-xs font-medium text-gray-900">{{ comment.userName }}</span>
                    <span class="text-xs text-gray-400">{{ formatTime(comment.createTime) }}</span>
                  </div>
                  <p class="text-sm text-gray-700 leading-relaxed">
                    {{ comment.content }}
                  </p>
                  <div class="flex items-center gap-4 mt-2">
                    <button
                      @click="handleCommentLike(comment)"
                      :class="[
                        'flex items-center gap-1 text-xs transition-colors',
                        comment.isLiked ? 'text-red-500' : 'text-gray-400 hover:text-red-500',
                        !userStore.isLoggedIn && 'opacity-50 cursor-pointer'
                      ]"
                      :title="!userStore.isLoggedIn ? '请先登录' : ''"
                    >
                      <Heart :class="['h-3 w-3', comment.isLiked ? 'fill-red-500' : '']" />
                      <span>{{ comment.likesCount || 0 }}</span>
                    </button>
                    <button
                      @click="handleReplyComment(comment)"
                      :class="[
                        'text-xs transition-colors',
                        replyingTo?.id === comment.id ? 'text-red-500' : 'text-gray-400 hover:text-gray-600',
                        !userStore.isLoggedIn && 'opacity-50 cursor-pointer'
                      ]"
                      :title="!userStore.isLoggedIn ? '请先登录' : ''"
                    >
                      {{ replyingTo?.id === comment.id ? '取消回复' : '回复' }}
                    </button>
                  </div>

                  <!-- 回复输入框 -->
                  <div v-if="replyingTo?.id === comment.id" class="mt-3 flex items-center gap-2">
                    <input
                      type="text"
                      v-model="commentContent"
                      :placeholder="`回复 @${comment.userName}`"
                      class="flex-1 px-3 py-2 bg-gray-50 rounded-full text-sm border-none focus:outline-none focus:ring-2 focus:ring-red-500"
                      @keyup.enter="handleSubmitComment"
                    />
                    <button
                      @click="handleSubmitComment"
                      :disabled="commentContent.trim() === ''"
                      class="px-3 py-2 bg-red-500 hover:bg-red-600 disabled:bg-gray-300 disabled:cursor-not-allowed text-white rounded-full text-xs transition-colors"
                    >
                      发送
                    </button>
                    <button
                      @click="cancelReply"
                      class="px-3 py-2 text-gray-600 hover:text-gray-900 text-xs transition-colors"
                    >
                      取消
                    </button>
                  </div>

                  <!-- 回复列表 -->
                  <div v-if="comment.replies && comment.replies.length > 0" class="mt-4 ml-2 space-y-3 border-l-2 border-red-200 pl-4 bg-gray-50/50 py-2 rounded-r-lg">
                    <div v-for="reply in comment.replies" :key="reply.id" class="flex gap-2">
                      <Avatar class="h-6 w-6 flex-shrink-0">
                        <AvatarImage v-if="reply.userAvatar" :src="reply.userAvatar" :alt="reply.userName" />
                        <AvatarFallback class="bg-gray-200 text-gray-600 text-xs">
                          {{ reply.userName?.substring(0, 2) || '用户' }}
                        </AvatarFallback>
                      </Avatar>
                      <div class="flex-1 min-w-0">
                        <div class="flex items-center gap-2 mb-1">
                          <span class="text-xs font-medium text-gray-900">{{ reply.userName }}</span>
                          <span class="text-xs text-gray-400">{{ formatTime(reply.createTime) }}</span>
                        </div>
                        <p class="text-xs text-gray-700 leading-relaxed break-words">
                          {{ reply.content }}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 底部固定操作栏 -->
        <div class="border-t border-gray-200 bg-white p-4">
          <div class="flex items-center gap-3">
            <input
              type="text"
              :placeholder="userStore.isLoggedIn ? '说点什么...' : '请先登录后评论'"
              v-model="commentContent"
              @click="!userStore.isLoggedIn && (showLoginDialog = true)"
              :readonly="!userStore.isLoggedIn"
              class="flex-1 px-4 py-2 bg-gray-50 rounded-full text-sm border-none focus:outline-none focus:ring-2 focus:ring-red-500 disabled:cursor-not-allowed"
            />
            <button
              @click="handleSubmitComment"
              :disabled="!userStore.isLoggedIn || commentContent.trim() === ''"
              class="px-4 py-2 bg-red-500 hover:bg-red-600 disabled:bg-gray-300 disabled:cursor-not-allowed text-white rounded-full text-sm transition-colors"
            >
              发送
            </button>
            <button
              @click="handleToggleLike"
              :class="[
                'flex items-center gap-1 px-3 py-2 hover:bg-gray-100 rounded-full transition-colors',
                displayContent?.isLiked ? 'text-red-500' : 'text-gray-600',
                !userStore.isLoggedIn && 'opacity-50 cursor-pointer'
              ]"
              :title="!userStore.isLoggedIn ? '请先登录' : (displayContent?.isLiked ? '取消点赞' : '点赞')"
            >
              <Heart
                :class="[
                  'h-5 w-5 transition-colors',
                  displayContent?.isLiked ? 'fill-red-500 text-red-500' : ''
                ]"
              />
              <span class="text-sm font-medium">{{ displayContent?.likes || '0' }}</span>
            </button>
            <button
              @click="handleToggleCollect"
              :class="[
                'flex items-center gap-1 px-3 py-2 hover:bg-gray-100 rounded-full transition-colors',
                displayContent?.isCollected ? 'text-yellow-500' : 'text-gray-600',
                !userStore.isLoggedIn && 'opacity-50 cursor-pointer'
              ]"
              :title="!userStore.isLoggedIn ? '请先登录' : (displayContent?.isCollected ? '取消收藏' : '收藏')"
            >
              <Star
                :class="[
                  'h-5 w-5 transition-colors',
                  displayContent?.isCollected ? 'fill-yellow-500 text-yellow-500' : ''
                ]"
              />
              <span class="text-sm font-medium">收藏</span>
            </button>
          </div>
        </div>
        </div>
      </div>
    </Transition>

    <!-- 登录弹窗 -->
    <LoginDialog 
      :is-open="showLoginDialog" 
      @close="showLoginDialog = false"
      @success="handleLoginSuccess"
    />
  </Teleport>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue'
import {
  X,
  Heart,
  MessageCircle,
  Eye,
  Clock,
  Star,
  ChevronLeft,
  ChevronRight,
  Share2
} from 'lucide-vue-next'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import Avatar from '@/components/ui/Avatar.vue'
import AvatarImage from '@/components/ui/AvatarImage.vue'
import AvatarFallback from '@/components/ui/AvatarFallback.vue'
import LoginDialog from '@/components/LoginDialog.vue'
import { getContentDetail, getCommentList, addComment, toggleLike, toggleCollect, toggleCommentLike, CommentItem } from '@/api/home'
import { useUserStore } from '@/stores/user'

interface ContentDetail {
  id: number
  title: string
  image: string
  images?: string[] // 多图片列表
  tags: string[]
  likes: string
  comments: string
  players: string
  platform: string
  category?: string
  description?: string
  publishTime?: string
  shares?: string
  isLiked?: boolean
  isCollected?: boolean
  influencerId?: number
  author?: {
    name: string
    avatar?: string
    followers?: string
  }
}

const props = defineProps<{
  isOpen: boolean
  content: ContentDetail | null
}>()

const emit = defineEmits<{
  close: []
  viewInfluencer: [influencerId: number]
}>()

const userStore = useUserStore()
const commentContent = ref('')
const comments = ref<CommentItem[]>([])
const currentImageIndex = ref(0)
const isLoading = ref(false)
const currentContent = ref<ContentDetail | null>(null)
const showLoginDialog = ref(false)
const pendingAction = ref<'like' | 'collect' | 'comment' | null>(null)
const replyingTo = ref<CommentItem | null>(null)

// 计算属性：获取图片列表
const contentImages = computed(() => {
  const content = currentContent.value || props.content
  if (content?.images && content.images.length > 0) {
    return content.images
  } else if (content?.image) {
    return [content.image]
  }
  return []
})

// 计算属性：获取当前显示的内容（优先使用从后端加载的数据）
const displayContent = computed(() => {
  return currentContent.value || props.content
})

// 加载内容详情和评论
const loadContentDetail = async () => {
  if (!props.content?.id) return
  
  try {
    isLoading.value = true
    // 获取内容详情
    const detailRes = await getContentDetail(props.content.id)
    if (detailRes.data) {
      // 更新内部content数据
      currentContent.value = detailRes.data
    }
    
    // 获取评论列表
    const commentsRes = await getCommentList(props.content.id)
    if (commentsRes.data) {
      comments.value = commentsRes.data
    }
  } catch (error) {
    console.error('加载内容详情失败:', error)
  } finally {
    isLoading.value = false
  }
}

// 提交评论
const handleSubmitComment = async () => {
  console.log('点击提交评论按钮')
  console.log('登录状态:', userStore.isLoggedIn)
  console.log('token:', userStore.token)

  if (!userStore.isLoggedIn) {
    console.log('未登录，显示登录弹窗')
    pendingAction.value = 'comment'
    showLoginDialog.value = true
    return
  }

  if (commentContent.value.trim() === '') {
    alert('评论内容不能为空！')
    return
  }

  if (!props.content?.id) {
    console.log('内容ID不存在')
    return
  }

  console.log('准备发送评论请求，内容ID:', props.content.id, '评论内容:', commentContent.value.trim(), '父评论ID:', replyingTo.value?.id)

  try {
    const res = await addComment(props.content.id, commentContent.value.trim(), replyingTo.value?.id)
    console.log('评论提交成功:', res)
    if (res.data) {
      // 如果是回复评论
      if (replyingTo.value) {
        // 找到父评论并添加回复到其 replies 数组
        const parentComment = comments.value.find(c => c.id === replyingTo.value?.id)
        if (parentComment) {
          if (!parentComment.replies) {
            parentComment.replies = []
          }
          parentComment.replies.push(res.data)
        }
        // 清除回复状态
        cancelReply()
      } else {
        // 添加顶级评论到列表
        comments.value.unshift(res.data)
      }

      commentContent.value = '' // 清空输入框

      // 更新评论数
      if (currentContent.value) {
        const currentCount = parseInt((currentContent.value.comments || '0').replace(/[^\d]/g, '')) || 0
        currentContent.value.comments = (currentCount + 1 >= 10000)
          ? `${((currentCount + 1) / 10000).toFixed(1)}万`
          : (currentCount + 1).toString()
      }
    }
  } catch (error: any) {
    console.error('提交评论失败:', error)
    if (error.response?.status === 401) {
      pendingAction.value = 'comment'
      showLoginDialog.value = true
    } else {
      alert(error.response?.data?.msg || '提交评论失败，请重试')
    }
  }
}

// 点赞/取消点赞
const handleToggleLike = async () => {
  console.log('点击点赞按钮')
  console.log('登录状态:', userStore.isLoggedIn)

  if (!userStore.isLoggedIn) {
    console.log('未登录，显示登录弹窗')
    pendingAction.value = 'like'
    showLoginDialog.value = true
    return
  }

  if (!props.content?.id) {
    console.log('内容ID不存在')
    return
  }

  console.log('准备发送点赞请求，内容ID:', props.content.id)

  try {
    const res = await toggleLike(props.content.id)
    console.log('点赞成功:', res)
    if (res.data) {
      // 更新点赞状态
      if (currentContent.value) {
        currentContent.value.isLiked = res.data.isLiked
        if (res.data.likes) {
          currentContent.value.likes = res.data.likes
        }
      }
    }
  } catch (error: any) {
    console.error('点赞失败:', error)
    if (error.response?.status === 401) {
      pendingAction.value = 'like'
      showLoginDialog.value = true
    } else {
      alert(error.response?.data?.msg || '操作失败，请重试')
    }
  }
}

// 收藏/取消收藏
const handleToggleCollect = async () => {
  if (!userStore.isLoggedIn) {
    pendingAction.value = 'collect'
    showLoginDialog.value = true
    return
  }
  
  if (!props.content?.id) return
  
  try {
    const res = await toggleCollect(props.content.id)
    if (res.data) {
      // 更新收藏状态
      if (currentContent.value) {
        currentContent.value.isCollected = res.data.isCollected
      }
    }
  } catch (error: any) {
    console.error('收藏失败:', error)
    if (error.response?.status === 401) {
      pendingAction.value = 'collect'
      showLoginDialog.value = true
    } else {
      alert(error.response?.data?.msg || '操作失败，请重试')
    }
  }
}

// 图片切换
const nextImage = () => {
  if (contentImages.value.length > 0) {
    currentImageIndex.value = (currentImageIndex.value + 1) % contentImages.value.length
  }
}

const previousImage = () => {
  if (contentImages.value.length > 0) {
    currentImageIndex.value = (currentImageIndex.value - 1 + contentImages.value.length) % contentImages.value.length
  }
}

const handleClose = () => {
  commentContent.value = ''
  comments.value = []
  currentImageIndex.value = 0
  currentContent.value = null
  emit('close')
}

// 监听isOpen变化，加载数据
watch(() => props.isOpen, (newVal) => {
  if (newVal) {
    document.body.style.overflow = 'hidden'
    currentImageIndex.value = 0
    loadContentDetail()
  } else {
    document.body.style.overflow = ''
  }
})

// 监听content变化，重置图片索引
watch(() => props.content?.id, () => {
  currentImageIndex.value = 0
})

// 格式化时间
const formatTime = (time: string | Date | undefined): string => {
  if (!time) return ''
  
  const date = typeof time === 'string' ? new Date(time) : time
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const seconds = Math.floor(diff / 1000)
  const minutes = Math.floor(seconds / 60)
  const hours = Math.floor(minutes / 60)
  const days = Math.floor(hours / 24)
  
  if (days > 0) {
    return `${days}天前`
  } else if (hours > 0) {
    return `${hours}小时前`
  } else if (minutes > 0) {
    return `${minutes}分钟前`
  } else {
    return '刚刚'
  }
}

// 评论点赞
const handleCommentLike = async (comment: CommentItem) => {
  if (!userStore.isLoggedIn) {
    showLoginDialog.value = true
    return
  }

  try {
    const res = await toggleCommentLike(comment.id)
    if (res.data) {
      // 更新评论点赞状态
      comment.isLiked = res.data.isLiked
      // 更新点赞数
      if (res.data.isLiked) {
        comment.likesCount = (comment.likesCount || 0) + 1
      } else {
        comment.likesCount = Math.max(0, (comment.likesCount || 0) - 1)
      }
    }
  } catch (error: any) {
    console.error('评论点赞失败:', error)
    if (error.response?.status === 401) {
      showLoginDialog.value = true
    } else {
      alert(error.response?.data?.msg || '操作失败，请重试')
    }
  }
}

// 回复评论
const handleReplyComment = (comment: CommentItem) => {
  if (!userStore.isLoggedIn) {
    showLoginDialog.value = true
    return
  }

  // 如果已经在回复这条评论，则取消回复
  if (replyingTo.value?.id === comment.id) {
    cancelReply()
    return
  }

  // 设置正在回复的评论
  replyingTo.value = comment
  commentContent.value = `@${comment.userName} `
}

// 取消回复
const cancelReply = () => {
  replyingTo.value = null
  commentContent.value = ''
}

// 登录成功后的处理
const handleLoginSuccess = async () => {
  showLoginDialog.value = false
  // 重新加载内容详情以获取用户的点赞和收藏状态
  if (props.content?.id) {
    await loadContentDetail()
  }
  // 如果有待执行的操作，执行它
  if (pendingAction.value === 'like') {
    pendingAction.value = null
    await handleToggleLike()
  } else if (pendingAction.value === 'collect') {
    pendingAction.value = null
    await handleToggleCollect()
  } else if (pendingAction.value === 'comment') {
    pendingAction.value = null
    // 评论操作需要用户手动输入，所以不需要自动执行
  }
}

// 点击头像查看达人主页
const handleAvatarClick = () => {
  const content = displayContent.value
  console.log('点击头像，内容数据:', content)
  console.log('influencerId:', content?.influencerId)

  if (content?.influencerId) {
    console.log('跳转到达人主页，达人ID:', content.influencerId)
    emit('viewInfluencer', content.influencerId)
    emit('close')
  } else {
    console.warn('该内容没有关联达人信息')
    alert('该内容暂未关联达人信息')
  }
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

/* 自定义滚动条 */
.custom-scrollbar::-webkit-scrollbar {
  width: 6px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background: #d1d5db;
  border-radius: 3px;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background: #9ca3af;
}
</style>

