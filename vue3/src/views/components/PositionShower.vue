<script setup>
import { ElMessageBox } from 'element-plus'
import { defineProps } from 'vue'
defineProps({
  positionInfo: Object,
  onShowDel: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['onDelete', 'navTo'])

const handleDel = (id) => {
  ElMessageBox.confirm('确认删除职位?', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      emit('onDelete', id)
    })
    .catch((err) => console.error(err))
}
const handleNav = (id) => {
  emit('navTo', id)
}
</script>

<template>
  <div class="position-box">
    <div class="position-name" @click="handleNav(positionInfo.positionId || positionInfo.id)">
      <div style="font-size: 18px">{{ positionInfo.name || positionInfo.positionName }}</div>
      <div style="font-size: 18px; color: red">{{ positionInfo.salary }}</div>
    </div>
    <div class="position-tag">
      <el-tag
        effect="plain"
        style="margin-right: 5px"
        type="info"
        v-for="tag in positionInfo.tagList"
        :key="tag"
        >{{ tag }}</el-tag
      >
    </div>
    <div class="position-info">
      <img style="width: 50px; height: 40px" :src="positionInfo.employAvatar" alt="" />
      <div>{{ positionInfo.employName }}</div>
      <div>{{ positionInfo.industryName }}</div>
      <div>{{ positionInfo.employStage }}</div>
      <div class="deleteButton" v-if="onShowDel" @click="handleDel(positionInfo.id)">
        <el-icon style="font-size: 16px; cursor: pointer"><Delete /></el-icon>
      </div>
    </div>
  </div>
</template>

<style scoped>
.position-box {
  background-color: white;
  height: 150px;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 10px;
}
.position-tag {
  margin: 10px 0px 20px 0px;
}
.position-name {
  cursor: pointer;
  display: flex;
  justify-content: space-between;
}
.position-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.deleteButton i:hover {
  color: #0066bc;
}
</style>
