<template>
  <el-container class="layout-container">
    <el-container class="select" ref="selectFormRef">
      <el-form class="select-form" label-width="80px" :model="courseSelectParam">
        <el-form-item label="课程编号">
          <el-input
            v-model="courseSelectParam.courseNumber"
            placeholder="请输入课程编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="courseSelectParam.courseName" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="学年">
          <el-input v-model="courseSelectParam.academicYear" placeholder="请输入学年"></el-input>
        </el-form-item>
        <el-form-item label="学期">
          <!-- <el-input v-model="courseSelectParam.semester" placeholder="请输入学期"></el-input> -->
          <el-select
            v-model="courseSelectParam.semester"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="1" value="1"></el-option>
            <el-option label="2" value="2"></el-option>
            <el-option label="3" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="courseSelectParam.departmentName" placeholder="请输入专业"></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="courseSelectParam.className" placeholder="请输入班级"></el-input>
        </el-form-item>
        <el-form-item label="课程状态">
          <!-- <el-input
            v-model="courseSelectParam.courseStatus"
            placeholder="请输入课程状态"
          ></el-input> -->
          <el-select
            v-model="courseSelectParam.courseStatus"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="开设" value="开设"></el-option>
            <el-option label="停开" value="停开"></el-option>
            <el-option label="暂停" value="暂停"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程类型">
          <!-- <el-input v-model="courseSelectParam.courseType" placeholder="请输入课程类型"></el-input> -->
          <el-select
            v-model="courseSelectParam.courseType"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="必修" value="必修"></el-option>
            <el-option label="选修" value="选修"></el-option>
            <el-option label="专业必修" value="专业必修"></el-option>
            <el-option label="专业选修" value="专业选修"></el-option>
            <el-option label="通识课" value="通识课"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核方式">
          <el-select
            v-model="courseSelectParam.courseExamType"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
          >
            <el-option label="考试" value="考试"></el-option>
            <el-option label="考察" value="考察"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="courseSelectParam.courseCredits" placeholder="请输入学分"></el-input>
        </el-form-item>
        <el-form-item label="学时">
          <el-input v-model="courseSelectParam.courseHours" placeholder="请输入学时"></el-input>
        </el-form-item>
        <el-form-item label="教师姓名">
          <el-input v-model="courseSelectParam.teacherName" placeholder="请输入教师姓名"></el-input>
        </el-form-item>
        <el-form-item label="教师编号">
          <el-input
            v-model="courseSelectParam.teacherNumber"
            placeholder="请输入教师编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="教师职称">
          <el-select
            v-model="courseSelectParam.teacherTitle"
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
        <el-form-item>
          <el-button @click="getCourseData(courseSelectParam, pageNum, pageSize)">查询</el-button>
          <el-button type="primary" @click="updateRow()">开设新课程</el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-container class="data">
      <el-table style="width: 100%" :data="courseInfoData">
        <el-table-column label="课程编号" prop="courseNumber"></el-table-column>
        <el-table-column label="课程名称" prop="courseName"></el-table-column>
        <el-table-column label="学年" prop="academicYear"></el-table-column>
        <el-table-column label="学期" prop="semester"></el-table-column>
        <el-table-column label="专业" prop="departmentName"></el-table-column>
        <el-table-column label="班级" prop="className"></el-table-column>
        <el-table-column label="课程状态" prop="courseStatus"></el-table-column>
        <el-table-column label="考核方式" prop="courseExamType"></el-table-column>
        <el-table-column label="课程类型" prop="courseType"></el-table-column>
        <el-table-column label="学分" prop="courseCredits"></el-table-column>
        <el-table-column label="学时" prop="courseHours"></el-table-column>
        <el-table-column label="教师姓名" prop="teacherName"></el-table-column>
        <el-table-column label="教师编号" prop="teacherNumber"></el-table-column>
        <el-table-column label="教师职称" prop="teacherTitle"></el-table-column>
        <el-table-column fixed="right" label="Operations" min-width="120">
          <template #default="scope">
            <el-button text size="default" @click.prevent="updateRow(scope.row)">编辑 </el-button>
            <el-button
              text
              size="default"
              style="color: red"
              @click.prevent="deleteRow(scope.row.courseNumber)"
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
    title="课程信息"
    v-model="dialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
  >
    <el-form
      class="select-form"
      label-width="80px"
      :label-position="'right'"
      :model="courseUpdateParam"
      ref="updateFormRef"
      :rules="updateFormRules"
    >
      <el-form-item label="课程编号" prop="courseNumber">
        <el-input
          v-model="courseUpdateParam.courseNumber"
          placeholder="请输入课程编号"
          :disabled="!addFlag"
        ></el-input>
      </el-form-item>
      <el-form-item label="课程名称" prop="courseName">
        <el-input v-model="courseUpdateParam.courseName" placeholder="请输入课程名称"></el-input>
      </el-form-item>
      <el-form-item label="学年" prop="academicYear">
        <el-select
          v-model="courseUpdateParam.academicYear"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
          :disabled="!addFlag"
        >
          <el-option
            v-for="year in academicYear"
            :key="year"
            :label="year"
            :value="year"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学期" prop="semester">
        <!-- <el-input v-model="courseSelectParam.semester" placeholder="请输入学期"></el-input> -->
        <el-select
          v-model="courseUpdateParam.semester"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
          :disabled="!addFlag"
        >
          <el-option label="1" value="1"></el-option>
          <el-option label="2" value="2"></el-option>
          <el-option label="3" value="3"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="专业">
        <el-input v-model="courseSelectParam.departmentName" placeholder="请输入专业"></el-input>
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="courseSelectParam.className" placeholder="请输入班级"></el-input>
      </el-form-item> -->
      <el-form-item label="班级" prop="classId">
        <el-cascader
          v-model="courseUpdateParam.classId"
          :options="classesData"
          placeholder="请选择班级"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="课程状态" prop="courseStatus">
        <el-select
          v-model="courseUpdateParam.courseStatus"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
        >
          <el-option label="开设" value="开设"></el-option>
          <el-option label="停开" value="停开"></el-option>
          <el-option label="暂停" value="暂停"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程类型" prop="courseType">
        <el-select
          v-model="courseUpdateParam.courseType"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
        >
          <el-option label="必修" value="必修"></el-option>
          <el-option label="选修" value="选修"></el-option>
          <el-option label="专业必修" value="专业必修"></el-option>
          <el-option label="专业选修" value="专业选修"></el-option>
          <el-option label="通识课" value="通识课"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考核方式">
        <el-select
          v-model="courseUpdateParam.courseExamType"
          :empty-values="[null, undefined]"
          :value-on-clear="null"
          clearable
        >
          <el-option label="考试" value="考试"></el-option>
          <el-option label="考察" value="考察"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学分" prop="courseCredits">
        <el-input
          v-model.number="courseUpdateParam.courseCredits"
          placeholder="请输入学分"
        ></el-input>
      </el-form-item>
      <el-form-item label="学时" prop="courseHours">
        <el-input
          v-model.number="courseUpdateParam.courseHours"
          placeholder="请输入学时"
        ></el-input>
      </el-form-item>
      <el-form-item label="教师姓名" prop="teacherName">
        <el-select
          v-model="courseUpdateParam.teacherNumber"
          :options="teacherData"
          placeholder="请选择教师"
          clearable
          :empty-values="[null, undefined]"
          :value-on-clear="null"
        >
          <el-option
            v-for="teacher in teacherData"
            :key="teacher.teacherNumber"
            :label="teacher.teacherName"
            :value="teacher.teacherNumber"
          ></el-option>
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
import request from '@/utils/request'
import type { ApiResponse } from '@/utils/request'

// import { ElMessageBox } from 'element-plus'
import { ElMessage, type FormInstance } from 'element-plus'

const courseInfoData = ref([])
const teacherData = ref([])
const classesData = ref([])

const courseSelectParam = ref({})
const courseUpdateParam = ref({})
const updateFormRef = ref<FormInstance>()

const updateFormRules = {
  courseNumber: [{ required: true, message: '课程编号不能为空', trigger: 'blur' }],
  courseName: [{ required: true, message: '课程名称不能为空', trigger: 'blur' }],
  academicYear: [{ required: true, message: '学年不能为空', trigger: 'blur' }],
  semester: [{ required: true, message: '学期不能为空', trigger: 'blur' }],
  classId: [{ required: true, message: '班级不能为空', trigger: 'change' }],
  courseStatus: [{ required: true, message: '课程状态不能为空', trigger: 'change' }],
  courseType: [{ required: true, message: '课程类型不能为空', trigger: 'change' }],
  courseCredits: [
    { required: true, message: '学分不能为空', trigger: 'blur' },
    { type: 'number', message: '请输入正确的学分', trigger: 'blur' },
    { type: 'min', value: 0, message: '学分不能小于0', trigger: 'blur' },
    { type: 'max', value: 10, message: '学分不能大于10', trigger: 'blur' },
  ],
  courseHours: [
    { required: true, message: '学时不能为空', trigger: 'blur' },
    { type: 'number', message: '请输入正确的学时', trigger: 'blur' },
    { type: 'min', value: 0, message: '学时不能小于0', trigger: 'blur' },
    { type: 'max', value: 200, message: '学时不能大于200', trigger: 'blur' },
  ],
  teacherNumber: [{ required: true, message: '教师编号不能为空', trigger: 'change' }],
}

const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const academicYear = ref(['2020/2021', '2021/2022', '2022/2023', '2023/2024', '2024/2025'])

const dialogVisible = ref(false)
const addFlag = ref(false)

const getCourseData = async (
  courseParam: Record<string, unknown>,
  pageNum: number,
  pageSize: number,
) => {
  const response = await request.get('/courses/selectByPage/FullInfo', {
    params: {
      pageNum: pageNum,
      pageSize: pageSize,
      ...courseParam,
    },
  })
  if (response.data === null) {
    courseInfoData.value = []
    total.value = 0
  } else {
    courseInfoData.value = response.data.records
    total.value = response.data.total
  }
}

const getTeacherData = async () => {
  const response = await request.get('/teachers/selectTeacherList')
  teacherData.value = response.data
}

const getClassesData = async () => {
  const response = await request.get('/classes/selectClassesListGroupByDepartment')
  classesData.value = response.data
}

const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getCourseData({}, pageNum.value, pageSize.value)
}

const updateRow = (row?: Record<string, unknown>) => {
  if (row) {
    courseUpdateParam.value = { ...row }
    addFlag.value = false
  } else {
    courseUpdateParam.value = {}
    addFlag.value = true
  }
  dialogVisible.value = true
}

const deleteRow = async (courseNumber: string) => {
  const response: ApiResponse = await request.delete(`/courses/deleteByNumber/${courseNumber}`)
  if (response.code === '200') {
    ElMessage.success('删除课程成功')
    getCourseData({}, pageNum.value, pageSize.value)
  } else {
    ElMessage.error('删除课程失败，请稍后再试')
  }
}

const save = async (formRef: FormInstance) => {
  if (!formRef) return
  await formRef.validate()
  const params = {
    courseNumber: courseUpdateParam.value.courseNumber,
    courseName: courseUpdateParam.value.courseName,
    teacherNumber: courseUpdateParam.value.teacherNumber,
    academicYear: courseUpdateParam.value.academicYear,
    semester: courseUpdateParam.value.semester,
    courseHours: courseUpdateParam.value.courseHours,
    courseType: courseUpdateParam.value.courseType,
    courseCredits: courseUpdateParam.value.courseCredits,
    classId: courseUpdateParam.value.classId[1],
    courseStatus: courseUpdateParam.value.courseStatus,
    courseExamType: courseUpdateParam.value.courseExamType,
  }

  let response: ApiResponse
  if (addFlag.value) {
    response = await request.post('/courses/add', params)
    ElMessage.success('新增课程成功')
  } else {
    response = await request.put('/courses/updateByNumber', params)
    ElMessage.success('更新课程成功')
  }

  if (response.code === '200') {
    dialogVisible.value = false

    formRef.value?.resetFields()

    addFlag.value = false
    courseUpdateParam.value = {}
    ElMessage.success('操作成功')

    getCourseData({}, pageNum.value, pageSize.value)
  } else if (response.code === '5022') {
    // 工号已存在
    ElMessageBox.alert('课程已存在，请重新输入', '提示', {
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
  getCourseData({}, pageNum.value, pageSize.value)
  getTeacherData()
  getClassesData()
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
