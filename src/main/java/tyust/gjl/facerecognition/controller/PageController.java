package tyust.gjl.facerecognition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : coderWu
 * @date : Created on 10:13 2018/5/5
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/add")
    public String add() {
        return "addPictures";
    }

    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

}
