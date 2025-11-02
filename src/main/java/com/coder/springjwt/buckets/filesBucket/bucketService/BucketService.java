package com.coder.springjwt.buckets.filesBucket.bucketService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.coder.springjwt.buckets.filesBucket.BucketUrlMappings;
import com.coder.springjwt.buckets.filesBucket.bucketModels.BucketModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class BucketService {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AmazonS3 s3Client;

    public BucketModel uploadFile(MultipartFile file) {

        try {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();

            //Creating Bucket Models
            return new BucketModel(BucketUrlMappings.BUCKET_URL + fileName, fileName);
        } catch (Exception e) {
            //log.error("Exception : " , e);
            //log.error("AWS Configuration Problem :::::::::::::::: {}");
            return this.getRandomFile();
        }
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        try {
            s3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : ", e);
            log.error("Error in delete File in AWS Bucket :::::::::::  {}");
        }
        return fileName + " removed ...";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            //log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private BucketModel getRandomFile() {
        return new BucketModel(
                "https://images.unsplash.com/photo-1591696205602-2f950c417cb9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
                "BUSINESS");
    }


    //=============Cloudinary File Upload Process Starting==================

    //Cloudinary Api Keys
    private final String CLOUDINARY = "cloudinary";
    private final String CLOUD_NAME = "dqke3lb3z";
    private final String API_KEY = "765958574628894";
    private final String API_SECRET = "OLHUAyKiZ-xAyWOm3erWfhtWctI";
    private final Boolean SECURE = Boolean.TRUE;


    public BucketModel uploadCloudinaryFile(@RequestParam("file") MultipartFile file , String fileType) {
        log.info("Cloudinary File Upload --- FLYING");
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", CLOUD_NAME,
                    "api_key", API_KEY,
                    "api_secret", API_SECRET,
                    "secure", SECURE));

            // Upload the image
            Map conditionVerifier = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", file.getOriginalFilename(),
                    "overwrite", false,
                    "public_id", file.getOriginalFilename());

             /*Important: set resource_type for video and Image Both us Important
            Otherwise only image is saved not video*/
            if ("VIDEO".equalsIgnoreCase(fileType)) {
                conditionVerifier.put("resource_type", "video");
            } else {
                conditionVerifier.put("resource_type", "image");
            }

            Map cloudinaryUpload = cloudinary.uploader().upload(file.getBytes(), conditionVerifier);
            String cloudinaryResponse = objectMapper.writeValueAsString(cloudinaryUpload);
            Map<String, String> node = objectMapper.readValue(cloudinaryResponse, Map.class);
            log.info("File Upload Success in Cloudinary");

            //Creating Bucket Models
            return new BucketModel(node.get("url"), file.getOriginalFilename().toString());
        } catch (IOException e) {
            e.getMessage();
            throw new RuntimeException(e);
        }
    }
}
