import { ref } from 'vue'
import { defineStore } from 'pinia'
import { cloneDeep } from 'lodash'

export const useAccountStore = defineStore(
  'userInfo',
  () => {
    const AccountInfo = ref({})
    const setAccountInfo = (data) => {
      AccountInfo.value = cloneDeep(data)
    }
    const setToken = (token) => {
      AccountInfo.value.token = token
    }
    const getToken = () => {
      return AccountInfo.value.token
    }
    return { AccountInfo, setAccountInfo, getToken, setToken }
  },
  {
    persist: true
  }
)
