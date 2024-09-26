package sapo.com.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class FirebaseFileServiceImpl {
    private final String bucketName = "group1-sapo.appspot.com";


    private static final Logger log = LoggerFactory.getLogger(FirebaseFileServiceImpl.class);

    public Boolean uploadFile(MultipartFile file,String directory) throws IOException, InterruptedException {
        try{
            InputStream inputStream = file.getInputStream();
            Bucket bucket = StorageClient.getInstance().bucket();
            bucket.create(directory+"/" + file.getOriginalFilename(), inputStream, "image/jpeg");
            return true;
        }catch(IOException e){
            log.error(e.getMessage());
            return false;
        }
    }
    public String uploadFileAndGetUrl(MultipartFile file,String directory) throws IOException, InterruptedException {
        try{
            uploadFile(file,directory);
            return getImageUrl(file.getOriginalFilename());
        }catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public String getImageUrl(String fileName) throws IOException, InterruptedException{
        String url="https://firebasestorage.googleapis.com/v0/b/group1-sapo.appspot.com/o/products%2F"+ URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();
        JsonNode node = new ObjectMapper().readTree(jsonResponse);
        String imageToken = node.get("downloadTokens").asText();
        return url + "?alt=media&token="+imageToken;
    }
}
