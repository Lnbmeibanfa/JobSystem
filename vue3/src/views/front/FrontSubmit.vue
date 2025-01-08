<script setup>
import { deleteById, selectAllSubmitAPI } from '@/api/submit'
import { useAccountStore } from '@/stores/login'
import { ROUTE_PATH, STATUS } from '@/utils/Contants'
import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive } from 'vue'
const accountStore = useAccountStore()
const data = reactive({
  submitTable: []
})
const loadSubmitData = () => {
  selectAllSubmitAPI(accountStore.AccountInfo.id).then((res) => {
    if (res.code === '200') {
      console.log(res)
      data.submitTable = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const showResume = (id) => {
  const baseURl = ROUTE_PATH.PREVIEW + '?id='
  window.open(baseURl + id, '_blank')
}
const handleDel = (id) => {
  ElMessageBox.confirm('确认撤回投递？', '撤回投递', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteById(id).then((res) => {
      if (res.code === '200') {
        ElMessage.success('撤回成功')
        loadSubmitData()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}
loadSubmitData()
</script>

<template>
  <div class="background">
    <div class="submit-box">
      <div class="title-box">
        <div style="font-size: 18px">我投递的简历</div>
        <el-table :data="data.submitTable" show-overflow-tooltip>
          <el-table-column prop="employName" label="企业名称" />
          <el-table-column prop="positionName" label="职位名称" />
          <el-table-column prop="userName" label="投递用户" />
          <el-table-column prop="resumeId" label="投递简历">
            <template #default="scope">
              <el-button type="primary" size="default" @click="showResume(scope.row.resumeId)"
                >查看简历</el-button
              >
            </template>
          </el-table-column>
          <el-table-column prop="time" label="投递时间" />
          <el-table-column prop="status" label="投递状态">
            <template #default="scope">
              <el-tag v-if="scope.row.status === STATUS.SUBMIT.HAVE_SUBMIT" type="info">{{
                scope.row.status
              }}</el-tag>
              <el-tag v-if="scope.row.status === STATUS.SUBMIT.INAPPROPRIATE" type="danger">{{
                scope.row.status
              }}</el-tag>
              <el-tag v-if="scope.row.status === STATUS.SUBMIT.ONINTERVIEW" type="primary">{{
                scope.row.status
              }}</el-tag>
              <el-tag v-if="scope.row.status === STATUS.SUBMIT.PASS" type="success">{{
                scope.row.status
              }}</el-tag>
              <el-tag v-if="scope.row.status === STATUS.SUBMIT.NOTPASS" type="warning">{{
                scope.row.status
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button type="danger" size="default" @click="handleDel(scope.row.id)"
                >撤回投递</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.background {
  background-color: #f6f6f8;
  height: 100%;
}
.submit-box {
  height: 100%;
  width: 70%;
  background-color: white;
  margin: 0px auto;
}
.title-box {
  padding: 20px;
  height: 60px;
  line-height: 60px;
}
</style>
