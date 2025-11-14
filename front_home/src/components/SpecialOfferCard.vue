<template>
  <div class="group relative overflow-hidden rounded-lg cursor-pointer">
    <div class="aspect-video relative">
      <img
        :src="deal.image"
        :alt="deal.title"
        class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
      />
      <div :class="['absolute inset-0 bg-gradient-to-t from-black via-transparent to-transparent opacity-80']" />
      <div :class="['absolute inset-0 bg-gradient-to-r', deal.gradient, 'opacity-20 group-hover:opacity-30 transition-opacity duration-300']" />
    </div>

    <div class="absolute inset-0 flex flex-col justify-end p-6">
      <Badge class="bg-red-600 w-fit mb-2">{{ deal.endDate }}</Badge>
      <h3 class="text-xl font-bold mb-2 text-white group-hover:text-pink-300 transition-colors duration-300">
        {{ deal.title }}
      </h3>
      <div class="text-2xl font-bold text-red-400">{{ deal.discount }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Badge from '@/components/ui/Badge.vue'

interface Deal {
  title: string
  discount: string
  image: string
  endDate: string
  gradient: string
}

defineProps<{
  deal: Deal
}>()
</script>

