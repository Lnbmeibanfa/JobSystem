<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ROUTE_PATH } from '@/utils/Contants'
const router = useRouter()
const data = reactive({
  resumeList: []
})
const createResume = () => {
  router.push(ROUTE_PATH.FRONT.RESUME_EDIT)
}
</script>

<template>
  <div class="background" style="background-color: #f6f6f8; height: 100%">
    <div class="resume-box" style="width: 70%; background-color: white; margin: 0px auto">
      <div
        class="title-box"
        style="height: 80px; display: flex; align-items: center; justify-content: center"
      >
        <div style="font-weight: bold; font-size: 20px; margin-right: 30px">
          我的简历({{ data.resumeList.length }})
        </div>
        <el-button type="primary" size="default" @click="createResume">创建新的简历</el-button>
      </div>
      <div></div>
    </div>
  </div>
</template>

<style scoped></style>
