<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import ResumeShower from '@/views/components/ResumeShower.vue'
import { ROUTE_PATH } from '@/utils/Contants'
import { deleteById, selectAllResumeAPI } from '@/api/resume'
import { ElMessage, ElMessageBox } from 'element-plus'
const router = useRouter()
const data = reactive({
  resumeList: []
})
const createResume = () => {
  router.push(ROUTE_PATH.FRONT.RESUME_EDIT)
}
const handleDel = (id) => {
  ElMessageBox.confirm('删除后无法恢复？', '确认删除？', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteById(id).then((res) => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        loadResumeList()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}
const loadResumeList = () => {
  selectAllResumeAPI().then((res) => {
    if (res.code === '200') {
      data.resumeList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadResumeList()
</script>

<template>
  <div class="background" style="background-color: #f6f6f8; height: 100%">
    <div class="resume-box" style="width: 70%; margin: 0px auto">
      <div
        class="title-box"
        style="height: 80px; display: flex; align-items: center; justify-content: center"
      >
        <div style="font-weight: bold; font-size: 20px; margin-right: 30px">
          我的简历({{ data.resumeList.length }})
        </div>
        <el-button type="primary" size="default" @click="createResume">创建新的简历</el-button>
      </div>
      <div>
        <el-row :gutter="10">
          <el-col v-for="resume in data.resumeList" :key="resume.id" :span="6" :offset="0">
            <resume-shower :resumeInfo="resume" @onDelete="handleDel" />
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
