<template>
  <Button
    variant="ghost"
    size="icon"
    @click="isOpen = true"
    class="md:hidden text-zinc-400 hover:text-white"
  >
    <Search class="h-5 w-5" />
  </Button>

  <Teleport to="body">
    <Transition name="fade">
      <div
        v-if="isOpen"
        class="fixed inset-0 z-50 bg-black/80 backdrop-blur-sm"
        @click="isOpen = false"
      >
        <div class="container mx-auto px-4 pt-20" @click.stop>
          <div class="relative max-w-2xl mx-auto">
            <Search class="absolute left-3 top-3.5 h-5 w-5 text-zinc-400" />
            <Input
              ref="searchInput"
              v-model="searchQuery"
              placeholder="搜索游戏、发行商..."
              class="pl-10 h-12 bg-zinc-800/50 border-zinc-700 focus-visible:border-emerald-500 focus-visible:ring-emerald-500/20 text-lg"
              @keydown.esc="isOpen = false"
            />
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
import { ref, watch, nextTick } from 'vue'
import { Search } from 'lucide-vue-next'
import Button from '@/components/ui/Button.vue'
import Input from '@/components/ui/Input.vue'

const isOpen = ref(false)
const searchQuery = ref('')
const searchInput = ref<HTMLInputElement | null>(null)

watch(isOpen, async (newValue) => {
  if (newValue) {
    await nextTick()
    searchInput.value?.focus()
  }
})
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>

