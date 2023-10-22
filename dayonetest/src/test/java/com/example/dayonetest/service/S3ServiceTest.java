package com.example.dayonetest.service;

import com.example.dayonetest.IntegrationTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

public class S3ServiceTest extends IntegrationTest {

    @Autowired
    private S3Service s3Service;

    @Test
    public void s3PutAndGetTest() throws Exception {
        //given
        var bucket = "test-bucket";
        var key = "sampleObject.xt";
        var sampleFile = new ClassPathResource("static/sample.txt").getFile();

        //when
        s3Service.putFile(bucket, key, sampleFile);

        //then
        var resultFile = s3Service.getFile(bucket, key);

        var sampleFileLines = FileUtils.readLines(sampleFile);
        var resultFileLines = FileUtils.readLines(resultFile);

        Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
    }
}
