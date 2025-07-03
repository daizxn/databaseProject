<template>
  <el-container class="layout-container">
    <el-container class="select" ref="selectFormRef">
      <el-form class="select-form" label-width="80px" :model="teacherSelectParam">
        <el-form-item label="工号">
          <el-input v-model="teacherSelectParam.teacherNumber"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="teacherSelectParam.teacherName"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="teacherSelectParam.teacherPhone"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="teacherSelectParam.teacherStatus"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="在职" value="在职"></el-option>
            <el-option label="离职" value="离职"></el-option>
            <el-option label="退休" value="退休"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称">
          <!-- <el-input v-model="teacherSelectParam.teacherTitle"></el-input> -->
          <el-select
            v-model="teacherSelectParam.teacherTitle"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="助教" value="助教"></el-option>
            <el-option label="讲师" value="讲师"></el-option>
            <el-option label="副教授" value="副教授"></el-option>
            <el-option label="教授" value="教授"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期">
          <el-date-picker
            v-model="teacherSelectParam.hireDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="teacherSelectParam.teacherAge"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="teacherSelectParam.teacherEmail"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getTeacherData(teacherSelectParam, pageNum, pageSize)"
            >查询</el-button
          >
          <el-button type="primary" icon="el-icon-search" @click="updateRow()">新增</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-container class="data">
      <el-table :data="teacherData" style="width: 100%">
        <el-table-column prop="teacherNumber" label="工号"></el-table-column>
        <el-table-column prop="teacherName" label="姓名"></el-table-column>
        <el-table-column prop="teacherPhone" label="电话"></el-table-column>
        <el-table-column prop="teacherStatus" label="状态"></el-table-column>
        <el-table-column prop="teacherTitle" label="职称"></el-table-column>
        <el-table-column prop="hireDate" label="入职日期"></el-table-column>
        <el-table-column prop="teacherAge" label="年龄"></el-table-column>
        <el-table-column prop="teacherEmail" label="邮箱"></el-table-column>
        <el-table-column fixed="right" label="Operations" min-width="120">
          <template #default="scope">
            <el-button text size="default" @click.prevent="updateRow(scope.row)">编辑 </el-button>
            <el-button
              text
              size="default"
              style="color: red"
              @click.prevent="deleteRow(scope.row.teacherNumber)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="prev, pager, next"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-container>
  </el-container>

  <el-dialog
    title="教师信息"
    v-model="dialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
  >
    <el-form
      class="select-form"
      label-width="80px"
      :label-position="'right'"
      :model="teacherUpdateParam"
      ref="updateFormRef"
      :rules="updateFormRules"
    >
      <el-form-item label="工号" prop="teacherNumber">
        <el-input v-model="teacherUpdateParam.teacherNumber" :disabled="!addFlag"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="teacherName">
        <el-input v-model="teacherUpdateParam.teacherName"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="teacherPhone">
        <el-input v-model="teacherUpdateParam.teacherPhone"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="teacherStatus">
        <el-select
          v-model="teacherUpdateParam.teacherStatus"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
        >
          <el-option label="在职" value="在职"></el-option>
          <el-option label="离职" value="离职"></el-option>
          <el-option label="退休" value="退休"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职称" prop="teacherTitle">
        <!-- <el-input v-model="teacherSelectParam.teacherTitle"></el-input> -->
        <el-select
          v-model="teacherUpdateParam.teacherTitle"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
        >
          <el-option label="助教" value="助教"></el-option>
          <el-option label="讲师" value="讲师"></el-option>
          <el-option label="副教授" value="副教授"></el-option>
          <el-option label="教授" value="教授"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期" prop="hireDate">
        <el-date-picker
          v-model="teacherUpdateParam.hireDate"
          type="date"
          placeholder="选择日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="年龄" prop="teacherAge">
        <el-input v-model.number="teacherUpdateParam.teacherAge"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="teacherEmail">
        <el-input v-model="teacherUpdateParam.teacherEmail"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save(updateFormRef)">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import request from '@/utils/request'
import type { ApiResponse } from '@/utils/request'

// import { ElMessageBox } from 'element-plus'
import { ElMessage, type FormInstance } from 'element-plus'

const teacherData = ref([])

const selectFormRef = ref<FormInstance>()
const teacherSelectParam = ref({})

const updateFormRef = ref<FormInstance>()
const teacherUpdateParam = ref({})
const updateFormRules = {
  teacherNumber: [{ required: true, message: '工号不能为空', trigger: 'blur' }],
  teacherName: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  teacherPhone: [
    { required: true, message: '电话不能为空', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
  ],
  teacherStatus: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
  teacherTitle: [{ required: true, message: '职称不能为空', trigger: 'blur' }],
  hireDate: [{ required: true, message: '入职日期不能为空', trigger: 'blur' }],
  teacherAge: [
    { required: true, message: '年龄不能为空', trigger: 'blur' },
    { type: 'number', message: '请输入正确的年龄', trigger: 'blur' },
  ],
  teacherEmail: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }],
}

const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const dialogVisible = ref(false)
const addFlag = ref(false)

const getTeacherData = async (
  teacherParam: Record<string, unknown>,
  pageNum: number,
  pageSize: number,
) => {
  const response = await request.get('/teachers/selectByPage', {
    params: {
      ...teacherParam,
      pageNum,
      pageSize,
    },
  })
  if (response.data === null) {
    teacherData.value = []
    total.value = 0
  } else {
    teacherData.value = response.data.records
    total.value = response.data?.total
  }
}

const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getTeacherData(teacherSelectParam.value, pageNum.value, pageSize.value)
}

const updateRow = (row?: Record<string, unknown>) => {
  if (row) {
    teacherUpdateParam.value = { ...row }
    addFlag.value = false // 设置为不可编辑状态
  } else {
    teacherUpdateParam.value = {}
    addFlag.value = true // 设置为可编辑状态
  }
  dialogVisible.value = true
}

const deleteRow = async (teacherNumber: string) => {
  const response: ApiResponse = await request.delete(`/teachers/deleteByNumber/${teacherNumber}`)
  if (response.code === '200') {
    ElMessage({
      message: '删除成功',
      type: 'success',
    })
    getTeacherData(teacherSelectParam.value, 1, 10)
  } else {
    ElMessage({
      message: '删除失败，请稍后再试',
      type: 'error',
    })
  }
}

const save = async (formRef: FormInstance) => {
  if (!formRef) return
  await formRef.validate()
  let response: ApiResponse

  if (addFlag.value) response = await request.post('/teachers/add', teacherUpdateParam.value)
  else response = await request.put('/teachers/updateByNumber', teacherUpdateParam.value)

  if (response.code === '200') {
    ElMessage({
      message: '操作成功',
      type: 'success',
    })
    // 重置表单
    formRef.value?.resetFields()
    // 重置查询参数
    addFlag.value = false
    teacherUpdateParam.value = {}
    dialogVisible.value = false
    getTeacherData(teacherSelectParam.value, pageNum.value, pageSize.value)
  } else if (response.code === '5022') {
    // 工号已存在
    ElMessageBox.alert('工号已存在，请重新输入', '提示', {
      confirmButtonText: '确定',
      type: 'warning',
    })
  } else {
    ElMessageBox.alert('操作失败，请稍后再试', '错误', {
      confirmButtonText: '确定',
      type: 'error',
    })
  }
}

onMounted(() => {
  dialogVisible.value = false
  getTeacherData(teacherSelectParam.value, pageNum.value, pageSize.value)
})
</script>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: column;
}

.select {
  padding: 20px;
}

.select .select-form {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.select .select-form .el-form-item {
  min-width: 250px;
  flex: 0 0 auto;
}

.data {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}
</style>
