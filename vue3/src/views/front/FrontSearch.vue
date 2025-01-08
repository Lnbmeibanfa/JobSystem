<script setup>
import { selectAllPositionByEmployAPI } from '@/api/position'
import PositionShower from '../components/PositionShower.vue'
import router from '@/router'
import { ElMessage } from 'element-plus'
import { reactive } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const data = reactive({
  positionData: [],
  positionName: route.params.name
})
const navTo = (id) => {
  router.push({ name: 'FrontPosition', params: { id } })
}
const loadPositionData = () => {
  selectAllPositionByEmployAPI(data.employId, data.positionName).then((res) => {
    if (res.code === '200') {
      data.positionData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadPositionData()
</script>

<template>
  <div style="background-color: #f6f6f8; height: 100%">
    <main class="position-detail" style="margin: 0px auto; width: 60%">
      <div
        class="search-box"
        style="display: flex; align-items: center; justify-content: center; height: 100px"
      >
        <el-input
          style="margin-right: 10px; width: 600px"
          size="large"
          v-model="data.positionName"
          placeholder="请输入职位名称查询"
          clearable
          @clear="loadPositionData"
        />
        <el-button type="primary" size="large" @click="loadPositionData">查询</el-button>
      </div>
      <div class="position-box">
        <el-row :gutter="10">
          <el-col :span="8" v-for="position in data.positionData" :key="position.id"
            ><position-shower :positionInfo="position" @navTo="navTo" />
          </el-col>
        </el-row>
      </div>
    </main>
  </div>
</template>

<style scoped></style>
