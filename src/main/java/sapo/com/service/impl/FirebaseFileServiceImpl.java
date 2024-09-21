package sapo.com.service.impl;


import org.springframework.stereotype.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseFileServiceImpl {
    private final String bucketName = "group1-sapo.appspot.com";

    public String uploadFile(MultipartFile file) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(file.getContentType())
                .build();

        Blob blob = storage.create(blobInfo, file.getBytes());

        // Return the public download URL
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}
