package com.wangqifan.toutiao.Service;


import com.wangqifan.toutiao.util.ToutiaoUtil;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    String ACCESS_KEY = "jt2ZPFgH2qxDQnCcxsQcopy6dWFYbJUU4bicTrSF";
    String SECRET_KEY = "rRKzd5R3dbQgqSOkFE4bXi2foXWe03S9qN5bWdiy";
    //要上传的空间
    String bucketname = "toutiao";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    private static String QINIU_IMAGE_DOMAIN ="http://pbqjblyft.bkt.clouddn.com/";

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了 
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
//            System.out.println("======"+fileName+"=======");
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            if (res.isOK() && res.isJson()) {
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            return null;
        }
    }

}
