package com.urosmitrasinovic61017.planinarski_klub_webapp.config;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Comparator;

public class FileUploadUtil {

    public static  void saveFile(String uploadDir, String fileName,
                                 MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        //create a directory if not exists
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }


        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName); //ovime dodajemo fileName u dati uploadPath, i tako kreiramo kompletnu putanju (bukv appendujemo fileName za uploadPath (nas direktorijum))
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); //copy the uploaded file from temporary location to a fixed directory in the file system
        } catch (IOException ioe){
            throw  new IOException("Could not save image file: " + fileName, ioe);
        }

    }

    public static void deleteSpecificFile(String uploadDir, String fileName)
        throws  IOException {

        Path uploadPath = Paths.get(uploadDir);
        Path filePathToDelete = uploadPath.resolve(fileName);

        try{
            Files.deleteIfExists(filePathToDelete);
        } catch(NoSuchFileException nsfe)
        {
            throw new NoSuchFileException("No such file/directory exists: " + fileName);
        } catch(IOException ioe)
        {
           throw new IOException("Could not delete image file: " + fileName, ioe);
        }

    }

    public static void deleteDirectory(String uploadDir) throws IOException{
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){ //ako ne postoji direktorijum, nemoj nista da radis
            return;
        }

        try{
            Files.walk(uploadPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        catch (IOException ioe){
            throw new IOException("Could not delete a directory: " + uploadDir, ioe);
        }

    }


}
