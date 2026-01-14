<script setup>
import {reactive,ref,onMounted} from 'vue'
import {adminUpdate,adminDetail} from '@/apis/admin'
import {useRouter,useRoute} from 'vue-router'
import { useAdminStore } from '@/stores/admin'

const router = useRouter()
const route = useRoute()
const adminStore = useAdminStore()
// 获取当前登录账号的ID
const currentUserId = adminStore.getAdmin && adminStore.getAdmin.id ? adminStore.getAdmin.id : null

//登录定义数据
const dataForm = reactive({
  id:0,
  username: '',
  avatarPath:'',
  email:'',
  telephone:'',
  qq:'',
  wechat:''
})
//vue的钩子函数
onMounted(async ()=>{
    //获取路由的query传的参数
    const id = currentUserId
    //todo 请求后端api 
    const admin = (await adminDetail(id)).data
    console.log(admin)
    dataForm.id=admin.id
    dataForm.username=admin.username
    dataForm.avatarPath=admin.avatarPath
    dataForm.email=admin.email
    dataForm.telephone=admin.telephone
    dataForm.qq=admin.qq
    dataForm.wechat=admin.wechat
})
const handleUpdate=()=>{
    router.push({
        path:'/account',
    })
}
</script>

<template>
    <h1>管理员信息</h1>
    <el-form
        style="max-width: 600px"
        :model="dataForm"
        :rules="rules"
        label-width="auto"
    >
        <el-form-item label="用户头像">
            <img v-if="dataForm.avatarPath" :src="dataForm.avatarPath" class="avatar" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
            <el-input v-model="dataForm.username" disabled/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
            <el-input v-model="dataForm.email" disabled/>
        </el-form-item>
        <el-form-item label="手机号" prop="telephone">
            <el-input v-model="dataForm.telephone" disabled/>
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
            <el-input v-model="dataForm.qq" disabled/>
        </el-form-item>
        <el-form-item label="微信" prop="wechat">
            <el-input v-model="dataForm.wechat" disabled/>
        </el-form-item>
        <el-form-item label=" ">
            <el-button @click="handleUpdate">修改信息</el-button>
        </el-form-item>
    </el-form>
</template>

<style scoped>
.avatar {
    width: 178px;
    height: 178px;
    display: block;
    border-radius: 10px;
}
</style>