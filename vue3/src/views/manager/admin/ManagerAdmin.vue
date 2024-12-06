<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElButton, ElDialog, ElMessage } from 'element-plus'
import { validateUsername, validateEmail, validatePhone } from './validateUtil'
import { addAdminAPI, selectAllAPI } from '@/api/admin'

onMounted(() => {
  load()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(1)
const total = ref(0)
// const name = ref('')

/**dialog数据和方法*/
// 表单数据和rules
const adminForm = ref(null)
const formData = ref({
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
// 处理表单是否可见
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}
const formVisiable = ref(false)
// 提交表单
const submit = () => {
  adminForm.value.validate(async (valid) => {
    if (valid) {
      addAdminAPI(formData.value).then((res) => {
        if (res.code === '200') {
          ElMessage.success('添加成功')
          toggleFormVisiable(false)
        } else {
          ElMessage.warning(res.msg)
        }
      })
    }
  })
}
const load = () => {
  selectAllAPI(pageNum.value, pageSize.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
    console.log(tableData.value)
  })
}
</script>

<template>
  <div class="manager-admin">
    <div class="card">
      <el-button @click="toggleFormVisiable(true)">添加</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData">
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="name" label="名字" />
        <el-table-column prop="avatar" label="头像" />
        <el-table-column prop="role" label="角色" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作">
          <template #default>
            <el-button type="primary" size="default"></el-button>
            <el-button type="danger" size="default"></el-button>
          </template>
        </el-table-column>
      </el-table>
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
          <el-input v-model="formData.name" placeholder="请输入名字" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="formData.avatar" placeholder="请输入头像" />
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
