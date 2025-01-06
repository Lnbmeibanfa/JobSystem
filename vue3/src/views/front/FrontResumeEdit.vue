<script setup>
import { reactive } from 'vue'
import { addResumeAPI, selectResumeById } from '@/api/resume'
import { useAccountStore } from '@/stores/login'
import { ElMessage, ElMessageBox } from 'element-plus'
import EduExpShower from '@/views/components/EduExpShower.vue'
import WorkExpShower from '@/views/components/WorkExpShower.vue'
import ProExpShower from '@/views/components/ProExpShower.vue'
import router from '@/router'
import { ROUTE_PATH } from '@/utils/Contants'
import { useRoute } from 'vue-router'
import { updateResumeAPI } from '@/api/resume'
const route = useRoute()
const accountStore = useAccountStore()
const data = reactive({
  id: route.query.id,
  resumeData: {
    eduExpList: [],
    workExpList: [],
    proExpList: []
  },
  eduDialogVisable: false,
  workDialogVisable: false,
  proDialogVisable: false,
  eduFrom: {},
  workFrom: {},
  proFrom: {}
})
const loadResumeData = () => {
  if (data.id) {
    selectResumeById(data.id).then((res) => {
      if (res.code === '200') {
        data.resumeData = res.data
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
}
const createEduExp = () => {
  data.eduFrom = {
    id: new Date().getTime() + Math.random().toString(36).substr(2)
  }
  data.eduDialogVisable = true
}
const createWorkExp = () => {
  data.workFrom = {
    id: new Date().getTime() + Math.random().toString(36).substr(2)
  }
  data.workDialogVisable = true
}
const createProExp = () => {
  data.proFrom = {
    id: new Date().getTime() + Math.random().toString(36).substr(2)
  }
  data.proDialogVisable = true
}
const handleEditEduExp = (eduExp) => {
  data.eduFrom = JSON.parse(JSON.stringify(eduExp))
  data.eduDialogVisable = true
}
const handleEditWorkExp = (workExp) => {
  data.workFrom = JSON.parse(JSON.stringify(workExp))
  data.workDialogVisable = true
}
const handleEditProExp = (proExp) => {
  data.proFrom = JSON.parse(JSON.stringify(proExp))
  data.proDialogVisable = true
}
const handleDelEduExp = (id) => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      data.resumeData.eduExpList = data.resumeData.eduExpList.filter((eduExp) => eduExp.id !== id)
      ElMessage.success('删除成功')
    })
    .catch((err) => console.error(err))
}
const handleDelWorkExp = (id) => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      data.resumeData.workExpList = data.resumeData.workExpList.filter(
        (workExp) => workExp.id !== id
      )
      ElMessage.success('删除成功')
    })
    .catch((err) => console.error(err))
}
const handleDelProExp = (id) => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      data.resumeData.proExpList = data.resumeData.proExpList.filter((proExp) => proExp.id !== id)
      ElMessage.success('删除成功')
    })
    .catch((err) => console.error(err))
}
const submitEduExp = () => {
  const form = data.resumeData.eduExpList.filter((eduExp) => eduExp.id === data.eduFrom.id)
  if (form.length === 0) {
    data.resumeData.eduExpList.push(data.eduFrom)
  } else {
    data.resumeData.eduExpList.forEach((eduExp) => {
      if (eduExp.id === data.eduFrom.id) {
        eduExp.schoolName = data.eduFrom.schoolName
        eduExp.majorName = data.eduFrom.majorName
        eduExp.education = data.eduFrom.education
        eduExp.beginTime = data.eduFrom.beginTime
        eduExp.endTime = data.eduFrom.endTime
        eduExp.majorCourse = data.eduFrom.majorCourse
      }
    })
  }
  data.eduDialogVisable = false
}
const submitWorkExp = () => {
  const form = data.resumeData.workExpList.filter((workExp) => workExp.id === data.workFrom.id)
  if (form.length === 0) {
    data.resumeData.workExpList.push(data.workFrom)
  } else {
    data.resumeData.workExpList.forEach((workExp) => {
      if (workExp.id === data.workFrom.id) {
        console.log('in')
        workExp.companyName = data.workFrom.companyName
        workExp.projectName = data.workFrom.projectName
        workExp.positionName = data.workFrom.positionName
        workExp.positionType = data.workFrom.positionType
        workExp.beginTime = data.workFrom.beginTime
        workExp.endTime = data.workFrom.endTime
        workExp.projectDesc = data.workFrom.projectDesc
      }
    })
  }
  data.workDialogVisable = false
}
const submitProExp = () => {
  const form = data.resumeData.proExpList.filter((proExp) => proExp.id === data.proFrom.id)
  if (form.length === 0) {
    data.resumeData.proExpList.push(data.proFrom)
  } else {
    data.resumeData.proExpList.forEach((proExp) => {
      if (proExp.id === data.proFrom.id) {
        proExp.projectName = data.proFrom.projectName
        proExp.beginTime = data.proFrom.beginTime
        proExp.endTime = data.proFrom.endTime
        proExp.projectDesc = data.proFrom.projectDesc
      }
    })
  }
  data.proDialogVisable = false
}
const saveResume = () => {
  if (data.id) {
    updateResumeAPI(data.resumeData).then((res) => {
      if (res.code === '200') {
        ElMessage.success('修改成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  } else {
    data.resumeData.userId = accountStore.AccountInfo.id
    addResumeAPI(data.resumeData).then((res) => {
      if (res.code === '200') {
        ElMessage.success('保存成功')
        router.push(ROUTE_PATH.FRONT.RESUME)
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
}
loadResumeData()
</script>

<template>
  <div class="background">
    <div class="resume-box">
      <div
        class="title-box"
        style="display: flex; justify-content: center; align-items: center; height: 60px"
      >
        <div class="title" style="font-size: 20px">在线编辑您的简历</div>
      </div>
      <div class="content-box">
        <div class="content">
          <div style="display: flex">
            <el-input
              class="input-style"
              v-model="data.resumeData.name"
              placeholder="请输入简历名称"
              clearable
            />
            <el-input
              style="margin-left: 10px"
              class="input-style"
              v-model="data.resumeData.username"
              placeholder="请输入姓名"
              clearable
            />
          </div>
          <div style="display: flex">
            <el-select class="input-style" v-model="data.resumeData.sex" placeholder="请输入性别">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
            <el-select
              class="input-style"
              style="margin-left: 10px"
              v-model="data.resumeData.salary"
              placeholder="请输入预期薪资"
            >
              <el-option label="3k以下" value="3k以下"></el-option>
              <el-option label="3-5k" value="3-5k"></el-option>
              <el-option label="5-10k" value="5-10k"></el-option>
              <el-option label="10-20k" value="10-20k"></el-option>
              <el-option label="20-50k" value="20-50k"></el-option>
              <el-option label="50k以上" value="50k以上"></el-option>
            </el-select>
          </div>
          <div style="display: flex">
            <el-select
              class="input-style"
              v-model="data.resumeData.education"
              placeholder="请输入最高学历"
            >
              <el-option label="初中及以下" value="初中及以下"></el-option>
              <el-option label="中专/中技" value="中专/中技"></el-option>
              <el-option label="高中" value="高中"></el-option>
              <el-option label="大专" value="大专"></el-option>
              <el-option label="本科" value="本科"></el-option>
              <el-option label="硕士" value="硕士"></el-option>
              <el-option label="博士" value="博士"></el-option>
              <el-option label="博士后" value="硕士"></el-option>
            </el-select>
            <el-select
              style="margin-left: 10px"
              class="input-style"
              v-model="data.resumeData.experience"
              placeholder="请输入工作经验"
            >
              <el-option label="在校生" value="在校生"></el-option>
              <el-option label="应届生" value="应届生"></el-option>
              <el-option label="经验不限" value="经验不限"></el-option>
              <el-option label="1年以内" value="1年以内"></el-option>
              <el-option label="1到3年" value="1到3年"></el-option>
              <el-option label="3到5年" value="3到5年"></el-option>
              <el-option label="5到10年" value="5到10年"></el-option>
              <el-option label="10年以上" value="10年以上"></el-option>
            </el-select>
          </div>
          <div style="display: flex">
            <el-input
              class="input-style"
              v-model="data.resumeData.phone"
              placeholder="请输入手机号码"
              clearable
            />
            <el-input
              style="margin-left: 10px"
              class="input-style"
              v-model="data.resumeData.email"
              placeholder="请输入邮箱"
              clearable
            />
          </div>
          <div>
            <el-button
              type="success"
              size="default"
              @click="createEduExp"
              style="margin-bottom: 20px"
              >添加学校经历</el-button
            >
            <div
              style="font-size: 16px; font-weight: bold; margin-top: 10px"
              v-if="data.resumeData.eduExpList.length > 0"
            >
              教育经历:
            </div>

            <div v-for="eduExp in data.resumeData.eduExpList" :key="eduExp.id">
              <edu-exp-shower
                :eduInfo="eduExp"
                @onEdit="handleEditEduExp"
                @onDelete="handleDelEduExp"
              />
            </div>
          </div>

          <div>
            <el-button
              type="success"
              size="default"
              @click="createWorkExp"
              style="margin-bottom: 20px"
              >添加工作经历</el-button
            >
            <div
              style="font-size: 16px; font-weight: bold; margin-top: 10px"
              v-if="data.resumeData.workExpList.length > 0"
            >
              工作经历:
            </div>
            <div v-for="workExp in data.resumeData.workExpList" :key="workExp.id">
              <work-exp-shower
                :workInfo="workExp"
                @onEdit="handleEditWorkExp"
                @onDelete="handleDelWorkExp"
              />
            </div>
          </div>
          <div>
            <el-button
              type="success"
              size="default"
              @click="createProExp"
              style="margin-bottom: 20px"
              >添加项目经验</el-button
            >
            <div
              style="font-size: 16px; font-weight: bold; margin-top: 10px"
              v-if="data.resumeData.proExpList.length > 0"
            >
              工作经历:
            </div>
            <div v-for="proExp in data.resumeData.proExpList" :key="proExp.id">
              <pro-exp-shower
                :proInfo="proExp"
                @onEdit="handleEditProExp"
                @onDelete="handleDelProExp"
              />
            </div>
          </div>
        </div>
        <div class="center">
          <el-button type="primary" size="default" @click="saveResume">保存简历</el-button>
        </div>
      </div>
    </div>
    <el-dialog v-model="data.eduDialogVisable" title="教育经历" width="40%">
      <div class="edu-experience-box">
        <el-form :model="data.eduFrom" label-width="80px" style="padding-right: 20px">
          <el-form-item label="学校名称" prop="schoolName">
            <el-input v-model="data.eduFrom.schoolName" placeholder="请输入学校名称"></el-input>
          </el-form-item>
          <el-form-item label="专业名称" prop="majorName">
            <el-input v-model="data.eduFrom.majorName" placeholder="请输入专业名称"></el-input>
          </el-form-item>
          <el-form-item label="选择学历" prop="educationDegree">
            <el-select
              class="input-style"
              v-model="data.eduFrom.educationDegree"
              placeholder="请选择您的学历"
            >
              <el-option label="初中及以下" value="初中及以下"></el-option>
              <el-option label="中专/中技" value="中专/中技"></el-option>
              <el-option label="高中" value="高中"></el-option>
              <el-option label="大专" value="大专"></el-option>
              <el-option label="本科" value="本科"></el-option>
              <el-option label="硕士" value="硕士"></el-option>
              <el-option label="博士" value="博士"></el-option>
              <el-option label="博士后" value="硕士"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入学时间" prop="beginTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.eduFrom.beginTime"
              type="date"
              placeholder="请选择入学时间"
              value-format="YYYY-MM-DD"
          /></el-form-item>
          <el-form-item label="毕业时间" prop="endTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.eduFrom.endTime"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择毕业时间"
            />
          </el-form-item>
          <el-form-item label="主修课程" prop="majorCourse">
            <el-input
              v-model="data.eduFrom.majorCourse"
              type="textarea"
              placeholder="请输入主修课程"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="width: 100%; display: flex; justify-content: end">
              <el-button type="primary" @click="submitEduExp">保存</el-button>
              <el-button size="default" @click="data.eduDialogVisable = false">取消</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <el-dialog v-model="data.workDialogVisable" title="工作经历" width="40%">
      <div class="work-experience-box">
        <el-form :model="data.workFrom" label-width="80px" style="padding-right: 20px">
          <el-form-item label="公司名称" prop="companyName">
            <el-input v-model="data.workFrom.companyName" placeholder="请输入公司名称"></el-input>
          </el-form-item>
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="data.workFrom.projectName" placeholder="请输入项目名称"></el-input>
          </el-form-item>
          <el-form-item label="职位名称" prop="positionName">
            <el-input v-model="data.workFrom.positionName" placeholder="请输入职位名称"></el-input>
          </el-form-item>
          <el-form-item label="职位类型" prop="positionType">
            <el-select
              class="input-style"
              v-model="data.workFrom.positionType"
              placeholder="请选择职位类型"
            >
              <el-option label="实习" value="实习"></el-option>
              <el-option label="全职" value="全职"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入职时间" prop="beginTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.workFrom.beginTime"
              type="date"
              placeholder="请选择入职时间"
              value-format="YYYY-MM-DD"
          /></el-form-item>
          <el-form-item label="离职时间" prop="endTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.workFrom.endTime"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择离职时间"
            />
          </el-form-item>
          <el-form-item label="项目介绍" prop="projectDesc">
            <el-input
              v-model="data.workFrom.projectDesc"
              type="textarea"
              placeholder="请填写项目介绍"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="width: 100%; display: flex; justify-content: end">
              <el-button type="primary" @click="submitWorkExp">保存</el-button>
              <el-button @click="data.workDialogVisable = false">取消</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    <el-dialog v-model="data.proDialogVisable" title="项目经理" width="40%">
      <div class="pro-experience-box">
        <el-form :model="data.proFrom" label-width="80px" style="padding-right: 20px">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="data.proFrom.projectName" placeholder="请输入项目名称"></el-input>
          </el-form-item>
          <el-form-item label="开始时间" prop="beginTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.proFrom.beginTime"
              type="date"
              placeholder="请选择开始时间"
              value-format="YYYY-MM-DD"
          /></el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              style="width: 100%"
              v-model="data.proFrom.endTime"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择结束时间"
            />
          </el-form-item>
          <el-form-item label="项目介绍" prop="projectDesc">
            <el-input
              v-model="data.proFrom.projectDesc"
              type="textarea"
              placeholder="请输入项目介绍"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="width: 100%; display: flex; justify-content: end">
              <el-button type="primary" @click="submitProExp">保存</el-button>
              <el-button @click="data.proDialogVisable = false">取消</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.background {
  height: 100%;
}
.resume-box {
  width: 50%;
  margin: 0px auto;
}
.input-style {
  flex: 1;
  margin-bottom: 10px;
}
.center {
  display: flex;
  justify-content: center;
}
</style>
