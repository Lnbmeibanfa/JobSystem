<script setup>
import { reactive } from 'vue'
import { ref } from 'vue'
import { registerAPI } from '@/api/login'
import { ElMessage } from 'element-plus'
import { ROUTE_PATH } from '@/utils/Contants'
import router from '@/router'

// 校验确认密码
const validatorPassword = (rules, value, callback) => {
  if (value === '') {
    callback(new Error('确认密码不得为空'))
  } else if (value !== data.form.password) {
    callback(new Error('两次密码输入不一致'))
  } else {
    callback()
  }
}
const data = reactive({
  form: {
    username: '',
    password: '',
    role: ''
  },
  rules: {
    username: [{ required: true, message: '用户名不得为空', trigger: 'blur' }],
    password: [{ required: true, message: '密码不得为空', trigger: 'blur' }],
    confirmPassword: [{ validator: validatorPassword }]
  }
})

const options = ref([
  { value: 'USER', label: '用户' },
  { value: 'EMPLOY', label: '企业' }
])
const form = ref(null)
const register = () => {
  form.value.validate((valid) => {
    if (valid) {
      registerAPI(data.form).then((res) => {
        if (res.code === '200') {
          ElMessage.success('注册成功')
          router.push(ROUTE_PATH.LOGIN)
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>
<template>
  <div class="register-container">
    <div class="register-box">
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
        欢迎注册
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
        <el-form-item prop="name">
          <el-input
            size="large"
            prefix-icon="User"
            v-model="data.form.name"
            placeholder="请输入昵称"
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
        <el-form-item prop="confirmPassword">
          <el-input
            size="large"
            prefix-icon="Lock"
            v-model="data.form.confirmPassword"
            placeholder="请再次输入密码"
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
            @click="register"
            >注 册</el-button
          >
        </el-form-item>
      </el-form>
      <div style="text-align: right">已有账号？去 <a href="/login">登 录</a></div>
    </div>
  </div>
</template>

<style scoped>
.register-container {
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(to top, #01477f, #4c8481, #a3ca82);
}
.register-box {
  width: 350px;
  padding: 30px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.5);
}
</style>
