package com.coder.springjwt.buckets.filesBucket.bucketModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketModel {
    private String bucketUrl;
    private String fileName;

}
