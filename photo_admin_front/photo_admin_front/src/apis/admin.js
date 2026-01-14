//@ 表示 src路径
//导入axios对象
import request from "@/utils/request"

//登录,data对象，传后端数据（数据格式是 form-urlencoded）
const login = (data) => {
    //调用axios({})  返回Promise
    return request({
        //这里与proxy配置有关系
        //  /api/login  代理成 http://localhost:808/login 进行访问
        url: "/api/login",
        method: "post",
        //简化属性
        data
    })
}

//分页查询admin账号信息(get请求)
const adminPage = (params) => {
    return request({
        url: "/api/admin/page",
        method: "get",
        //简化属性
        params
    })
}



//新增保存数据
const adminAdd = (data) => {
    return request({
        url: "/api/admin/add",
        method: "post",
        //简化属性
        data
    })
}

//校验用户名是否存在
const adminCheckExist = (params) => {
    return request({
        url: "/api/admin/check-exist",
        method: "get",
        //简化属性
        params
    })
}

//根据id查询用户信息
const adminDetail = (id) => {
    return request({
        url: `/api/admin/detail/${id}`,
        method: "get"
    })
}

//更新用户（数据格式是json）
const adminUpdate = (data) => {
    return request({
        url: "/api/admin/update",
        method: "post",
        //简化属性
        data,
        //请求的数据是json，加入如下代码
        headers: { 'Content-Type': 'application/json;charset=utf-8' }
    })
}

//根据id删除
const adminDeleteById = (params) => {
    return request({
        url: `/api/admin/delete/${params.id}`,
        method: "get"
    })
}

export { login, adminPage, adminAdd, adminCheckExist, adminDetail, adminUpdate, adminDeleteById }