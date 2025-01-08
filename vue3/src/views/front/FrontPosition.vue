<script setup>
import { reactive, onMounted } from 'vue'
import { selectById, selectRecommend } from '@/api/position'
import PositionShower from '../components/PositionShower.vue'
import { addSubmitAPI } from '@/api/submit'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addCollectAPI } from '@/api/collect'
import { useAccountStore } from '@/stores/login'
import router from '@/router'
import { watch } from 'vue'
import { selectAllResumeAPI } from '@/api/resume'
import { ROUTE_PATH } from '@/utils/Contants'
const route = useRoute()
const accountStore = useAccountStore()
const data = reactive({
  jobInfo: {},
  recommendJobs: [],
  resumeList: [],
  submitVisiable: false,
  resumeId: null
})
const loadJobInfo = () => {
  selectById(route.params.id).then((res) => {
    if (res.code === '200') {
      data.jobInfo = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadResumeList = () => {
  selectAllResumeAPI(accountStore.AccountInfo.id).then((res) => {
    if (res.code === '200') {
      data.resumeList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadRecommendJobs = () => {
  selectRecommend().then((res) => {
    if (res.code === '200') {
      data.recommendJobs = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadJobInfo()
  loadRecommendJobs()
  loadResumeList()
})
const submitInit = () => {
  data.submitVisiable = true
}
const submitResume = () => {
  const submitData = {
    userId: accountStore.AccountInfo.id,
    positionId: data.jobInfo.id,
    resumeId: data.resumeId,
    employId: data.jobInfo.employId
  }
  addSubmitAPI(submitData).then((res) => {
    if (res.code === '200') {
      ElMessage.success('投递成功')
      data.submitVisiable = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const showAllPosition = () => {
  const baseUrl = ROUTE_PATH.FRONT.EMPLOY + '?id='
  router.push(baseUrl + data.jobInfo.employId)
}
const starPosition = () => {
  addCollectAPI({
    positionId: data.jobInfo.id,
    studentId: accountStore.AccountInfo.id
  }).then((res) => {
    if (res.code === '200') {
      ElMessage.success('收藏成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
watch(
  () => route.params.id,
  (newId, oldId) => {
    if (newId !== oldId) {
      loadJobInfo()
    }
  },
  { immediate: true }
)
const navTo = (id) => {
  router.push({ name: 'FrontPosition', params: { id }, replace: true })
}
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
        <div style="display: flex; color: #fff; padding: 20px 0px; font-size: 16px">
          <div style="display: flex; align-items: center">
            <el-icon style="margin-right: 3px"><Location /></el-icon>{{ data.jobInfo.employCity }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon style="margin-right: 3px"><Calendar /></el-icon>{{ data.jobInfo.experience }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon style="margin-right: 3px"><School /></el-icon>{{ data.jobInfo.education }}
          </div>
          <div style="display: flex; align-items: center; margin-left: 10px">
            <el-icon style="margin-right: 3px"><Monitor /></el-icon>{{ data.jobInfo.type }}
          </div>
        </div>
        <div class="operation">
          <el-button type="success" size="default" @click="starPosition">收藏岗位</el-button>
          <el-button type="primary" size="default" @click="submitInit">投递简历</el-button>
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
      <aside class="aside" style="width: 300px; margin-left: 10px">
        <div class="company-detail-info" style="margin-bottom: 10px">
          <div
            style="
              font-weight: bold;
              background-color: #d1d7d7;
              height: 40px;
              border-top-left-radius: 10px;
              border-top-right-radius: 10px;
              text-align: center;
              line-height: 40px;
              font-size: 16px;
            "
          >
            公司基本信息
          </div>
          <div
            style="
              padding: 20px;
              background-color: #fff;
              border-bottom-left-radius: 10px;
              border-bottom-right-radius: 10px;
            "
          >
            <div>
              <div style="display: flex; align-items: center">
                <img
                  style="width: 40px; height: 40px; margin-right: 20px"
                  :src="data.jobInfo.employAvatar"
                  alt=""
                />
                <div style="font-size: 18px">{{ data.jobInfo.employName }}</div>
              </div>
            </div>
            <div style="display: flex; flex-direction: column">
              <div
                style="
                  font-size: 15px;
                  color: rgb(51, 51, 51);
                  display: flex;
                  align-items: center;
                  margin-top: 10px;
                "
              >
                <el-icon><Coordinate /></el-icon>
                <div style="margin-left: 10px">{{ data.jobInfo.employStage }}</div>
              </div>
              <div
                style="
                  font-size: 15px;
                  color: rgb(51, 51, 51);
                  display: flex;
                  align-items: center;
                  margin-top: 10px;
                "
              >
                <el-icon><User /></el-icon>
                <div style="margin-left: 10px">{{ data.jobInfo.employScale }}</div>
              </div>
              <div
                style="
                  font-size: 15px;
                  color: rgb(51, 51, 51);
                  display: flex;
                  align-items: center;
                  margin-top: 10px;
                "
              >
                <el-icon><OfficeBuilding /></el-icon>
                <div style="margin-left: 10px">{{ data.jobInfo.employAddress }}</div>
              </div>
            </div>
            <div style="text-align: center">
              <el-button
                style="margin: 20px 0px; text-align: center"
                type="success"
                size="default"
                @click="showAllPosition"
                >查看全部职位</el-button
              >
            </div>
          </div>
        </div>
        <div class="recommend">
          <div
            style="
              font-weight: bold;
              background-color: #d1d7d7;
              height: 40px;
              border-top-left-radius: 10px;
              border-top-right-radius: 10px;
              text-align: center;
              line-height: 40px;
              font-size: 16px;
            "
          >
            你可能对这些职位感兴趣
          </div>
          <div style="background-color: #fff; border-radius: 10px">
            <div
              class="card"
              style="margin-bottom: 5px"
              v-for="position in data.recommendJobs"
              :key="position.id"
            >
              <position-shower :positionInfo="position" @navTo="navTo"></position-shower>
            </div>
          </div>
        </div>
      </aside>
    </main>
    <el-dialog v-model="data.submitVisiable" title="简历投递" width="40%">
      <el-select v-model="data.resumeId" placeholder="请选择要投递的简历">
        <el-option
          v-for="item in data.resumeList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        ></el-option>
      </el-select>
      <div style="display: flex; justify-content: end; padding: 10px">
        <el-button type="info" size="default" @click="submitResume">投递</el-button>
        <el-button type="warning" size="default" @click="data.submitVisiable = false"
          >取消</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<style scoped></style>
