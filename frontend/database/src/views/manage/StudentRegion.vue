<template>
  <el-container class="layout-container">
    <el-container class="data">
      <el-table :data="regionsData" style="width: 100%">
        <el-table-column prop="regionName" label="属地名称" width="180"> </el-table-column>
        <el-table-column prop="studentCount" label="学生数量" width="180"> </el-table-column>
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

<script lang="ts" setup>
import request from '@/utils/request'

const regionsData = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const getRegionData = async () => {
  try {
    const response = await request.get('/regions/selectByPage/FullInfo')
    regionsData.value = response.data.records
    total.value = response.data?.total
    console.log('获取属地数据成功:', regionsData.value)
  } catch (error) {
    console.error('获取属地数据失败:', error)
  }
}

const handleCurrentChange = (val: number) => {
  pageNum.value = val
  getRegionData()
}
onMounted(() => {
  getRegionData()
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
