package com.store.store_app.services;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStorageService {

    String loadImage(MultipartFile archivoLocalCliente, String carpeta, Long id);

    void deleteImage(String urlImagen);

}
