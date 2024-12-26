<script setup>
import router from '@/router'
import { useRoute } from 'vue-router'
import { useAccountStore } from '@/stores/login'
import { ElMessageBox } from 'element-plus'
import { ROUTE_PATH } from '@/utils/Contants'
const accountStore = useAccountStore()
const route = useRoute()
const handleQuit = () => {
  ElMessageBox.confirm('确认退出登录？', '退出登录？', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    accountStore.setAccountInfo({})
    router.push(ROUTE_PATH.LOGIN)
  })
}
const toPassword = () => {
  router.push(ROUTE_PATH.ACCOUNT.PASSWORD)
}
</script>

<template>
  <div class="back-manager">
    <div class="back-manager-header">
      <div class="back-manager-header-left">
        <div class="content">
          <img src="../assets/imgs/logo.png" alt="" />
          <div class="title">后台管理系统</div>
        </div>
      </div>
      <div class="back-manager-header-center">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: ROUTE_PATH.HOME }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>
            {{ route.meta.name }}
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="back-manager-header-right">
        <el-dropdown>
          <div class="content">
            <img style="border-radius: 50%" :src="accountStore.AccountInfo.avatar" />
            <span>{{ accountStore.AccountInfo.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                v-if="accountStore.AccountInfo.role === 'ADMIN'"
                @click="router.push(ROUTE_PATH.ACCOUNT.SELF)"
                >个人资料</el-dropdown-item
              >
              <el-dropdown-item
                v-if="accountStore.AccountInfo.role === 'EMPLOY'"
                @click="router.push(ROUTE_PATH.ACCOUNT.EMPLOYSELF)"
                >企业资料</el-dropdown-item
              >
              <el-dropdown-item @click="toPassword">修改密码</el-dropdown-item>
              <el-dropdown-item @click="handleQuit">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!-- 页面主体部分开始 -->
    <div class="back-manager-main">
      <div class="back-manager-main-left">
        <el-menu :default-active="ROUTE_PATH.HOME" class="el-menu-vertical-demo" router>
          <el-menu-item :index="ROUTE_PATH.HOME" :class="{ tag: route.path === ROUTE_PATH.HOME }">
            <el-icon><House /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu
            index="/manager/admin"
            :class="{ tag: Object.values(ROUTE_PATH.MESSAGE).includes(route.path) }"
          >
            <template #title>
              <el-icon><User /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item
              :index="ROUTE_PATH.MESSAGE.NOTICE"
              :class="{ tag: route.path === ROUTE_PATH.MESSAGE.NOTICE }"
              >系统公告</el-menu-item
            >
            <el-menu-item
              :index="ROUTE_PATH.ACCOUNT.ADVERTISE"
              :class="{ tag: route.path === ROUTE_PATH.ACCOUNT.ADVERTISE }"
              >广告信息</el-menu-item
            >
            <el-menu-item
              :index="ROUTE_PATH.MESSAGE.INDUSTRY"
              :class="{ tag: route.path === ROUTE_PATH.MESSAGE.INDUSTRY }"
              >行业信息</el-menu-item
            >
            <el-menu-item :index="ROUTE_PATH.ACCOUNT.POSITION">职位信息</el-menu-item>
            <el-menu-item index="2-5">岗位投递</el-menu-item>
          </el-sub-menu>
          <el-sub-menu
            index="3"
            :class="{ tag: Object.values(ROUTE_PATH.ACCOUNT).includes(route.path) }"
            v-if="accountStore.AccountInfo.role === 'ADMIN'"
          >
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户信息</span>
            </template>
            <el-menu-item
              :index="ROUTE_PATH.ACCOUNT.ADMIN"
              :class="{ tag: route.path === ROUTE_PATH.ACCOUNT.ADMIN }"
              >管理员信息</el-menu-item
            >
            <el-menu-item
              :index="ROUTE_PATH.ACCOUNT.EMPLOY"
              :class="{ tag: route.path === ROUTE_PATH.ACCOUNT.EMPLOY }"
              >企业信息</el-menu-item
            >
            <el-menu-item
              :index="ROUTE_PATH.ACCOUNT.USER"
              :class="{ tag: route.path === ROUTE_PATH.ACCOUNT.USER }"
              >用户信息</el-menu-item
            >
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
