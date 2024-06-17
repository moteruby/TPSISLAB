package com.example.ERT.services;

import com.example.ERT.models.Result;
import com.example.ERT.models.User;
import com.example.ERT.repositories.ResultRepository;
import com.example.ERT.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final UserRepository userRepository;

    private final ResultRepository resultRepository;

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public List<Result> getByUser(User user) {
        return resultRepository.findAllByUser(user);
    }

    public boolean createResult(Result result) {
        resultRepository.save(result);
        return true;
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    public ResponseEntity<byte[]> downloadReport(Long resultId) throws IOException, InvalidFormatException {
        Map<String, Object> result = getResultById(resultId);

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Emotion Recognition Report");
        titleRun.setBold(true);
        titleRun.setFontSize(20);

        // Add image to the document
        if (result.containsKey("image") && result.get("image") != null) {
            byte[] imageBytes = Base64.getDecoder().decode((String) result.get("image"));
            InputStream imageInputStream = new ByteArrayInputStream(imageBytes);
            XWPFParagraph imageParagraph = document.createParagraph();
            XWPFRun imageRun = imageParagraph.createRun();
            imageRun.addBreak();
            imageRun.addPicture(imageInputStream, XWPFDocument.PICTURE_TYPE_JPEG, "image.jpg", Units.toEMU(400), Units.toEMU(300));
            imageRun.addBreak();
            imageInputStream.close();
        }

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Title: " + result.get("title"));
        run.addBreak();
        run.setText("EmotionDf: " + result.get("emotionsDf"));
        run.addBreak();
        run.setText("ValuesDf: " + result.get("valuesDf"));
        run.addBreak();
        run.setText("EmotionsMl: " + result.get("emotionsMl"));
        run.addBreak();
        run.setText("ValuesMl: " + result.get("valuesMl"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();  // Close the document

        byte[] documentBytes = out.toByteArray();
        out.close();  // Close the output stream

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.docx");
        headers.setContentLength(documentBytes.length);

        return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
    }

    private Map<String, Object> getResultById(Long resultId) {
        Result result = resultRepository.findById(resultId).orElse(null);
        if (result != null) {
            Map<String, Object> resultData = new HashMap<>();
            resultData.put("title", result.getTitle());
            resultData.put("emotionsDf", result.getEmotionsDf());
            resultData.put("valuesDf", result.getValuesDf());
            resultData.put("emotionsMl", result.getEmotionsMl());
            resultData.put("valuesMl", result.getValuesMl());
            resultData.put("image", result.getImage());
            return resultData;
        } else {
            return null;
        }
    }
}