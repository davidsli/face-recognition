package tyust.gjl.facerecognition.entity.dto;

/**
 * @author : coderWu
 * @date : Created on 21:03 2018/5/11
 */
public class Response {

    private String code;
    private String data;


    public String getCode() {
        return code;
    }

    public Response setCode(String code) {
        this.code = code;
        return this;
    }

    public String getData() {
        return data;
    }

    public Response setData(String data) {
        this.data = data;
        return this;
    }
}
