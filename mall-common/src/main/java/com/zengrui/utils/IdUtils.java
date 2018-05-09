package com.zengrui.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Zeng Rui on 2018/3/10.
 */
public class IdUtils {

    public static String getImgId(MultipartFile file){
        //取文件长度
        long len = file.getSize();
        //取时间
        long mills = System.currentTimeMillis();
        Random random = new Random();
        //取随机数
        int end = random.nextInt(999);
        String id =String.valueOf(len)+String.valueOf(mills)+String.format("%03d", end);
        byte[] bytes = id.getBytes();
        final BASE64Encoder encoder = new BASE64Encoder();
        id = encoder.encode(bytes);
        return id;
    }

/*
    public static void main(String[] args) {
        //System.out.println(getImgId(new File("C:\\Users\\Zeng Rui\\Desktop\\test.jpg")));
        for (int i = 0; i <1000 ; i++) {
            System.out.println(getItemId());
        }



    }
*/

    /**
     * 生成最多10位的long id
     * @return id
     */
    public static long getItemId(){
        //生成6位long id
        Random random = new Random();
        String id = String.valueOf(random.nextInt(10));
        for (int i = 0; i <9; i++) {
            id+=String.valueOf(random.nextInt(10));

        }
       return Long.valueOf(id);




    }


}
