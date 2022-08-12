package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.product.ImageDto;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
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
import java.util.ArrayList;
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
    public List<ImageDto> getAllProductImageLinks(Product post) {
        List<Image> images = imageRepo.findAllByProduct(post);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : images) {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(image.getId());
            imageDto.setUrl(image.getUrl());
            imageDtos.add(imageDto);
        }
        return imageDtos;
    }

    @Override
    public void deleteImage(Long id) {
        Image image = imageRepo.findById(id).orElseThrow(() -> new NotFoundException(Image.class, id));
        image.setProduct(null);
        imageRepo.save(image);
    }
}
