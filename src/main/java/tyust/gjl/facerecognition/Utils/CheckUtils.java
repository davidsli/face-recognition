package tyust.gjl.facerecognition.Utils;

import tyust.gjl.facerecognition.entity.User;

/**
 * @author : coderWu
 * @date : Created on 21:05 2018/5/11
 */
public class CheckUtils {

    public static boolean loginCheck(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        return username != null && password != null &&
                !"".equals(username) && !"".equals(password);
    }

}
