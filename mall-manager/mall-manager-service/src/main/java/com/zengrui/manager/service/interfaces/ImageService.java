package com.zengrui.manager.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by Zeng Rui on 2018/3/9.
 */
public interface ImageService {
     String uploadImg(MultipartFile multipartFile);

}
