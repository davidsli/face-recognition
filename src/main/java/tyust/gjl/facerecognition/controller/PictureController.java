package tyust.gjl.facerecognition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tyust.gjl.facerecognition.Utils.ControllerUtils;
import tyust.gjl.facerecognition.entity.dto.Detect;
import tyust.gjl.facerecognition.service.JavaCVService;
import tyust.gjl.facerecognition.service.PersonService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author : coderWu
 * @date : Created on 11:31 2018/5/5
 */
@Controller
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    JavaCVService javaCVService;
    @Autowired
    PersonService userService;

    @ResponseBody
    @RequestMapping("/add")
    public HashMap<String, Object> add(@RequestParam(value = "username", required = false) String username,
                                       @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        HashMap<String, Object> data = new HashMap<>();
        if (file.isEmpty()) {
            ControllerUtils.setReturnData(data, "400", "please add picture");
            return data;
        }
        if (username == null || "".equals(username)) {
            ControllerUtils.setReturnData(data, "400", "please input username");
            return data;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String contentType = file.getContentType();
        String imageName = contentType.substring(contentType.indexOf("/") + 1);
        String rootPath = new ClassPathResource("static/upload/images").getFile().getPath();
        imageName = uuid + "." + imageName;
        String path = rootPath + "/" + imageName;
        file.transferTo(new File(path));

        if (javaCVService.detectFace(imageName, true)) {
            userService.add(username, imageName);
            ControllerUtils.setReturnData(data, "200", "success");
        } else {
            ControllerUtils.setReturnData(data, "400", "the picture has no face or more than on face");
        }
        return data;
    }

    @ResponseBody
    @RequestMapping("/search")
    public HashMap<String, Object> detect(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        HashMap<String, Object> data = new HashMap<>();
        if (file.isEmpty()) {
            ControllerUtils.setReturnData(data, "400", "please add picture");
            return data;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String contentType = file.getContentType();
        String imageName = contentType.substring(contentType.indexOf("/") + 1);
        String rootPath = new ClassPathResource("static/upload/images").getFile().getPath();
        imageName = uuid + "." + imageName;
        String path = rootPath + "/" + imageName;
        file.transferTo(new File(path));

        if (!javaCVService.detectFace(imageName, false)) {
            ControllerUtils.setReturnData(data, "400", "the picture has no face or more than on face");
            return data;
        }

        File faceImagesDic = new ClassPathResource("static/upload/faceimages").getFile();
        File[] faceImages = faceImagesDic.listFiles();
        if (faceImages == null) {
            ControllerUtils.setReturnData(data, "200", null);
            return data;
        }

        List<Detect> result = new ArrayList<>(faceImages.length);
        Future<Detect> detect;
        Detect detectValue;
        for (File faceImage : faceImages) {
            detect = javaCVService.comparePictures(faceImage, new File(path));
            if (detect != null && (detectValue = detect.get()) != null) {
                result.add(detectValue);
            }
        }
        if (result.size() <= 0) {
            ControllerUtils.setReturnData(data,"400", "no match picture");
        } else {
            ControllerUtils.setReturnData(data,"200", result);
        }
        return data;
    }

}
