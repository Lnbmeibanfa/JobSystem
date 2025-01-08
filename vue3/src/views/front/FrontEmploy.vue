<script setup>
import { selectById } from '@/api/employ'
import { selectAllPositionByEmployAPI } from '@/api/position'
import PositionShower from '../components/PositionShower.vue'
import router from '@/router'
import { ElMessage } from 'element-plus'
import { reactive } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const data = reactive({
  employId: route.query.id,
  employData: {},
  positionData: [],
  positionName: null
})
const loadEmployData = () => {
  selectById(data.employId).then((res) => {
    if (res.code === '200') {
      data.employData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
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
loadEmployData()
loadPositionData()
</script>

<template>
  <div style="background-color: #f6f6f8; height: 100%">
    <header
      class="position-header"
      style="background-color: #3b526a; display: flex; justify-content: center; padding: 40px 30px"
    >
      <div class="header-box" style="width: 70%; color: white">
        <div style="display: flex" class="info">
          <img
            style="
              width: 70px;
              height: 70px;
              margin-right: 10px;
              margin-bottom: 20px;
              border-radius: 5px;
            "
            :src="data.employData.avatar"
            alt=""
          />
          <div
            style="
              height: 70px;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            "
          >
            <div class="name" style="font-size: 28px; font-weight: bold">
              {{ data.employData.name }}
            </div>
            <div style="display: flex">
              <div style="display: flex; align-items: center; margin-right: 15px; font-size: 18px">
                <el-icon style="margin-right: 3px"><Place /></el-icon>{{ data.employData.stage }}
              </div>
              <div style="display: flex; align-items: center; margin-right: 15px; font-size: 18px">
                <el-icon style="margin-right: 3px"><User /></el-icon>{{ data.employData.scale }}
              </div>
              <div style="display: flex; align-items: center; font-size: 18px">
                <el-icon style="margin-right: 3px"><Notebook /></el-icon>在招职位：
                {{ data.positionData.length }}
              </div>
            </div>
          </div>
        </div>
        <div class="address" style="display: flex; align-items: center; font-size: 18px">
          <el-icon style="margin-right: 3px"><Location /></el-icon>{{ data.employData.address }}
        </div>
      </div>
    </header>
    <main class="position-detail" style="margin: 0px auto; width: 60%">
      <div
        class="search-box"
        style="display: flex; align-items: center; justify-content: center; margin: 40px 0px"
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
