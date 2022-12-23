package com.quizz.lessonservice.service.image;

import com.quizz.lessonservice.common.Image;
import com.quizz.lessonservice.repository.image.ImageRepository;
import com.quizz.lessonservice.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * This method to save new image to DB
     *
     * @param file
     * @return name of image stored in DB
     * @throws IOException
     */
    public String saveImage(MultipartFile file) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyy-hhmmss.SSS");
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileNameNew = simpleDateFormat.format(new Date()) + "." + fileExtension;
        imageRepository.save(Image.builder()
                .name(fileNameNew)
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return fileNameNew;
    }

    /**
     * This method to get info of image
     *
     * @param name
     * @return image information stored in DB
     * @throws Exception
     */
    public Image getImageInfo(String name) throws Exception {
        final Optional<Image> dbImage = imageRepository.findByName(name);
        if (!dbImage.isPresent()) {
            throw new Exception("Cannot found image");
        }
        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    }
}