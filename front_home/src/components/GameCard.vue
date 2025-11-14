<template>
  <div
    class="transform transition-all duration-300 hover:scale-105 cursor-pointer"
    @mouseenter="isHovered = true"
    @mouseleave="isHovered = false"
    @click="handleClick"
  >
    <Card
      class="bg-white/80 backdrop-blur-sm border-gray-200/50 overflow-hidden group relative h-full"
    >
      <!-- 悬停时的发光效果 -->
      <div
        :class="[
          'absolute inset-0 bg-gradient-to-r from-red-500/20 to-pink-500/20 opacity-0 transition-opacity duration-500 pointer-events-none',
          isHovered && 'opacity-100'
        ]"
      />

      <!-- 边框发光 -->
      <div
        :class="[
          'absolute inset-0 rounded-lg border-2 border-red-500/0 transition-all duration-500',
          isHovered && 'border-red-500/70 shadow-[0_0_15px_rgba(239,68,68,0.3)]'
        ]"
      />

      <div class="aspect-video relative overflow-hidden">
        <img
          :src="game.image"
          :alt="game.title"
          class="w-full h-full object-cover transition-transform duration-700"
          :class="{ 'scale-110': isHovered, 'scale-100': !isHovered }"
        />

        <div v-if="game.tags && game.tags.length > 0" class="absolute top-2 right-2 z-10">
          <Badge
            class="bg-gradient-to-r from-red-500 to-pink-500 shadow-lg shadow-red-500/20 font-bold text-white"
          >
            {{ game.tags[game.tags.length - 1] }}
          </Badge>
        </div>

        <!-- 悬停时的覆盖层 -->
        <div
          :class="[
            'absolute inset-0 bg-gradient-to-t from-gray-900/90 via-gray-900/50 to-transparent flex items-end justify-between p-3 transition-opacity duration-300',
            isHovered ? 'opacity-100' : 'opacity-0'
          ]"
        >
          <Button class="bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 text-white transition-all duration-300 shadow-lg shadow-red-500/20">
            查看详情
          </Button>

          <Button
            variant="outline"
            size="icon"
            class="bg-white/80 border-gray-200 hover:bg-white hover:border-red-500 transition-all duration-300"
          >
            <Heart
              :class="[
                'w-5 h-5 transition-all duration-300',
                isHovered && 'fill-rose-500 stroke-rose-500'
              ]"
            />
          </Button>
        </div>
      </div>

      <CardContent class="p-4 relative z-10">
        <h3
          :class="[
            'font-semibold mb-2 text-gray-900 transition-colors duration-300 line-clamp-2 h-[3rem]',
            isHovered && 'text-red-500'
          ]"
        >
          {{ game.title }}
        </h3>

        <!-- 标签展示 - 最多显示3个 -->
        <div class="flex flex-wrap gap-1 mb-3 min-h-[1.5rem]">
          <Badge
            v-for="(tag, i) in (game.tags || []).slice(0, 3)"
            :key="i"
            variant="outline"
            :class="[
              'text-xs border-gray-300 text-gray-600 transition-all duration-300',
              isHovered && 'border-red-500/50 bg-red-500/10 text-red-600'
            ]"
          >
            {{ tag }}
          </Badge>
        </div>

        <div class="flex items-center justify-between min-h-[28px]">
          <div class="flex items-center gap-3">
            <div class="flex items-center gap-1">
              <Heart class="h-4 w-4 text-red-500" />
              <span class="text-sm text-gray-700">{{ game.likes }}</span>
            </div>
            <div class="text-sm text-gray-500 flex items-center">
              <MessageCircle class="h-4 w-4 mr-1" />
              {{ game.comments }}
            </div>
            <div class="text-sm text-gray-500 flex items-center">
              <Eye class="h-4 w-4 mr-1" />
              {{ game.players }}
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Heart, MessageCircle, Eye } from 'lucide-vue-next'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'
import Card from '@/components/ui/Card.vue'
import CardContent from '@/components/ui/CardContent.vue'

interface Game {
  title: string
  image: string
  tags: string[]
  likes: string
  comments: string
  players: string
  update?: string
  rating?: number
  price?: number
  discount?: number
}

const props = defineProps<{
  game: Game
}>()

const emit = defineEmits<{
  click: [game: Game]
}>()

const isHovered = ref(false)

const handleClick = () => {
  emit('click', props.game)
}
</script>

