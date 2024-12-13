<script setup>
import { selectAllAPI } from '@/api/notice'
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'

const data = reactive({
  notices: {},
  top: ''
})
const loadNotice = () => {
  selectAllAPI().then((res) => {
    if (res.code === '200') {
      console.log(111)
      data.notices = res.data
      let i = 0
      if (data.notices && data.notices.length) {
        data.top = data.notices[0].content
        setInterval(() => {
          data.top = data.notices[i].content
          i++
          if (i === data.notices.length) {
            i = 0
          }
        }, 2500)
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadNotice()
const isLogin = ref(false)
</script>

<template>
  <div
    class="front-notice"
    style="
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      padding: 5px 10px;
      color: #666;
    "
  >
    <el-icon><Bell /></el-icon> 公告:{{ data.top }}
  </div>
  <div class="front-shower">
    <header class="front-shower-header">
      <div class="left">
        <img src="../assets/imgs/logo.svg" />
        <span class="title">项目前台</span>
      </div>
      <div class="center">
        <el-menu class="el-menu-demo" mode="horizontal" router>
          <el-menu-item index="/front">首页</el-menu-item>
          <el-menu-item index="/front/user">个人中心</el-menu-item>
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
              <img src="../assets/imgs/user.svg" />
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
    <div class="front-shower-content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
@import url('@/assets/css/shower.css');
</style>
