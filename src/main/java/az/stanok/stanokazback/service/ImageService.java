package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.product.ImageDto;
import az.stanok.stanokazback.models.Image;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image uploadPostImage(MultipartFile file, Post post);
    Image uploadProductImage(MultipartFile file, Product product);
    List<String> getAllPostImageLinks(Post post);
    List<ImageDto> getAllProductImageLinks(Product product);
    void deleteImage(Long id);
}
