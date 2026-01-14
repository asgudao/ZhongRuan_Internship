import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

//调用defineStore函数返回store函数
const useAdminStore = defineStore(
    //id
    'admin',
    () => {
        //定义token
        const token = ref('')
        //定义admin
        const admin = ref({})
        //定义计算属性(注意：只定义get不能set)
        const getToken = computed(() => token.value)
        const getAdmin = computed(() => admin.value)
        //保存token
        const saveToken = (value) => {
            token.value = value
        }
        const saveAdmin = (value) => {
            admin.value = value
        }
        //暴露 变量、计算属性、action等
        return {
            token,
            getToken,
            saveToken,
            admin,
            getAdmin,
            saveAdmin
        }
    },
    //持久化
    {
        //默认持久化到localStorage ，key为id值（例如：admin），value值就是 定义变量（例如：token）
        persist: true
    }
)

export { useAdminStore }