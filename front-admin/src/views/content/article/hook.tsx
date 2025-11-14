import { reactive, ref, onMounted, h, computed } from "vue";
import { type PaginationProps } from "@pureadmin/table";
import { message } from "@/utils/message";
import {
  getContentList,
  addContent,
  updateContent,
  deleteContent,
  batchDeleteContent,
  auditContent,
  getAllCategories,
  type ContentDTO,
  type ContentQuery,
  type CategoryDTO
} from "@/api/content";
import { addDialog } from "@/components/ReDialog";
import editForm from "./form.vue";
import { ElMessageBox, ElImage, ElTag } from "element-plus";

export function useHook() {
  const searchFormParams = reactive<ContentQuery>({
    title: "",
    categoryId: undefined,
    contentType: undefined,
    platform: "",
    status: undefined,
    auditStatus: undefined,
    pageNum: 1,
    pageSize: 10
  });

  const dataList = ref<ContentDTO[]>([]);
  const pageLoading = ref(true);
  const selectedRows = ref<ContentDTO[]>([]);
  const categoryOptions = ref<CategoryDTO[]>([]);

  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true
  });

  const contentTypeMap = {
    1: "精选",
    2: "最新",
    3: "热门"
  };

  const columns: TableColumnList = [
    {
      type: "selection",
      width: 55,
      align: "left",
      hide: ({ checkList = [] } = {}) => !checkList.includes("勾选列")
    },
    {
      label: "序号",
      type: "index",
      width: 70,
      hide: ({ checkList = [] } = {}) => !checkList.includes("序号列")
    },
    {
      label: "稿件ID",
      prop: "contentId",
      minWidth: 100
    },
    {
      label: "封面图",
      prop: "imageUrl",
      minWidth: 120,
      cellRenderer: ({ row }) => (
        <el-image
          src={row.imageUrl || "https://picsum.photos/200/150"}
          fit="cover"
          style="width: 80px; height: 60px; border-radius: 4px"
          preview-src-list={[row.imageUrl || "https://picsum.photos/800/600"]}
        />
      )
    },
    {
      label: "标题",
      prop: "title",
      minWidth: 200
    },
    {
      label: "分类",
      prop: "categoryName",
      minWidth: 120
    },
    {
      label: "类型",
      prop: "contentType",
      minWidth: 100,
      cellRenderer: ({ row }) => (
        <el-tag type="primary">{contentTypeMap[row.contentType]}</el-tag>
      )
    },
    {
      label: "平台",
      prop: "platform",
      minWidth: 120
    },
    {
      label: "所属达人",
      prop: "influencerName",
      minWidth: 150
    },
    {
      label: "审核状态",
      prop: "auditStatus",
      minWidth: 120,
      cellRenderer: ({ row }) => {
        const statusMap = {
          0: { text: "待审核", type: "warning" },
          1: { text: "审核通过", type: "success" },
          2: { text: "审核拒绝", type: "danger" }
        };
        const status = statusMap[row.auditStatus] || statusMap[0];
        return <el-tag type={status.type}>{status.text}</el-tag>;
      }
    },
    {
      label: "点赞数",
      prop: "likesCount",
      minWidth: 100
    },
    {
      label: "评论数",
      prop: "commentsCount",
      minWidth: 100
    },
    {
      label: "浏览数",
      prop: "viewsCount",
      minWidth: 100
    },
    {
      label: "状态",
      prop: "status",
      minWidth: 100,
      cellRenderer: ({ row }) => (
        <el-tag type={row.status === 1 ? "success" : "danger"}>
          {row.status === 1 ? "启用" : "停用"}
        </el-tag>
      )
    },
    {
      label: "创建时间",
      prop: "createTime",
      minWidth: 180
    },
    {
      label: "操作",
      fixed: "right",
      width: 280,
      slot: "operation"
    }
  ];

  const buttonClass = computed(() => {
    return [
      "!h-[20px]",
      "reset-margin",
      "!text-gray-500",
      "dark:!text-white",
      "dark:hover:!text-primary"
    ];
  });

  async function loadCategories() {
    try {
      const { data } = await getAllCategories();
      categoryOptions.value = data;
    } catch (error) {
      console.error("加载分类失败:", error);
    }
  }

  async function getList() {
    pageLoading.value = true;
    try {
      searchFormParams.pageNum = pagination.currentPage;
      searchFormParams.pageSize = pagination.pageSize;

      const response = await getContentList(searchFormParams);
      // 后端返回的是 MyBatis-Plus 的 Page 对象，字段是 records 而不是 rows
      dataList.value = response.data.records || response.data.rows;
      pagination.total = response.data.total;
    } catch (error) {
      console.error("获取稿件列表失败:", error);
      message("获取稿件列表失败", { type: "error" });
    } finally {
      pageLoading.value = false;
    }
  }

  function onSearch() {
    pagination.currentPage = 1;
    getList();
  }

  function resetForm(formEl) {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  }

  function handleSelectionChange(val: ContentDTO[]) {
    selectedRows.value = val;
  }

  function openDialog(row?: ContentDTO) {
    addDialog({
      title: row ? "编辑稿件" : "新增稿件",
      props: {
        formInline: {
          contentId: row?.contentId ?? undefined,
          title: row?.title ?? "",
          categoryId: row?.categoryId ?? undefined,
          imageUrl: row?.imageUrl ?? "",
          images: row?.images ?? "",
          tags: row?.tags ?? "",
          contentType: row?.contentType ?? 1,
          platform: row?.platform ?? "",
          description: row?.description ?? "",
          status: row?.status ?? 1,
          influencerId: row?.influencerId ?? undefined
        }
      },
      width: "60%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: "formRef" }),
      beforeSure: (done, { options }) => {
        const curData = options.props.formInline;

        function chores() {
          message(`您${row ? "编辑" : "新增"}了稿件${curData.title}`, {
            type: "success"
          });
          done();
          getList();
        }

        if (row) {
          updateContent(curData)
            .then(() => {
              chores();
            })
            .catch(error => {
              message(error.message || "更新失败", { type: "error" });
            });
        } else {
          addContent(curData)
            .then(() => {
              chores();
            })
            .catch(error => {
              message(error.message || "新增失败", { type: "error" });
            });
        }
      }
    });
  }

  async function handleDelete(row: ContentDTO) {
    try {
      await deleteContent(row.contentId);
      message(`删除稿件${row.title}成功`, { type: "success" });
      getList();
    } catch (error) {
      message(error.message || "删除失败", { type: "error" });
    }
  }

  function handleBatchDelete() {
    if (selectedRows.value.length === 0) {
      message("请选择要删除的数据", { type: "warning" });
      return;
    }

    ElMessageBox.confirm(
      `确认要删除选中的${selectedRows.value.length}条数据吗?`,
      "批量删除",
      {
        type: "warning"
      }
    )
      .then(async () => {
        const ids = selectedRows.value.map(row => row.contentId);
        await batchDeleteContent(ids);
        message("批量删除成功", { type: "success" });
        getList();
      })
      .catch(() => {});
  }

  async function handleAudit(row: ContentDTO, auditStatus: number) {
    try {
      await ElMessageBox.prompt(
        auditStatus === 1 ? "请输入审核通过备注（可选）" : "请输入审核拒绝原因",
        "审核稿件",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          inputType: "textarea",
          inputPlaceholder: auditStatus === 1 ? "审核通过" : "请输入拒绝原因"
        }
      )
        .then(async ({ value }) => {
          await auditContent(row.contentId, auditStatus, value || "");
          message(
            `稿件${row.title}已${auditStatus === 1 ? "审核通过" : "审核拒绝"}`,
            { type: "success" }
          );
          getList();
        })
        .catch(() => {});
    } catch (error) {
      message(error.message || "审核失败", { type: "error" });
    }
  }

  onMounted(() => {
    loadCategories();
    getList();
  });

  return {
    searchFormParams,
    pageLoading,
    columns,
    dataList,
    pagination,
    buttonClass,
    categoryOptions,
    onSearch,
    resetForm,
    handleSelectionChange,
    handleDelete,
    handleBatchDelete,
    handleAudit,
    openDialog,
    getList
  };
}