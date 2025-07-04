<template>
  <el-container class="layout-container">
    <el-container class="select" ref="selectFormRef">
      <el-form class="select-form" label-width="80px" :model="courseSelectParam">
        <el-form-item label="班级" prop="classId">
          <el-cascader
            v-model="courseSelectParam.classId"
            :options="classesData"
            placeholder="请选择班级"
            ref="cascaderRef"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="学年" prop="academicYear">
          <el-select
            v-model="courseSelectParam.academicYear"
            :empty-values="[null, undefined]"
            :value-on-clear="null"
            clearable
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
        <el-form-item>
          <el-button @click="getCourseData(courseSelectParam, pageNum, pageSize)">查询</el-button>
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
</template>

<script setup lang="ts">
import request from '@/utils/request'

const courseInfoData = ref([])
const teacherData = ref([])
const classesData = ref([])

interface CourseParam {
  academicYear?: string
  semester?: string
  className?: string
  classId?: string[]
}

const courseSelectParam = ref<CourseParam>({})
const cascaderRef = ref()

const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const academicYear = ref(['2020/2021', '2021/2022', '2022/2023', '2023/2024', '2024/2025'])

const getCourseData = async (courseParam: CourseParam, pageNum: number, pageSize: number) => {
  console.log('查询课程参数:', courseParam)
  const params = {
    academicYear: courseParam.academicYear,
    semester: courseParam.semester,
    className: cascaderRef.value.presentText.split(' / ')[1] || courseParam.className,
  }

  console.log('查询课程参数:', params)
  const response = await request.get('/courses/selectByPage/FullInfo', {
    params: {
      pageNum: pageNum,
      pageSize: pageSize,
      ...params,
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
  console.log('班级数据:', classesData.value)
}

const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getCourseData({}, pageNum.value, pageSize.value)
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
