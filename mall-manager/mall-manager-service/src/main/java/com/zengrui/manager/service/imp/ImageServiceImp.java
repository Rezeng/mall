package com.zengrui.manager.service.imp;

import com.zengrui.manager.service.interfaces.ImageService;
import com.zengrui.utils.FtpUtils;
import com.zengrui.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zeng Rui on 2018/3/9.
 */
@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    FtpUtils ftpUtils;
    @Value("${FTP_URL}")
    String FTP_URL;

    @Override
    public String uploadImg(MultipartFile file) {
        String imgURL;
        //保存图片，并返回URL
        if((imgURL=saveImg(file))!=null){
            //生成返回信息
            return imgURL;
        }
        else
            return null;

    }

    /**
     * 保存图片
     * @param file
     * @return 图片URL
     */
    private String saveImg(MultipartFile file){
        if(file.isEmpty())
            return null;
        String[] oldname= file.getOriginalFilename().split("\\.");

        String suffix = oldname[oldname.length-1];
        //重新生成文件名字
        String newfilename  = IdUtils.getImgId(file)+"."+suffix;
        //获取文件夹目录
        String[] dirlist = ftpUtils.getDirList("");
        //System.out.println(dirlist[0]);
        //获取最后一个文件夹的名字
        int dirnum = Integer.parseInt(dirlist[dirlist.length-1]);
        int num=  0;
        //如果当前目录的文件数量>10000
        if((num= ftpUtils.getFileNum(dirlist[dirlist.length-1]))>10000){
            //新目录名= 旧目录名+1
            dirnum++;
        }
        //生成新的目录
        String newpath = ftpUtils.getBasePath()+"/"+dirnum;
        try {
            //FileInputStream inputStream = new FileInputStream((File) file);
            if(!ftpUtils.uploadFile(newpath,newfilename,file.getInputStream())){
               return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String URL = "http://"+FTP_URL+"/"+dirnum+"/"+newfilename;
        System.out.println(URL);
        return URL;


    }
}
