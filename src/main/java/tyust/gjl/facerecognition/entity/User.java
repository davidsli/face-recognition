package tyust.gjl.facerecognition.entity;

/**
 * @author : coderWu
 * @date : Created on 20:02 2018/5/11
 */
public class User {

    private int userId;
    private String username;
    private String password;

    public int getUserId() {
        return userId;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
