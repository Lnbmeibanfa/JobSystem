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
    <header
      class="position-header"
      style="
        background-color: #79bbff;
        display: flex;
        justify-content: center;
        padding: 30px 20px;
        margin-bottom: 20px;
      "
    >
      <div class="header-box" style="width: 80%">
        <div style="display: flex; align-items: center">
          <div class="name" style="font-size: 28px; color: #fff; font-weight: bold">
            {{ data.jobInfo.name }}
          </div>
          <div
            class="salary"
            style="font-weight: bold; font-size: 24px; color: #f26d49; margin-left: 30px"
          >
            {{ data.jobInfo.salary }}
          </div>
        </div>
        <div style="display: flex; color: #fff; padding: 20px 0px">
          <div style="display: flex; align-items: center">
            <el-icon><Location /></el-icon>{{ data.jobInfo.employCity }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon><Calendar /></el-icon>{{ data.jobInfo.experience }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon><School /></el-icon>{{ data.jobInfo.education }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon><Monitor /></el-icon>{{ data.jobInfo.type }}
          </div>
        </div>
        <div class="operation">
          <el-button type="success" size="default" @click="starPosition">收藏岗位</el-button>
          <el-button type="primary" size="default" @click="sendResume">投递简历</el-button>
        </div>
      </div>
    </header>
    <main class="position-detail" style="display: flex; margin: 0px auto; width: 80%">
      <div
        class="description"
        style="padding: 20px; background-color: #fff; border-radius: 10px; flex: 1"
      >
        <div class="title" style="font-weight: bold; font-size: 24px; margin-bottom: 10px">
          职位描述
        </div>
        <div class="tags">
          <el-tag
            effect="plain"
            style="margin-right: 5px"
            type="info"
            v-for="tag in data.jobInfo.tagList"
            :key="tag"
            >{{ tag }}</el-tag
          >
        </div>
        <div v-html="data.jobInfo.content"></div>
      </div>
      <aside class="aside" style="width: 300px"></aside>
    </main>
  </div>
</template>

<style scoped></style>
