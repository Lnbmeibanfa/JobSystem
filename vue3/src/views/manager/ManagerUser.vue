<script setup>
import { reactive, ref } from 'vue'
import { useAccountStore } from '@/stores/login'
import { validateUsername, validateEmail, validatePhone } from './admin/validateUtil'
const accountStore = new useAccountStore()
const baseUrl = import.meta.env.VITE_BASE_URL
const data = reactive({
  formData: accountStore.AccountInfo
})
const rules = reactive({
  username: [{ validator: validateUsername, trigger: 'blur' }],
  phone: [{ validator: validatePhone, trigger: 'blur' }],
  email: [{ validator: validateEmail, trigger: 'blur' }]
})
const fileList = ref([])
const handleUploadSuccess = (res) => {
  data.formData.avatar = res.data
}
const save = () => []
</script>
<template>
  <div class="card" style="width: 500px; height: 600px">
    <el-form
      ref="adminForm"
      :model="data.formData"
      label-width="70px"
      style="padding: 20px"
      :rules="rules"
    >
      <el-form-item label="头像" prop="avatar">
        <el-upload
          :action="baseUrl + '/files/upload'"
          :on-success="handleUploadSuccess"
          list-type="picture"
          :file-list="fileList"
        >
          <img
            style="width: 120px; height: 120px; border-radius: 5px"
            v-if="data.formData.avatar"
            :src="data.formData.avatar"
            class="avatar"
          />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="data.formData.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="名字" prop="name">
        <el-input v-model="data.formData.name" placeholder="请输入名字" />
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input v-model="data.formData.phone" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="data.formData.email" placeholder="请输入邮箱" />
      </el-form-item>
    </el-form>
    <div style="text-align: center"><el-button type="primary" @click="save">保 存</el-button></div>
  </div>
</template>

<style scoped></style>
