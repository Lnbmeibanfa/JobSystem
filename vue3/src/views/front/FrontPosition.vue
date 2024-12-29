<script setup>
import { reactive, onMounted } from 'vue'
import { selectById } from '@/api/position'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
const route = useRoute()
const data = reactive({
  jobInfo: {}
})

const loadJobInfo = () => {
  selectById(route.query.id).then((res) => {
    if (res.code === '200') {
      data.jobInfo = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadJobInfo()
})
</script>

<template>
  <div style="background-color: #f6f6f8">
    <header class="position-header" style="background-color: #3b526a">
      <div class="header-box" style="width: 80%">
        <div>
          <div class="name">{{ data.jobInfo.name }}</div>
          <div class="salary">{{ data.jobInfo.salary }}</div>
        </div>
        <div>
          <div>{{ data.jobInfo.employAddress }}</div>
          <div>{{ data.jobInfo.experience }}</div>
          <div>{{ data.jobInfo.education }}</div>
          <div>{{ data.jobInfo.type }}</div>
        </div>
        <div class="operation">
          <el-button type="success" size="default" @click="starPosition">收藏岗位</el-button>
          <el-button type="primary" size="default" @click="sendResume">投递简历</el-button>
        </div>
      </div>
    </header>
    <main class="position-detail">
      <div class="description"></div>
      <aside class="aside"></aside>
    </main>
  </div>
</template>

<style scoped></style>
