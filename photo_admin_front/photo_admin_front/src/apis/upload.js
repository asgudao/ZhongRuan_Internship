import request from "@/utils/request"

//上传图片
const uploadImage = (data) => {
    return request({
        url: "/api/upload/image",
        //post请求
        method: "post",
        //简化属性
        data,
        //一定要设置Content-Type 值是 multipart/form-data
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

//上传视频
const uploadVideo = (data) => {
    return request({
        url: "/api/upload/video",
        //post请求
        method: "post",
        //简化属性
        data,
        //一定要设置Content-Type 值是 multipart/form-data
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

//上传视频
const uploadFile = (data) => {
    return request({
        url: "/api/upload/file",
        //post请求
        method: "post",
        //简化属性
        data,
        //一定要设置Content-Type 值是 multipart/form-data
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

export { uploadImage, uploadVideo, uploadFile }