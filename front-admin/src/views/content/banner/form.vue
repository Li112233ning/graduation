<script setup lang="ts">
import { ref, reactive, computed } from "vue";
import { FormInstance } from "element-plus";

interface FormProps {
  formInline: {
    bannerId?: number;
    title: string;
    imageUrl: string;
    linkUrl?: string;
    sortOrder: number;
    status: number;
  };
}

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    title: "",
    imageUrl: "",
    linkUrl: "",
    sortOrder: 0,
    status: 1
  })
});

const ruleFormRef = ref<FormInstance>();

const rules = reactive({
  title: [
    { required: true, message: "请输入标题", trigger: "blur" },
    { min: 1, max: 128, message: "长度在 1 到 128 个字符", trigger: "blur" }
  ],
  imageUrl: [
    { required: true, message: "请输入图片URL", trigger: "blur" },
    { max: 512, message: "长度不能超过 512 个字符", trigger: "blur" }
  ],
  linkUrl: [
    { max: 512, message: "长度不能超过 512 个字符", trigger: "blur" }
  ],
  status: [{ required: true, message: "请选择状态", trigger: "change" }]
});

const imagePreviewUrl = computed(() => {
  if (props.formInline.imageUrl) {
    return props.formInline.imageUrl;
  }
  return "https://picsum.photos/800/400";
});

function setExampleImage() {
  const randomId = Math.floor(Math.random() * 1000);
  props.formInline.imageUrl = `https://picsum.photos/800/400?random=${randomId}`;
}

defineExpose({
  getFormRuleRef: () => ruleFormRef.value
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
        placeholder="请输入轮播图标题"
        clearable
      />
    </el-form-item>
    <el-form-item label="图片URL" prop="imageUrl">
      <el-input
        v-model="props.formInline.imageUrl"
        placeholder="请输入图片URL"
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
          style="width: 100%; max-width: 600px; height: 300px; border-radius: 4px"
          :preview-src-list="[imagePreviewUrl]"
        />
      </div>
    </el-form-item>
    <el-form-item label="链接地址" prop="linkUrl">
      <el-input
        v-model="props.formInline.linkUrl"
        placeholder="请输入链接地址（可选）"
        clearable
      />
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

