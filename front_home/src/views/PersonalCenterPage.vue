<template>
  <div class="min-h-screen bg-background py-12 px-4">
    <div class="max-w-6xl mx-auto">
      <h1 class="text-3xl font-bold mb-8">个人中心</h1>
      
      <Tabs default-value="info" class="w-full">
        <TabsList class="grid w-full grid-cols-3">
          <TabsTrigger value="info">个人信息</TabsTrigger>
          <TabsTrigger value="application">达人申请</TabsTrigger>
          <TabsTrigger value="content" v-if="isInfluencer">我的稿件</TabsTrigger>
        </TabsList>
        
        <TabsContent value="info" class="mt-6">
          <Card class="p-6">
            <h2 class="text-xl font-semibold mb-4">个人信息</h2>
            <div class="space-y-4">
              <div class="flex items-center gap-4">
                <img :src="userAvatar" alt="头像" class="w-16 h-16 rounded-full" />
                <div>
                  <p class="font-medium">{{ displayName }}</p>
                  <p class="text-sm text-muted-foreground">{{ userInfo?.email || '未设置邮箱' }}</p>
                </div>
              </div>
              <div v-if="isInfluencer" class="flex items-center gap-2">
                <Badge class="bg-purple-100 text-purple-800 dark:bg-purple-900/20 dark:text-purple-400">
                  达人用户
                </Badge>
              </div>
            </div>
          </Card>
        </TabsContent>
        
        <TabsContent value="application" class="mt-6">
          <ApplicationStatusCard :application="application" />
        </TabsContent>
        
        <TabsContent value="content" class="mt-6" v-if="isInfluencer">
          <ContentListTable />
        </TabsContent>
      </Tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { useInfluencerStore } from '@/stores/influencer'
import Card from '@/components/ui/Card.vue'
import Badge from '@/components/ui/Badge.vue'
import Tabs from '@/components/ui/Tabs.vue'
import TabsList from '@/components/ui/TabsList.vue'
import TabsTrigger from '@/components/ui/TabsTrigger.vue'
import TabsContent from '@/components/ui/TabsContent.vue'
import ApplicationStatusCard from '@/components/ApplicationStatusCard.vue'
import ContentListTable from '@/components/ContentListTable.vue'

const userStore = useUserStore()
const influencerStore = useInfluencerStore()

const userInfo = computed(() => userStore.userInfo)
const userAvatar = computed(() => userStore.userAvatar)
const displayName = computed(() => userStore.displayName)
const isInfluencer = computed(() => influencerStore.isInfluencer)
const application = computed(() => influencerStore.application)

onMounted(async () => {
  await influencerStore.checkInfluencerRole()
  await influencerStore.fetchMyApplication()
})
</script>
