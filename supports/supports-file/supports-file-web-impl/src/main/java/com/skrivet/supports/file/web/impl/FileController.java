package com.skrivet.supports.file.web.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.file.web.core.FileService;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@SkrivetDoc
@Controller
@RequestMapping("/file")
@ApiSort(value = 13)
@DynamicDataBase
@Api(tags = "文件接口")
public class FileController extends BasicController {
    @Autowired
    private FileService fileService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "文件上传")
    @ResponseBody
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public ResponseJson<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new ValidationExp("文件不能为空");
        }
        return ResponseBuilder.success(fileService.storeFile(file.getOriginalFilename(), file.getInputStream(), file.getContentType()));
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "文件下载")
    @ResponseBody
    @ApiOperation(value = "文件下载")
    @GetMapping("/downLoad")
    public void upload(String id, HttpServletResponse response) throws Exception {
        fileService.writeFile(id, response.getOutputStream());
    }
}
