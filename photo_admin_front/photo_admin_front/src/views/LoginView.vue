<script setup>
import { ref, reactive } from 'vue'
import { login } from '@/apis/admin'
import { useAdminStore } from '@/stores/admin'
import { useRouter } from 'vue-router'
// 从element-plus直接引入图标组件
import {User,Lock} from '@element-plus/icons-vue'


const router = useRouter()
const adminStore = useAdminStore()

// 登录数据（逻辑不变）
const dataForm = reactive({
  username: '',
  password: ''
})

// 表单校验规则（逻辑不变）
const rules = reactive({
  username: [
    { required: true, message: '用户名不能为空', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度3到20个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 3, max: 20, message: '密码长度3到20个字符', trigger: 'blur' },
  ]
})

const ruleFormRef = ref()

// 提交表单（逻辑不变）
const submitForm = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      login(dataForm).then((res) => {
        adminStore.saveToken(res.message)
        adminStore.saveAdmin(res.data)
        router.push({ path: '/home' })
      })
    } else {
      console.log('error submit!', fields)
    }
  })
}

// 重置表单（逻辑不变）
const resetForm = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
}
</script>

<template>
  <div class="login-page">
    <!-- 登录卡片 -->
    <div class="login-card">
      <!-- 标题区域 -->
      <div class="login-title">
        <el-icon class="title-icon">
          <User />
        </el-icon>
        <h1>管理员登录</h1>
      </div>

      <!-- 登录表单 -->
      <el-form
        ref="ruleFormRef"
        :model="dataForm"
        :rules="rules"
        label-width="80px"
        class="login-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input 
            v-model="dataForm.username" 
            placeholder="请输入用户名"
            class="custom-input"
          >
            <template #prefix>
              <el-icon class="input-icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="dataForm.password" 
            type="password" 
            show-password
            placeholder="请输入密码"
            class="custom-input"
          >
            <template #prefix>
              <el-icon class="input-icon"><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item class="form-actions">
          <el-button type="primary" @click="submitForm" class="login-btn">登录</el-button>
          <el-button @click="resetForm" class="reset-btn">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 页脚信息 -->
      <div class="login-footer">
        © 2024 管理员系统
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局样式 */
.login-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url('@/assets/login.jpg'); 
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  padding: 20px;
  box-sizing: border-box;
}

/* 登录卡片样式 */
.login-card {
  width: 100%;
  max-width: 400px;
  background-color: rgba(226, 226, 226, 0.8);
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 30px;
  box-sizing: border-box;
}

/* 标题样式 */
.login-title {
  text-align: center;
  margin-bottom: 30px;
}

.login-title h1 {
  margin: 15px 0 0 0;
  font-size: 22px;
  color: #303133;
}

.title-icon {
  font-size: 36px;
  color: #409eff;
}

/* 表单样式 */
.login-form {
  margin-top: 20px;
}

/* 输入框样式 */
.custom-input {
  border-radius: 4px;
}

.input-icon {
  color: #c0c4cc;
}

/* 按钮区域 */
.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.login-btn {
  flex: 1;
}

.reset-btn {
  flex: 1;
}

/* 页脚样式 */
.login-footer {
  text-align: center;
  margin-top: 25px;
  color: #909399;
  font-size: 12px;
}
</style>
    