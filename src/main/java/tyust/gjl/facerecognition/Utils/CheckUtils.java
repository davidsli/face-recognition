package tyust.gjl.facerecognition.Utils;

import tyust.gjl.facerecognition.config.Global;
import tyust.gjl.facerecognition.entity.Person;
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

    public static boolean personCheck(Person person) {
        String personName = person.getPersonName();
        String phone = person.getPhone();
        String photoTime = person.getPhotoTime();
        String photoAddress = person.getPhotoAddress();
        int type = person.getType();
        return personName != null && !"".equals(personName) &&
                phone != null && !"".equals(phone) &&
                photoTime != null && !"".equals(photoTime) &&
                photoAddress != null && !"".equals(photoAddress) &&
                (type == Global.LOST_PERSON || type == Global.FIND_PERSON);
    }

}
