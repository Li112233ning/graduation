<script setup lang="ts">
import { ref } from "vue";
import { useHook } from "./hook.tsx";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import Delete from "@iconify-icons/ep/delete";
import EditPen from "@iconify-icons/ep/edit-pen";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import AddFill from "@iconify-icons/ri/add-circle-line";
import View from "@iconify-icons/ep/view";

defineOptions({
  name: "ContentArticle"
});

const formRef = ref();
const {
  searchFormParams,
  pageLoading,
  columns,
  dataList,
  pagination,
  buttonClass,
  onSearch,
  resetForm,
  handleDelete,
  handleBatchDelete,
  handleAudit,
  openDialog,
  getList,
  categoryOptions
} = useHook();
</script>

<template>
  <div class="main">
    <el-form
      ref="formRef"
      :inline="true"
      :model="searchFormParams"
      class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
    >
      <el-form-item label="标题：" prop="title">
        <el-input
          v-model="searchFormParams.title"
          placeholder="请输入标题"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item label="分类：" prop="categoryId">
        <el-select
          v-model="searchFormParams.categoryId"
          placeholder="请选择分类"
          clearable
          class="!w-[180px]"
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="类型：" prop="contentType">
        <el-select
          v-model="searchFormParams.contentType"
          placeholder="请选择类型"
          clearable
          class="!w-[180px]"
        >
          <el-option label="精选" :value="1" />
          <el-option label="最新" :value="2" />
          <el-option label="热门" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="平台：" prop="platform">
        <el-input
          v-model="searchFormParams.platform"
          placeholder="请输入平台"
          clearable
          class="!w-[180px]"
        />
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-select
          v-model="searchFormParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-[180px]"
        >
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态：" prop="auditStatus">
        <el-select
          v-model="searchFormParams.auditStatus"
          placeholder="请选择审核状态"
          clearable
          class="!w-[180px]"
        >
          <el-option label="待审核" :value="0" />
          <el-option label="审核通过" :value="1" />
          <el-option label="审核拒绝" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon(Search)"
          :loading="pageLoading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <PureTableBar title="稿件列表" :columns="columns" @refresh="getList">
      <template #buttons>
        <el-button
          type="primary"
          :icon="useRenderIcon(AddFill)"
          @click="openDialog()"
        >
          新增稿件
        </el-button>
        <el-button
          type="danger"
          :icon="useRenderIcon(Delete)"
          @click="handleBatchDelete"
        >
          批量删除
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          align-whole="center"
          showOverflowTooltip
          table-layout="auto"
          :loading="pageLoading"
          :size="size"
          :data="dataList"
          :columns="dynamicColumns"
          :pagination="pagination"
          :paginationSmall="size === 'small' ? true : false"
          :header-cell-style="{
            background: 'var(--el-fill-color-light)',
            color: 'var(--el-text-color-primary)'
          }"
          @selection-change="handleSelectionChange"
          @page-size-change="getList"
          @page-current-change="getList"
        >
          <template #operation="{ row }">
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EditPen)"
              @click="openDialog(row)"
            >
              修改
            </el-button>
            <el-button
              v-if="row.auditStatus === 0"
              class="reset-margin"
              link
              type="success"
              :size="size"
              @click="handleAudit(row, 1)"
            >
              通过
            </el-button>
            <el-button
              v-if="row.auditStatus === 0"
              class="reset-margin"
              link
              type="warning"
              :size="size"
              @click="handleAudit(row, 2)"
            >
              拒绝
            </el-button>
            <el-popconfirm
              :title="`是否确认删除稿件${row.title}?`"
              @confirm="handleDelete(row)"
            >
              <template #reference>
                <el-button
                  class="reset-margin"
                  link
                  type="danger"
                  :size="size"
                  :icon="useRenderIcon(Delete)"
                >
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<style scoped lang="scss">
.main {
  margin: 0;
  padding: 0;
}

:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>