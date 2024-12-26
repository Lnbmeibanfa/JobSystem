<script setup>
import { reactive } from 'vue'
const data = reactive({
  name: ''
})

const search = () => {
  console.log('search')
}
</script>

<template>
  <div class="home">
    <img src="@/assets/imgs/banner.jpg" alt="" />
    <div class="search">
      <el-input
        style="width: 400px; margin-right: 5px"
        v-model="data.name"
        placeholder="请输入你感兴趣的职位"
        size="large"
        @key-up.enter="search"
      ></el-input>
      <el-button type="primary" size="large" @click="search">搜索</el-button>
    </div>
  </div>
</template>

<style scoped>
.home img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}
.search {
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
