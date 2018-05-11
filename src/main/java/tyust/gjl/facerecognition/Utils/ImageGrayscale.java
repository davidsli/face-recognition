package tyust.gjl.facerecognition.Utils;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author : coderWu
 * @date : Created on 16:40 2018/5/6
 */
public class ImageGrayscale {

    public static void transfer(String imageName) throws Exception {
        String path;
        try {
            path = new ClassPathResource("static/upload/images/" + imageName).getFile().getPath();
        } catch (Exception e) {
            path = null;
        }
        if (path != null) {
            BufferedImage image;
            File file;
            file = new File(path);
            image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    int p = image.getRGB(i, j);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int avg = (r + g + b) / 3;

                    p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                    image.setRGB(i, j, p);
                }
            }
            file = new File(new ClassPathResource("static/upload/grayimages").getFile().getPath() + "/" + imageName);
            file.createNewFile();
            ImageIO.write(image, "jpg", file);

        }
    }

}
