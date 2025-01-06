<script setup>
defineProps({
  proInfo: Object
})
const emit = defineEmits(['onEdit', 'onDelete'])
const handleEdit = (proExp) => {
  emit('onEdit', proExp)
}
const handleDel = (id) => {
  console.log(id)
  emit('onDelete', id)
}
</script>
<template>
  <div>
    <div class="pro-box">
      <div class="content" style="display: flex">
        <div style="flex: 1">{{ proInfo.beginTime }} ~ {{ proInfo.endTime }}</div>
        <div style="flex: 1">{{ proInfo.projectName }}</div>
        <div class="icon">
          <el-icon class="edit" style="cursor: pointer; color: #17c4c3" @click="handleEdit(proInfo)"
            ><Edit
          /></el-icon>
          <el-icon
            class="del"
            style="cursor: pointer; margin-left: 5px; color: rgb(172, 58, 48)"
            @click="handleDel(proInfo.id)"
            ><Delete
          /></el-icon>
        </div>
      </div>
      <div class="major">项目介绍: {{ proInfo.projectDesc }}</div>
    </div>
  </div>
</template>

<style scoped>
.pro-box {
  height: 80px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.content {
  color: #17c4c3;
  font-weight: bold;

  font-size: 16px;
}
.major {
  font-size: 16px;
}
.icon .edit,
.del {
  color: #000000;
}
.icon .edit:hover,
.del:hover {
  color: #0066bc;
}
</style>
