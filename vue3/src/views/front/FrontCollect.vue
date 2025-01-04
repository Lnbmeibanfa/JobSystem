<script setup>
import { reactive } from 'vue'
import { deleteById, selectAllCollectAPI } from '@/api/collect'
import PositionShower from '@/views/components/PositionShower.vue'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import router from '@/router'

const data = reactive({
  collectList: []
})
const loadCollectList = () => {
  selectAllCollectAPI().then((res) => {
    if (res.code === '200') {
      data.collectList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const navTo = (id) => {
  router.push({ name: 'FrontPosition', params: { id } })
}
const handleDelete = (id) => {
  deleteById(id).then((res) => {
    if (res.code === '200') {
      ElMessage.success('删除成功')
      loadCollectList()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadCollectList()
})
</script>

<template>
  <div style="background-color: #f6f6f8; display: block; height: 100%">
    <div class="collect-box">
      <div
        class="title-box"
        style="display: flex; justify-content: center; align-items: center; height: 100px"
      >
        <div class="title" style="font-weight: bold; font-size: 20px">
          我收藏的岗位({{ data.collectList.length }})
        </div>
      </div>
      <div class="display-box">
        <el-row :gutter="10">
          <el-col :span="8" v-for="position in data.collectList" :key="position.id"
            ><position-shower
              :positionInfo="position"
              :onShowDel="true"
              @navTo="navTo"
              @onDelete="handleDelete"
            />
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped>
.collect-box {
  width: 70%;
  margin: 0px auto;
}
</style>
