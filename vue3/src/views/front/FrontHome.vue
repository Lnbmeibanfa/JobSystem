<script setup>
import { reactive } from 'vue'
import { selectAllAdvertiseAPI } from '@/api/advertise'
import { selectAllIndustryAPI } from '@/api/industry'
import { selectAllPositionAPI } from '@/api/position'
import { ElMessage } from 'element-plus'
const data = reactive({
  leftLargeImg: '',
  centerLargeImg: '',
  rightLargeImg: '',
  leftSmallImg: '',
  centerSmallImg: '',
  rightSmallImg: '',
  // 搜索框中搜索职位
  position: '',
  industryList: [],
  // 职位tag栏目中筛选的高亮职位
  activeIndustryName: '',
  positionList: []
})
const loadAdvertiseImg = () => {
  selectAllAdvertiseAPI().then((res) => {
    if (res.code === '200') {
      res.data.forEach((item) => {
        switch (item.location) {
          case '左侧大图':
            data.leftLargeImg = item.img
            break
          case '中心大图':
            data.centerLargeImg = item.img
            break
          case '右侧大图':
            data.rightLargeImg = item.img
            break
          case '左侧小图':
            data.leftSmallImg = item.img
            break
          case '中心小图':
            data.centerSmallImg = item.img
            break
          case '右侧小图':
            data.rightSmallImg = item.img
            break
        }
      })
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadIndustry = () => {
  selectAllIndustryAPI().then((res) => {
    if (res.code === '200') {
      data.industryList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleClick = (industry) => {
  console.log(industry.props.name)
  selectAllPositionAPI(industry.props.name).then((res) => {
    if (res.code === '200') {
      data.positionList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const search = () => {
  console.log('search')
}
loadIndustry()
loadAdvertiseImg()
</script>

<template>
  <img src="@/assets/imgs/banner.jpg" alt="" />
  <div class="home">
    <div class="search">
      <el-input
        style="width: 400px; margin-right: 5px"
        v-model="data.position"
        placeholder="请输入你感兴趣的职位"
        size="large"
        @key-up.enter="search"
      ></el-input>
      <el-button type="primary" size="large" @click="search">搜索</el-button>
    </div>
    <div class="advertise">
      <div class="largeImg">
        <div style="flex: 1">
          <img
            style="width: 100%; height: 240px; border-top-left-radius: 5px"
            :src="data.leftLargeImg"
            alt=""
          />
        </div>
        <div style="flex: 2; margin: 0px 5px">
          <img style="width: 100%; height: 240px" :src="data.centerLargeImg" alt="" />
        </div>
        <div style="flex: 1">
          <img
            style="width: 100%; height: 240px; border-top-right-radius: 5px"
            :src="data.rightLargeImg"
            alt=""
          />
        </div>
      </div>
      <div class="smallImg">
        <div style="flex: 1">
          <img
            style="width: 100%; height: 120px; border-bottom-left-radius: 5px"
            :src="data.leftSmallImg"
            alt=""
          />
        </div>
        <div style="flex: 2; margin: 0px 5px">
          <img style="width: 100%; height: 120px" :src="data.centerSmallImg" alt="" />
        </div>
        <div style="flex: 1">
          <img
            style="width: 100%; height: 120px; border-bottom-right-radius: 5px"
            :src="data.rightSmallImg"
            alt=""
          />
        </div>
      </div>
    </div>
    <div class="position-title">热招职位</div>
    <div class="positions">
      <el-tabs v-model="data.activeIndustryName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane
          v-for="industry in data.industryList"
          :key="industry.id"
          :label="industry.name"
          :name="industry.id"
          >{{ industry.name }}</el-tab-pane
        >
      </el-tabs>
    </div>
    <div class="front-footer">
      <div
        style="
          width: 70%;
          margin: 0 auto;
          line-height: 50px;
          height: 50px;
          font-size: 14px;
          color: #93999f;
          text-align: center;
        "
      >
        <span style="color: #666666">友情链接：</span>
        <span style="margin-left: 10px"
          ><a
            style="margin-left: 10px; color: #93999f"
            href="https://www.baidu.com/"
            target="_blank"
            >百度一下</a
          ></span
        >
        <span style="margin-left: 10px"
          ><a
            style="margin-left: 10px; color: #93999f"
            href="https://www.google.com"
            target="_blank"
            >谷歌一下</a
          ></span
        >
        <span style="margin-left: 10px"
          ><a style="margin-left: 10px; color: #93999f" href="https://www.baidu.com" target="_blank"
            >我的博客</a
          ></span
        >
      </div>
      <div
        style="
          text-align: center;
          line-height: 30px;
          font-size: 13px;
          margin-bottom: 10px;
          color: #93999f;
        "
      >
        Copyright ©2024 www.baidu.cn 版权所有
        <a
          style="margin-left: 10px; color: #93999f"
          href="https://beian.miit.gov.cn/"
          target="_blank"
          >皖ICP备 2023033553号-1</a
        >
      </div>
    </div>
  </div>
</template>

<style scoped>
.home {
  margin: 0 auto;
  width: 70%;
}
.home img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}
.search {
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.advertise .largeImg {
  display: flex;
}

.advertise .smallImg {
  display: flex;
}

.position-title {
  text-align: center;
  font-size: 25px;
  font-weight: bold;
  padding: 30px;
}
</style>
