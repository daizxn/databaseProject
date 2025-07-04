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
      <el-table style="width: 100%" :data="courseInfoData" @expand-change="handleExpand">
        <el-table-column type="expand">
          <template #default="props">
            <div style="padding: 20px">
              <h4>{{ props.row.courseName }}({{ props.row.courseNumber }}) - 学生成绩列表</h4>
              <el-table
                :data="studentScoresMap[props.row.courseNumber] || []"
                style="width: 100%; margin-top: 10px"
                v-loading="expandLoading[props.row.courseNumber]"
              >
                <el-table-column prop="studentNumber" label="学号" width="120" />
                <el-table-column prop="studentName" label="姓名" width="120" />
                <el-table-column prop="score" label="成绩" width="100" />
                <el-table-column fixed="right" label="操作" width="120">
                  <template #default="scope">
                    <el-button
                      text
                      size="small"
                      type="primary"
                      @click="
                        editScore(props.row.courseNumber, scope.row.studentNumber, scope.row.score)
                      "
                    >
                      修改成绩
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </template>
        </el-table-column>
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
        <el-table-column fixed="right" label="Operations" min-width="240">
          <template #default="scope">
            <el-button text size="default" @click.prevent="updateRow(scope.row)">编辑 </el-button>
            <el-button text size="default" @click.prevent="deleteRow(scope.row.courseNumber)"
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

  <!-- 成绩修改对话框 -->
  <el-dialog
    title="修改成绩"
    v-model="scoreDialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
  >
    <el-form label-width="80px">
      <el-form-item label="学号">
        <el-input v-model="scoreUpdateParam.studentNumber" disabled></el-input>
      </el-form-item>
      <el-form-item label="课程编号">
        <el-input v-model="scoreUpdateParam.courseNumber" disabled></el-input>
      </el-form-item>
      <el-form-item label="成绩">
        <el-input
          v-model="scoreUpdateParam.score"
          placeholder="请输入成绩"
          type="number"
        ></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="scoreDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveScore">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import request from '@/utils/request'

import { ElMessage, type FormInstance } from 'element-plus'

const courseInfoData = ref([])
const teacherData = ref([])
const classesData = ref([])

// 存储每个课程的学生成绩数据
const studentScoresMap = ref<
  Record<string, Array<{ studentNumber: string; studentName: string; score: number }>>
>({})
// 存储展开行的加载状态
const expandLoading = ref<Record<string, boolean>>({})

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

const scoreDialogVisible = ref(false)
const scoreUpdateParam = ref({
  studentNumber: '',
  courseNumber: '',
  score: null,
})

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
    console.log('获取课程数据成功:', courseInfoData.value)
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
  try {
    const response = await request.delete(`/courses/deleteByNumber/${courseNumber}`)
    if (response.data.code === '200') {
      ElMessage.success('删除课程成功')
      getCourseData({}, pageNum.value, pageSize.value)
    } else {
      ElMessage.error('删除课程失败，请稍后再试')
    }
  } catch (error) {
    console.error('删除课程失败:', error)
    ElMessage.error('删除课程失败，请稍后再试')
  }
}

const save = async (formRef: FormInstance) => {
  if (!formRef) return
  try {
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

    let response
    if (addFlag.value) {
      response = await request.post('/courses/add', params)
    } else {
      response = await request.put('/courses/updateByNumber', params)
    }

    if (response.data.code === '200') {
      dialogVisible.value = false
      formRef.resetFields()
      addFlag.value = false
      courseUpdateParam.value = {}
      ElMessage.success('操作成功')
      getCourseData({}, pageNum.value, pageSize.value)
    } else if (response.data.code === '5022') {
      ElMessage.warning('课程已存在，请重新输入')
    } else {
      ElMessage.error('操作失败，请稍后再试')
    }
  } catch (error) {
    console.error('保存课程失败:', error)
    ElMessage.error('操作失败，请稍后再试')
  }
}

// 处理表格展开行
const handleExpand = async (
  row: Record<string, unknown>,
  expandedRows: Record<string, unknown>[],
) => {
  const courseNumber = row.courseNumber as string

  // 如果是展开状态且还没有数据，则获取数据
  if (expandedRows.find((r) => r.courseNumber === courseNumber)) {
    if (!studentScoresMap.value[courseNumber]) {
      await getStudentScores(courseNumber)
    }
  }
}

// 获取指定课程的学生成绩
const getStudentScores = async (courseNumber: string) => {
  try {
    expandLoading.value[courseNumber] = true
    const response = await request.get(`/scores/selectByCourseNumber/${courseNumber}`)

    if (response.data && Array.isArray(response.data)) {
      studentScoresMap.value[courseNumber] = response.data
    } else {
      studentScoresMap.value[courseNumber] = []
    }
  } catch (error) {
    console.error('获取学生成绩失败:', error)
    studentScoresMap.value[courseNumber] = []
  } finally {
    expandLoading.value[courseNumber] = false
  }
}

// 编辑成绩
const editScore = (courseNumber: string, studentNumber: string, currentScore: number) => {
  scoreUpdateParam.value = {
    courseNumber,
    studentNumber,
    score: currentScore,
  }
  scoreDialogVisible.value = true
}

// 保存修改的成绩
const saveScore = async () => {
  try {
    const response = await request.put('/scores/updateScoresByNumber', scoreUpdateParam.value)

    if (response.data.code === '200') {
      ElMessage.success('修改成绩成功')
      scoreDialogVisible.value = false
      // 重新获取该课程的学生成绩
      await getStudentScores(scoreUpdateParam.value.courseNumber)
    } else {
      ElMessage.error('修改成绩失败')
    }
  } catch (error) {
    console.error('修改成绩失败:', error)
    ElMessage.error('修改成绩失败，请稍后再试')
  }
}

onMounted(() => {
  getCourseData({}, pageNum.value, pageSize.value)
  getTeacherData()
  getClassesData()
})
</script>

<style scoped>
@import '@/styles/manage-common.css';
</style>
