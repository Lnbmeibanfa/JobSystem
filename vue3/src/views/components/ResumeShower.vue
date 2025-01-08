<script setup>
import router from '@/router'
import { ROUTE_PATH } from '@/utils/Contants'
const props = defineProps({
  resumeInfo: Object
})
const emit = defineEmits(['onDelete'])
const navToDetail = () => {
  const baseURl = ROUTE_PATH.FRONT.RESUME_EDIT + '?id='
  router.push(baseURl + props.resumeInfo.id)
}
const handlePreview = () => {
  const baseURl = ROUTE_PATH.PREVIEW + '?id='
  window.open(baseURl + props.resumeInfo.id)
}
const handleDel = () => {
  emit('onDelete', props.resumeInfo.id)
}
</script>

<template>
  <div style="background-color: white; padding: 5px">
    <img
      style="width: 100%; cursor: pointer"
      @click="navToDetail"
      src="@/assets/imgs/img.png"
      alt=""
    />
    <div style="display: flex; padding: 15px; align-items: center; font-size: 18px">
      <div style="flex: 1; text-align: center">{{ resumeInfo.name }}</div>
      <div style="width: 45px; display: flex; justify-content: space-around" class="icons">
        <el-icon style="color: #17c4c3; cursor: pointer" @click="handlePreview"
          ><Position
        /></el-icon>
        <el-icon style="color: #ac3a30; cursor: pointer" @click="handleDel"><Delete /></el-icon>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
