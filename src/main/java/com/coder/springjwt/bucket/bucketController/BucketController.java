package com.coder.springjwt.bucket.bucketController;

import com.coder.springjwt.bucket.BucketUrlMappings;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.adminConstants.adminUrlMappings.AdminUrlMappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AdminUrlMappings.ADMIN_AUTHORIZE_URL)
@Slf4j
public class BucketController {

    @Autowired
    private BucketService serviceBucket;

    @PostMapping(BucketUrlMappings.UPLOAD_FILE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file" ) MultipartFile file) {
        log.info(file.getOriginalFilename());
        return ResponseEntity.ok(this.serviceBucket.uploadFile(file));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(BucketUrlMappings.DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = serviceBucket.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(BucketUrlMappings.DELETE_BUCKET_FILE)
    public ResponseEntity<String> deleteBucketFile(@PathVariable String fileName) {
        return new ResponseEntity<>(serviceBucket.deleteFile(fileName), HttpStatus.OK);
    }






}
