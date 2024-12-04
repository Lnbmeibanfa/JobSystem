<script setup>
import { ref } from 'vue';
const isLogin = ref(false)
</script>

<template>
  <div class="front-shower">
    <header class="front-shower-header">
      <div class="left">
        <img src="../assets/imgs/logo.svg">
        <span class="title">项目前台</span>
      </div>
      <div class="center">
        <el-menu
          class="el-menu-demo"
          mode="horizontal"
        >
          <el-menu-item index="1">首页</el-menu-item>
          <el-menu-item index="2">个人中心</el-menu-item>
        </el-menu>
      </div>
      <div class="right">
        <div v-if="isLogin">
          <el-button>登录</el-button>
          <el-button>注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
          <div class="content">
            <img src="../assets/imgs/user.svg">
            <span class="info">管理员</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        </div>
      </div>
    </header>
    <div class="front-shower-content"></div>
  </div>
</template>

<style scoped>
@import url('@/assets/css/shower.css');
</style>