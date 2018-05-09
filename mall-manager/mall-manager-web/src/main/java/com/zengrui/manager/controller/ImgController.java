package com.zengrui.manager.controller;

import com.zengrui.manager.service.interfaces.ImageService;
import com.zengrui.pojo.KindEditorResult;
import com.zengrui.pojo.Result;
import com.zengrui.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Zeng Rui on 2018/3/10.
 */

@RestController
public class ImgController {
    @Autowired
    ImageService imageService;


    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    public Result<Object> imgUpload(@RequestParam("file") MultipartFile file){

        //上传图片并获取图片URL
        String imgURL = imageService.uploadImg(file);
        //生成返回数据
        if(imgURL!=null){
            ResultUtils<Object> resultUtils = new ResultUtils<>(true);
            resultUtils.setData(imgURL);
            //Gson gson =new Gson();
            //System.out.println(gson.toJson(resultUtils.getResult()));
            return resultUtils.getResult();
        }
        else
        {
            ResultUtils<Object> resultUtils = new ResultUtils<>(false);
            return resultUtils.getResult();
        }

    }


    /**
     * 富文本编辑器的图片上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/kindeditor/upload",method = RequestMethod.POST)
    public KindEditorResult kindEditorImgUpload(@RequestParam("imgFile") MultipartFile file){

        //上传图片并获取图片URL
        String imgURL = imageService.uploadImg(file);
        //生成返回数据
        KindEditorResult result  = new KindEditorResult();
        if(imgURL!=null){
           result.setError(0);
           result.setUrl(imgURL);
        }
        else
        {
            result.setError(1);
            result.setMessage("上传失败");
        }
        return result;

    }





}
