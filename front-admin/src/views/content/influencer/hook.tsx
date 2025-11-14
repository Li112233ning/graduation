import { reactive, ref, onMounted, h, computed } from "vue";
import { type PaginationProps } from "@pureadmin/table";
import { message } from "@/utils/message";
import {
  getInfluencerList,
  addInfluencer,
  updateInfluencer,
  deleteInfluencer,
  batchDeleteInfluencer,
  type InfluencerDTO,
  type InfluencerQuery
} from "@/api/content";
import { addDialog } from "@/components/ReDialog";
import editForm from "./form.vue";
import { ElMessageBox, ElImage, ElTag } from "element-plus";

export function useHook() {
  const searchFormParams = reactive<InfluencerQuery>({
    name: "",
    platform: "",
    field: "",
    status: undefined,
    pageNum: 1,
    pageSize: 10
  });

  const dataList = ref<InfluencerDTO[]>([]);
  const pageLoading = ref(true);
  const selectedRows = ref<InfluencerDTO[]>([]);

  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true
  });

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
      label: "达人ID",
      prop: "influencerId",
      minWidth: 100
    },
    {
      label: "头像",
      prop: "imageUrl",
      minWidth: 100,
      cellRenderer: ({ row }) => (
        <el-image
          src={row.imageUrl || "https://picsum.photos/100/100"}
          fit="cover"
          style="width: 60px; height: 60px; border-radius: 50%"
          preview-src-list={[row.imageUrl || "https://picsum.photos/400/400"]}
        />
      )
    },
    {
      label: "达人名称",
      prop: "name",
      minWidth: 150
    },
    {
      label: "平台",
      prop: "platform",
      minWidth: 120
    },
    {
      label: "粉丝数",
      prop: "followers",
      minWidth: 120
    },
    {
      label: "领域",
      prop: "field",
      minWidth: 120
    },
    {
      label: "排序",
      prop: "sortOrder",
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
      width: 180,
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

  async function getList() {
    pageLoading.value = true;
    try {
      searchFormParams.pageNum = pagination.currentPage;
      searchFormParams.pageSize = pagination.pageSize;

      const response = await getInfluencerList(searchFormParams);
      // 后端返回的是 MyBatis-Plus 的 Page 对象，字段是 records 而不是 rows
      dataList.value = response.data.records || response.data.rows;
      pagination.total = response.data.total;
    } catch (error) {
      console.error("获取达人列表失败:", error);
      message("获取达人列表失败", { type: "error" });
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

  function handleSelectionChange(val: InfluencerDTO[]) {
    selectedRows.value = val;
  }

  function openDialog(row?: InfluencerDTO) {
    addDialog({
      title: row ? "编辑达人" : "新增达人",
      props: {
        formInline: {
          influencerId: row?.influencerId ?? undefined,
          name: row?.name ?? "",
          platform: row?.platform ?? "",
          followers: row?.followers ?? "",
          field: row?.field ?? "",
          imageUrl: row?.imageUrl ?? "",
          sortOrder: row?.sortOrder ?? 0,
          status: row?.status ?? 1
        }
      },
      width: "50%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: "formRef" }),
      beforeSure: (done, { options }) => {
        const curData = options.props.formInline;

        function chores() {
          message(`您${row ? "编辑" : "新增"}了达人${curData.name}的数据`, {
            type: "success"
          });
          done();
          getList();
        }

        if (row) {
          updateInfluencer(curData)
            .then(() => {
              chores();
            })
            .catch(error => {
              message(error.message || "更新失败", { type: "error" });
            });
        } else {
          addInfluencer(curData)
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

  async function handleDelete(row: InfluencerDTO) {
    try {
      await deleteInfluencer(row.influencerId);
      message(`删除达人${row.name}成功`, { type: "success" });
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
        const ids = selectedRows.value.map(row => row.influencerId);
        await batchDeleteInfluencer(ids);
        message("批量删除成功", { type: "success" });
        getList();
      })
      .catch(() => {});
  }

  onMounted(() => {
    getList();
  });

  return {
    searchFormParams,
    pageLoading,
    columns,
    dataList,
    pagination,
    buttonClass,
    onSearch,
    resetForm,
    handleSelectionChange,
    handleDelete,
    handleBatchDelete,
    openDialog,
    getList
  };
}