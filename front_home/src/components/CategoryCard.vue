<template>
  <div
    class="group relative overflow-hidden rounded-xl p-6 cursor-pointer transition-all duration-300 hover:scale-105"
    :style="{ background: `linear-gradient(to bottom right, ${category.fromColor}, ${category.toColor})` }"
  >
    <div class="relative z-10">
      <div class="mb-4 transform transition-transform duration-300 group-hover:scale-110">
        <component :is="category.icon" />
      </div>
      <h3 class="text-lg font-semibold text-white">{{ category.name }}</h3>
    </div>
    <div class="absolute inset-0 bg-black/20 group-hover:bg-black/10 transition-colors duration-300" />
  </div>
</template>

<script setup lang="ts">
import type { Component } from 'vue'

interface Category {
  name: string
  icon: Component
  fromColor: string
  toColor: string
}

defineProps<{
  category: Category
}>()
</script>

