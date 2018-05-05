package tyust.gjl.facerecognition.Utils;

import java.util.*;


public class ControllerUtils {
    public static void setReturnData (Map<String, Object> map, String code, Object obj) {
        map.put("code", code);
        map.put("data", obj);
    }
}
