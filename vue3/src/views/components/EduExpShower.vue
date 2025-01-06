<script setup>
defineProps({
  eduInfo: Object
})
const emit = defineEmits(['onEdit', 'onDelete'])
const handleEdit = (eduExp) => {
  emit('onEdit', eduExp)
}
const handleDel = (id) => {
  emit('onDelete', id)
}
</script>
<template>
  <div>
    <div class="edu-box">
      <div class="content" style="display: flex">
        <div style="flex: 1">{{ eduInfo.beginTime }} ~ {{ eduInfo.endTime }}</div>
        <div style="flex: 1">{{ eduInfo.schoolName }}</div>
        <div style="flex: 1">{{ eduInfo.majorName }}</div>
        <div class="icon">
          <el-icon class="edit" style="cursor: pointer; color: #17c4c3" @click="handleEdit(eduInfo)"
            ><Edit
          /></el-icon>
          <el-icon
            class="del"
            style="cursor: pointer; margin-left: 5px; color: rgb(172, 58, 48)"
            @click="handleDel(eduInfo.id)"
            ><Delete
          /></el-icon>
        </div>
      </div>
      <div class="major">主修课程: {{ eduInfo.majorCourse }}</div>
    </div>
  </div>
</template>

<style scoped>
.edu-box {
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
