<script setup>
import {reactive,ref,onMounted,computed} from 'vue'
import {ElMessage} from 'element-plus'
import {uploadImage} from '@/apis/upload'
import {adminUpdate,adminDetail} from '@/apis/admin'
import {useRouter,useRoute} from 'vue-router'
import { useAdminStore } from '@/stores/admin'


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

//自定义校验手机号
const checkTelephone=(rule,value,callback)=>{
    if(value){
        const reg= /^1[3456789]\d{9}$/ 
        if(reg.test(value)){
            //校验通过
            callback()
        }else{
            //调用callback(new Error('错误信息')) 校验不通过，被拦截住
            callback(new Error('电话号码是11位数字'))
        }
    }else{
        //没有值就是校验通过
        callback()
    }
}
//自定义校验qq号
const checkQQ=(rule,value,callback)=>{
    if(value){
        const reg= /^\d{5,20}$/ 
        if(reg.test(value)){
            //校验通过
            callback()
        }else{
            callback(new Error('qq号码是5到19位'))
        }
    }else{
        //没有值就是校验通过
        callback()
    }
}
//自定义校验微信
const checkWechat=(rule,value,callback)=>{
    if(value){
        const reg= /^[a-zA-Z0-9_-]{5,40}$/ 
        if(reg.test(value)){
            //校验通过
            callback()
        }else{
            callback(new Error('微信号码是5到40位'))
        }
    }else{
        //没有值就是校验通过
        callback()
    }
}
//表单校验规则
const rules = reactive({
  email:[
    //邮箱不是必填项
    {
        // type 的是email 自动校验
        type: 'email',
        message: '请输入正确的邮箱地址',
        // 触发条件：blur和change
        trigger: ['blur', 'change'],
    }
  ],
  telephone:[
    //自定义校验
    { validator: checkTelephone, trigger: 'blur' }
  ],
  qq:[
    //自定义校验
    { validator: checkQQ, trigger: 'blur' }
  ],
  wechat:[
    //自定义校验
    { validator: checkWechat, trigger: 'blur' }
  ]
})

//必须要有，不然获取不到Form原生的DOM对象
const ruleFormRef=ref()
//提交表单回调函数
const submitForm = () => {
    if (!ruleFormRef.value) return
    ruleFormRef.value.validate((valid, fields) => {
        if (valid) {
            console.log('submit!')
            console.log(dataForm)
            //使用axios请求后端api 
            adminUpdate(dataForm).then((res)=>{
                ElMessage.success("更新管理员用户成功")
                router.push({
                    path:'/admin/page'
                })
            })
        } else {
            console.log('error submit!', fields)
        }
    })
}
//重置表单元素的数据为初始化状态，回调函数
const resetForm = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
}
//上传文件之前校验数据和自定义上传逻辑，不触发组件的默认action动作
const beforeAvatarUpload= async (rawFile)=>{
    console.log('beforeAvatarUpload.......')
    if (!(rawFile.type == 'image/jpeg' || rawFile.type=='image/png' || rawFile.type == 'image/jpg')) {
        ElMessage.error('上传头像为jpeg、png和jpg格式!')
        return false
    } else if (rawFile.size / 1024 / 1024 > 5) {
        ElMessage.error('上传头像大小不能超过5MB!')
        return false
    }
    console.log(rawFile)
    //调用自定义上传函数 ，参数 对象的属性名称与后端api的 @RequestParam("参数名称") 保持一致
    const result = await uploadImage({image:rawFile})
    console.log(result)
    //把返回的图片 url地址赋值dataForm.avatarPath 页面就会刷新 回显上传的图片
    dataForm.avatarPath=result.data

    //不放行，目的是不触发上传组件的action动作
    return false
}
//返回
const handleReturnBack=()=>{
    router.push({
        path:'/admin/page'
    })
}
</script>

<template>
    <h1>编辑管理员</h1>
    <el-form
        ref="ruleFormRef"
        style="max-width: 600px"
        :model="dataForm"
        :rules="rules"
        label-width="auto"
    >
        <el-form-item label="用户名" prop="username">
            <el-input v-model="dataForm.username" disabled/>
        </el-form-item>
        <el-form-item label="用户头像">
            <!-- action 设置为空，不使用el-upload默认的上传功能，自己定义写上传 -->
            <el-upload
                class="avatar-uploader"
                action=""
                :show-file-list="false"
                :before-upload="beforeAvatarUpload"
            >
                <img v-if="dataForm.avatarPath" :src="dataForm.avatarPath" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
            <el-input v-model="dataForm.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="telephone">
            <el-input v-model="dataForm.telephone" />
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
            <el-input v-model="dataForm.qq" />
        </el-form-item>
        <el-form-item label="微信" prop="wechat">
            <el-input v-model="dataForm.wechat" />
        </el-form-item>
        <el-form-item label=" ">
            <el-button type="primary" @click="submitForm">
                保存
            </el-button>
            <el-button @click="resetForm">重置</el-button>
            <el-button @click="handleReturnBack">返回</el-button>
        </el-form-item>
    </el-form>
</template>

<style scoped>
:deep(.avatar-uploader .el-upload) {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

:deep(.avatar-uploader .el-upload:hover) {
  border-color: var(--el-color-primary);
}

:deep(.el-icon.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>