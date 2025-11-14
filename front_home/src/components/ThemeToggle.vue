<template>
  <Button variant="ghost" size="icon" @click="toggleTheme" class="text-zinc-400 hover:text-white">
    <Sun v-if="!isDark" class="h-5 w-5 transition-transform duration-300 hover:rotate-90" />
    <Moon v-else class="h-5 w-5 transition-transform duration-300 hover:-rotate-12" />
  </Button>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { Sun, Moon } from 'lucide-vue-next'
import Button from '@/components/ui/Button.vue'
import { useThemeStore } from '@/stores/theme'

const themeStore = useThemeStore()
const { isDark } = storeToRefs(themeStore)
const { toggleTheme } = themeStore
</script>

