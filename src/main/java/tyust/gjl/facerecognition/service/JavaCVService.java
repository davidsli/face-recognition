package tyust.gjl.facerecognition.service;

import org.bytedeco.javacpp.opencv_core;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import tyust.gjl.facerecognition.config.Global;
import tyust.gjl.facerecognition.entity.Person;
import tyust.gjl.facerecognition.entity.dto.Detect;
import tyust.gjl.facerecognition.mapper.PersonMapper;

import java.io.File;
import java.util.concurrent.Future;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.opencv_core.CV_HIST_ARRAY;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvCompareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvNormalizeHist;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.opencv.imgproc.Imgproc.CV_COMP_CORREL;


/**
 * @author : coderWu
 * @date : Created on 11:58 2018/5/5
 */
@Service
public class JavaCVService {

    private static final Logger logger = LoggerFactory.getLogger(JavaCVService.class);

    private static final Resource CLASSIFIER_RESOURCE = new ClassPathResource("haarcascade_frontalface_alt.xml");

    @Autowired
    PersonMapper personMapper;

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        logger.info("\nRunning FaceDetector");
    }

    public double comparePictures(String imagePath1, String imagePath2) {

        int lBins = 20;
        int histSize[] = {lBins};

        float vRanges[] = {0, 100};
        float ranges[][] = {vRanges};

        opencv_core.IplImage image1 = cvLoadImage(imagePath1, CV_LOAD_IMAGE_GRAYSCALE);
        opencv_core.IplImage image2 = cvLoadImage(imagePath2, CV_LOAD_IMAGE_GRAYSCALE);

        opencv_core.IplImage imageArr1[] = {image1};
        opencv_core.IplImage imageArr2[] = {image2};

        opencv_core.CvHistogram histogram1 = opencv_core.CvHistogram.create(1, histSize, CV_HIST_ARRAY, ranges, 1);
        opencv_core.CvHistogram histogram2 = opencv_core.CvHistogram.create(1, histSize, CV_HIST_ARRAY, ranges, 1);

        cvCalcHist(imageArr1, histogram1, 0, null);
        cvCalcHist(imageArr2, histogram2, 0, null);

        cvNormalizeHist(histogram1, 100.0);
        cvNormalizeHist(histogram2, 100.0);

        return cvCompareHist(histogram1, histogram2, CV_COMP_CORREL);
    }

    public boolean detectFace(String imageName, boolean save) throws Exception {

        logger.info("Running DetectFace ... ");
        CascadeClassifier faceDetector =
                new CascadeClassifier(CLASSIFIER_RESOURCE.getFile().getPath());
        Mat image = Imgcodecs.imread(new ClassPathResource("static/upload/images/" + imageName).getFile().getPath());

        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(image, faceDetections);

        logger.info(String.format("Detected %s faces", faceDetections.toArray().length));

        Rect[] rects = faceDetections.toArray();
        if (rects == null || rects.length == 0 || rects.length > 1) {
            return false;
        }
        if (!save) {
            return true;
        }
        // 在每一个识别出来的人脸周围画出一个方框
        Rect rect = rects[0];

        Imgproc.rectangle(image, new Point(rect.x - 2, rect.y - 2),
                new Point(rect.x + rect.width, rect.y + rect.height),
                new Scalar(0, 255, 0));
        String outFile = new ClassPathResource("static/upload/faceimages").getFile().getPath() + "/" + imageName;
        Imgcodecs.imwrite(outFile, image);
        logger.info(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
        return true;
    }

    @Async("asyncServiceExecutor")
    public Future<Detect> comparePictures(File image, File target) {
        logger.info(image.getName());
        Future<Detect> result = null;
        double compareValue = 0;
        compareValue = this.comparePictures(image.getPath(), target.getPath());
        Person person;
        if (compareValue >= Global.MATCH_PERCENT &&
                (person = personMapper.selectByImageName(image.getName())) != null) {
            result = new AsyncResult<>(new Detect()
                    .setCompareValue(compareValue)
                    .setPerson(person));
        }
        return result;
    }

}
