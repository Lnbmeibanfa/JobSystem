<script setup>
import router from '@/router'
import { useRoute } from 'vue-router'
import { useAccountStore } from '@/stores/login'
import { ElMessageBox } from 'element-plus'
const accountStore = useAccountStore()
const route = useRoute()
const handleQuit = () => {
  ElMessageBox.confirm('确认退出登录？', '退出登录？', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    accountStore.setAccountInfo({})
    router.push('/login')
  })
}
</script>

<template>
  <div class="back-manager">
    <div class="back-manager-header">
      <div class="back-manager-header-left">
        <div class="content">
          <img src="../assets/imgs/logo.svg" alt="" />
          <div class="title">后台管理系统</div>
        </div>
      </div>
      <div class="back-manager-header-center">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/manager/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>
            {{ route.meta.name }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="back-manager-header-right">
        <el-dropdown>
          <div class="content">
            <img src="../assets/imgs/user(white).svg" />
            <span>管理员</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item @click="handleQuit">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!-- 页面主体部分开始 -->
    <div class="back-manager-main">
      <div class="back-manager-main-left">
        <el-menu default-active="2" class="el-menu-vertical-demo" router>
          <el-menu-item index="/manager/home" class="tag">
            <el-icon><House /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu index="/manager/admin">
            <template #title>
              <el-icon><User /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="2-1">系统公告</el-menu-item>
            <el-menu-item index="2-2">广告信息</el-menu-item>
            <el-menu-item index="2-3">行业信息</el-menu-item>
            <el-menu-item index="2-4">职位信息</el-menu-item>
            <el-menu-item index="2-5">岗位投递</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户信息</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="3-2">企业信息</el-menu-item>
            <el-menu-item index="3-3">用户信息</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="back-manager-main-right">
        <RouterView />
      </div>
    </div>
    <!-- 页面主体部分结束 -->
  </div>
</template>

<style scoped>
@import url('../assets/css/manager.css');
</style>

<script></script>
