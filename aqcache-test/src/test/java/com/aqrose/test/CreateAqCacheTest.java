package com.aqrose.test;

import com.aqrose.test.util.CacheTools;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.opencv.core.CvType.CV_8UC1;

/**
 * @author hongkun
 * @version 1.0.0
 * @since 1.8
 **/
@SpringBootTest
class CreateAqCacheTest {

    @Resource
    CacheTools cacheTools;

    static {
        //可以使用System.load()加载动态链接库的绝对路径
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test
    void contextLoads() {
        try {
            Mat mat1 = new Mat(1, 1, CV_8UC1, new Scalar(0));
            Mat mat2 = new Mat(2, 2, CV_8UC1, new Scalar(0));
            Mat mat3 = new Mat(3, 3, CV_8UC1, new Scalar(0));
            Mat mat4 = new Mat(4, 4, CV_8UC1, new Scalar(0));
            Mat mat5 = new Mat(5, 5, CV_8UC1, new Scalar(0));
            cacheTools.test.put("key1", mat1);
            cacheTools.test.put("key2", mat2);
            cacheTools.test.put("key3", mat3);
            cacheTools.test.put("key4", mat4);
            cacheTools.test.put("key5", mat5);
            System.out.println(cacheTools.test.get("key5"));
            cacheTools.test.values().forEach(mat -> System.out.println(mat.width()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
