<script setup lang="ts">
import { ref, reactive, onMounted, computed, watch } from "vue";
import { FormInstance } from "element-plus";
import {
  getAllCategories,
  getAllPlatforms,
  getAllTags,
  getAllInfluencers,
  type CategoryDTO,
  type PlatformDTO,
  type TagDTO,
  type InfluencerDTO
} from "@/api/content";

interface FormProps {
  formInline: {
    contentId?: number;
    title: string;
    categoryId?: number;
    imageUrl: string;
    images?: string;
    tags?: string;
    contentType: number;
    platform?: string;
    description?: string;
    status: number;
    influencerId?: number;
  };
}

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    title: "",
    imageUrl: "",
    images: "",
    tags: "",
    contentType: 1,
    platform: "",
    description: "",
    status: 1
  })
});

const ruleFormRef = ref<FormInstance>();
const categoryOptions = ref<CategoryDTO[]>([]);
const platformOptions = ref<PlatformDTO[]>([]);
const tagOptions = ref<TagDTO[]>([]);
const influencerOptions = ref<InfluencerDTO[]>([]);
const selectedTags = ref<number[]>([]);

const rules = reactive({
  title: [
    { required: true, message: "请输入标题", trigger: "blur" },
    { min: 1, max: 256, message: "长度在 1 到 256 个字符", trigger: "blur" }
  ],
  imageUrl: [
    { required: true, message: "请输入封面图片URL", trigger: "blur" },
    { max: 512, message: "长度不能超过 512 个字符", trigger: "blur" }
  ],
  contentType: [
    { required: true, message: "请选择内容类型", trigger: "change" }
  ],
  status: [{ required: true, message: "请选择状态", trigger: "change" }]
});

async function loadCategories() {
  try {
    const { data } = await getAllCategories();
    categoryOptions.value = data;
  } catch (error) {
    console.error("加载分类失败:", error);
  }
}

async function loadPlatforms() {
  try {
    const response = await getAllPlatforms();
    platformOptions.value = response.data || response;
  } catch (error) {
    console.error("加载平台失败:", error);
  }
}

async function loadTags() {
  try {
    const response = await getAllTags();
    tagOptions.value = response.data || response;
    // 如果已有标签，解析为选中状态
    if (props.formInline.tags) {
      try {
        const tagsArray = JSON.parse(props.formInline.tags);
        if (Array.isArray(tagsArray)) {
          // 根据标签名称找到对应的ID
          selectedTags.value = tagsArray
            .map(tagName => {
              const tag = tagOptions.value.find(t => t.tagName === tagName);
              return tag?.tagId;
            })
            .filter(id => id !== undefined) as number[];
        }
      } catch (e) {
        // 如果不是JSON格式，可能是字符串，尝试按逗号分割
        if (typeof props.formInline.tags === 'string' && props.formInline.tags.includes(',')) {
          const tagNames = props.formInline.tags.split(',').map(s => s.trim());
          selectedTags.value = tagNames
            .map(tagName => {
              const tag = tagOptions.value.find(t => t.tagName === tagName);
              return tag?.tagId;
            })
            .filter(id => id !== undefined) as number[];
        }
      }
    }
  } catch (error) {
    console.error("加载标签失败:", error);
  }
}

// 监听 formInline 变化，重新加载标签选中状态
watch(() => props.formInline.tags, () => {
  if (tagOptions.value.length > 0 && props.formInline.tags) {
    try {
      const tagsArray = JSON.parse(props.formInline.tags);
      if (Array.isArray(tagsArray)) {
        selectedTags.value = tagsArray
          .map(tagName => {
            const tag = tagOptions.value.find(t => t.tagName === tagName);
            return tag?.tagId;
          })
          .filter(id => id !== undefined) as number[];
      }
    } catch (e) {
      // 忽略解析错误
    }
  }
});

async function loadInfluencers() {
  try {
    const response = await getAllInfluencers();
    influencerOptions.value = response.data || response;
  } catch (error) {
    console.error("加载达人失败:", error);
  }
}

// 监听标签选择变化，更新tags字段
function handleTagsChange(value: number[]) {
  selectedTags.value = value;
  const selectedTagNames = value
    .map(id => {
      const tag = tagOptions.value.find(t => t.tagId === id);
      return tag?.tagName;
    })
    .filter(name => name !== undefined);
  props.formInline.tags = JSON.stringify(selectedTagNames);
}

const imagePreviewUrl = computed(() => {
  if (props.formInline.imageUrl) {
    return props.formInline.imageUrl;
  }
  return "https://picsum.photos/400/300";
});

function setExampleImage() {
  const randomId = Math.floor(Math.random() * 1000);
  props.formInline.imageUrl = `https://picsum.photos/800/600?random=${randomId}`;
}

onMounted(() => {
  loadCategories();
  loadPlatforms();
  loadTags();
  loadInfluencers();
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="props.formInline"
    :rules="rules"
    label-width="100px"
  >
    <el-form-item label="标题" prop="title">
      <el-input
        v-model="props.formInline.title"
        placeholder="请输入标题"
        clearable
      />
    </el-form-item>
    <el-form-item label="分类" prop="categoryId">
      <el-select
        v-model="props.formInline.categoryId"
        placeholder="请选择分类"
        clearable
        style="width: 100%"
      >
        <el-option
          v-for="item in categoryOptions"
          :key="item.categoryId"
          :label="item.categoryName"
          :value="item.categoryId"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="封面图片" prop="imageUrl">
      <el-input
        v-model="props.formInline.imageUrl"
        placeholder="请输入封面图片URL"
        clearable
      >
        <template #append>
          <el-button @click="setExampleImage">使用示例图片</el-button>
        </template>
      </el-input>
      <div v-if="props.formInline.imageUrl" style="margin-top: 10px">
        <el-image
          :src="imagePreviewUrl"
          fit="cover"
          style="width: 200px; height: 150px; border-radius: 4px"
          :preview-src-list="[imagePreviewUrl]"
        />
      </div>
    </el-form-item>
    <el-form-item label="图片列表" prop="images">
      <el-input
        v-model="props.formInline.images"
        type="textarea"
        :rows="2"
        placeholder="请输入图片列表（JSON数组格式）"
      />
    </el-form-item>
    <el-form-item label="标签" prop="tags">
      <el-select
        v-model="selectedTags"
        multiple
        placeholder="请选择标签"
        clearable
        style="width: 100%"
        @change="handleTagsChange"
      >
        <el-option
          v-for="item in tagOptions"
          :key="item.tagId"
          :label="item.tagName"
          :value="item.tagId"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="内容类型" prop="contentType">
      <el-radio-group v-model="props.formInline.contentType">
        <el-radio :label="1">精选</el-radio>
        <el-radio :label="2">最新</el-radio>
        <el-radio :label="3">热门</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="发布平台" prop="platform">
      <el-select
        v-model="props.formInline.platform"
        placeholder="请选择发布平台"
        clearable
        style="width: 100%"
      >
        <el-option
          v-for="item in platformOptions"
          :key="item.platformId"
          :label="item.platformName"
          :value="item.platformName"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="所属达人" prop="influencerId">
      <el-select
        v-model="props.formInline.influencerId"
        placeholder="请选择所属达人"
        clearable
        style="width: 100%"
      >
        <el-option
          v-for="item in influencerOptions"
          :key="item.influencerId"
          :label="item.name"
          :value="item.influencerId"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="内容描述" prop="description">
      <el-input
        v-model="props.formInline.description"
        type="textarea"
        :rows="3"
        placeholder="请输入内容描述"
      />
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-radio-group v-model="props.formInline.status">
        <el-radio :label="1">启用</el-radio>
        <el-radio :label="0">停用</el-radio>
      </el-radio-group>
    </el-form-item>
  </el-form>
</template>