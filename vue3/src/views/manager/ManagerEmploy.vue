<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElButton, ElDialog, ElMessage, ElMessageBox } from 'element-plus'
import { OPTIONS } from '@/utils/Contants'
import {
  addEmployAPI,
  selectByPageAPI,
  updateEmployAPI,
  deleteBatch,
  deleteById
} from '@/api/employ'
import { selectAllAPI } from '@/api/industry'

onMounted(() => {
  // 加载表格数据
  load()
  // 加载行业选择器数据
  loadIndustryOptions()
})
/**table 数据和方法*/
const tableData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
// 搜索关键字
const name = ref(null)
// 筛选关键字
const status = ref(null)
const load = () => {
  selectByPageAPI(pageNum.value, pageSize.value, name.value, status.value).then((res) => {
    if (res.code === '200') {
      tableData.value = res.data?.list || []
      total.value = res.data?.total
    }
  })
}
// 重置搜索关键字
const replace = () => {
  name.value = null
  status.value = null
  load()
}
/**dialog数据和方法*/
// 表单数据和rules
const employForm = ref(null)
// 图片访问地址
const baseUrl = import.meta.env.VITE_BASE_URL
// 图片上传列表
const fileList = ref([])
const formData = ref({})
const rules = reactive({
  username: [{ required: true, message: '企业名称不得为空', trigger: 'blur' }],
  industryId: [{ required: true, message: '请选择行业', trigger: 'blur' }]
})
// 获取行业选择器数据
const loadIndustryOptions = () => {
  selectAllAPI().then((res) => {
    if (res.code === '200') {
      res.data.forEach((industry) => {
        optionData.industryOptions.push({
          value: industry.id,
          label: industry.name
        })
      })
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 选择器数据
const optionData = reactive({
  industryOptions: [],
  scaleOptions: OPTIONS.EMPLOY.SCALE,
  stageOptions: OPTIONS.EMPLOY.STAGE,
  statusOptions: OPTIONS.EMPLOY.STATUS
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
  formData.value.avatar = res.data
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
  employForm.value.validate(async (valid) => {
    if (valid) {
      formData.value.id ? update() : add()
    }
  })
}
const add = () => {
  addEmployAPI(formData.value).then((res) => {
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
  updateEmployAPI(formData.value).then((res) => {
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
  <div class="manager-employ">
    <div class="card">
      <el-input
        v-model="name"
        prefix-icon="Search"
        placeholder="请输入名称查询"
        style="width: 400px; margin-right: 15px"
        @keyup.enter="load"
      ></el-input>
      <el-select
        v-model="status"
        style="width: 400px; margin-right: 15px"
        placeholder="请选择审核状态"
      >
        <el-option
          v-for="option in optionData.statusOptions"
          :key="option.value"
          :label="option.label"
          :value="option.value"
        ></el-option>
      </el-select>
      <el-button type="primary" size="default" @click="load" plain>查询</el-button>
      <el-button type="warning" size="default" @click="replace" plain>重置</el-button>
    </div>
    <div class="card">
      <el-button @click="handeleAdd" plain type="primary">添加</el-button>
      <el-button @click="handleDelBatch" plain type="danger">批量删除</el-button>
    </div>
    <div class="card">
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="企业账号" />
        <el-table-column prop="name" label="企业名称" />
        <el-table-column prop="avatar" label="企业头像">
          <template #default="scope">
            <el-image
              style="width: 40px; height: 40px; border-radius: 50%"
              :preview-src-list="[scope.row.avatar]"
              :preview-teleported="true"
              :src="scope.row.avatar"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="city" label="所在城市" />
        <el-table-column prop="address" label="详细地址" show-overflow-tooltip />
        <el-table-column prop="industryName" label="所属行业" />
        <el-table-column prop="scale" label="公司规模" />
        <el-table-column prop="stage" label="融资阶段" />
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === '待审核'" type="warning">{{
              scope.row.status
            }}</el-tag>
            <el-tag v-if="scope.row.status === '审核通过'" type="success">{{
              scope.row.status
            }}</el-tag>
            <el-tag v-if="scope.row.status === '审核不通过'" type="danger">{{
              scope.row.status
            }}</el-tag>
          </template></el-table-column
        >

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
        ref="employForm"
        :model="formData"
        label-width="80px"
        style="padding: 20px"
        :rules="rules"
      >
        <el-form-item label="企业账号" prop="username">
          <el-input v-model="formData.username" placeholder="请输入企业账号" />
        </el-form-item>
        <el-form-item label="企业名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="公司logo" prop="avatar">
          <el-upload
            :before-upload="beforeUpload"
            :action="baseUrl + '/files/upload'"
            :on-success="handleUploadSuccess"
            list-type="picture"
            :file-list="fileList"
          >
            <template #trigger><el-button type="primary">上传logo</el-button></template>
          </el-upload>
        </el-form-item>
        <el-form-item label="所在城市" prop="city">
          <el-input v-model="formData.city" placeholder="请输入所在城市" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="所属行业" prop="industryId">
          <el-select v-model="formData.industryId" placeholder="请选择所属行业">
            <el-option
              v-for="item in optionData.industryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司规模" prop="scale">
          <el-select v-model="formData.scale" placeholder="请选择公司规模">
            <el-option
              v-for="item in optionData.scaleOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="融资阶段" prop="stage">
          <el-select v-model="formData.stage" placeholder="请选择融资阶段">
            <el-option
              v-for="item in optionData.stageOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择审核状态">
            <el-option
              v-for="item in optionData.statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
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
