<script setup>
defineProps({
  workInfo: Object
})
const emit = defineEmits(['onEdit', 'onDelete'])
const handleEdit = (workExp) => {
  console.log(workExp)
  emit('onEdit', workExp)
}
const handleDel = (id) => {
  emit('onDelete', id)
}
</script>
<template>
  <div>
    <div class="work-box">
      <div class="content" style="display: flex">
        <div style="flex: 1">{{ workInfo.beginTime }} ~ {{ workInfo.endTime }}</div>
        <div style="flex: 1">{{ workInfo.companyName }}</div>
        <div style="flex: 1">{{ workInfo.positionType }}</div>
        <div class="icon">
          <el-icon
            class="edit"
            style="cursor: pointer; color: #17c4c3"
            @click="handleEdit(workInfo)"
            ><Edit
          /></el-icon>
          <el-icon
            class="del"
            style="cursor: pointer; margin-left: 5px; color: rgb(172, 58, 48)"
            @click="handleDel(workInfo.id)"
            ><Delete
          /></el-icon>
        </div>
      </div>
      <div class="major">工作经历: {{ workInfo.projectDesc }}</div>
    </div>
  </div>
</template>

<style scoped>
.work-box {
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
