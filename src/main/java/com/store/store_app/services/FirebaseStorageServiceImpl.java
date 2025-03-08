package com.store.store_app.services;

import com.google.auth.Credentials;
import com.google.auth.ServiceAccountSigner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Value("${firebase.top.level.storage.path}")
    private String topLevelStoragePath;

    @Value("${firebase.json.file.path}")
    private String jsonFilePath;

    @Value("${firebase.json.file.name}")
    private String jsonFileName;

    @Override
    public String loadImage(MultipartFile localClientFile, String folder, Long id) {
        if (localClientFile.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        try {
            String originalFilename = localClientFile.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("File name is missing");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = "img" + formatId(id) + extension;

            File file = convertToFile(localClientFile);
            String url = uploadFile(file, folder, fileName);

            return url;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    @Override
    public void deleteImage(String imageUrl) {
        try {
            String[] parts = imageUrl.split("/");
            String fileName = parts[parts.length - 1].split("\\?")[0];

            BlobId blobId = BlobId.of(bucketName, topLevelStoragePath + "/flag/" + fileName);

            ClassPathResource json = new ClassPathResource(Paths.get(jsonFilePath, jsonFileName).toString());
            Credentials credentials = GoogleCredentials.fromStream(json.getInputStream());
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            storage.delete(blobId);
            System.out.println("File successfully deleted: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image", e);
        }
    }

    private String uploadFile(File file, String folder, String fileName) throws IOException {
        ClassPathResource json = new ClassPathResource(Paths.get(jsonFilePath, jsonFileName).toString());
        BlobId blobId = BlobId.of(bucketName, topLevelStoragePath + "/" + folder + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        Credentials credentials = GoogleCredentials.fromStream(json.getInputStream());
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return storage.signUrl(blobInfo, 3650, TimeUnit.DAYS, Storage.SignUrlOption.signWith((ServiceAccountSigner) credentials)).toString();
    }

    private File convertToFile(MultipartFile localClientFile) throws IOException {
        File tempFile = File.createTempFile("img", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(localClientFile.getBytes());
        }
        return tempFile;
    }

    private String formatId(long id) {
        return String.format("%019d", id);
    }
}
