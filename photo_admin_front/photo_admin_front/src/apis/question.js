import request from "@/utils/request"

//分页查询admin账号信息(get请求)
const questionPage = (params) => {
    return request({
        url: "/api/question/page",
        method: "get",
        //简化属性
        params
    })
}



//根据id删除
const questionDeleteById = (params) => {
    return request({
        url: `/api/question/delete/${params.id}`,
        method: "get"
    })
}
export{
    questionDeleteById,
    questionPage
}
