package com.chessd.chess.storage.service;

import com.chessd.chess.user.entity.User;
import com.chessd.chess.storage.StorageProperties;
import com.chessd.chess.storage.exception.StorageException;
import com.chessd.chess.storage.exception.StorageFileNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties) {
        if(storageProperties.getLocation().trim().isEmpty()){
            throw new StringIndexOutOfBoundsException("File upload location can not be Empty");
        }
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file, User user) {
        try {
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file");
            }
            Path directoryPath = this.rootLocation.resolve(String.valueOf(user.getId())).toAbsolutePath();
            String extension = this.getString(file, directoryPath);
            this.cleanDirectory(this.rootLocation + "/" + user.getId());
            Path path = directoryPath.resolve(Paths.get("photo." + extension)).normalize().toAbsolutePath();
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            return "photo." + extension;
        }catch (Exception e){
            throw new StorageException("Failed to store file", e);
        }
    }

    public void cleanDirectory(String directoryPath) throws IOException{
        Path dir = Paths.get(directoryPath);

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Ścieżka nie istnieje lub nie jest katalogiem: " + directoryPath);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                Files.deleteIfExists(file);
            }
        }
    }

    private @NotNull String getString(MultipartFile file, Path directoryPath) throws IOException {
        File dir = new File(directoryPath.toString());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        if (!directoryPath.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside current directory.");
        }
        String contentType = file.getContentType();
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case null, default -> throw new IOException("Nieobsługiwany typ pliku");
        };
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = this.load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteUserFile(User user) {
        Path path = rootLocation.resolve(String.valueOf(user.getId()));
        File dir = path.toFile();
        if(dir.exists() && dir.isDirectory()){
            FileSystemUtils.deleteRecursively(dir);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
