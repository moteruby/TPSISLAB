package com.example.ERT;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImagLoader {

    public String loadImageAsBase64(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
