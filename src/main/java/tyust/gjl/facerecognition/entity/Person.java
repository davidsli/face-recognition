package tyust.gjl.facerecognition.entity;

/**
 * @author : coderWu
 * @date : Created on 15:37 2018/5/5
 */
public class Person {

    private int personId;
    private int userId;
    private String personName;
    private String photoName;
    private String phone;
    private String photoAddress;
    private String photoTime;
    private Integer type;

    public String getPersonName() {
        return personName;
    }

    public Person setPersonName(String personName) {
        this.personName = personName;
        return this;
    }

    public int getPersonId() {
        return personId;
    }

    public Person setPersonId(int personId) {
        this.personId = personId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Person setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getPhotoName() {
        return photoName;
    }

    public Person setPhotoName(String photoName) {
        this.photoName = photoName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public Person setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
        return this;
    }

    public String getPhotoTime() {
        return photoTime;
    }

    public Person setPhotoTime(String photoTime) {
        this.photoTime = photoTime;
        return this;
    }


    public int getType() {
        return type;
    }

    public Person setType(int type) {
        this.type = type;
        return this;
    }
}
