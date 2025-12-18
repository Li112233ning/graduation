<template>
  <div v-if="modelValue" class="fixed inset-0 z-50 flex items-center justify-center">
    <div class="fixed inset-0 bg-black/50" @click="$emit('update:modelValue', false)"></div>
    <div :class="cn('relative z-50 w-full max-w-lg rounded-lg border bg-background p-6 shadow-lg', props.class)">
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
import { cn } from '@/lib/utils'

interface Props {
  class?: string
  modelValue?: boolean
}

const props = defineProps<Props>()

defineEmits<{
  'update:modelValue': [value: boolean]
}>()
</script>
