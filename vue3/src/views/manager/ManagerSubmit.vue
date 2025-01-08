<script setup>
import { ref, onMounted } from 'vue'
import { ElButton, ElMessage } from 'element-plus'
import { selectByPageAPI, updateSubmitAPI } from '@/api/submit'
import { useAccountStore } from '@/stores/login'
import { ROUTE_PATH, STATUS } from '@/utils/Contants'
const accountStore = useAccountStore()
onMounted(() => {
  // 加载表格数据
  load()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const positionName = ref('')
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, positionName.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  positionName.value = ''
  load()
}
// 维护批量删除的id
const ids = ref([])
const handleSelectionChange = (rows) => {
  ids.value = rows.map((row) => row.id)
}
const showResume = (id) => {
  const baseURl = ROUTE_PATH.PREVIEW + '?id='
  window.open(baseURl + id, '_blank')
}
const updateStatus = (submit, status) => {
  updateSubmitAPI({
    employId: submit.employId,
    positionId: submit.positionId,
    userId: accountStore.userId,
    resumeId: submit.resumeId,
    status
  }).then((res) => {
    if (res.code !== '200') {
      ElMessage.error(res.msg)
    } else {
      ElMessage.success('操作成功')
    }
  })
  load()
}
</script>

<template>
  <div class="manager-submit">
    <div class="card">
      <el-input
        v-model="positionName"
        prefix-icon="Search"
        placeholder="请输入名称查询"
        style="width: 400px; margin-right: 15px"
        @keyup.enter="load"
      ></el-input>
      <el-button type="primary" size="default" @click="load" plain>查询</el-button>
      <el-button type="warning" size="default" @click="replace" plain>重置</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData" @selection-change="handleSelectionChange" show-overflow-tooltip>
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
        <el-table-column label="操作" width="350" v-if="accountStore.AccountInfo.role === 'EMPLOY'">
          <template #default="scope">
            <el-button type="danger" @click="updateStatus(scope.row, '不合适')">不合适</el-button>
            <el-button type="primary" @click="updateStatus(scope.row, '面试中')">面试中</el-button>
            <el-button type="success" @click="updateStatus(scope.row, '通过')">通过</el-button>
            <el-button type="warning" @click="updateStatus(scope.row, '不通过')">不通过</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
        background
        :page-size="pageSize"
        v-model:current-page="pageNum"
        layout="prev, pager, next"
        :total="total"
        @current-change="load"
      />
    </div>
  </div>
</template>

<style scoped></style>
