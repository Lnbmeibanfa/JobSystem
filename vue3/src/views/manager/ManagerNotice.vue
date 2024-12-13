<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElButton, ElDialog, ElMessage, ElMessageBox } from 'element-plus'
import { validateUsername, validateEmail, validatePhone } from '@/utils/validateUtil'
import {
  addNoticeAPI,
  selectByPageAPI,
  updateNoticeAPI,
  deleteBatch,
  deleteById
} from '@/api/notice'

onMounted(() => {
  // 加载表格数据
  load()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const title = ref('')
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, title.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  title.value = ''
  load()
}
/**dialog数据和方法*/
// 表单数据和rules
const noticeForm = ref(null)
const formData = ref({
  id: '',
  username: '',
  password: '',
  name: '',
  avatar: '',
  phone: '',
  email: ''
})
const rules = reactive({
  username: [{ validator: validateUsername, trigger: 'blur' }],
  phone: [{ validator: validatePhone, trigger: 'blur' }],
  email: [{ validator: validateEmail, trigger: 'blur' }]
})
// 打开dialog
const handeleAdd = () => {
  formData.value = {}
  toggleFormVisiable(true)
}
const handleEdit = (row) => {
  formData.value = {}
  formData.value = JSON.parse(JSON.stringify(row))
  toggleFormVisiable(true)
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
  noticeForm.value.validate(async (valid) => {
    if (valid) {
      formData.value.id ? update() : add()
    }
  })
}
const add = () => {
  addNoticeAPI(formData.value).then((res) => {
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
  updateNoticeAPI(formData.value).then((res) => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('修改成功')
      toggleFormVisiable(false)
      load()
    } else {
      ElMessage.warning(res.msg)
    }
  })
}
</script>

<template>
  <div class="manager-notice">
    <div class="card">
      <el-input
        v-model="title"
        prefix-icon="Search"
        placeholder="请输入名称查询"
        style="width: 400px; margin-right: 15px"
        @keyup.enter="load"
      ></el-input>
      <el-button type="primary" size="default" @click="load" plain>查询</el-button>
      <el-button type="warning" size="default" @click="replace" plain>重置</el-button>
    </div>
    <div class="card">
      <el-button @click="handeleAdd" plain type="primary">添加</el-button>
      <el-button @click="handleDelBatch" plain type="danger">批量删除</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="通知内容" />
        <el-table-column prop="time" label="通知时间" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              circle
              icon="Edit"
              size="default"
              @click="handleEdit(scope.row)"
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
    <el-dialog v-model="formVisiable" title="新增管理员" width="40%">
      <el-form
        ref="noticeForm"
        :model="formData"
        label-width="70px"
        style="padding: 20px"
        :rules="rules"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 8 }"
            v-model="formData.content"
            placeholder="请输入通知内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="toggleFormVisiable(false)">取消</el-button>
          <el-button type="primary" @click="submit"> 确定 </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped></style>
