package com.image.upload.service;

import com.image.upload.entity.ProductImage;
import com.image.upload.repository.ProductImageRepository;
import com.image.upload.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProductImageService {

    private ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Transactional
    public ResponseEntity<?> uploadImage(MultipartFile file) throws IOException {
        ProductImage productImage = new ProductImage();
        if (productImageRepository.existsByName(file.getOriginalFilename())){
            return new ResponseEntity("File with this name already exist", HttpStatus.BAD_REQUEST);
        }
        productImage.setName(file.getOriginalFilename());
        productImage.setType(file.getContentType());
        productImage.setImageData(file.getBytes());
        productImage.setImageData(file.getBytes());
        productImageRepository.save(productImage);
        return new ResponseEntity("File uploaded successfully", HttpStatus.OK);
    }

    public byte[] downloadImage(String fileName){
        Optional<ProductImage> byName = productImageRepository.findByName(fileName);
        return byName.get().getImageData();
    }
}
