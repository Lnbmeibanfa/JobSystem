<script setup>
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { ref, reactive, onMounted, shallowRef, onBeforeUnmount } from 'vue'
import { ElButton, ElDialog, ElMessage, ElMessageBox } from 'element-plus'
import { useAccountStore } from '@/stores/login'
import {
  addPositionAPI,
  selectByPageAPI,
  updatePositionAPI,
  deleteBatch,
  deleteById
} from '@/api/position'
import { cloneDeep } from 'lodash'
import { STATUS } from '@/utils/Contants'

onMounted(() => {
  // 加载表格数据
  load()
})
// pinia用户数据仓库初始化
const accountStore = useAccountStore()

// 富文本编辑器配置
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
// 内容 HTML
const toolbarConfig = {}
const editorConfig = { MENU_CONF: {} }
editorConfig.MENU_CONF['uploadImage'] = {
  server: 'http://localhost:9090/files/wang/upload', // 服务端图片上传接口
  fieldName: 'file' // 服务端图片上传接口参数
}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const mode = ref('default')
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref(null)
const employName = ref(null)
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, name.value, employName.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  name.value = null
  employName.value = null
  load()
}
// 展示职位描述
const contentVisiable = ref(false)
const viewContent = ref(null)
const showContent = (content) => {
  viewContent.value = cloneDeep(content)
  contentVisiable.value = true
}
/**dialog数据和方法*/
// 表单数据和rules
const positionForm = ref(null)
const formData = ref({})
const rules = reactive({})
// 打开dialog
const handeleAdd = () => {
  formData.value = {}
  toggleFormVisiable(true)
}
// 修改行表格
const handleEdit = (row) => {
  formData.value = {}
  formData.value = JSON.parse(JSON.stringify(row))
  toggleFormVisiable(true)
}
// 审批通过行表格
const handleApprove = (row) => {
  formData.value = JSON.parse(JSON.stringify(row))
  formData.value.status = STATUS.AUDIT_STATUS_APPROVED
  update()
}
// 审批不通过行表格
const handleReject = (row) => {
  formData.value = JSON.parse(JSON.stringify(row))
  formData.value.status = STATUS.AUDIT_STATUS_REJECTED
  update()
}
const handleDel = (id) => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteById(id).then((res) => {
        if (res.code === '200') {
          ElMessage.success('删除成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch((err) => console.error(err))
}
// 维护批量删除的id
const ids = ref([])
const handleSelectionChange = (rows) => {
  ids.value = rows.map((row) => row.id)
}
const handleDelBatch = () => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteBatch(ids.value).then((res) => {
        if (res.code === '200') {
          ElMessage.success('批量删除成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch((err) => console.error(err))
}
// 处理表单是否可见
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}
const formVisiable = ref(false)
// 提交表单
const submit = () => {
  positionForm.value.validate(async (valid) => {
    if (valid) {
      formData.value.id ? update() : add()
    }
  })
}
const add = () => {
  addPositionAPI(formData.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      toggleFormVisiable(false)
      load()
    } else {
      ElMessage.warning(res.msg)
    }
  })
}
const update = () => {
  updatePositionAPI(formData.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      toggleFormVisiable(false)
      load()
    } else {
      ElMessage.warning(res.msg)
    }
  })
}
</script>

<template>
  <div class="manager-position">
    <div class="card">
      <el-input
        v-model="name"
        prefix-icon="Search"
        placeholder="请输入名称查询"
        style="width: 400px; margin-right: 15px"
        @keyup.enter="load"
      ></el-input>
      <el-input
        v-model="employName"
        prefix-icon="Search"
        placeholder="请输入公司名称查询"
        style="width: 400px; margin-right: 15px"
        @keyup.enter="load"
      ></el-input>
      <el-button type="primary" size="default" @click="load" plain>查询</el-button>
      <el-button type="warning" size="default" @click="replace" plain>重置</el-button>
    </div>
    <div class="card" v-if="accountStore.AccountInfo.role === 'EMPLOY'">
      <el-button @click="handeleAdd" plain type="primary">添加</el-button>
      <el-button @click="handleDelBatch" plain type="danger">批量删除</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData" @selection-change="handleSelectionChange" show-overflow-tooltip>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="name" label="职位名称" />
        <el-table-column prop="employName" label="招聘企业" />
        <el-table-column prop="industryName" label="行业" />
        <el-table-column prop="type" label="求职类型" />
        <el-table-column prop="experience" label="工作经验" />
        <el-table-column prop="salary" label="薪资待遇" />
        <el-table-column prop="education" label="学历要求" />
        <el-table-column prop="content" label="职位描述">
          <template v-slot="scope">
            <el-button type="primary" size="default" @click="showContent(scope.row.content)"
              >查看详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="tag" label="职位标签" />
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === STATUS.AUDIT_STATUS_PENDING" type="warning">{{
              scope.row.status
            }}</el-tag>
            <el-tag v-if="scope.row.status === STATUS.AUDIT_STATUS_APPROVED" type="success">{{
              scope.row.status
            }}</el-tag>
            <el-tag v-if="scope.row.status === STATUS.AUDIT_STATUS_REJECTED" type="danger">{{
              scope.row.status
            }}</el-tag>
          </template></el-table-column
        >
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              circle
              icon="Edit"
              size="default"
              @click="handleEdit(scope.row)"
              v-if="accountStore.AccountInfo.role === 'EMPLOY'"
            ></el-button>
            <el-button
              type="primary"
              circle
              icon="Select"
              size="default"
              @click="handleApprove(scope.row)"
              v-if="accountStore.AccountInfo.role === 'ADMIN'"
            ></el-button>
            <el-button
              type="primary"
              circle
              icon="Close"
              size="default"
              @click="handleReject(scope.row)"
              v-if="accountStore.AccountInfo.role === 'ADMIN'"
            ></el-button>
            <el-button
              type="danger"
              circle
              icon="Delete"
              size="default"
              @click="handleDel(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
        background
        :page-size="pageSize"
        v-model:current-page="pageNum"
        layout="prev, pager, next"
        :total="total"
        @current-change="load"
      />
    </div>
    <el-dialog v-model="formVisiable" title="职位信息" width="50%">
      <el-form
        ref="positionForm"
        :model="formData"
        label-width="70px"
        style="padding: 20px"
        :rules="rules"
      >
        <el-form-item label="职位名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入职位名称" />
        </el-form-item>
        <el-form-item label="求职类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择求职类型">
            <el-option label="全职" value="全职"></el-option>
            <el-option label="兼职" value="兼职"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工作经验" prop="experience">
          <el-select v-model="formData.experience" placeholder="请选择工作经验">
            <el-option label="在校生" value="在校生"></el-option>
            <el-option label="应届生" value="应届生"></el-option>
            <el-option label="经验不限" value="经验不限"></el-option>
            <el-option label="1年以内" value="1年以内"></el-option>
            <el-option label="1到3年" value="1到3年"></el-option>
            <el-option label="3到5年" value="3到5年"></el-option>
            <el-option label="5到10年" value="5到10年"></el-option>
            <el-option label="10年以上" value="10年以上"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="薪资待遇" prop="salary">
          <el-select v-model="formData.salary" placeholder="请输入薪资待遇">
            <el-option label="3k以下" value="3k以下"></el-option>
            <el-option label="3-5k" value="3-5k"></el-option>
            <el-option label="5-10k" value="5-10k"></el-option>
            <el-option label="10-20k" value="10-20k"></el-option>
            <el-option label="20-50k" value="20-50k"></el-option>
            <el-option label="50k以上" value="50k以上"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历要求" prop="education">
          <el-select v-model="formData.education" placeholder="请输入学历要求">
            <el-option label="初中及以下" value="初中及以下"></el-option>
            <el-option label="中专/中技" value="中专/中技"></el-option>
            <el-option label="高中" value="高中"></el-option>
            <el-option label="大专" value="大专"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="硕士" value="硕士"></el-option>
            <el-option label="博士" value="博士"></el-option>
            <el-option label="博士后" value="硕士"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位标签" prop="tag">
          <el-input v-model="formData.tag" placeholder="请输入职位标签,标签间请用英文逗号分开" />
        </el-form-item>
        <el-form-item label="职位描述" prop="content">
          <div style="border: 1px solid #ccc">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              :mode="mode"
            />
            <Editor
              style="height: 500px; overflow-y: hidden"
              v-model="formData.content"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="toggleFormVisiable(false)">取消</el-button>
          <el-button type="primary" @click="submit"> 确定 </el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog v-model="contentVisiable" title="职位描述" width="50%">
      <div style="padding: 0px 20px" v-html="viewContent"></div>
    </el-dialog>
  </div>
</template>

<style scoped></style>
