<script setup>
import { reactive, ref } from 'vue'
import { useAccountStore } from '@/stores/login'
import { validateUsername, validateEmail, validatePhone } from '@/utils/validateUtil'
import { LOCALSTORAGE_KEY } from '@/utils/Contants'
import { updateAdminAPI } from '@/api/admin'
import { ROLE } from '@/utils/Contants'
import { ElMessage } from 'element-plus'
const accountStore = new useAccountStore()
const baseUrl = import.meta.env.VITE_BASE_URL
const data = reactive({
  formData: JSON.parse(localStorage.getItem(LOCALSTORAGE_KEY.USER)).AccountInfo
})
const adminForm = ref(null)
const rules = reactive({
  username: [
    { required: true, message: '用户名不得为空' },
    { validator: validateUsername, trigger: 'blur' }
  ],
  name: [
    { required: true, message: '呢称不得为空', trigger: 'blur' },
    { min: 5, max: 50, message: '呢称为5-50个字符' }
  ],
  phone: [
    { required: true, message: '手机号码不得为空' },
    { validator: validatePhone, trigger: 'blur' }
  ],
  email: [
    { required: true, message: '邮箱不得为空' },
    { validator: validateEmail, trigger: 'blur' }
  ]
})
const fileList = ref([])
const handleUploadSuccess = (res) => {
  data.formData.avatar = res.data
}
const save = () => {
  adminForm.value.validate((valid) => {
    if (valid) {
      updateAdminAPI(data.formData).then((res) => {
        if (res.code === '200') {
          accountStore.setAccountInfo(data.formData)
          ElMessage.success('修改成功')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>
<template>
  <div class="manager-user card" style="height: 100%">
    <div class="card avatar-box" style="display: flex; justify-content: center">
      <el-upload
        :action="baseUrl + '/files/upload'"
        :on-success="handleUploadSuccess"
        list-type="picture-card"
        :file-list="fileList"
        :limit="1"
        :show-file-list="false"
      >
        <img
          style="width: 100px; height: 100px; border-radius: 5px"
          v-if="data.formData.avatar"
          :src="data.formData.avatar"
          class="avatar"
        />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
      <div class="user-name" style="padding: 20px">
        <div class="name" style="font-size: 20px; font-weight: bold; height: 60px; padding: 10px">
          {{ accountStore.AccountInfo.name }}
        </div>
        <div
          class="role"
          style="
            font-size: 20px;
            font-weight: bold;
            color: aqua;
            height: 60px;
            padding: 10px;
            text-align: center;
          "
        >
          <el-icon style="vertical-align: middle">
            <User />
          </el-icon>
          {{ ROLE[data.formData.role] }}
        </div>
      </div>
    </div>
    <div class="card user-info" style="display: flex; justify-content: center">
      <div class="manager-form" style="width: 700px">
        <el-form
          ref="adminForm"
          :model="data.formData"
          label-width="70px"
          style="padding: 20px"
          :rules="rules"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="data.formData.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="昵称" prop="name">
            <el-input v-model="data.formData.name" placeholder="请输入名字" />
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="data.formData.phone" placeholder="请输入手机号" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="data.formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-form>
        <div style="text-align: center">
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
