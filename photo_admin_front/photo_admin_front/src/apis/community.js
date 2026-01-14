import request from "@/utils/request"

//分页查询admin账号信息(get请求)
const communityPage = (params) => {
    return request({
        url: "/api/community/page",
        method: "get",
        //简化属性
        params
    })
}

//根据id删除
const communityDeleteById = (params) => {
    return request({
        url: `/api/community/delete/${params.id}`,
        method: "get"
    })
}
export{
    communityDeleteById,
    communityPage
}
