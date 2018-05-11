package tyust.gjl.facerecognition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tyust.gjl.facerecognition.Utils.CheckUtils;
import tyust.gjl.facerecognition.entity.User;
import tyust.gjl.facerecognition.entity.dto.Response;
import tyust.gjl.facerecognition.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @author : coderWu
 * @date : Created on 21:01 2018/5/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    public Response login(User user, HttpSession session) {
        if (!CheckUtils.loginCheck(user)) {
            return new Response("400", "username or password is empty");
        }
        User loginUser = userService.login(user);
        if (loginUser == null) {
            return new Response("400", "username or password is wrong");
        }
        session.setAttribute("user", user);
        return new Response("200", "login succeeds");
    }


    public Response logout(User user, HttpSession session) {
        User loginUser = (User)session.getAttribute("user");
        if (loginUser == null) {
            return new Response("400", "not login");
        }
        session.removeAttribute("user");
        return new Response("200", "logout succeeds");
    }

    public Response register(User user, @RequestParam(value = "repeatPassword", required = false) String repeatPassword) {
        if (!CheckUtils.loginCheck(user) || null == repeatPassword || "".equals(repeatPassword)) {
            return new Response("400", "please input all information");
        }
        if (!user.getPassword().equals(repeatPassword)) {
            return new Response("400", "repeat password is not equals password");
        }
        boolean register = userService.register(user);
        if (register) {
            return new Response("200", "register succeeds");
        }
        return new Response("400", "register fails");
    }

}
