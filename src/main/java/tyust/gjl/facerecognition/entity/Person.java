package tyust.gjl.facerecognition.entity;

/**
 * @author : coderWu
 * @date : Created on 15:37 2018/5/5
 */
public class Person {

    String username;
    String imagename;

    public String getUsername() {
        return username;
    }

    public Person setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getImagename() {
        return imagename;
    }

    public Person setImagename(String imagename) {
        this.imagename = imagename;
        return this;
    }
}
