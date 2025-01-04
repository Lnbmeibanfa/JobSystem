<script setup>
import { reactive } from 'vue'
import { addResumeAPI } from '@/api/resume'
import { ElMessage } from 'element-plus'
const data = reactive({
  resumeData: {
    eduExpList: []
  },
  eduDialogVisable: false,
  eduFrom: {}
})
const createEduExp = () => {
  data.eduDialogVisable = true
}
const submitEduExp = () => {
  data.resumeData.eduExpList.push(data.eduFrom)
  data.eduDialogVisable = false
}
const saveResume = () => {
  addResumeAPI(data.resumeData).then((res) => {
    if (res.code === '200') {
      ElMessage.success('保存成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
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
            <el-button type="success" size="default" @click="createEduExp">添加学校经历</el-button>
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
              v-model="data.resumeData.educationDegree"
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
            <div><el-button type="primary" @click="submitEduExp">确认</el-button></div>
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
