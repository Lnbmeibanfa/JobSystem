<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElButton, ElDialog, ElMessage, ElMessageBox } from 'element-plus'
import { validateUsername, validateEmail, validatePhone } from '@/utils/validateUtil'
import { addAdminAPI, selectByPageAPI, updateAdminAPI, deleteBatch, deleteById } from '@/api/admin'

onMounted(() => {
  // 加载表格数据
  load()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref('')
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, name.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  name.value = ''
  load()
}
/**dialog数据和方法*/
// 表单数据和rules
const adminForm = ref(null)
// 图片访问地址
const baseUrl = import.meta.env.VITE_BASE_URL
// 图片上传列表
const fileList = ref([])
const formData = ref({})
const rules = reactive({
  username: [
    { required: true, message: '用户名不得为空', trigger: 'blur' },
    { validator: validateUsername, trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '电话号码不得为空', trigger: 'blur' },
    { validator: validatePhone, trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱不得为空', trigger: 'blur' },
    { validator: validateEmail, trigger: 'blur' }
  ]
})
// 打开dialog
const handeleAdd = () => {
  formData.value = {}
  fileList.value = []
  toggleFormVisiable(true)
}
const handleEdit = (row) => {
  formData.value = {}
  fileList.value = []
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
const handleUploadSuccess = (res) => {
  formData.value.avatar = res.data
}
const beforeUpload = (file) => {
  const maxSize = 1024 * 1024
  if (file.size > maxSize) {
    ElMessage.error('文件过大，请上传小于1MB的文件')
    return false
  }
  return true
}
// 处理表单是否可见
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}
const formVisiable = ref(false)
// 提交表单
const submit = () => {
  adminForm.value.validate(async (valid) => {
    if (valid) {
      formData.value.id ? update() : add()
    }
  })
}
const add = () => {
  addAdminAPI(formData.value).then((res) => {
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
  updateAdminAPI(formData.value).then((res) => {
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
  <div class="manager-admin">
    <div class="card">
      <el-input
        v-model="name"
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
      <el-table :data="tableData" @selection-change="handleSelectionChange" show-overflow-tooltip>
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="name" label="名字" />
        <el-table-column prop="avatar" label="头像">
          <template #default="scope">
            <el-image
              style="width: 40px; height: 40px; border-radius: 50%"
              :preview-src-list="[scope.row.avatar]"
              :preview-teleported="true"
              :src="scope.row.avatar"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="角色" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
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
        ref="adminForm"
        :model="formData"
        label-width="70px"
        style="padding: 20px"
        :rules="rules"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-input v-model="formData.name" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
            :before-upload="beforeUpload"
            :action="baseUrl + '/files/upload'"
            :on-success="handleUploadSuccess"
            list-type="picture"
            :file-list="fileList"
          >
            <template #trigger><el-button type="primary">上传文件</el-button></template>
          </el-upload>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
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
