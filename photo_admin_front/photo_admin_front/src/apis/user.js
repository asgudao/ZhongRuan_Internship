//@ 表示 src路径
//导入axios对象
import request from "@/utils/request"

//分页查询admin账号信息(get请求)
const userPage = (params) => {
    return request({
        url: "/api/user/page",
        method: "get",
        //简化属性
        params
    })
}


//新增保存数据
const userAdd = (data) => {
    return request({
        url: "/api/user/add",
        method: "post",
        //简化属性
        data
    })
}

//校验用户名是否存在
const userCheckExist = (params) => {
    return request({
        url: "/api/user/check-exist",
        method: "get",
        //简化属性
        params
    })
}

//根据id查询用户信息
const userDetail = (id) => {
    return request({
        url: `/api/user/detail/${id}`,
        method: "get"
    })
}

//更新用户（数据格式是json）
const userUpdate = (data) => {
    return request({
        url: "/api/user/update",
        method: "post",
        //简化属性
        data,
        //请求的数据是json，加入如下代码
        headers: { 'Content-Type': 'application/json;charset=utf-8' }
    })
}

//根据id删除
const userDeleteById = (params) => {
    return request({
        url: `/api/user/delete/${params.id}`,
        method: "get"
    })
}
export{
    userPage,
    userAdd,
    userCheckExist,
    userDetail,
    userUpdate,
    userDeleteById
}
