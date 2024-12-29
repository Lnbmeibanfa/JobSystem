<script setup>
import { reactive } from 'vue'
import { selectAllAdvertiseAPI } from '@/api/advertise'
import { selectAllIndustryAPI } from '@/api/industry'
import { selectAllPositionAPI } from '@/api/position'
import PositionShower from '../components/PositionShower.vue'
import { ElMessage } from 'element-plus'
import router from '@/router'
const data = reactive({
  leftLargeAd: {},
  centerLargeAd: {},
  rightLargeAd: {},
  leftSmallAd: {},
  centerSmallAd: {},
  rightSmallAd: {},
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
            data.leftLargeAd.img = item.img
            data.leftLargeAd.positionId = item.positionId
            break
          case '中心大图':
            data.centerLargeAd.img = item.img
            data.centerLargeAd.positionId = item.positionId
            break
          case '右侧大图':
            data.rightLargeAd.img = item.img
            data.rightLargeAd.positionId = item.positionId
            break
          case '左侧小图':
            data.leftSmallAd.img = item.img
            data.leftSmallAd.positionId = item.positionId
            break
          case '中心小图':
            data.centerSmallAd.img = item.img
            data.centerSmallAd.positionId = item.positionId
            break
          case '右侧小图':
            data.rightSmallAd.img = item.img
            data.rightSmallAd.positionId = item.positionId
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
      data.activeIndustryName = data.industryList[0].id
      handleClick(data.activeIndustryName)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleClick = (industryId) => {
  selectAllPositionAPI(industryId).then((res) => {
    if (res.code === '200') {
      data.positionList = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const navTo = (positionId) => {
  const preUrl = '/front/position?id='
  router.push(preUrl + positionId)
}

const search = () => {
  console.log('search')
}
loadIndustry()
loadAdvertiseImg()
</script>

<template>
  <div class="home-box">
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
              style="width: 100%; height: 240px; border-top-left-radius: 5px; cursor: pointer"
              :src="data.leftLargeAd.img"
              @click="navTo(data.leftLargeAd.positionId)"
              alt=""
            />
          </div>
          <div style="flex: 2; margin: 0px 5px; cursor: pointer">
            <img
              style="width: 100%; height: 240px"
              :src="data.centerLargeAd.img"
              alt=""
              @click="navTo(data.centerLargeAd.positionId)"
            />
          </div>
          <div style="flex: 1">
            <img
              style="width: 100%; height: 240px; border-top-right-radius: 5px; cursor: pointer"
              :src="data.rightLargeAd.img"
              @click="navTo(data.rightLargeAd.positionId)"
              alt=""
            />
          </div>
        </div>
        <div class="smallImg">
          <div style="flex: 1">
            <img
              style="width: 100%; height: 120px; border-bottom-left-radius: 5px; cursor: pointer"
              :src="data.leftSmallAd.img"
              @click="navTo(data.leftSmallAd.positionId)"
              alt=""
            />
          </div>
          <div style="flex: 2; margin: 0px 5px; cursor: pointer">
            <img
              style="width: 100%; height: 120px"
              :src="data.centerSmallAd.img"
              alt=""
              @click="navTo(data.centerSmallAd.positionId)"
            />
          </div>
          <div style="flex: 1">
            <img
              style="width: 100%; height: 120px; border-bottom-right-radius: 5px; cursor: pointer"
              :src="data.rightSmallAd.img"
              @click="navTo(data.rightSmallAd.positionId)"
              alt=""
            />
          </div>
        </div>
      </div>
      <div class="position-title">热招职位</div>
      <div class="positions">
        <el-tabs v-model="data.activeIndustryName" class="demo-tabs" @tab-change="handleClick">
          <el-tab-pane
            v-for="industry in data.industryList"
            :key="industry.id"
            :label="industry.name"
            :name="industry.id"
          >
            <el-row :gutter="10">
              <el-col :span="8" v-for="position in data.positionList" :key="position.id"
                ><position-shower
                  style="cursor: pointer"
                  @click="navTo(position.id)"
                  :positionInfo="position"
                />
              </el-col>
            </el-row>
          </el-tab-pane>
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
            ><a
              style="margin-left: 10px; color: #93999f"
              href="https://www.baidu.com"
              target="_blank"
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
  </div>
</template>

<style scoped>
.home-box {
  background-color: #f6f6f8;
}
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
