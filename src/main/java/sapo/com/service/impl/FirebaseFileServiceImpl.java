package sapo.com.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
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

    public String uploadile(MultipartFile file) throws IOException, InterruptedException {
        InputStream inputStream= file.getInputStream();
        Bucket bucket= StorageClient.getInstance().bucket();

        bucket.create("products/"+file.getOriginalFilename(),inputStream,"image/jpeg");

        return "okee";
    }
    public String uploadFile(MultipartFile file) throws IOException, InterruptedException {
//
        uploadile(file);
        System.out.println(getImageUrl(file.getOriginalFilename()));


        return "okee";
    }

    public String getImageUrl(String fileName) throws IOException, InterruptedException{
        String url="https://firebasestorage.googleapis.com/v0/b/group1-sapo.appspot.com/o/products%2F"+ URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();
        System.out.println(response.body());
        JsonNode node = new ObjectMapper().readTree(jsonResponse);
        String imageToken = node.get("downloadTokens").asText();
        return url + "?alt=media&token="+imageToken;
    }
}
