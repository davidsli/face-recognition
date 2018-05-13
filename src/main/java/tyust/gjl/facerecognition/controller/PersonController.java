package tyust.gjl.facerecognition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tyust.gjl.facerecognition.Utils.CheckUtils;
import tyust.gjl.facerecognition.Utils.FileUtils;
import tyust.gjl.facerecognition.config.Global;
import tyust.gjl.facerecognition.entity.Person;
import tyust.gjl.facerecognition.entity.User;
import tyust.gjl.facerecognition.entity.dto.Detect;
import tyust.gjl.facerecognition.entity.dto.Response;
import tyust.gjl.facerecognition.service.JavaCVService;
import tyust.gjl.facerecognition.service.PersonService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author : coderWu
 * @date : Created on 11:31 2018/5/5
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    JavaCVService javaCVService;
    @Autowired
    PersonService personService;

    @RequestMapping("/add")
    public Response add(Person person,
                        @RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws Exception {

        if (file.isEmpty()) {
            return new Response("400", "please add picture");
        }
        if (!CheckUtils.personCheck(person)) {
            return new Response("400", "please input all information");
        }
        String[] pathAndImageName = FileUtils.saveImage("static/upload/images", file);
        if (pathAndImageName == null) {
            return new Response("400", "photo save fails");
        }

        if (javaCVService.detectFace(pathAndImageName[0], pathAndImageName[1], person.getType(), true)) {
            person.setPhotoName(pathAndImageName[1]);
            person.setUserId(((User)session.getAttribute("user")).getUserId());
            personService.add(person);
            return new Response("200", "success");
        } else {
            return new Response("400", "the picture has no face or more than on face");
        }
    }

    @RequestMapping("/search")
    public Response detect(@RequestParam(value = "file", required = false) MultipartFile file,
                           @RequestParam(value = "type", required = false) Integer type) throws Exception {

        if (file.isEmpty()) {
            return new Response("400", "please add picture");
        }
        if (type == null || type <= 0 || type > 2) {
            return new Response("400", "select a type");
        }
        String[] pathAndImageName = FileUtils.saveImage("static/upload/images", file);
        if (pathAndImageName == null) {
            return new Response("400", "photo save fails");
        }
        if (!javaCVService.detectFace(pathAndImageName[0], pathAndImageName[1], 0, false)) {
            return new Response("400", "the picture has no face or more than on face");
        }

        File faceImagesDic = new
                ClassPathResource(type == Global.LOST_PERSON ? "static/upload/lostfaceimages" : "static/upload/findfaceimages").getFile();
        File[] faceImages = faceImagesDic.listFiles();
        if (faceImages == null) {
            return new Response("200", null);
        }

        List<Detect> result = new ArrayList<>(faceImages.length);
        Future<Detect> detect;
        Detect detectValue;
        for (File faceImage : faceImages) {
            detect = javaCVService.comparePictures(faceImage, new File(pathAndImageName[0]));
            if (detect != null && (detectValue = detect.get()) != null) {
                result.add(detectValue);
            }
        }
        if (result.size() <= 0) {
            return new Response("400", "no match picture");
        } else {
            return new Response("200", result);
        }
    }

    @RequestMapping("/show")
    public Response show() {
        List<Person> lost = personService.getPersons(Global.LOST_PERSON);
        List<Person> find = personService.getPersons(Global.FIND_PERSON);
        Map<String, List<Person>> results = new HashMap<>();
        results.put("lostChildren", lost);
        results.put("findChildren", find);
        return new Response("200", results);
    }

}
