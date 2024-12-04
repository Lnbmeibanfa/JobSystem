<script setup>
import { ref } from 'vue'
import { ElButton, ElDialog } from 'element-plus';
import {addAdminAPI} from '@/api/admin'
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}
const form = ref({
  username: '',
  password: '',
  name: '',
  avatar: '',
  phone: '',
  email: '',
})
const formVisiable = ref(false)
const add = async () => {
  const res = await addAdminAPI(form.value)
  console.log(res);
}
const submit = () => {
  add()
}
</script>

<template>
  <div class="manager-admin">
    <div class="card">管理员信息
      <el-button @click="toggleFormVisiable(true)">添加</el-button>
    </div>
    <el-dialog v-model="formVisiable" title="新增管理员" width="40%">
      <el-form :model="form"  label-width="60px" style="padding: 20px;">
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