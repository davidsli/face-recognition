package tyust.gjl.facerecognition.entity.dto;

/**
 * @author : coderWu
 * @date : Created on 21:03 2018/5/11
 */
public class Response {

    private String code;
    private Object data;

    public Response(){
        this.code = "400";
        this.data = "error";
    }

    public Response(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public Response setCode(String code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }
}
