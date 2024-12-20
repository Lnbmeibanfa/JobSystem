<script setup>
import { reactive, ref } from 'vue'
import { LOCALSTORAGE_KEY } from '@/utils/Contants'
import { updatePasswordAPI } from '@/api/login'
import { useAccountStore } from '@/stores/login'
import { ElMessage } from 'element-plus'
import router from '@/router'
const accountStore = new useAccountStore()
const confirmPassword = (rules, value, callback) => {
  if (value !== data.form.newPassword) {
    callback(new Error('两次密码输入不一致'))
  }
  callback()
}
const form = ref(null)
const data = reactive({
  form: JSON.parse(localStorage.getItem(LOCALSTORAGE_KEY.USER)).AccountInfo,
  rules: {
    password: [{ required: true, message: '原密码不得为空', trigger: 'blur' }],
    newPassword: [{ required: true, message: '密码不得为空', trigger: 'blur' }],
    confirmPassword: [
      { required: true, message: '确认密码不得为空', trigger: 'blur' },
      { validator: confirmPassword, trigger: 'blur' }
    ]
  }
})
const save = () => {
  form.value.validate((valid) => {
    if (valid) {
      updatePasswordAPI(data.form).then((res) => {
        if (res.code === '200') {
          accountStore.AccountInfo.password = data.form.newPassword
          ElMessage.success('修改密码成功')
          router.push('/login')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>
<template>
  <div class="card" style="display: flex; justify-content: center; padding: 50px">
    <div class="password-box" style="width: 800px">
      <el-form :model="data.form" ref="form" :rules="data.rules" label-width="80px">
        <el-form-item label="原密码" prop="password">
          <el-input show-password v-model="data.form.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="data.form.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="data.form.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item>
          <div><el-button type="primary" @click="save">确认修改</el-button></div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped></style>
