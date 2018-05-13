package tyust.gjl.facerecognition.Utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author : coderWu
 * @date : Created on 21:50 2018/5/11
 */
public class FileUtils {

    public static String[] saveImage(String classPath, MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String contentType = file.getContentType();
            String imageName = contentType.substring(contentType.indexOf("/") + 1);
            String rootPath = new ClassPathResource(classPath).getFile().getPath();
            imageName = uuid + "." + imageName;
            String path = rootPath + "/" + imageName;
            file.transferTo(new File(path));
            return new String[] {path, imageName};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
