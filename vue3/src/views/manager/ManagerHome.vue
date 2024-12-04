<script setup>
import { testRequest } from '@/api/test';
const test = async () => {
  const res = await testRequest();
  console.log(res)
}
</script>
<template>
  <div class="card">这是home
  <el-button @click="test" type="primary">123</el-button>
  </div>
</template>