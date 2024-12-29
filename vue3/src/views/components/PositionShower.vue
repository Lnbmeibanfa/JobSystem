<script setup>
import { defineProps } from 'vue'
defineProps({
  positionInfo: Object
})
</script>

<template>
  <div class="position-box">
    <div class="position-name">
      <div>{{ positionInfo.name }}</div>
      <div style="color: red">{{ positionInfo.salary }}</div>
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
  display: flex;
  justify-content: space-between;
}
.position-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
