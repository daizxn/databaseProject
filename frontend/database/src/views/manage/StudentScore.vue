<template>
  <el-container class="layout-container">
    <el-container class="select">
      <el-form
        class="select-form"
        :model="studentsSelectParam"
        :rules="selectFormRules"
        ref="selectFormRef"
      >
        <el-form-item label="学号" prop="studentNumber">
          <el-input v-model="studentsSelectParam.studentNumber" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="studentsSelectParam.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="专业" prop="departmentName">
          <el-input v-model="studentsSelectParam.departmentName" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="班级" prop="className">
          <el-input v-model="studentsSelectParam.className" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="课程" prop="courseName">
          <el-input v-model="studentsSelectParam.courseName" placeholder="请输入课程" />
        </el-form-item>
        <el-form-item label="教师姓名" prop="teacherName">
          <el-input v-model="studentsSelectParam.teacherName" placeholder="请输入教师姓名" />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getStudentScoresData(studentsSelectParam)"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
    </el-container>
    <el-container class="data">
      <el-table :data="studentScoreData" style="width: 100%">
        <el-table-column prop="studentNumber" label="学号" />
        <el-table-column prop="studentName" label="学生姓名" />
        <el-table-column prop="gradeLevel" label="评级" />
        <el-table-column prop="departmentName" label="院系" />
        <el-table-column prop="className" label="班级" />
        <el-table-column prop="courseName" label="课程" />
        <el-table-column prop="teacherName" label="教师姓名" />
        <el-table-column prop="score" label="分数" />
        <el-table-column fixed="right" label="Operations" min-width="240">
          <template #default="scope">
            <el-button
              text
              size="default"
              @click.prevent="updateRow(scope.row.studentNumber, scope.row.courseNumber)"
              >修改成绩
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
    <el-input v-model="updateParams.score" placeholder="成绩"></el-input>
    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import request, { type ApiResponse } from '@/utils/request'

const studentScoreData = ref([])
const studentsSelectParam = ref({})
const updateParams = ref({})

const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const dialogVisible = ref(false)

const getStudentScoresData = async (
  params: Record<string, unknown>,
  pageNum: number,
  pageSize: number,
) => {
  try {
    const response = await request.get('/scores/selectByPageWithStudent', {
      params: {
        ...params,
        pageNum: pageNum,
        pageSize: pageSize,
      },
    })
    if (response.data === null) {
      console.error('获取学生成绩数据失败: 数据为空')
      studentScoreData.value = []
      total.value = 0
    } else {
      console.log('获取学生成绩数据成功:', response.data)
      total.value = response.data?.total
      studentScoreData.value = response.data.records
    }
  } catch (error) {
    console.error('获取学生成绩数据失败:', error)
  }
}

const updateRow = (studentNumber: string, courseNumber: string) => {
  dialogVisible.value = true
  // 这里可以添加逻辑来填充更新表单的数据
  updateParams.value = { studentNumber: studentNumber, courseNumber: courseNumber }
}

const save = async () => {
  const response: ApiResponse = await request.put('/scores/updateScoresByNumber', {
    studentNumber: updateParams.value.studentNumber,
    courseNumber: updateParams.value.courseNumber,
    score: updateParams.value.score,
  })
  if (response.code === '200') {
    ElMessage.success('修改成绩成功')
    dialogVisible.value = false
    getStudentScoresData({}, pageNum.value, pageSize.value)
  } else {
    ElMessage.error('修改成绩失败')
  }
}

onMounted(() => {
  getStudentScoresData({}, pageNum.value, pageSize.value)
})
</script>

<style scoped>
@import '@/styles/manage-common.css';
</style>
