package com.wzx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/share/upload")
public class ShareController {
    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    private String getFileList(String filePath) {
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        StringBuilder list = new StringBuilder();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                list.append("File: ").append(listOfFiles[i].getName()).append("</ br>");
            } else if (listOfFiles[i].isDirectory()) {
                list.append("Directory: ").append(listOfFiles[i].getName()).append("</ br>");
            }
        }
        return list.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", true);
            logger.info("Didn't select file");
            return "share";
        }
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir");
        File dest = new File(filePath + "/" + fileName);
        try {
            file.transferTo(dest);
            model.addAttribute("success", true);
            String list = this.getFileList(filePath);
            model.addAttribute("fileList", list);
            logger.info("success!");
        } catch (IOException e) {
            System.out.println("fail");
        }
        return "share";
    }
}
