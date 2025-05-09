<template>
  <div class="dashboard-editor-container">
    <div v-if="!checkPermi(['manage:statics'])">
      <h1 style="font-size: 36px;text-align: center">科研项目管理系统</h1>
    </div>
    <div v-else>
      <panel-group @handleSetLineChartData="handleSetLineChartData"/>

      <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
        <my-line-chart :chart-data="loginCount" name="每日登录"/>
      </el-row>

      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <pie-chart :chart-data="userSexCount" chart-name="性别比例"/>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <my-only-bar-chart :bar-data="userAgeCount" bar-name="年龄段"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { checkPermi } from '@/utils/permission'
import MyLineChart from '@/views/dashboard/MyLineChart.vue'
import MyOnlyBarChart from '@/views/dashboard/MyOnlyBarChart.vue'
import { loginCount, loginCountDay, userAge, userSexPie } from '@/api/manage/statics'

export default {
  name: 'Index',
  components: {
    MyOnlyBarChart,
    MyLineChart,
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      loginCount: {},
      userSexCount: [],
      userAgeCount: {}
    }
  },
  created() {
    if (checkPermi(['manage:statics'])) {
      loginCountDay().then(response => {
        this.loginCount = response.data
      })
      userSexPie().then(response => {
        this.userSexCount = response.data.datas
      })
      userAge().then(response => {
        this.userAgeCount = response.data
      })
    }
  },
  methods: {
    checkPermi,
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
