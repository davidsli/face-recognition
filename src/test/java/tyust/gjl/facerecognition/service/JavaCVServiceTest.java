package tyust.gjl.facerecognition.service;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import tyust.gjl.facerecognition.Utils.ImageGrayscale;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author : coderWu
 * @date : Created on 11:58 2018/5/5
 */
public class JavaCVServiceTest {

    private JavaCVService javaCVService = new JavaCVService();

    @Test
    public void smooth() throws Exception {
        File faceImagesDic = new ClassPathResource("static/upload/images").getFile();
        File[] faceImages = faceImagesDic.listFiles();
        for (int i = 0; i < faceImages.length; i++) {
            for (int j = i + 1; j < faceImages.length; j++) {
                compare(faceImages[i].getName(), faceImages[j].getName());
            }
//            System.out.println(faceImages[i].getPath());
//            javaCVService.detectFace(faceImages[i].getPath(), faceImages[i].getName(), true);
        }
    }

    public void compare(String name1, String name2) throws Exception {
        String path1 = new ClassPathResource("static/upload/images/" + name1).getFile().getPath();
        String path2 = new ClassPathResource("static/upload/images/" + name2).getFile().getPath();
        javaCVService.detectFace(path1, name1, true);
        javaCVService.detectFace(path2, name2, true);
        System.out.println(javaCVService.comparePictures(path1, path2));
        javaCVService.smooth(path1);
        javaCVService.smooth(path2);
        System.out.println(javaCVService.comparePictures(path1, path2));

        String facepath1 = new ClassPathResource("static/upload/faceimages/" + name1).getFile().getPath();
        String facepath2 = new ClassPathResource("static/upload/faceimages/" + name2).getFile().getPath();
        System.out.println(javaCVService.comparePictures(facepath1, facepath2));
        javaCVService.smooth(facepath1);
        javaCVService.smooth(facepath1);
        System.out.println(javaCVService.comparePictures(facepath1, facepath2));
    }

    @Test
    public void compare() throws Exception {
        String path2 = new ClassPathResource("static/upload/faceimages/1ba11a5c96c640c698e93426865d47a3.jpeg").getFile().getPath();
        String facepath2 = new ClassPathResource("static/upload/faceimages/dd846eb1a4ae4925905cbe3d92fe0d3f.jpeg").getFile().getPath();
        System.out.println(javaCVService.comparePictures(path2, facepath2));

    }
}