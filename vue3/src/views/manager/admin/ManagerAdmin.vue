<script setup>
import { ref, reactive } from 'vue'
import { ElButton, ElDialog } from 'element-plus'
import { validateUsername, validatePassword, validateEmail, validatePhone } from './validateUtil'
import { addAdminAPI } from '@/api/admin'
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
  password: [{ validator: validatePassword, trigger: 'blur' }],
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
      await addAdminAPI(formData)
    }
  })
}
</script>

<template>
  <div class="manager-admin">
    <div class="card">
      管理员信息
      <el-button @click="toggleFormVisiable(true)">添加</el-button>
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
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" placeholder="请输入密码" />
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
