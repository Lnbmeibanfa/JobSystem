<script setup>
import { reactive, ref } from 'vue'
import { useAccountStore } from '@/stores/login'
// import { LOCALSTORAGE_KEY } from '@/utils/Contants'
import { updateEmployAPI, selectById } from '@/api/employ'
import { selectAllIndustryAPI } from '@/api/industry'
import { ROLE, OPTIONS } from '@/utils/Contants'
import { ElMessage, ElMessageBox } from 'element-plus'
const accountStore = new useAccountStore()
const baseUrl = import.meta.env.VITE_BASE_URL
const data = reactive({
  formData: {}
})
const optionData = reactive({
  industryOptions: [],
  scaleOptions: OPTIONS.EMPLOY.SCALE,
  stageOptions: OPTIONS.EMPLOY.STAGE,
  statusOptions: OPTIONS.EMPLOY.STATUS
})
// 获取行业选择器数据
const loadIndustryOptions = () => {
  selectAllIndustryAPI().then((res) => {
    if (res.code === '200') {
      res.data.forEach((industry) => {
        optionData.industryOptions.push({
          value: industry.id,
          label: industry.name
        })
      })
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadEmployInfo = () => {
  selectById(accountStore.AccountInfo.id).then((res) => {
    if (res.code === '200') {
      data.formData = res.data
      accountStore.setAccountInfo(res.data)
    }
  })
}
const userForm = ref(null)
const rules = reactive({})
const fileList = ref([])
const handleUploadSuccess = (res) => {
  data.formData.avatar = res.data
}
const save = () => {
  ElMessageBox.confirm('修改信息后需要审核', '确认修改', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      data.formData.status = '待审核'
      userForm.value.validate((valid) => {
        if (valid) {
          updateEmployAPI(data.formData).then((res) => {
            if (res.code === '200') {
              accountStore.setAccountInfo(data.formData)
              ElMessage.success('修改成功')
            } else {
              ElMessage.error(res.msg)
            }
          })
        }
      })
    })
    .catch((err) => console.error(err))
}
loadEmployInfo()
loadIndustryOptions()
</script>
<template>
  <div class="manager-user card" style="height: 100%">
    <div class="card avatar-box" style="display: flex; justify-content: center">
      <el-upload
        :action="baseUrl + '/files/upload'"
        :on-success="handleUploadSuccess"
        list-type="picture-card"
        :file-list="fileList"
        :limit="1"
        :show-file-list="false"
      >
        <img
          style="width: 100px; height: 100px; border-radius: 5px"
          v-if="data.formData.avatar"
          :src="data.formData.avatar"
          class="avatar"
        />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
      <div class="user-name" style="padding: 20px">
        <div class="name" style="font-size: 20px; font-weight: bold; height: 60px; padding: 10px">
          {{ accountStore.AccountInfo.name }}
        </div>
        <div
          class="role"
          style="
            font-size: 20px;
            font-weight: bold;
            color: aqua;
            height: 60px;
            padding: 10px;
            text-align: center;
          "
        >
          <el-icon style="vertical-align: middle">
            <User />
          </el-icon>
          {{ ROLE[data.formData.role] }}
        </div>
      </div>
    </div>
    <div class="card user-info" style="display: flex; justify-content: center">
      <div class="manager-form" style="width: 700px">
        <el-form
          ref="userForm"
          :model="data.formData"
          label-width="70px"
          style="padding: 20px"
          :rules="rules"
        >
          <el-form-item label="企业账号" prop="username">
            <el-input disabled v-model="data.formData.username" placeholder="请输入企业账号" />
          </el-form-item>
          <el-form-item label="企业名称" prop="name">
            <el-input v-model="data.formData.name" placeholder="请输入企业名称" />
          </el-form-item>
          <el-form-item label="所在城市" prop="city">
            <el-input v-model="data.formData.city" placeholder="请输入所在城市" />
          </el-form-item>
          <el-form-item label="详细地址" prop="address">
            <el-input v-model="data.formData.address" placeholder="请输入详细地址" />
          </el-form-item>
          <el-form-item label="所属行业" prop="industryId">
            <el-select v-model="data.formData.industryId" placeholder="请选择所属行业">
              <el-option
                v-for="item in optionData.industryOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司规模" prop="scale">
            <el-select v-model="data.formData.scale" placeholder="请选择公司规模">
              <el-option
                v-for="item in optionData.scaleOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="融资阶段" prop="stage">
            <el-select v-model="data.formData.stage" placeholder="请选择融资阶段">
              <el-option
                v-for="item in optionData.stageOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态" prop="status">
            <el-tag v-if="data.formData.status === '待审核'" type="warning">{{
              data.formData.status
            }}</el-tag>
            <el-tag v-if="data.formData.status === '审核通过'" type="success">{{
              data.formData.status
            }}</el-tag>
            <el-tag v-if="data.formData.status === '审核不通过'" type="danger">{{
              data.formData.status
            }}</el-tag>
          </el-form-item>
        </el-form>
        <div style="text-align: center">
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
