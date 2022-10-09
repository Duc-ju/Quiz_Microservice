package com.quizz.resourceserver.controller.image;

import com.quizz.resourceserver.common.Image;
import com.quizz.resourceserver.common.ResponseObject;
import com.quizz.resourceserver.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin()
@RequestMapping("/static/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadImage(@RequestParam("image") MultipartFile file)
            throws IOException {
        String fileNameNew = imageService.saveImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("Image uploaded successfully",
                        fileNameNew));
    }

    @GetMapping(path = {"/info/{name}"})
    public ResponseEntity<ResponseObject> getImageDetails(@PathVariable("name") String name) throws IOException {
        try {
            Image image = imageService.getImageInfo(name);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("get image infomation", image)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping(path = {"/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
        try {
            Image image = imageService.getImageInfo(name);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(image.getType()))
                    .body(image.getImage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}