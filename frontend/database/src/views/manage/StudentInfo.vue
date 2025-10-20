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
          <el-button type="success" icon="el-icon-upload" @click="openImportDialog"
            >批量导入</el-button
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

  <!-- 批量导入对话框 -->
  <el-dialog
    title="批量导入学生数据"
    v-model="importDialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
    width="600px"
  >
    <div class="import-content">
      <!-- 文件上传区域 -->
      <div class="upload-section">
        <el-upload
          ref="uploadRef"
          class="upload-demo"
          drag
          :auto-upload="false"
          :on-change="handleFileChange"
          :on-remove="handleFileRemove"
          :limit="1"
          accept=".xlsx,.xls,.csv"
          :file-list="fileList"
        >
          <el-icon class="el-icon--upload"><i-ep-upload-filled /></el-icon>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <template #tip>
            <div class="el-upload__tip">
              支持 .xlsx、.xls、.csv 格式文件，文件大小不超过 10MB<br />
              <strong>日期格式要求：</strong>请使用 yyyy-mm-dd 格式，如：2023-09-01
            </div>
          </template>
        </el-upload>
      </div>

      <!-- 模板下载 -->
      <div class="template-section">
        <el-divider content-position="left">模板下载</el-divider>
        <el-button type="primary" link @click="downloadTemplate">
          <el-icon><i-ep-download /></el-icon>
          下载导入模板
        </el-button>
        <span class="template-tip">请按照模板格式准备数据</span>
      </div>

      <!-- 数据预览 -->
      <div v-if="previewData.length > 0" class="preview-section">
        <el-divider content-position="left">数据预览</el-divider>
        <div class="preview-info">
          <span>共识别到 {{ previewData.length }} 条学生数据</span>
          <el-tag
            :type="validDataCount === previewData.length ? 'success' : 'warning'"
            size="small"
          >
            有效数据: {{ validDataCount }} 条
          </el-tag>
          <el-tag v-if="invalidDataCount > 0" type="danger" size="small">
            无效数据: {{ invalidDataCount }} 条
          </el-tag>
        </div>

        <el-table
          :data="previewData.slice(0, 10)"
          style="width: 100%; margin-top: 10px"
          max-height="300px"
          size="small"
        >
          <el-table-column prop="studentNumber" label="学号" width="120" />
          <el-table-column prop="studentName" label="姓名" width="100" />
          <el-table-column prop="studentGender" label="性别" width="60" />
          <el-table-column prop="studentAge" label="年龄" width="60" />
          <el-table-column prop="departmentName" label="专业" width="100" />
          <el-table-column prop="className" label="班级" width="80" />
          <el-table-column prop="regionName" label="属地" width="80" />
          <el-table-column label="状态" width="80">
            <template #default="scope">
              <el-tag :type="scope.row.isValid ? 'success' : 'danger'" size="small">
                {{ scope.row.isValid ? '有效' : '无效' }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>

        <div v-if="previewData.length > 10" class="preview-more">
          <span>仅显示前10条数据，点击导入查看完整结果</span>
        </div>
      </div>
    </div>

    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="closeImportDialog">取 消</el-button>
        <el-button
          type="primary"
          @click="importStudentData"
          :loading="importLoading"
          :disabled="validDataCount === 0"
        >
          确认导入 ({{ validDataCount }}条)
        </el-button>
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

// 批量导入相关状态
const importDialogVisible = ref(false)
const importLoading = ref(false)
const fileList = ref<File[]>([])
const previewData = ref<
  Array<{
    studentNumber: string
    studentName: string
    studentGender: string
    studentAge: string
    departmentName: string
    className: string
    regionName: string
    enrollmentDate: string
    studentStatus: string
    isValid: boolean
  }>
>([])
const validDataCount = ref(0)
const invalidDataCount = ref(0)
const uploadRef = ref()

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
    const response: ApiResponse = await request.delete(`/students/deleteByNumber/${number}`)
    if (response.code === '200') {
      console.log('删除学生成功:', number)
      // 重新获取学生数据
      getStudentsData(studentsSelectParam.value)
    } else if (response.msg) {
      console.error('删除学生失败:', response.msg)
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

// 批量导入相关方法
const openImportDialog = () => {
  importDialogVisible.value = true
  resetImportData()
}

const closeImportDialog = () => {
  importDialogVisible.value = false
  resetImportData()
}

const resetImportData = () => {
  fileList.value = []
  previewData.value = []
  validDataCount.value = 0
  invalidDataCount.value = 0
  if (uploadRef.value) {
    uploadRef.value.clearFiles()
  }
}

// 日期格式化函数，确保格式为 yyyy-MM-dd
const formatDateForBackend = (dateStr: string): string => {
  if (!dateStr) {
    return new Date().toISOString().split('T')[0] // 默认当前日期
  }

  try {
    // 处理常见的日期格式
    let normalizedDate = dateStr.trim()

    // 替换常见的分隔符为标准格式
    normalizedDate = normalizedDate.replace(/[\/\-\.]/g, '-')

    // 处理不同的日期格式
    const parts = normalizedDate.split('-')

    if (parts.length === 3) {
      let year = parts[0]
      let month = parts[1]
      let day = parts[2]

      // 处理年份格式（如果是2位数年份，转换为4位数）
      if (year.length === 2) {
        const currentYear = new Date().getFullYear()
        const century = Math.floor(currentYear / 100) * 100
        year = String(century + parseInt(year))
      }

      // 确保月份和日期是两位数
      month = month.padStart(2, '0')
      day = day.padStart(2, '0')

      const formattedDate = `${year}-${month}-${day}`

      // 验证日期是否有效
      const dateObj = new Date(formattedDate)
      if (
        dateObj.getFullYear() == parseInt(year) &&
        dateObj.getMonth() == parseInt(month) - 1 &&
        dateObj.getDate() == parseInt(day)
      ) {
        return formattedDate
      }
    }

    // 如果无法解析，尝试直接创建Date对象
    const dateObj = new Date(dateStr)
    if (!isNaN(dateObj.getTime())) {
      return dateObj.toISOString().split('T')[0]
    }

    // 如果都失败了，返回当前日期
    return new Date().toISOString().split('T')[0]
  } catch (error) {
    console.warn('日期格式化失败，使用当前日期:', dateStr, error)
    return new Date().toISOString().split('T')[0]
  }
}

// 处理文件变化
const handleFileChange = (file: { raw: File }) => {
  if (file.raw) {
    parseExcelFile(file.raw)
  }
}

// 处理文件移除
const handleFileRemove = () => {
  resetImportData()
}

// 解析Excel文件
const parseExcelFile = (file: File) => {
  const reader = new FileReader()

  reader.onload = (e) => {
    try {
      const data = e.target?.result
      if (!data) return

      // 简化版本的CSV解析，实际项目中建议使用专业的Excel解析库
      if (file.name.endsWith('.csv')) {
        const text = data as string
        const lines = text.split('\n')

        const students: Array<{
          studentNumber: string
          studentName: string
          studentGender: string
          studentAge: string
          departmentName: string
          className: string
          regionName: string
          enrollmentDate: string
          studentStatus: string
          isValid: boolean
        }> = []
        for (let i = 1; i < lines.length; i++) {
          if (lines[i].trim()) {
            const values = lines[i].split(',').map((v) => v.trim())
            const student = {
              studentNumber: values[0] || '',
              studentName: values[1] || '',
              studentGender: values[2] || '',
              studentAge: values[3] || '',
              departmentName: values[4] || '',
              className: values[5] || '',
              regionName: values[6] || '',
              enrollmentDate: formatDateForBackend(values[7] || ''),
              studentStatus: values[8] || '在读',
              isValid: true,
            }

            // 简单验证
            if (!student.studentNumber || !student.studentName) {
              student.isValid = false
            }

            // 验证年龄是否为有效数字
            if (
              student.studentAge &&
              (isNaN(Number(student.studentAge)) ||
                Number(student.studentAge) < 0 ||
                Number(student.studentAge) > 150)
            ) {
              student.isValid = false
            }

            // 验证日期格式（检查是否成功格式化）
            if (
              student.enrollmentDate === new Date().toISOString().split('T')[0] &&
              values[7] &&
              values[7].trim() !== ''
            ) {
              // 如果原始输入不为空但被格式化为当前日期，说明格式化失败
              console.warn('日期格式可能有问题:', values[7])
            }

            students.push(student)
          }
        }

        previewData.value = students
        updateValidationCounts()
      } else {
        ElMessage.error('暂时只支持CSV格式文件，Excel文件解析需要安装xlsx库')
      }
    } catch (error) {
      console.error('文件解析失败:', error)
      ElMessage.error('文件解析失败，请检查文件格式')
    }
  }

  if (file.name.endsWith('.csv')) {
    reader.readAsText(file, 'UTF-8')
  } else {
    reader.readAsArrayBuffer(file)
  }
}

// 更新验证统计
const updateValidationCounts = () => {
  validDataCount.value = previewData.value.filter(
    (item: { isValid: boolean }) => item.isValid,
  ).length
  invalidDataCount.value = previewData.value.filter(
    (item: { isValid: boolean }) => !item.isValid,
  ).length
}

// 下载模板
const downloadTemplate = () => {
  const templateData = [
    ['学号', '姓名', '性别', '年龄', '专业名称', '班级名称', '属地名称', '入学日期', '状态'],
    [
      'S20230001',
      '张三',
      '男',
      '20',
      '计算机科学与技术',
      '计科1班',
      '浙江省',
      '2023-09-01',
      '在读',
    ],
    ['S20230002', '李四', '女', '19', '软件工程', '软工1班', '江苏省', '2023-09-01', '在读'],
  ]

  const csvContent = templateData.map((row) => row.join(',')).join('\n')

  const blob = new Blob(['\uFEFF' + csvContent], { type: 'text/csv;charset=utf-8;' })
  const link = document.createElement('a')
  const url = URL.createObjectURL(blob)
  link.setAttribute('href', url)
  link.setAttribute('download', '学生数据导入模板.csv')
  link.style.visibility = 'hidden'
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)

  ElMessage.success('模板下载成功')
}

// 导入学生数据
const importStudentData = async () => {
  if (validDataCount.value === 0) {
    ElMessage.warning('没有有效的学生数据可以导入')
    return
  }

  try {
    importLoading.value = true

    const validStudents = previewData.value.filter((item: { isValid: boolean }) => item.isValid)

    // 转换数据格式以匹配后端Students实体
    const studentsData = await Promise.all(
      validStudents.map(
        async (student: {
          studentNumber: string
          studentName: string
          studentGender: string
          studentAge: string
          departmentName: string
          className: string
          regionName: string
          enrollmentDate: string
          studentStatus: string
        }) => {
          // 查找regionId
          const region = regionsData.value?.find(
            (r: { regionId: number; regionName: string }) => r.regionName === student.regionName,
          )
          const regionId = region?.regionId || 1 // 默认regionId

          // 查找departmentId
          const department = departmentsData.value?.find(
            (d: { departmentId: number; departmentName: string }) =>
              d.departmentName === student.departmentName,
          )
          const departmentId = department?.departmentId

          // 如果有departmentId，获取该专业的班级信息
          let classId = 1 // 默认classId
          if (departmentId) {
            try {
              const classResponse = await request.get(
                `/classes/selectByDepartmentId/${departmentId}`,
              )
              const classes = classResponse.data || []
              const classInfo = classes.find(
                (c: { classId: number; className: string }) => c.className === student.className,
              )
              if (classInfo) {
                classId = classInfo.classId
              }
            } catch (error) {
              console.warn('获取班级信息失败，使用默认值:', error)
            }
          }

          return {
            studentNumber: student.studentNumber,
            studentName: student.studentName,
            studentGender: student.studentGender,
            studentAge: parseInt(student.studentAge) || 0,
            studentTotalCredits: 0, // 新生默认学分为0
            enrollmentDate: formatDateForBackend(student.enrollmentDate),
            studentStatus: student.studentStatus || '在读',
            regionId: regionId,
            classId: classId,
          }
        },
      ),
    )

    console.log('准备导入的学生数据:', studentsData)

    // 调用批量导入API，直接传递学生数组
    const response: ApiResponse = await request.post('/students/batchImport', studentsData)

    if (response.code === '200') {
      ElMessage.success(`成功导入 ${studentsData.length} 条学生数据`)
      closeImportDialog()

      // 刷新学生列表
      getStudentsData(studentsSelectParam.value)
    } else {
      ElMessage.error(response.msg || '导入失败')
    }
  } catch (error) {
    console.error('导入学生数据失败:', error)
    ElMessage.error('导入失败，请稍后再试')
  } finally {
    importLoading.value = false
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

/* 批量导入对话框样式 */
.import-content {
  padding: 10px 0;
}

.upload-section {
  margin-bottom: 20px;
}

.upload-demo :deep(.el-upload) {
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
}

.upload-demo :deep(.el-upload:hover) {
  border-color: #667eea;
}

.upload-demo :deep(.el-upload-dragger) {
  background-color: #fafafa;
  border: none;
  border-radius: 8px;
  width: 100%;
  height: 120px;
  text-align: center;
  padding: 20px;
}

.upload-demo :deep(.el-icon--upload) {
  font-size: 40px;
  color: #c0c4cc;
  margin: 10px 0;
}

.upload-demo :deep(.el-upload__text) {
  color: #606266;
  font-size: 14px;
  text-align: center;
}

.upload-demo :deep(.el-upload__text em) {
  color: #667eea;
  font-style: normal;
}

.upload-demo :deep(.el-upload__tip) {
  font-size: 12px;
  color: #909399;
  margin-top: 7px;
}

.template-section {
  margin-bottom: 20px;
}

.template-tip {
  margin-left: 10px;
  font-size: 12px;
  color: #909399;
}

.preview-section {
  margin-top: 20px;
}

.preview-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  font-size: 14px;
  color: #606266;
}

.preview-more {
  text-align: center;
  margin-top: 10px;
  font-size: 12px;
  color: #909399;
}

/* 对话框内表格样式优化 */
.preview-section :deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

.preview-section :deep(.el-table th) {
  background-color: #f5f7fa;
  font-weight: 600;
}

.preview-section :deep(.el-table td) {
  padding: 8px 0;
}
</style>
