<script setup>
import { reactive } from 'vue'
import { selectAllNoticeAPI } from '@/api/notice'
import { ElMessage } from 'element-plus'

const data = reactive({
  notices: {}
})
const load = () => {
  selectAllNoticeAPI().then((res) => {
    if (res.code === '200') {
      data.notices = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
load()
</script>
<template>
  <div class="card" style="font-size: 16px; color: #666; margin-bottom: 5px">
    你好管理员，欢迎使用本系统！
  </div>
  <div style="display: flex">
    <div class="card" style="flex: 50%">
      <div style="font-size: 19px; font-weight: bold; padding: 10px 0 30px 10px">系统公告</div>
      <el-timeline style="max-width: 600px">
        <el-timeline-item v-for="notice in data.notices" :key="notice.id" :timestamp="notice.time">
          {{ notice.content }}
        </el-timeline-item>
      </el-timeline>
    </div>
    <div style="flex: 50%"></div>
  </div>
</template>
<style scope></style>
