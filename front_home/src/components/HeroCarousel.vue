<template>
  <div
    class="relative rounded-xl overflow-hidden"
    @mouseenter="isHovered = true"
    @mouseleave="isHovered = false"
  >
    <div class="aspect-[21/9] relative" v-if="heroGames.length > 0 && currentGame">
      <Transition :name="transitionName">
        <div
          :key="currentIndex"
          class="absolute inset-0"
        >
          <img
            :src="currentGame.image"
            :alt="currentGame.title"
            class="w-full h-full object-cover"
          />
          <div class="absolute inset-0 bg-gradient-to-t from-black/90 via-black/50 to-transparent flex flex-col justify-end p-6 md:p-10">
            <div class="max-w-2xl">
              <div class="animate-fade-in-up" style="animation-delay: 0.2s">
                <Badge class="bg-gradient-to-r from-red-500 to-pink-500 mb-3 uppercase tracking-wider text-white">
                  精选
                </Badge>
              </div>

              <h2 class="text-3xl md:text-5xl font-bold mb-2 text-white animate-fade-in-up" style="animation-delay: 0.3s">
                {{ currentGame.title }}
              </h2>

              <p class="text-gray-200 mb-4 text-base md:text-lg animate-fade-in-up" style="animation-delay: 0.4s">
                {{ currentGame.description }}
              </p>

              <div class="flex flex-wrap gap-2 mb-4 animate-fade-in-up" style="animation-delay: 0.5s">
                <Badge
                  v-for="(tag, index) in currentGame.tags"
                  :key="index"
                  variant="secondary"
                  class="bg-white/20 hover:bg-white/30 text-white transition-colors duration-300"
                >
                  {{ tag }}
                </Badge>
              </div>

              <div class="flex items-center gap-4 flex-wrap animate-fade-in-up" style="animation-delay: 0.6s">
                <Button class="bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white transition-all duration-300 shadow-lg shadow-red-500/20 text-base md:text-lg py-6 px-8">
                  查看详情
                </Button>
<!--                <Button variant="outline" class="text-white border-white hover:bg-white/10 text-base md:text-lg py-6 px-8">-->
<!--                  收藏内容-->
<!--                </Button>-->
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </div>

    <!-- 导航圆点 -->
    <div class="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2 z-10" v-if="heroGames.length > 0">
      <button
        v-for="(_, index) in heroGames"
        :key="index"
        @click="goToSlide(index)"
        :class="[
          'w-3 h-3 rounded-full transition-all duration-300',
          index === currentIndex ? 'bg-red-500 w-6' : 'bg-white/50 hover:bg-white/70'
        ]"
        :aria-label="`跳转到第 ${index + 1} 张幻灯片`"
      />
    </div>

    <!-- 导航箭头 -->
    <button
      v-if="heroGames.length > 1"
      @click="prevSlide"
      class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-black/30 hover:bg-black/50 text-white p-2 rounded-full transition-all duration-300 backdrop-blur-sm z-10"
      aria-label="上一张"
    >
      <ChevronLeft class="h-6 w-6" />
    </button>

    <button
      v-if="heroGames.length > 1"
      @click="nextSlide"
      class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-black/30 hover:bg-black/50 text-white p-2 rounded-full transition-all duration-300 backdrop-blur-sm z-10"
      aria-label="下一张"
    >
      <ChevronRight class="h-6 w-6" />
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { ChevronLeft, ChevronRight } from 'lucide-vue-next'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import { getBannerList, type BannerItem } from '@/api/home'

const heroGames = ref<BannerItem[]>([])
const currentIndex = ref(0)
const isHovered = ref(false)
const transitionName = ref('slide-right')

const currentGame = computed(() => heroGames.value[currentIndex.value])

// 加载轮播图数据
const loadBanners = async () => {
  const response = await getBannerList()
  if (response.data && response.data.length > 0) {
    heroGames.value = response.data
  } else {
    throw new Error('轮播图数据为空')
  }
}

let intervalId: number | null = null

const nextSlide = () => {
  transitionName.value = 'slide-right'
  currentIndex.value = (currentIndex.value + 1) % heroGames.value.length
}

const prevSlide = () => {
  transitionName.value = 'slide-left'
  currentIndex.value = (currentIndex.value - 1 + heroGames.value.length) % heroGames.value.length
}

const goToSlide = (index: number) => {
  transitionName.value = index > currentIndex.value ? 'slide-right' : 'slide-left'
  currentIndex.value = index
}

const startAutoPlay = () => {
  if (heroGames.value.length > 1) {
    intervalId = window.setInterval(() => {
      if (!isHovered.value) {
        nextSlide()
      }
    }, 6000)
  }
}

onMounted(async () => {
  await loadBanners()
  if (heroGames.value.length > 0) {
    startAutoPlay()
  }
})

onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId)
  }
})
</script>

<style scoped>
@keyframes fade-in-up {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fade-in-up 0.5s ease-out forwards;
  opacity: 0;
}

.slide-right-enter-active,
.slide-right-leave-active,
.slide-left-enter-active,
.slide-left-leave-active {
  transition: all 0.6s ease;
  position: absolute;
  width: 100%;
}

.slide-right-enter-from {
  transform: translateX(100%);
}

.slide-right-leave-to {
  transform: translateX(-100%);
}

.slide-left-enter-from {
  transform: translateX(-100%);
}

.slide-left-leave-to {
  transform: translateX(100%);
}
</style>

