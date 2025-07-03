<script setup lang="ts"></script>

<template>
  <el-container class="layout-container" style="height: 100vh">
    <el-header style="text-align: left; font-size: 24px; height: 50px">
      <div class="toolbar">
        <span>学生成绩管理系统</span>
      </div>
    </el-header>

    <el-container style="height: 100%">
      <el-aside width="300px">
        <el-scrollbar>
          <el-menu :router="true">
            <el-sub-menu index="1">
              <template #title>
                <el-icon><i-ep-message /></el-icon>学生信息管理
              </template>
              <el-menu-item-group>
                <el-menu-item index="student-info">学生信息查看</el-menu-item>
                <el-menu-item index="about">学生地区统计</el-menu-item>
                <el-menu-item index="student-score">学生成绩查询</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon><i-ep-menu /></el-icon>教师信息管理
              </template>
              <el-menu-item-group>
                <el-menu-item index="teacher-info">教师信息查看</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="3">
              <template #title>
                <el-icon><i-ep-setting /></el-icon>课程管理
              </template>
              <el-menu-item-group>
                <el-menu-item index="course-info">课程信息查看</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="4">
              <template #title>
                <el-icon><i-ep-setting /></el-icon>成绩管理
              </template>
              <el-menu-item-group>
                <el-menu-item index="grade-info">成绩信息查看</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-main>
        <el-scrollbar>
          <router-view />
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container .el-aside {
  color: var(--el-text-color-primary);
  /* background: var(--el-color-primary-light-8); */
}
.layout-container .el-menu {
  border-right: none;
}
.layout-container .el-main {
  padding: 0;
}
.layout-container .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.el-menu .el-sub-menu .el-menu-item-group {
  padding-left: 20px;
}
</style>
