<template>
  <img
    :class="cn('aspect-square h-full w-full', props.class)"
    :src="src"
    :alt="alt"
  />
</template>

<script setup lang="ts">
import { cn } from '@/lib/utils'

interface Props {
  src?: string
  alt?: string
  class?: string
}

const props = defineProps<Props>()
</script>

