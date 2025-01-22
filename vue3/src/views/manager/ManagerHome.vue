<script setup>
import { reactive } from 'vue'
import { selectAllCountsAPI, selectPieAPI, selectBarAPI, selectLineAPI } from '@/api/statistics'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import { onMounted } from 'vue'

const data = reactive({
  employCount: null,
  userCount: null,
  positionCount: null,
  resumeCount: null,
  pieOption: {
    title: {
      text: '职位投递热度统计',
      subtext: '统计维度：职位名称',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  },
  barOption: {
    title: {
      text: '不同行业在招职位数量统计', // 主标题
      subtext: '统计维度：行业分类', // 副标题
      left: 'center'
    },
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'], // 示例数据：统计的维度（横坐标）
      axisLabel: {
        show: true, // 是否显示刻度标签，默认显示
        interval: 0 // 坐标轴刻度标签的显示间隔，在类目轴中有效；默认会采用标签不重叠的策略间隔显示标签；可以设置成0强制显示所有标签；如果设置为1，表示『隔一个标签显示一个标签』，如果值为2，表示隔两个标签显示一个标签，以此类推。
        // rotate: -60 // 刻度标签旋转的角度，在类目轴的类目标签显示不下的时候可以通过旋转防止标签之间重叠；旋转的角度从-90度到90度
        // inside: false, // 刻度标签是否朝内，默认朝外
        // margin: 6 // 刻度标签与轴线之间的距离
      }
    },
    yAxis: {
      type: 'value'
    },
    tooltip: {
      trigger: 'item'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
        type: 'bar',
        itemStyle: {
          normal: {
            color: function () {
              return '#' + Math.floor(Math.random() * (256 * 256 * 256 - 1)).toString(16)
            }
          }
        }
      }
    ]
  },
  lineOption: {
    title: {
      text: '近一个月岗位投递走势图', // 主标题
      subtext: '统计维度：日期', // 副标题
      left: 'center'
    },
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: 'line',
        smooth: true
      }
    ]
  }
})
const load = () => {
  selectAllCountsAPI().then((res) => {
    if (res.code === '200') {
      data.employCount = res.data.employCount
      data.userCount = res.data.userCount
      data.positionCount = res.data.positionCount
      data.resumeCount = res.data.resumeCount
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadEchartsData = () => {
  selectPieAPI().then((res) => {
    if (res.code === '200') {
      const pieChartDom = document.getElementById('pie')
      const myChart = echarts.init(pieChartDom)
      data.pieOption.series[0].data = res.data
      myChart.setOption(data.pieOption)
    } else {
      ElMessage.error(res.msg)
    }
  })
  selectBarAPI().then((res) => {
    if (res.code === '200') {
      const barCharDom = document.getElementById('bar')
      const myChart = echarts.init(barCharDom)
      data.barOption.xAxis.data = res.data.xAxis
      data.barOption.series[0].data = res.data.yAxis
      myChart.setOption(data.barOption)
    } else {
      ElMessage.error(res.msg)
    }
  })
  selectLineAPI().then((res) => {
    if (res.code === '200') {
      const lineCharDom = document.getElementById('line')
      const myChart = echarts.init(lineCharDom)
      data.lineOption.xAxis.data = res.data.xAxis
      data.lineOption.series[0].data = res.data.yAxis
      myChart.setOption(data.lineOption)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  load()
  loadEchartsData()
})
</script>
<template>
  <div class="home-box">
    <div style="display: flex; background-color: #fff" class="overview-box">
      <div style="display: flex; margin: 2px 5px; flex: 1" class="card">
        <img style="width: 80px; height: 80px" src="@/assets/imgs/build.jpg" alt="" />
        <div
          style="
            font-size: 16px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            margin-left: 10px;
          "
        >
          <div>平台在招企业数量</div>
          <div>{{ data.employCount }}</div>
        </div>
      </div>
      <div style="display: flex; margin: 2px 5px; flex: 1" class="card">
        <img style="width: 80px; height: 80px" src="@/assets/imgs/homework.jpg" alt="" />
        <div
          style="
            font-size: 16px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            margin-left: 10px;
          "
        >
          <div>平台求职用户数量</div>
          <div>{{ data.userCount }}</div>
        </div>
      </div>
      <div style="display: flex; margin: 2px 5px; flex: 1" class="card">
        <img style="width: 80px; height: 80px" src="@/assets/imgs/paper.jpg" alt="" />
        <div
          style="
            font-size: 16px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            margin-left: 10px;
          "
        >
          <div>平台在招岗位数量</div>
          <div>{{ data.positionCount }}</div>
        </div>
      </div>
      <div style="display: flex; margin: 2px 5px; flex: 1" id="card">
        <img style="width: 80px; height: 80px" src="@/assets/imgs/person.jpg" alt="" />
        <div
          style="
            font-size: 16px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            margin-left: 10px;
          "
        >
          <div>平台岗位总投递数量</div>
          <div>{{ data.resumeCount }}</div>
        </div>
      </div>
    </div>
    <div style="height: 300px; margin-top: 10px" class="card" id="bar"></div>
    <div style="height: 300px; margin-top: 10px" class="card" id="pie"></div>
    <div style="height: 300px; margin-top: 10px" class="card" id="line"></div>
  </div>
</template>
<style scope></style>
