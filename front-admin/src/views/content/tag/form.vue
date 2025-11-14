<script setup lang="ts">
import { ref, reactive } from "vue";
import { FormInstance } from "element-plus";

interface FormProps {
  formInline: {
    tagId?: number;
    tagName: string;
    sortOrder: number;
    status: number;
  };
}

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    tagName: "",
    sortOrder: 0,
    status: 1
  })
});

const ruleFormRef = ref<FormInstance>();

const rules = reactive({
  tagName: [
    { required: true, message: "请输入标签名称", trigger: "blur" },
    { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
  ],
  status: [{ required: true, message: "请选择状态", trigger: "change" }]
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="props.formInline"
    :rules="rules"
    label-width="100px"
  >
    <el-form-item label="标签名称" prop="tagName">
      <el-input
        v-model="props.formInline.tagName"
        placeholder="请输入标签名称"
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