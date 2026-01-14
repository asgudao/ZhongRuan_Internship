<script setup>
import {ref,onMounted} from 'vue'
import {useRouter} from 'vue-router'
import { ElMessage } from 'element-plus'
import {communityPage,communityDeleteById} from '@/apis/community'

const username = ref('')
const pageNo=ref(1)
const pageSize=ref(8)
const total=ref(0)
const router= useRouter()

const tableData = ref([])

//根据行索引返回样式名称
const tableRowClassName = ({row,rowIndex}) => {
  if (rowIndex%4 === 0) {
    return 'warning-row'
  } else if (rowIndex%4 === 2) {
    return 'success-row'
  }else{
    return ''
  }
}

const searchCommunityPage= async (pageNo,pageSize,username)=>{
    const data = {
        pageNo,
        pageSize,
        username
    }
    const result  = await communityPage(data)
    tableData.value=result.data.list
    total.value = result.data.total
}

onMounted(()=>{
    // 从localStorage中读取保存的pageSize
    const savedPageSize = localStorage.getItem('communityPageSize');
    if (savedPageSize) {
        pageSize.value = parseInt(savedPageSize);
    }
    searchCommunityPage(pageNo.value,pageSize.value,username.value)
})

const handleSearch=()=>{
    searchCommunityPage(pageNo.value,pageSize.value,username.value)
}

//每页数量发生改变回调函数
const handleSizeChange=(value)=>{
    console.log(`pageSize=${value}`)
    // 将选择的pageSize保存到localStorage
    localStorage.setItem('communityPageSize', value.toString());
    searchCommunityPage(pageNo.value,value,username.value)
}
//页码发生改变回调函数
const handleCurrentChange=(value)=>{
    console.log(`pageNo=${value}`)
    searchCommunityPage(value,pageSize.value,username.value)
}


//确认删除回调函数
const handleConfirmDelete= async (id)=>{
    console.log(`id=${id}`)
    //请求后端api做删除操作
    await communityDeleteById({id})
    ElMessage.success("删除问题成功")
    //要重新查询
    searchCommunityPage(pageNo.value,pageSize.value,username.value)
}
</script>

<template>
    <h1>用户管理列表</h1>
    <div class="div1">
        <el-input v-model="username" placeholder="请输入用户名" class="searchInput"/>
        <el-button type="primary" @click="handleSearch" >查询</el-button>
    </div>
    <div class="div1">
        <el-table
            :data="tableData"
            style="width: 100%"
            :row-class-name="tableRowClassName"
        >
            <el-table-column fixed="left" prop="id" label="编号" width="120" />
            <el-table-column prop="username" label="用户名" width="180" />
            <el-table-column prop="content" label="社区内容" width="400" />
            <el-table-column label="社区图片" width="180">
                <template #default="scope">
                    <el-image style="width: 30%;" :src="scope.row.photoPath" fit="fill" ></el-image>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" width="120">
                <template #default="scope">
                    <el-popconfirm
                        confirm-button-text="确定"
                        cancel-button-text="取消"
                        icon="WarningFilled"
                        icon-color="#626AEF"
                        title="是否确认删除此社区内容?"
                        @confirm="handleConfirmDelete(scope.row.id)"
                    >
                        <template #reference>
                            <el-button link type="primary" size="small">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <!-- 分页插件 -->
    <el-pagination
      v-model:current-page="pageNo"
      v-model:page-size="pageSize"
      :page-sizes="[2, 4, 6, 8]"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</template>

<style scoped>
    .div1{
        margin-bottom: 20px;
    }
    .searchInput{
        width: 40%; 
        margin-right: 30px;
    }
    /* :deep() 穿透组件设置样式 */
    :deep(.el-table .warning-row) {
        /* elementplus定义的样式 */
        --el-table-tr-bg-color: var(--el-color-warning-light-9);
        /* 自定义样式也可以 */
        /* background-color: rgba(133, 184, 145, 1); */
    }
    :deep(.el-table .success-row) {
        --el-table-tr-bg-color: var(--el-color-success-light-9);
    }
</style>