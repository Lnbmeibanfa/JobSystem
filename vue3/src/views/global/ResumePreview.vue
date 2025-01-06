<script setup>
import { selectResumeById } from '@/api/resume'
import EduExpShower from '../components/EduExpShower.vue'
import WorkExpShower from '../components/WorkExpShower.vue'
import ProExpShower from '../components/ProExpShower.vue'
import router from '@/router'
import { ElMessage } from 'element-plus'
import { reactive } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const data = reactive({
  id: route.query.id,
  resumeList: {
    eduExpList: [],
    workExpList: [],
    proExpList: []
  }
})

const loadResumeData = () => {
  if (data.id) {
    selectResumeById(data.id).then((res) => {
      if (res.code === '200') {
        console.log(res.data)
        data.resumeList = res.data
      } else {
        ElMessage.error(res.msg)
      }
    })
  } else {
    router.push('/404')
  }
}
loadResumeData()
</script>

<template>
  <div class="backgroud">
    <div class="resume-box">
      <div class="title">{{ data.resumeList.name }}</div>
      <div class="resume-info" style="display: flex; margin-top: 30px">
        <img class="avatar" :src="data.resumeList.avatar" alt="" />
        <div
          class="msg-box"
          style="
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
            padding-left: 40px;
          "
        >
          <div class="name" style="font-size: 20px">{{ data.resumeList.username }}</div>
          <div class="sex-education">
            {{
              data.resumeList.sex +
              ' | ' +
              data.resumeList.education +
              '  |  ' +
              data.resumeList.experience
            }}
          </div>
          <div class="phone">电话： {{ data.resumeList.phone }}</div>
          <div class="email">邮箱：{{ data.resumeList.email }}</div>
          <div class="salary">期望薪资：{{ data.resumeList.salary }}</div>
        </div>
      </div>
      <div class="edu-box" style="margin-top: 20px">
        <div class="exp-title" v-if="data.resumeList.eduExpList.length">教育经历:</div>
        <div v-for="eduExp in data.resumeList.eduExpList" :key="eduExp.id">
          <edu-exp-shower :eduInfo="eduExp" :showIcon="false" />
        </div>
      </div>
      <div class="work-box" style="margin-top: 20px">
        <div class="exp-title" v-if="data.resumeList.workExpList.length">工作经历:</div>
        <div v-for="workExp in data.resumeList.workExpList" :key="workExp.id">
          <work-exp-shower :workInfo="workExp" :showIcon="false" />
        </div>
      </div>
      <div class="pro-box" style="margin-top: 20px">
        <div class="exp-title" v-if="data.resumeList.proExpList.length">项目经历:</div>
        <div v-for="proExp in data.resumeList.proExpList" :key="proExp.id">
          <pro-exp-shower :proInfo="proExp" :showIcon="false" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.backgroud {
  height: 100vh;
  background-color: #f6f6f8;
  padding: 30px 0;
}
.resume-box {
  padding: 40px;
  background-color: #fff;
  margin: 0 auto;
  width: 50%;
}
.title {
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 22px;
  font-weight: bold;
}
.avatar {
  width: 150px;
  height: 150px;
  border-radius: 10px;
}
.exp-title {
  padding: 10px;
  font-weight: bold;
  font-size: 18px;
  background: linear-gradient(to right, rgb(142, 223, 238), rgb(254, 255, 255));
}
</style>
