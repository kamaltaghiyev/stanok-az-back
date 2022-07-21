package az.stanok.stanokazback.service;

import az.stanok.stanokazback.models.Image;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Product;
import az.stanok.stanokazback.repo.ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepo imageRepo;

    @SneakyThrows
    @Override
    public Image uploadPostImage(MultipartFile file, Post post) {
        Image image = new Image();
        image.setUuid(UUID.randomUUID());
        File convertedFile = convertMultiPartToFile(file);
        Map<String, Object> resultImage = cloudinaryService.upload(convertedFile);
        image.setUrl((String) resultImage.get("secure_url"));
        image.setPost(post);

        return imageRepo.save(image);
    }

    @SneakyThrows
    @Override
    public Image uploadProductImage(MultipartFile file, Product post) {
        Image image = new Image();
        image.setUuid(UUID.randomUUID());
        File convertedFile = convertMultiPartToFile(file);
        Map<String, Object> resultImage = cloudinaryService.upload(convertedFile);
        image.setUrl((String) resultImage.get("secure_url"));
        image.setProduct(post);

        return imageRepo.save(image);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    @Override
    public List<String> getAllPostImageLinks(Post post) {
        List<Image> images = imageRepo.findAllByPost(post);
        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllProductImageLinks(Product post) {
        List<Image> images = imageRepo.findAllByProduct(post);
        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
