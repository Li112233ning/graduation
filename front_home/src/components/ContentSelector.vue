<template>
  <div class="content-selector-container">
    <div class="content-selector-wrapper">
      <!-- Desktop and tablet view: horizontal expandable cards -->
      <div
        v-for="content in contents"
        :key="content.id"
        :class="['content-item', { active: activeContent === content.id }]"
        @click="handleContentClick(content.id)"
      >
        <img 
          :src="content.image" 
          :alt="content.title"
          class="content-image"
          loading="lazy"
        />
        <div class="content-shadow"></div>
        <div class="content-label">
          <div class="content-info">
            <div class="content-main">{{ content.title }}</div>
            <div class="content-sub">
              <span class="flex items-center gap-2">
                <Heart class="h-4 w-4" />{{ content.likes }}
                <MessageCircle class="h-4 w-4 ml-2" />{{ content.comments }}
              </span>
            </div>
          </div>
        </div>
        
        <!-- Active card details -->
        <div 
          v-if="activeContent === content.id" 
          :key="`details-${content.id}`"
          class="content-details"
        >
          <div class="tags-wrapper">
            <Badge
              v-for="(tag, i) in content.tags"
              :key="i"
              class="bg-white/90 text-gray-900 text-xs"
            >
              {{ tag }}
            </Badge>
          </div>
        </div>
      </div>

      <!-- Mobile view: inactive options as circular icons -->
      <div class="inactive-contents">
        <div
          v-for="content in contents"
          :key="`mobile-${content.id}`"
          v-show="content.id !== activeContent"
          class="inactive-content"
          @click="handleContentClick(content.id)"
        >
          <img 
            :src="content.image" 
            :alt="content.title"
            class="inactive-content-image"
            loading="lazy"
          />
          <div class="inactive-content-inner">
            <Eye class="h-5 w-5 text-red-500" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { Heart, MessageCircle, Eye } from 'lucide-vue-next'
import Badge from '@/components/ui/Badge.vue'
import Button from '@/components/ui/Button.vue'

interface Content {
  id: number
  title: string
  image: string
  tags: string[]
  likes: string
  comments: string
  players: string
}

const props = defineProps<{
  contents: Content[]
  initialActive?: number
}>()

const emit = defineEmits<{
  'view-detail': [content: Content]
}>()

const activeContent = ref(props.initialActive || (props.contents[0]?.id ?? 0))

const handleContentClick = (contentId: number) => {
  if (activeContent.value !== contentId) {
    // 立即更新状态，减少感知延迟
    activeContent.value = contentId
  }
}

// 监听contents变化，如果当前active不在新列表中，重置到第一个
watch(
  () => props.contents,
  (newContents) => {
    if (newContents.length > 0) {
      const currentActiveExists = newContents.find(c => c.id === activeContent.value)
      if (!currentActiveExists) {
        activeContent.value = newContents[0].id
      }
    }
  },
  { deep: true }
)

// 监听initialActive变化
watch(
  () => props.initialActive,
  (newInitialActive) => {
    if (newInitialActive !== undefined) {
      activeContent.value = newInitialActive
    }
  }
)
</script>

<style scoped>
.content-selector-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  min-height: 320px;
  font-family: 'Roboto', system-ui, -apple-system, sans-serif;
  transition: 0.25s;
  padding: 20px 0;
  contain: layout style paint;
}

.content-selector-wrapper {
  display: flex;
  flex-direction: row;
  align-items: stretch;
  overflow: hidden;
  min-width: 600px;
  max-width: 1400px;
  width: calc(100% - 40px);
  height: 380px;
  transform: translateZ(0);
  will-change: transform;
}

.content-item {
  position: relative;
  overflow: hidden;
  min-width: 60px;
  max-width: 100px;
  margin: 10px;
  cursor: pointer;
  transition: 
    flex-grow 0.35s cubic-bezier(0.4, 0.0, 0.2, 1),
    max-width 0.35s cubic-bezier(0.4, 0.0, 0.2, 1),
    min-width 0.35s cubic-bezier(0.4, 0.0, 0.2, 1),
    margin 0.35s cubic-bezier(0.4, 0.0, 0.2, 1),
    border-radius 0.35s cubic-bezier(0.4, 0.0, 0.2, 1),
    box-shadow 0.35s cubic-bezier(0.4, 0.0, 0.2, 1);
  border-radius: 30px;
  flex-grow: 1;
  flex-shrink: 1;
  flex-basis: 80px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  will-change: flex-grow, max-width;
  transform: translateZ(0);
  backface-visibility: hidden;
  perspective: 1000px;
}

.content-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  transition: transform 0.3s ease-out;
  image-rendering: -webkit-optimize-contrast;
  image-rendering: crisp-edges;
  image-rendering: high-quality;
  transform: translateZ(0);
  will-change: transform;
}

.content-item.active {
  flex-grow: 10000;
  flex-shrink: 0;
  flex-basis: auto;
  min-width: 600px;
  max-width: 900px;
  margin: 0px;
  border-radius: 40px;
  box-shadow: 0 20px 25px -5px rgba(239, 68, 68, 0.2), 0 10px 10px -5px rgba(239, 68, 68, 0.1);
}

.content-item:not(.active) {
  flex-grow: 1;
  flex-shrink: 1;
  max-width: 100px;
  min-width: 60px;
}

.content-item:hover:not(.active) .content-image {
  transform: scale(1.05) translateZ(0);
}

.content-item.active .content-shadow {
  box-shadow: none;
}

.content-item:not(.active) .content-shadow {
  bottom: -40px;
  box-shadow: none;
}

.content-item.active .content-label {
  bottom: 125px;
  left: 40px;
  opacity: 1;
}

.content-item:not(.active) .content-label {
  bottom: 20px;
  left: 20px;
  opacity: 1;
}

.content-item.active .content-info > div {
  left: 0px;
  opacity: 1;
}

.content-item:not(.active) .content-info > div {
  left: 20px;
  opacity: 0;
}

.content-shadow {
  position: absolute;
  bottom: 0px;
  left: 0px;
  right: 0px;
  height: 250px;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.85) 0%, rgba(0, 0, 0, 0.5) 50%, transparent 100%);
  transition: bottom 0.3s cubic-bezier(0.4, 0.0, 0.2, 1);
  border-radius: inherit;
  will-change: bottom;
}

.content-label {
  display: flex;
  position: absolute;
  right: auto;
  left: 35px;
  bottom: 110px;
  transition: 
    bottom 0.3s cubic-bezier(0.4, 0.0, 0.2, 1),
    left 0.3s cubic-bezier(0.4, 0.0, 0.2, 1),
    opacity 0.25s ease-out;
  z-index: 10;
  will-change: bottom, left, opacity;
}

.content-info {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  color: white;
  white-space: normal;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.6);
}

.content-info > div {
  position: relative;
  transition: 
    left 0.3s cubic-bezier(0.4, 0.0, 0.2, 1),
    opacity 0.25s ease-out;
  will-change: left, opacity;
}

.content-main {
  font-weight: 700;
  font-size: 1.5rem;
  line-height: 1.5;
  max-width: 650px;
  margin-bottom: 12px;
  letter-spacing: -0.01em;
}

.content-sub {
  transition-delay: 0.1s;
  font-size: 1rem;
  margin-top: 0;
  opacity: 0.9;
  font-weight: 500;
}

.content-details {
  position: absolute;
  bottom: 35px;
  left: 40px;
  right: 40px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  opacity: 0;
  animation: fadeInUp 0.3s ease-out 0.1s forwards;
  z-index: 10;
  transform: translateZ(0);
  will-change: opacity, transform;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(10px) translateZ(0);
  }
  to {
    opacity: 1;
    transform: translateY(0) translateZ(0);
  }
}

.tags-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 2px;
}

.view-detail-btn {
  width: fit-content;
  padding: 10px 24px;
  box-shadow: 0 4px 6px rgba(239, 68, 68, 0.3);
  font-weight: 600;
}

.inactive-contents {
  display: none;
}

/* Tablet and Mobile Responsive Styles */
@media screen and (max-width: 1024px) {
  .content-selector-container {
    padding: 20px;
    min-height: auto;
    flex-direction: column;
  }
  
  .content-selector-wrapper {
    display: flex;
    flex-direction: column;
    min-width: auto;
    max-width: none;
    width: 100%;
    height: auto;
    align-items: center;
  }
  
  /* Active content takes full width and proper height */
  .content-item.active {
    display: block;
    width: 100%;
    max-width: 700px;
    height: 320px;
    margin: 0 0 30px 0;
    border-radius: 25px;
    background-size: cover;
    flex-grow: 0;
    transform: none;
  }
  
  /* Ensure content is in bottom left */
  .content-item.active .content-label {
    bottom: 120px;
    left: 30px;
    right: auto;
  }

  .content-item.active .content-details {
    bottom: 28px;
    left: 30px;
    right: 30px;
  }
  
  .content-item.active .content-info > div {
    left: 0px;
    opacity: 1;
  }
  
  /* Hide inactive contents from normal flow and show as icons */
  .content-item:not(.active) {
    display: none;
  }
  
  /* Show inactive contents as circular icons */
  .inactive-contents {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 15px;
    width: 100%;
    max-width: 700px;
  }
  
  .inactive-content {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    position: relative;
    cursor: pointer;
    transition: transform 0.3s ease;
    overflow: hidden;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }

  .inactive-content:hover {
    transform: scale(1.05);
  }

  .inactive-content-image {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
    border-radius: 50%;
  }
  
  .inactive-content::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.3);
    border-radius: 50%;
    z-index: 1;
  }
  
  .inactive-content-inner {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: white;
    width: 45px;
    height: 45px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
    z-index: 2;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
}

/* Mobile specific adjustments */
@media screen and (max-width: 768px) {
  .content-item.active {
    height: 300px;
    border-radius: 20px;
    max-width: 500px;
  }
  
  .content-item.active .content-label {
    bottom: 115px;
    left: 25px;
  }

  .content-item.active .content-details {
    bottom: 25px;
    left: 25px;
    right: 25px;
  }

  .content-main {
    font-size: 1.3rem;
    max-width: 350px;
  }

  .content-sub {
    font-size: 0.95rem;
  }
  
  .inactive-content {
    width: 70px;
    height: 70px;
  }
  
  .inactive-content-inner {
    width: 40px;
    height: 40px;
  }
}

/* Small mobile adjustments */
@media screen and (max-width: 480px) {
  .content-item.active {
    height: 280px;
    border-radius: 18px;
  }
  
  .content-item.active .content-label {
    bottom: 100px;
    left: 22px;
  }

  .content-item.active .content-details {
    bottom: 22px;
    left: 22px;
    right: 22px;
  }
  
  .content-main {
    font-size: 1.15rem;
    max-width: 280px;
  }

  .content-sub {
    font-size: 0.9rem;
  }
  
  .inactive-content {
    width: 60px;
    height: 60px;
  }
  
  .inactive-content-inner {
    width: 35px;
    height: 35px;
  }
}
</style>

