<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElButton, ElDialog, ElMessage, ElMessageBox } from 'element-plus'
import {
  addAdvertiseAPI,
  selectByPageAPI,
  updateAdvertiseAPI,
  deleteBatch,
  deleteById
} from '@/api/advertise'
import { selectAllPositionAPI } from '@/api/position'

onMounted(() => {
  // 加载表格数据
  load()
  // 加载行业选择器数据
  loadPositionOptions()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
// 筛选关键字
const location = ref(null)
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, location.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  location.value = null
  load()
}
/**dialog数据和方法*/
// 表单数据和rules
const advertiseForm = ref(null)
// 图片访问地址
const baseUrl = import.meta.env.VITE_BASE_URL
// 图片上传列表
const fileList = ref([])
const formData = ref({})
const rules = reactive({})
// 获取行业选择器数据
const loadPositionOptions = () => {
  selectAllPositionAPI().then((res) => {
    if (res.code === '200') {
      res.data.forEach((position) => {
        optionData.positionOptions.push({
          value: position.id,
          label: position.employName + '/' + position.name + '/' + position.salary
        })
      })
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 选择器数据
const optionData = reactive({
  positionOptions: []
})
// 打开dialog
const handeleAdd = () => {
  formData.value = {
    status: '待审核'
  }
  fileList.value = []
  toggleFormVisiable(true)
}
const handleEdit = (row) => {
  formData.value = {}
  fileList.value = []
  formData.value = JSON.parse(JSON.stringify(row))
  toggleFormVisiable(true)
}
const handleDel = (id) => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteById(id).then((res) => {
        if (res.code === '200') {
          ElMessage.success('删除成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch((err) => console.error(err))
}
// 维护批量删除的id
const ids = ref([])
const handleSelectionChange = (rows) => {
  ids.value = rows.map((row) => row.id)
}
const handleDelBatch = () => {
  ElMessageBox.confirm('删除数据后不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      deleteBatch(ids.value).then((res) => {
        if (res.code === '200') {
          ElMessage.success('批量删除成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch((err) => console.error(err))
}
const handleUploadSuccess = (res) => {
  formData.value.img = res.data
}
const beforeUpload = (file) => {
  const maxSize = 1024 * 1024
  if (file.size > maxSize) {
    ElMessage.error('文件过大，请上传小于1MB的文件')
    return false
  }
  return true
}
// 处理表单是否可见
const toggleFormVisiable = (isvisiable) => {
  formVisiable.value = isvisiable
}
const formVisiable = ref(false)
// 提交表单
const submit = () => {
  advertiseForm.value.validate(async (valid) => {
    if (valid) {
      formData.value.id ? update() : add()
    }
  })
}
const add = () => {
  addAdvertiseAPI(formData.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      toggleFormVisiable(false)
      load()
    } else {
      ElMessage.warning(res.msg)
    }
  })
}
const update = () => {
  updateAdvertiseAPI(formData.value).then((res) => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('修改成功')
      toggleFormVisiable(false)
      load()
    } else {
      ElMessage.warning(res.msg)
    }
  })
}
</script>

<template>
  <div class="manager-advertise">
    <div class="card">
      <el-select
        v-model="location"
        style="width: 400px; margin-right: 15px"
        placeholder="请选择审核状态"
        @change="load"
      >
        <el-option label="中心大图" value="中心大图"></el-option>
        <el-option label="中心小图" value="中心小图"></el-option>
        <el-option label="左侧大图" value="左侧大图"></el-option>
        <el-option label="左侧小图" value="左侧小图"></el-option>
        <el-option label="右侧大图" value="右侧大图"></el-option>
        <el-option label="右侧小图" value="右侧小图"></el-option>
      </el-select>
      <el-button type="warning" size="default" @click="replace" plain>重置</el-button>
    </div>
    <div class="card">
      <el-button @click="handeleAdd" plain type="primary">添加</el-button>
      <el-button @click="handleDelBatch" plain type="danger">批量删除</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="img" label="主图">
          <template #default="scope">
            <el-image
              style="width: 60px; height: 40px; border-radius: 5px"
              :preview-src-list="[scope.row.img]"
              :preview-teleported="true"
              :src="scope.row.img"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="推荐职位" prop="positionName" show-overflow-tooltip>
          <template v-slot="scope">
            <div>
              {{
                scope.row.employName + '/' + scope.row.positionName + '/' + scope.row.positionSalary
              }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="广告位置" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              type="primary"
              circle
              icon="Edit"
              size="default"
              @click="handleEdit(scope.row)"
            ></el-button>
            <el-button
              type="danger"
              circle
              icon="Delete"
              size="default"
              @click="handleDel(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination
        background
        :page-size="pageSize"
        v-model:current-page="pageNum"
        layout="prev, pager, next"
        :total="total"
        @current-change="load"
      />
    </div>
    <el-dialog v-model="formVisiable" title="新增管理员" width="40%">
      <el-form
        ref="advertiseForm"
        :model="formData"
        label-width="80px"
        style="padding: 20px"
        :rules="rules"
      >
        <el-form-item label="广告图片" prop="img">
          <el-upload
            :before-upload="beforeUpload"
            :action="baseUrl + '/files/upload'"
            :on-success="handleUploadSuccess"
            list-type="picture"
            :file-list="fileList"
          >
            <template #trigger><el-button type="primary">上传广告图片</el-button></template>
          </el-upload>
        </el-form-item>
        <el-form-item label="岗位">
          <el-select v-model="formData.positionId" placeholder="请选择岗位">
            <el-option
              v-for="position in optionData.positionOptions"
              :key="position.value"
              :label="position.label"
              :value="position.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="展示位置" prop="location">
          <el-select v-model="formData.location" placeholder="请选择岗位">
            <el-option label="中心大图" value="中心大图"></el-option>
            <el-option label="中心小图" value="中心小图"></el-option>
            <el-option label="左侧大图" value="左侧大图"></el-option>
            <el-option label="左侧小图" value="左侧小图"></el-option>
            <el-option label="右侧大图" value="右侧大图"></el-option>
            <el-option label="右侧小图" value="右侧小图"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="toggleFormVisiable(false)">取消</el-button>
          <el-button type="primary" @click="submit"> 确定 </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped></style>
