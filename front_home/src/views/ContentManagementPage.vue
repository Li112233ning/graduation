<template>
  <div class="min-h-screen bg-background py-12 px-4">
    <div class="max-w-6xl mx-auto">
      <h1 class="text-3xl font-bold mb-8">内容管理</h1>
      
      <div v-if="!isInfluencer" class="text-center py-12">
        <p class="text-muted-foreground mb-4">您还不是达人用户，无法访问此页面</p>
        <Button @click="$router.push('/influencer-application')">
          申请成为达人
        </Button>
      </div>
      
      <div v-else>
        <div v-if="!showForm">
          <ContentListTable @create="handleCreate" @edit="handleEdit" />
        </div>
        
        <div v-else>
          <Card class="p-6">
            <h2 class="text-xl font-semibold mb-6">
              {{ editingSubmission ? '编辑稿件' : '创建稿件' }}
            </h2>
            <ContentSubmissionForm
              :submission="editingSubmission"
              @success="handleFormSuccess"
              @cancel="handleFormCancel"
            />
          </Card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useInfluencerStore } from '@/stores/influencer'
import type { ContentSubmission } from '@/types/content'
import Card from '@/components/ui/Card.vue'
import Button from '@/components/ui/Button.vue'
import ContentListTable from '@/components/ContentListTable.vue'
import ContentSubmissionForm from '@/components/ContentSubmissionForm.vue'

const influencerStore = useInfluencerStore()

const isInfluencer = computed(() => influencerStore.isInfluencer)
const showForm = ref(false)
const editingSubmission = ref<ContentSubmission | null>(null)

onMounted(async () => {
  await influencerStore.checkInfluencerRole()
})

const handleCreate = () => {
  editingSubmission.value = null
  showForm.value = true
}

const handleEdit = (submission: ContentSubmission) => {
  editingSubmission.value = submission
  showForm.value = true
}

const handleFormSuccess = () => {
  showForm.value = false
  editingSubmission.value = null
}

const handleFormCancel = () => {
  showForm.value = false
  editingSubmission.value = null
}
</script>
