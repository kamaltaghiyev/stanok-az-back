package az.stanok.stanokazback.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dhfana3ya",
            "api_key", "789324414595846",
            "api_secret", "GYPByzS80eeiyYoPU7jM1r4fnwk"));

    @SneakyThrows
    public Map upload(File file) {
        return cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    }

    @SneakyThrows
    public Map uploadVideo(File multipartFile) {
        return cloudinary.uploader().upload(multipartFile, ObjectUtils.asMap(
                "resource_type", "video"
        ));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}