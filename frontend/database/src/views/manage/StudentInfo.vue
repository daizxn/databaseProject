<template>
  <el-container class="layout-container">
    <el-container class="select">
      <el-form
        class="select-form"
        :model="studentsSelectParam"
        :rules="selectFormRules"
        ref="selectFormRef"
      >
        <el-form-item label="学号">
          <el-input v-model="studentsSelectParam.studentNumber" placeholder="请输入学号"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="studentsSelectParam.studentName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="studentAge">
          <el-input v-model="studentsSelectParam.studentAge" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="studentsSelectParam.studentGender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-select
            v-model="studentsSelectParam.departmentName"
            :value-on-clear="null"
            clearable
            placeholder="请选择专业"
          >
            <el-option
              v-for="department in departmentsData"
              :key="department.departmentId"
              :label="department.departmentName"
              :value="department.departmentName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="studentsSelectParam.classesId" placeholder="请输入班级"></el-input>
        </el-form-item>
        <el-form-item label="属地">
          <el-select
            v-model="studentsSelectParam.regionName"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
            placeholder="请选择属地"
          >
            <el-option
              v-for="region in regionsData"
              :key="region.regionName"
              :label="region.regionName"
              :value="region.regionName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学分" prop="studentTotalCredits">
          <el-input
            v-model="studentsSelectParam.studentTotalCredits"
            placeholder="请输入学分"
          ></el-input>
        </el-form-item>
        <el-form-item label="入学日期">
          <el-date-picker
            v-model="studentsSelectParam.enrollmentDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="studentsSelectParam.studentStatus" placeholder="请选择状态">
            <el-option label="在读" value="在读"></el-option>
            <el-option label="休学" value="休学"></el-option>
            <el-option label="退学" value="退学"></el-option>
            <el-option label="毕业" value="毕业"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getStudentsData(studentsSelectParam)"
            >查询</el-button
          >
          <el-button type="primary" icon="el-icon-search" @click="(updateRow(), (addFlag = true))"
            >新增</el-button
          >
        </el-form-item>
      </el-form>
    </el-container>
    <el-container class="data">
      <el-table :data="studentsData" style="width: 100%">
        <el-table-column prop="studentNumber" label="学号"></el-table-column>
        <el-table-column prop="studentName" label="姓名"></el-table-column>
        <el-table-column prop="studentAge" label="年龄"></el-table-column>
        <el-table-column prop="studentGender" label="性别"></el-table-column>
        <el-table-column prop="departmentName" label="专业"></el-table-column>
        <el-table-column prop="className" label="班级"></el-table-column>
        <el-table-column prop="regionName" label="属地"></el-table-column>
        <el-table-column prop="studentTotalCredits" label="学分"></el-table-column>
        <el-table-column prop="enrollmentDate" label="入学日期"></el-table-column>
        <el-table-column prop="studentStatus" label="状态"></el-table-column>
        <el-table-column prop="weightedGpa" label="绩点"></el-table-column>
        <el-table-column prop="departmentGpaRank" label="专业内排名">
          <template #default="scope">
            {{ scope.row.departmentGpaRank + '/' + scope.row.departmentTotalStudents }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Operations" min-width="200">
          <template #default="scope">
            <el-button text size="default" @click.prevent="updateRow(scope.row)">编辑 </el-button>
            <el-button text size="default" @click.prevent="deleteRow(scope.row.studentNumber)"
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

  <el-dialog title="学生" v-model="dialogVisible" :close-on-click-modal="false" destroy-on-close>
    <el-form
      class="select-form"
      :rules="updateFormRules"
      :model="studentsUpdateParam"
      ref="updateFormRef"
    >
      <el-form-item label="学号" prop="studentNumber">
        <el-input
          v-model="studentsUpdateParam.studentNumber"
          placeholder="请输入学号"
          :disabled="!addFlag"
        ></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="studentName">
        <el-input v-model="studentsUpdateParam.studentName" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="studentAge">
        <el-input v-model="studentsUpdateParam.studentAge" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="studentGender">
        <el-select v-model="studentsUpdateParam.studentGender" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业" prop="departmentId">
        <!-- <el-input placeholder="请输入专业"></el-input> -->
        <el-select
          v-model="departmentId"
          :value-on-clear="null"
          clearable
          placeholder="请选择专业"
          @change="getClassesData(departmentId)"
        >
          <el-option
            v-for="department in departmentsData"
            :key="department.departmentId"
            :label="department.departmentName"
            :value="department.departmentId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="classId">
        <el-select
          v-model="studentsUpdateParam.classId"
          :value-on-clear="null"
          clearable
          placeholder="请选择班级"
        >
          <el-option
            v-for="classes in classesData"
            :key="classes.classId"
            :label="classes.className"
            :value="classes.classId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="属地" prop="regionId">
        <el-select
          v-model="studentsUpdateParam.regionId"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
          placeholder="请选择属地"
        >
          <el-option
            v-for="region in regionsData"
            :key="region.regionId"
            :label="region.regionName"
            :value="region.regionId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="!addFlag" label="学分" prop="studentTotalCredits">
        <el-input
          v-model="studentsUpdateParam.studentTotalCredits"
          placeholder="请输入学分"
          disabled
        ></el-input>
      </el-form-item>
      <el-form-item label="入学日期" prop="enrollmentDate">
        <el-date-picker
          v-model="studentsUpdateParam.enrollmentDate"
          type="date"
          placeholder="选择日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="studentStatus">
        <el-select v-model="studentsUpdateParam.studentStatus" placeholder="请选择状态">
          <el-option label="在读" value="在读"></el-option>
          <el-option label="休学" value="休学"></el-option>
          <el-option label="退学" value="退学"></el-option>
          <el-option label="毕业" value="毕业"></el-option>
        </el-select>
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
// 假设你有一个 request 实例（如 axios），需要先导入
import request from '@/utils/request'
import type { ApiResponse } from '@/utils/request'

import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'

interface STUDENT_FULL_INFO {
  studentNumber: string
  studentName: string
  studentGender: string
  studentAge: number
  studentTotalCredits: number
  enrollmentDate: string
  studentStatus: string
  regionName: string
  className: string
  departmentName: string
}

interface STUDENT {
  studentNumber: string
  studentName: string
  studentGender: string
  studentAge: number
  studentTotalCredits: number
  enrollmentDate: string
  studentStatus: string
  regionId: number
  classesId: number
}

const regionsData = ref()
const departmentsData = ref()
const departmentId = ref() // 用于存储选中的专业ID
const classesData = ref()

const studentsData = ref<STUDENT_FULL_INFO[]>([])
const studentsSelectParam = ref<STUDENT_FULL_INFO>({})
const studentsUpdateParam = ref<STUDENT>({})

const dialogVisible = ref(false) // 控制对话框的显示与隐藏

const pageNum = ref(1) // 当前的页码
const pageSize = ref(10) // 每页显示的个数
const total = ref(0)

const addFlag = ref(false) // 控制表格行是否可编辑

const selectFormRef = ref<FormInstance>()

const selectFormRules = {
  studentNumber: [
    {
      required: true,
      message: '学号不能为空',
      trigger: 'blur',
    },
  ],
  studentName: [
    {
      required: true,
      message: '姓名不能为空',
      trigger: 'blur',
    },
  ],
  studentGender: [
    {
      required: true,
      message: '性别不能为空',
      trigger: 'blur',
    },
  ],
  studentAge: [
    {
      type: 'number',
      message: '年龄必须为数字',
      trigger: 'blur',
      transform: (value: unknown) => (value === '' ? undefined : Number(value)),
    },
    {
      validator: (_rule: unknown, value: number, callback: (error?: Error) => void) => {
        if (value && (value < 0 || value > 150)) {
          callback(new Error('年龄必须在 0 到 150 之间'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    },
  ],
  studentTotalCredits: [
    {
      type: 'number',
      message: '学分必须为数字',
      trigger: 'blur',
      transform: (value: unknown) => (value === '' ? undefined : Number(value)),
    },
  ],
}

const updateFormRef = ref<FormInstance>()

const updateFormRules = {
  studentNumber: [
    {
      required: true,
      message: '学号不能为空',
      trigger: 'blur',
    },
  ],
  studentName: [
    {
      required: true,
      message: '姓名不能为空',
      trigger: 'blur',
    },
  ],
  studentGender: [
    {
      required: true,
      message: '性别不能为空',
      trigger: 'blur',
    },
  ],
  studentAge: [
    {
      required: true,
      message: '年龄不能为空',
      trigger: 'blur',
    },
    {
      type: 'number',
      message: '年龄必须为数字',
      trigger: 'blur',
      transform: (value: unknown) => (value === '' ? undefined : Number(value)),
    },
    {
      validator: (_rule: unknown, value: number, callback: (error?: Error) => void) => {
        if (value && (value < 0 || value > 150)) {
          callback(new Error('年龄必须在 0 到 150 之间'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    },
  ],
  studentTotalCredits: [
    {
      type: 'number',
      message: '学分必须为数字',
      trigger: 'blur',
      transform: (value: unknown) => (value === '' ? undefined : Number(value)),
    },
  ],
  enrollmentDate: [
    {
      type: 'date',
      required: true,
      message: '入学日期不能为空',
      trigger: 'blur',
    },
  ],
  studentStatus: [
    {
      required: true,
      message: '状态不能为空',
      trigger: 'blur',
    },
  ],
  regionId: [
    {
      required: true,
      message: '属地不能为空',
      trigger: 'blur',
    },
  ],
  classId: [
    {
      required: true,
      message: '班级不能为空',
      trigger: 'blur',
    },
  ],
  // departmentId: [
  //   {
  //     required: true,
  //     message: '专业不能为空',
  //     trigger: 'blur',
  //   },
  // ],
}

const getRegionData = async () => {
  try {
    const response = await request.get('/regions/selectAll')
    regionsData.value = response.data
  } catch (error) {
    console.error('获取属地数据失败:', error)
  }
}

const getDepartmentData = async () => {
  try {
    const response = await request.get('/departments/selectAll')
    departmentsData.value = response.data
  } catch (error) {
    console.error('获取部门数据失败:', error)
  }
}

const getClassesData = async (departmentId: number) => {
  try {
    const response = await request.get('/classes/selectByDepartmentId/' + departmentId)
    classesData.value = response.data
  } catch (error) {
    console.error('获取班级数据失败:', error)
  }
}

const getStudentsData = async (studentParam: Record<string, unknown>) => {
  try {
    const response = await request.get('/students/selectByPage/FullInfo', {
      params: {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        ...studentParam, // 将学生参数展开到请求参数中
      },
    })
    studentsData.value = response.data.records
    console.log('获取学生数据成功:', studentsData.value)
    total.value = response.data?.total
  } catch (error) {
    console.error('获取学生数据失败:', error)
  }
}

const handleCurrentChange = (newPage: number) => {
  pageNum.value = newPage
  // 重新获取学生数据
  getStudentsData(studentsSelectParam.value)
}

const deleteRow = async (number: string) => {
  try {
    const response = await request.delete(`/students/deleteByNumber/${number}`)
    if (response.data.code === 200) {
      console.log('删除学生成功:', number)
      // 重新获取学生数据
      getStudentsData(studentsSelectParam.value)
    } else if (response.data.msg) {
      console.error('删除学生失败:', response.data.msg)
    }
  } catch (error) {
    console.error('删除学生失败:', error)
  }

  getStudentsData(studentsSelectParam.value)
}

const updateRow = (row: STUDENT_FULL_INFO | undefined) => {
  if (row) {
    studentsUpdateParam.value = { ...row }
    addFlag.value = false // 设置为不可编辑状态
  } else {
    studentsUpdateParam.value = {
      studentNumber: '',
      studentName: '',
      studentGender: '',
      studentAge: undefined,
      studentTotalCredits: undefined,
      enrollmentDate: '',
      studentStatus: '',
      regionName: '',
      className: '',
      departmentName: '',
    }
    addFlag.value = true // 设置为可编辑状态
  }
  dialogVisible.value = true
}

const save = async (formRef: FormInstance) => {
  if (!formRef) return
  await formRef.validate()
  let response: ApiResponse
  if (addFlag.value)
    response = await request.post('/students/add', studentsUpdateParam.value) //插入
  else response = await request.put('/teachers/updateByNumber', studentsUpdateParam.value) //删除

  if (response.code === '200') {
    ElMessage({
      message: '操作成功',
      type: 'success',
    })
    // 重置表单
    formRef.resetFields()
    // 重置查询参数
    addFlag.value = false
    studentsUpdateParam.value = {}
    studentsSelectParam.value = {}
    dialogVisible.value = false
    getStudentsData(studentsSelectParam.value)
  } else if (response.code === '5022') {
    ElMessageBox.alert('学号已存在', '提示', {
      confirmButtonText: '确定',
      type: 'warning',
    })
  }
}
onMounted(() => {
  addFlag.value = false
  getRegionData()
  getDepartmentData()
  getClassesData(-1)
  getStudentsData(studentsSelectParam.value)
})
</script>

<style scoped>
@import '@/styles/manage-common.css';
</style>
