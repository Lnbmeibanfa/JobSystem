<script setup>
import { ref, reactive } from 'vue'
import { ElButton, ElDialog } from 'element-plus';
import isLegal from '@/utils/ManagerUtil'
import {addAdminAPI} from '@/api/admin'
const form = ref({
  username: '',
  password: '',
  name: '',
  avatar: '',
  phone: '',
  email: '',
})
const rules = reactive({
  username: [
  { validator: validateUsername, trigger: 'blur' },
  ]
})
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('用户名不得为空'))
  } else if (value.length < 5 || value.length > 50) {
    callback(new Error('用户名长度应为5-50'))
  } else if(!isLegal(value)) {
    callback(new Error('用户名中不得含有特殊字符'))
  } else {
    callback()
  }
}


const formVisiable = ref(false)
const addAdmin = async () => {
  const res = await addAdminAPI(form.value)
  console.log(res);
}
const submit = () => {
  addAdmin()
}
</script>

<template>
  <div class="manager-admin">
    <div class="card">管理员信息
      <el-button @click="toggleFormVisiable(true)">添加</el-button>
    </div>
    <el-dialog v-model="formVisiable" title="新增管理员" width="40%">
      <el-form :model="form"  label-width="70px" style="padding: 20px;" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入名字" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入头像" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="toggleFormVisiable(false)">取消</el-button>
          <el-button type="primary" @click="submit">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
</style>