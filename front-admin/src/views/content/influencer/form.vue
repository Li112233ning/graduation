<script setup lang="ts">
import { ref, reactive, computed } from "vue";
import { FormInstance } from "element-plus";

interface FormProps {
  formInline: {
    influencerId?: number;
    name: string;
    platform: string;
    followers?: string;
    field?: string;
    imageUrl: string;
    sortOrder: number;
    status: number;
  };
}

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    name: "",
    platform: "",
    followers: "",
    field: "",
    imageUrl: "",
    sortOrder: 0,
    status: 1
  })
});

const ruleFormRef = ref<FormInstance>();

const rules = reactive({
  name: [
    { required: true, message: "请输入达人名称", trigger: "blur" },
    { min: 1, max: 128, message: "长度在 1 到 128 个字符", trigger: "blur" }
  ],
  platform: [
    { required: true, message: "请输入所属平台", trigger: "blur" },
    { min: 1, max: 64, message: "长度在 1 到 64 个字符", trigger: "blur" }
  ],
  imageUrl: [
    { required: true, message: "请输入封面图片URL", trigger: "blur" },
    { max: 512, message: "长度不能超过 512 个字符", trigger: "blur" }
  ],
  status: [{ required: true, message: "请选择状态", trigger: "change" }]
});

const imagePreviewUrl = computed(() => {
  if (props.formInline.imageUrl) {
    return props.formInline.imageUrl;
  }
  return "https://picsum.photos/400/400";
});

function setExampleImage() {
  const randomId = Math.floor(Math.random() * 1000);
  props.formInline.imageUrl = `https://picsum.photos/400/400?random=${randomId}`;
}
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="props.formInline"
    :rules="rules"
    label-width="100px"
  >
    <el-form-item label="达人名称" prop="name">
      <el-input
        v-model="props.formInline.name"
        placeholder="请输入达人名称"
        clearable
      />
    </el-form-item>
    <el-form-item label="所属平台" prop="platform">
      <el-input
        v-model="props.formInline.platform"
        placeholder="请输入所属平台"
        clearable
      />
    </el-form-item>
    <el-form-item label="粉丝数量" prop="followers">
      <el-input
        v-model="props.formInline.followers"
        placeholder="请输入粉丝数量，如：58万"
        clearable
      />
    </el-form-item>
    <el-form-item label="专业领域" prop="field">
      <el-input
        v-model="props.formInline.field"
        placeholder="请输入专业领域"
        clearable
      />
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
          style="width: 150px; height: 150px; border-radius: 4px"
          :preview-src-list="[imagePreviewUrl]"
        />
      </div>
    </el-form-item>
    <el-form-item label="排序" prop="sortOrder">
      <el-input-number
        v-model="props.formInline.sortOrder"
        :min="0"
        :max="9999"
        controls-position="right"
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