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
    const getToken = () => {
      return AccountInfo.value.token
    }
    return { AccountInfo, setAccountInfo, getToken }
  },
  {
    persist: true
  }
)
