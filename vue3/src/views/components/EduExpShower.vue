<script setup>
defineProps({
  eduInfo: Object,
  showIcon: {
    type: Boolean,
    default: true
  }
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
        <div class="icon" v-if="showIcon">
          <el-icon class="edit" style="cursor: pointer; color: #17c4c3" @click="handleEdit(eduInfo)"
            ><Edit
          /></el-icon>
          <el-icon
            class="del"
            style="cursor: pointer; margin-left: 5px; color: #ac3a30"
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
  height: 70px;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
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
