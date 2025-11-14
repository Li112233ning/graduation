<template>
  <Button
    variant="ghost"
    size="icon"
    @click="isOpen = true"
    class="md:hidden text-gray-600 hover:text-gray-900"
  >
    <Menu class="h-5 w-5" />
  </Button>

  <Teleport to="body">
    <Transition name="slide">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 bg-black/50 backdrop-blur-sm"
        @click="isOpen = false"
      >
        <div
          class="absolute right-0 top-0 h-full w-64 bg-white/95 backdrop-blur-md border-l border-gray-200/50 shadow-lg p-6"
          @click.stop
        >
          <div class="flex justify-between items-center mb-8">
            <h2 class="text-xl font-bold text-gray-900">菜单</h2>
            <Button variant="ghost" size="icon" @click="isOpen = false" class="text-gray-600 hover:text-gray-900">
              <X class="h-5 w-5" />
            </Button>
          </div>

          <nav class="space-y-4">
            <a
              href="#"
              class="block text-gray-900 hover:text-red-500 font-medium transition-colors duration-300"
              @click="isOpen = false"
            >
              首页
            </a>
            <a
              href="#"
              class="block text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300"
              @click="isOpen = false"
            >
              稿件
            </a>
            <a
              href="#"
              class="block text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300"
              @click="isOpen = false"
            >
              任务
            </a>
            <a
              href="#"
              class="block text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300"
              @click="isOpen = false"
            >
              数据
            </a>
            <a
              href="#"
              class="block text-gray-600 hover:text-gray-900 font-medium transition-colors duration-300"
              @click="isOpen = false"
            >
              入驻
            </a>
          </nav>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Menu, X } from 'lucide-vue-next'
import Button from '@/components/ui/Button.vue'

const isOpen = ref(false)
</script>

<style scoped>
.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease;
}

.slide-enter-from,
.slide-leave-to {
  opacity: 0;
}

.slide-enter-from > div,
.slide-leave-to > div {
  transform: translateX(100%);
}
</style>

