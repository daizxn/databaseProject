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
        <el-form-item label="教师姓名" v-if="current_user?.userInfo?.roles === 'admin'">
          <el-input v-model="courseSelectParam.teacherName" placeholder="请输入教师姓名"></el-input>
        </el-form-item>
        <el-form-item label="教师编号" v-if="current_user?.userInfo?.roles === 'admin'">
          <el-input
            v-model="courseSelectParam.teacherNumber"
            placeholder="请输入教师编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="教师职称" v-if="current_user?.userInfo?.roles === 'admin'">
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
          <el-button type="success" @click="openScoreImportDialog">
            <el-icon><i-ep-upload /></el-icon>
            成绩导入
          </el-button>
        </el-form-item>
      </el-form>
    </el-container>
    <el-container class="data">
      <el-table style="width: 100%" :data="courseInfoData" @expand-change="handleExpand">
        <el-table-column fit type="expand">
          <template #default="props">
            <div style="padding: 20px">
              <div
                style="
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  margin-bottom: 15px;
                "
              >
                <h4>{{ props.row.courseName }}({{ props.row.courseNumber }}) - 学生成绩列表</h4>
                <el-button
                  type="primary"
                  size="small"
                  @click="openBatchUpdateDialog(props.row)"
                  :disabled="
                    !studentScoresMap[props.row.courseNumber] ||
                    studentScoresMap[props.row.courseNumber].length === 0
                  "
                >
                  批量更新成绩
                </el-button>
              </div>
              <el-table
                :data="studentScoresMap[props.row.courseNumber] || []"
                style="width: 100%"
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
        <el-table-column fit label="课程编号" prop="courseNumber"></el-table-column>
        <el-table-column fit label="课程名称" prop="courseName"></el-table-column>
        <el-table-column fit label="学年" prop="academicYear"></el-table-column>
        <el-table-column fit label="学期" prop="semester"></el-table-column>
        <el-table-column fit label="专业" prop="departmentName"></el-table-column>
        <el-table-column fit label="班级" prop="className"></el-table-column>
        <el-table-column fit label="课程状态" prop="courseStatus"></el-table-column>
        <el-table-column fit label="考核方式" prop="courseExamType"></el-table-column>
        <el-table-column fit label="课程类型" prop="courseType"></el-table-column>
        <el-table-column fit label="学分" prop="courseCredits"></el-table-column>
        <el-table-column fit label="学时" prop="courseHours"></el-table-column>
        <el-table-column fit label="教师姓名" prop="teacherName"></el-table-column>
        <el-table-column fit label="教师编号" prop="teacherNumber"></el-table-column>
        <el-table-column fit label="教师职称" prop="teacherTitle"></el-table-column>
        <el-table-column fit fixed="right" label="Operations" min-width="240">
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
      <el-form-item
        label="教师姓名"
        prop="teacherName"
        v-if="current_user?.userInfo?.roles === 'admin'"
      >
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

  <!-- 批量更新成绩对话框 -->
  <el-dialog
    title="批量更新成绩"
    v-model="batchUpdateDialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
    width="800px"
  >
    <div style="margin-bottom: 15px">
      <span style="color: #606266; font-size: 14px">
        课程：{{ batchUpdateInfo.courseName }}({{ batchUpdateInfo.courseNumber }}) -
        {{ batchUpdateInfo.academicYear }} 学年 第{{ batchUpdateInfo.semester }}学期
      </span>
    </div>

    <el-table :data="batchScoreData" style="width: 100%" max-height="400px">
      <el-table-column prop="studentNumber" label="学号" width="120" />
      <el-table-column prop="studentName" label="姓名" width="120" />
      <el-table-column label="原成绩" width="100">
        <template #default="scope">
          <span>{{ scope.row.originalScore || '未录入' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="新成绩" width="150">
        <template #default="scope">
          <el-input
            v-model="scope.row.newScore"
            type="number"
            placeholder="请输入成绩"
            min="0"
            max="100"
            size="small"
          />
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.newScore !== undefined && scope.row.newScore !== '' ? 'success' : 'info'
            "
            size="small"
          >
            {{
              scope.row.newScore !== undefined && scope.row.newScore !== '' ? '已填写' : '待填写'
            }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 15px; color: #909399; font-size: 12px">
      提示：请为需要更新的学生填写新成绩，留空表示不更新该学生的成绩
    </div>

    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="closeBatchUpdateDialog">取 消</el-button>
        <el-button type="primary" @click="saveBatchScores" :loading="batchUpdateLoading">
          批量更新
        </el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 成绩导入对话框 -->
  <el-dialog
    title="成绩批量导入"
    v-model="scoreImportDialogVisible"
    :close-on-click-modal="false"
    destroy-on-close
    width="700px"
  >
    <div class="import-content">
      <!-- 课程选择区域 -->
      <div class="course-selection">
        <el-form :model="importCourseInfo" label-width="100px" class="import-form">
          <el-form-item label="教师编号" required>
            <el-input v-model="importCourseInfo.teacherNumber" placeholder="请输入教师编号" />
          </el-form-item>
          <el-form-item label="课程编号" required>
            <el-input v-model="importCourseInfo.courseNumber" placeholder="请输入课程编号" />
          </el-form-item>
          <el-form-item label="学年" required>
            <el-input
              v-model="importCourseInfo.academicYear"
              placeholder="请输入学年，如：2023-2024"
            />
          </el-form-item>
          <el-form-item label="学期" required>
            <el-select v-model="importCourseInfo.semester" placeholder="请选择学期">
              <el-option label="第1学期" :value="1" />
              <el-option label="第2学期" :value="2" />
              <el-option label="第3学期" :value="3" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <el-divider />

      <!-- 文件上传区域 -->
      <div class="upload-section">
        <el-upload
          ref="scoreUploadRef"
          class="upload-demo"
          drag
          :auto-upload="false"
          :on-change="handleScoreFileChange"
          :on-remove="handleScoreFileRemove"
          :limit="1"
          accept=".csv,.xlsx,.xls"
          :file-list="scoreFileList"
        >
          <el-icon class="el-icon--upload"><i-ep-upload-filled /></el-icon>
          <div class="el-upload__text">将成绩文件拖到此处，或<em>点击上传</em></div>
          <template #tip>
            <div class="el-upload__tip">
              支持 .csv、.xlsx、.xls 格式文件，文件大小不超过 10MB<br />
              <strong>文件格式要求：</strong><br />
              • 第一列：学号（必填，字母数字组合）<br />
              • 第二列：成绩（必填，0-100之间的数字）<br />
              • 可包含标题行，系统会自动识别<br />
              • 建议先下载模板参考格式
            </div>
          </template>
        </el-upload>
      </div>

      <!-- 模板下载 -->
      <div class="template-section">
        <el-divider content-position="left">模板下载</el-divider>
        <el-button type="primary" link @click="downloadScoreTemplate">
          <el-icon><i-ep-download /></el-icon>
          下载成绩导入模板
        </el-button>
        <span class="template-tip">请按照模板格式准备成绩数据</span>
      </div>

      <!-- 数据预览 -->
      <div v-if="scorePreviewData.length > 0" class="preview-section">
        <el-divider content-position="left">数据预览</el-divider>
        <div class="preview-info">
          <span>共识别到 {{ scorePreviewData.length }} 条成绩数据</span>
          <el-tag
            :type="validScoreCount === scorePreviewData.length ? 'success' : 'warning'"
            size="small"
          >
            有效数据: {{ validScoreCount }} 条
          </el-tag>
          <el-tag v-if="invalidScoreCount > 0" type="danger" size="small">
            无效数据: {{ invalidScoreCount }} 条
          </el-tag>
        </div>

        <el-table
          :data="scorePreviewData.slice(0, 10)"
          style="width: 100%; margin-top: 10px"
          max-height="300px"
          size="small"
        >
          <el-table-column prop="studentNumber" label="学号" width="150" />
          <el-table-column prop="score" label="成绩" width="100" />
          <el-table-column label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.isValid ? 'success' : 'danger'" size="small">
                {{ scope.row.isValid ? '有效' : '无效' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="errorMsg" label="错误信息" />
        </el-table>

        <div v-if="scorePreviewData.length > 10" class="preview-more">
          <span>仅显示前10条数据，点击导入查看完整结果</span>
        </div>
      </div>
    </div>

    <template v-slot:footer>
      <div class="dialog-footer">
        <el-button @click="closeScoreImportDialog">取 消</el-button>
        <el-button
          type="primary"
          @click="importScoreData"
          :loading="scoreImportLoading"
          :disabled="!canImportScores"
        >
          确认导入 ({{ validScoreCount }}条)
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import request, { type ApiResponse } from '@/utils/request'
import { ElMessage, type FormInstance } from 'element-plus'
import {
  Upload as IEpUpload,
  UploadFilled as IEpUploadFilled,
  Download as IEpDownload,
} from '@element-plus/icons-vue'

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

const academicYear = ref(['2020-2021', '2021-2022', '2022-2023', '2023-2024', '2024-2025'])

const dialogVisible = ref(false)
const addFlag = ref(false)

const current_user = ref()

const scoreDialogVisible = ref(false)
const scoreUpdateParam = ref({
  studentNumber: '',
  courseNumber: '',
  score: null,
})

// 批量更新成绩相关状态
const batchUpdateDialogVisible = ref(false)
const batchUpdateLoading = ref(false)
const batchUpdateInfo = ref({
  courseNumber: '',
  courseName: '',
  academicYear: '',
  semester: '',
  teacherNumber: '',
})
const batchScoreData = ref<
  Array<{
    studentNumber: string
    studentName: string
    originalScore: number | null
    newScore: number | string
  }>
>([])

// 成绩导入相关状态
const scoreImportDialogVisible = ref(false)
const scoreImportLoading = ref(false)
const scoreFileList = ref<File[]>([])
const scoreUploadRef = ref()
const importCourseInfo = ref({
  teacherNumber: '',
  courseNumber: '',
  academicYear: '',
  semester: null as number | null,
})
const scorePreviewData = ref<
  Array<{
    studentNumber: string
    score: number | string
    isValid: boolean
    errorMsg: string
  }>
>([])
const validScoreCount = ref(0)
const invalidScoreCount = ref(0)

// 计算是否可以导入成绩
const canImportScores = computed(() => {
  return (
    validScoreCount.value > 0 &&
    importCourseInfo.value.teacherNumber &&
    importCourseInfo.value.courseNumber &&
    importCourseInfo.value.academicYear &&
    importCourseInfo.value.semester
  )
})

const getCourseData = async (
  courseParam: Record<string, unknown>,
  pageNum: number,
  pageSize: number,
) => {
  console.log('查询课程参数:', courseParam)
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
    console.error('获取课程数据失败: 数据为空', response)
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
    const response: ApiResponse = await request.delete(`/courses/deleteByNumber/${courseNumber}`)
    if (response.code === '200') {
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
    console.log('保存课程参数:', params)
    let response: ApiResponse
    if (addFlag.value) {
      response = await request.post('/courses/add', params)
    } else {
      response = await request.put('/courses/updateByNumber', params)
    }

    if (response.code === '200') {
      dialogVisible.value = false
      formRef.resetFields()
      addFlag.value = false
      courseUpdateParam.value = {}
      ElMessage.success('操作成功')
      getCourseData({}, pageNum.value, pageSize.value)
    } else if (response.code === '5022') {
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
    const response: ApiResponse = await request.get(`/scores/selectByCourseNumber/${courseNumber}`)

    if (response.data && Array.isArray(response.data)) {
      studentScoresMap.value[courseNumber] = response.data
      console.log(`获取课程 ${courseNumber} 的学生成绩成功:`, studentScoresMap.value[courseNumber])
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
    const response: ApiResponse = await request.put(
      '/scores/updateScoresByNumber',
      scoreUpdateParam.value,
    )

    if (response.code != '200') {
      ElMessage.error('修改成绩失败')
    } else {
      ElMessage.success('修改成绩成功')
      scoreDialogVisible.value = false
      // 重新获取该课程的学生成绩
      await getStudentScores(scoreUpdateParam.value.courseNumber)
    }
  } catch (error) {
    console.error('修改成绩失败:', error)
    ElMessage.error('修改成绩失败，请稍后再试')
  }
}

// 打开批量更新对话框
const openBatchUpdateDialog = (row: Record<string, unknown>) => {
  batchUpdateInfo.value = {
    courseNumber: row.courseNumber as string,
    courseName: row.courseName as string,
    academicYear: row.academicYear as string,
    semester: row.semester as string,
    teacherNumber: row.teacherNumber as string,
  }

  // 准备批量更新数据
  const studentScores = studentScoresMap.value[row.courseNumber as string] || []
  batchScoreData.value = studentScores.map(
    (student: { studentNumber: string; studentName: string; score: number }) => ({
      studentNumber: student.studentNumber,
      studentName: student.studentName,
      originalScore: student.score,
      newScore: student.score || '',
    }),
  )

  batchUpdateDialogVisible.value = true
}

// 关闭批量更新对话框
const closeBatchUpdateDialog = () => {
  batchUpdateDialogVisible.value = false
  batchScoreData.value = []
  batchUpdateInfo.value = {
    courseNumber: '',
    courseName: '',
    academicYear: '',
    semester: '',
    teacherNumber: '',
  }
}

// 批量保存成绩
const saveBatchScores = async () => {
  try {
    // 过滤出有效的成绩数据（新成绩不为空且为有效数字）
    const validScores = batchScoreData.value
      .filter(
        (item: { newScore: number | string }) =>
          item.newScore !== undefined &&
          item.newScore !== '' &&
          !isNaN(Number(item.newScore)) &&
          Number(item.newScore) >= 0 &&
          Number(item.newScore) <= 100,
      )
      .map((item: { studentNumber: string; newScore: number | string }) => ({
        student_no: item.studentNumber,
        score: Number(item.newScore),
      }))

    if (validScores.length === 0) {
      ElMessage.warning('请至少填写一个有效的成绩')
      return
    }

    batchUpdateLoading.value = true

    const requestData = {
      teacherNumber: batchUpdateInfo.value.teacherNumber,
      courseNumber: batchUpdateInfo.value.courseNumber,
      academicYear: batchUpdateInfo.value.academicYear,
      semester: Number(batchUpdateInfo.value.semester),
      scores: validScores,
    }

    console.log('批量更新成绩请求数据:', requestData)

    const response: ApiResponse = await request.post('/scores/batchUpdate', requestData)

    if (response.code === '200') {
      ElMessage.success(`成功更新 ${validScores.length} 个学生的成绩`)
      closeBatchUpdateDialog()
      // 重新获取该课程的学生成绩
      await getStudentScores(batchUpdateInfo.value.courseNumber)
    } else {
      ElMessage.error(response.msg || '批量更新成绩失败')
    }
  } catch (error) {
    console.error('批量更新成绩失败:', error)
    ElMessage.error('批量更新成绩失败，请稍后再试')
  } finally {
    batchUpdateLoading.value = false
  }
}

// 成绩导入相关方法
const openScoreImportDialog = () => {
  scoreImportDialogVisible.value = true
  resetScoreImportData()

  // 如果当前用户是教师，自动填入教师编号
  if (current_user.value?.userInfo?.roles === 'teacher') {
    importCourseInfo.value.teacherNumber = current_user.value.userInfo.userNumber || ''
  }
}

const closeScoreImportDialog = () => {
  scoreImportDialogVisible.value = false
  resetScoreImportData()
}

const resetScoreImportData = () => {
  scoreFileList.value = []
  scorePreviewData.value = []
  validScoreCount.value = 0
  invalidScoreCount.value = 0
  importCourseInfo.value = {
    teacherNumber: '',
    courseNumber: '',
    academicYear: '',
    semester: null,
  }
  if (scoreUploadRef.value) {
    scoreUploadRef.value.clearFiles()
  }
}

// 处理成绩文件变化
const handleScoreFileChange = (file: { raw: File }) => {
  if (file.raw) {
    parseScoreFile(file.raw)
  }
}

// 处理成绩文件移除
const handleScoreFileRemove = () => {
  scorePreviewData.value = []
  validScoreCount.value = 0
  invalidScoreCount.value = 0
}

// 解析成绩文件
const parseScoreFile = (file: File) => {
  const reader = new FileReader()

  reader.onload = (e) => {
    try {
      const data = e.target?.result
      if (!data) return

      if (file.name.endsWith('.csv')) {
        parseCSVData(data as string)
      } else if (file.name.endsWith('.xlsx') || file.name.endsWith('.xls')) {
        parseExcelData()
      } else {
        ElMessage.error('不支持的文件格式，请使用 CSV 或 Excel 文件')
      }
    } catch (error) {
      console.error('成绩文件解析失败:', error)
      ElMessage.error('文件解析失败，请检查文件格式')
    }
  }

  if (file.name.endsWith('.csv')) {
    reader.readAsText(file, 'UTF-8')
  } else if (file.name.endsWith('.xlsx') || file.name.endsWith('.xls')) {
    reader.readAsArrayBuffer(file)
  }
}

// 解析CSV数据
const parseCSVData = (text: string) => {
  const lines = text.split('\n')

  const scores: Array<{
    studentNumber: string
    score: number | string
    isValid: boolean
    errorMsg: string
  }> = []

  // 检测是否有标题行
  const startIndex =
    lines[0] && (lines[0].includes('学号') || lines[0].includes('studentNumber')) ? 1 : 0

  for (let i = startIndex; i < lines.length; i++) {
    if (lines[i].trim()) {
      const values = lines[i].split(',').map((v) => v.trim().replace(/"/g, ''))
      processScoreRow(values, scores)
    }
  }

  scorePreviewData.value = scores
  updateScoreValidationCounts()
}

// 解析Excel数据（简化版，实际项目中建议使用 xlsx 库）
const parseExcelData = () => {
  // 这里简化处理，实际应该使用 xlsx 库来解析 Excel
  // 为了演示，这里提示用户转换为 CSV 格式
  ElMessage.warning('Excel 文件解析需要专门的库，建议将文件另存为 CSV 格式后重新上传')
}

// 处理单行成绩数据
const processScoreRow = (
  values: string[],
  scores: Array<{
    studentNumber: string
    score: number | string
    isValid: boolean
    errorMsg: string
  }>,
) => {
  const studentNumber = values[0] || ''
  const scoreStr = values[1] || ''

  let isValid = true
  let errorMsg = ''
  let score: number | string = scoreStr

  // 验证学号
  if (!studentNumber) {
    isValid = false
    errorMsg = '学号不能为空'
  } else if (!/^[A-Za-z0-9]+$/.test(studentNumber)) {
    isValid = false
    errorMsg = '学号格式不正确'
  }

  // 验证成绩
  if (!scoreStr) {
    isValid = false
    errorMsg = errorMsg ? errorMsg + '; 成绩不能为空' : '成绩不能为空'
  } else {
    const scoreNum = parseFloat(scoreStr)
    if (isNaN(scoreNum)) {
      isValid = false
      errorMsg = errorMsg ? errorMsg + '; 成绩必须是数字' : '成绩必须是数字'
    } else if (scoreNum < 0 || scoreNum > 100) {
      isValid = false
      errorMsg = errorMsg ? errorMsg + '; 成绩必须在0-100之间' : '成绩必须在0-100之间'
    } else {
      score = Math.round(scoreNum * 100) / 100 // 保留两位小数
    }
  }

  scores.push({
    studentNumber,
    score,
    isValid,
    errorMsg: errorMsg || '数据有效',
  })
}

// 更新成绩验证统计
const updateScoreValidationCounts = () => {
  validScoreCount.value = scorePreviewData.value.filter(
    (item: { isValid: boolean }) => item.isValid,
  ).length
  invalidScoreCount.value = scorePreviewData.value.filter(
    (item: { isValid: boolean }) => !item.isValid,
  ).length
}

// 下载成绩模板
const downloadScoreTemplate = () => {
  const templateData = [
    ['学号', '成绩', '备注'],
    ['S20230001', '85', '示例数据1'],
    ['S20230002', '92', '示例数据2'],
    ['S20230003', '78', '示例数据3'],
    ['', '', ''],
    ['说明：', '', ''],
    ['1. 学号列：必填，学生学号', '', ''],
    ['2. 成绩列：必填，数字格式，范围0-100', '', ''],
    ['3. 备注列：可选，可删除此列', '', ''],
    ['4. 请删除说明行后再上传', '', ''],
  ]

  const csvContent = templateData.map((row) => row.join(',')).join('\n')

  const blob = new Blob(['\uFEFF' + csvContent], { type: 'text/csv;charset=utf-8;' })
  const link = document.createElement('a')
  const url = URL.createObjectURL(blob)
  link.setAttribute('href', url)
  link.setAttribute('download', '成绩导入模板.csv')
  link.style.visibility = 'hidden'
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  URL.revokeObjectURL(url)

  ElMessage.success('模板下载成功，请按照模板格式准备数据')
}

// 导入成绩数据
const importScoreData = async () => {
  if (!canImportScores.value) {
    ElMessage.warning('请填写完整的课程信息并确保有有效的成绩数据')
    return
  }

  try {
    scoreImportLoading.value = true

    const validScores = scorePreviewData.value
      .filter((item: { isValid: boolean }) => item.isValid)
      .map((item: { studentNumber: string; score: number | string }) => ({
        student_no: item.studentNumber,
        score: Number(item.score),
      }))

    if (validScores.length === 0) {
      ElMessage.warning('没有有效的成绩数据可以导入')
      return
    }

    const requestData = {
      teacherNumber: importCourseInfo.value.teacherNumber,
      courseNumber: importCourseInfo.value.courseNumber,
      academicYear: importCourseInfo.value.academicYear,
      semester: importCourseInfo.value.semester,
      scores: validScores,
    }

    console.log('成绩导入请求数据:', requestData)

    const response: ApiResponse = await request.post('/scores/batchImport', requestData)

    if (response.code === '200') {
      ElMessage.success(
        `成功导入 ${validScores.length} 条成绩数据${invalidScoreCount.value > 0 ? `，跳过 ${invalidScoreCount.value} 条无效数据` : ''}`,
      )
      closeScoreImportDialog()

      // 刷新课程列表
      await getCourseData(courseSelectParam.value, pageNum.value, pageSize.value)

      // 如果当前展开的课程正好是导入的课程，刷新成绩数据
      if (studentScoresMap.value[importCourseInfo.value.courseNumber]) {
        await getStudentScores(importCourseInfo.value.courseNumber)
      }
    } else {
      ElMessage.error(response.msg || '成绩导入失败，请检查数据格式和课程信息')
    }
  } catch (error) {
    console.error('成绩导入失败:', error)
    if (error instanceof Error) {
      ElMessage.error(`成绩导入失败：${error.message}`)
    } else {
      ElMessage.error('成绩导入失败，请稍后再试')
    }
  } finally {
    scoreImportLoading.value = false
  }
}

const getCurrentUser = async () => {
  const userInfo = localStorage.getItem('xm-user') || sessionStorage.getItem('xm-user')
  console.log('获取当前用户信息:', userInfo)
  if (userInfo) {
    current_user.value = JSON.parse(userInfo)
    console.log('当前用户信息:', current_user.value)
  } else {
    console.error('当前用户信息未找到')
  }
}

onMounted(() => {
  getCourseData({}, pageNum.value, pageSize.value)
  getTeacherData()
  getClassesData()
  getCurrentUser()
})
</script>

<style scoped>
@import '@/styles/manage-common.css';

/* 成绩导入对话框样式 */
.import-content {
  padding: 10px 0;
}

.course-selection {
  margin-bottom: 20px;
}

.import-form .el-form-item {
  margin-bottom: 18px;
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
