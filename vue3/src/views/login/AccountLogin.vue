<script setup>
import { reactive } from 'vue'
import { ref } from 'vue'
import { loginAPI } from '@/api/login'
import { ElMessage } from 'element-plus'
import { useAccountStore } from '@/stores/login'
import router from '@/router'

const accountStore = useAccountStore()
const data = reactive({
  form: {
    role: 'ADMIN'
  },
  rules: {
    username: [{ required: true, message: '用户名不得为空', trigger: 'blur' }],
    password: [{ required: true, message: '密码不得为空', trigger: 'blur' }]
  }
})
const options = ref([
  { value: 'ADMIN', label: '管理员' },
  { value: 'USER', label: '用户' }
])
const form = ref(null)
const login = () => {
  form.value.validate((valid) => {
    if (valid) {
      loginAPI(data.form).then((res) => {
        console.log(res)
        if (res.code === '200') {
          accountStore.setAccountInfo(res.data)
          ElMessage.success('登录成功')
          router.push('/manager/home')
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>
<template>
  <div class="login-container">
    <div class="login-box">
      <div
        style="
          text-align: center;
          font-weight: bold;
          font-size: 24px;
          margin-bottom: 30px;
          color: #1450aa;
        "
        class="title"
      >
        欢迎登录
      </div>
      <el-form :model="data.form" ref="form" :rules="data.rules">
        <el-form-item prop="username">
          <el-input
            size="large"
            prefix-icon="User"
            v-model="data.form.username"
            placeholder="请输入账号"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            size="large"
            prefix-icon="Lock"
            v-model="data.form.password"
            placeholder="请输入密码"
            show-password
          ></el-input>
        </el-form-item>
        <el-select size="large" v-model="data.form.role" placeholder="请选择角色">
          <el-option
            v-for="item in options"
            :key="item.value"
            :value="item.value"
            :label="item.label"
          ></el-option>
        </el-select>
        <el-form-item>
          <el-button
            style="width: 100%; margin-top: 20px"
            type="primary"
            size="large"
            @click="login"
            >登 录</el-button
          >
        </el-form-item>
      </el-form>
      <div style="text-align: right">没有账号？去 <a href="/register">注 册</a></div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to top, #7f7fd5, #86a8e7, #91eae4);
}
.login-box {
  width: 350px;
  padding: 30px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.5);
}
</style>
