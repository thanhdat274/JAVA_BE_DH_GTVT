package vn.com.javaapi.service.Impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.javaapi.bean.ProductsResponse;
import vn.com.javaapi.config.minio.MinIOClientManager;
import vn.com.javaapi.config.minio.MinioConfig;
import vn.com.javaapi.constant.ProductContant;
import vn.com.javaapi.dto.ProductDTO;
import vn.com.javaapi.entity.Products;
import vn.com.javaapi.repository.ProductRepository;
import vn.com.javaapi.service.Mapper.ProductMapper;
import vn.com.javaapi.service.ProductService;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Gson gson;
    private final MinioConfig minioConfig;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final MinIOClientManager minIOClientManager;

    @Override
    public void addPro(ProductDTO request) throws Exception {
        var startTime = System.currentTimeMillis();
        log.info("Begin create product with request: {}.", gson.toJson(request));
        request.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        request.setStatus(ProductContant.ProductStatus.ACTIVE.getCode());

        // Xử lý thumbnail (ảnh thumb)
        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            String base64Thumbnail = request.getThumbnail();
            // Nếu có tiền tố "data:image/xxx;base64," thì loại bỏ nó
            if (base64Thumbnail.contains(",")) {
                base64Thumbnail = base64Thumbnail.substring(base64Thumbnail.indexOf(",") + 1);
            }
            byte[] thumbnailBytes = Base64.getDecoder().decode(base64Thumbnail);
            ByteArrayInputStream thumbStream = new ByteArrayInputStream(thumbnailBytes);
            log.info("Thumbnail file: {}", thumbStream);
            // Đặt tên file (có thể thay đổi extension dựa vào loại file thực tế)
            String thumbFileName = "thumb_" + System.currentTimeMillis() + ".jpg";
            minIOClientManager.uploadFileToMinIO(minioConfig.getMinioBucket(), thumbFileName, thumbStream);
            String url = minIOClientManager.getUrlPublicFromMinIO(minioConfig.getMinioBucket(), thumbFileName);
            log.info("End export trans with data: {}", url);
            request.setThumbnail(thumbFileName);
        }

        // Xử lý image (ảnh chi tiết)
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            String base64Image = request.getImage();
            if (base64Image.contains(",")) {
                base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
            log.info("imageStream file: {}", imageStream);
            String imageFileName = "image_" + System.currentTimeMillis() + ".jpg";
            minIOClientManager.uploadFileToMinIO(minioConfig.getMinioBucket(), imageFileName, imageStream);
            String url = minIOClientManager.getUrlPublicFromMinIO(minioConfig.getMinioBucket(), imageFileName);
            log.info("End export trans with data: {}", url);
            request.setImage(imageFileName);
        }


        Products products = productMapper.toEntityProduct(request);
        log.info("Data products mapping: {}", products);
        productRepository.save(products);
        log.info("Created product successfully Time handler: {} ms.", (System.currentTimeMillis() - startTime));
    }

    public ProductsResponse listProduct() {
        var startTime = System.currentTimeMillis();
        log.info("Begin list product. ");
        List<Products> listProducts = productRepository.findAll();
        //// Get total rows

        List<ProductDTO> productDTOList = productMapper.toDTOProduct(listProducts);
        log.info("End list product with response size {} and Time handler: {} ms.", productDTOList.size(), (System.currentTimeMillis() - startTime));
        return ProductsResponse.builder()
            .code("00")
            .message("List products successful").data(productDTOList)
            .build();
    }

    public ProductsResponse getProductById(Long id) {
        var startTime = System.currentTimeMillis();
        log.info("Begin get product with id: {}.", id);
        Optional<Products> optProducts = productRepository.findById(id);
        if (optProducts.isEmpty()) {
            log.info("User not found at DMS.");
            ProductsResponse.builder()
                .code("01")
                .message("Product does not exist")
                .build();
        }
        Products products = optProducts.get();
        ProductDTO productDTO = productMapper.toDTO(products);

        log.info("End get product with response: {} and Time handler: {} ms.", gson.toJson(productDTO), (System.currentTimeMillis() - startTime));
        return ProductsResponse.builder()
            .code("00")
            .message("Get product successful").data(productDTO)
            .build();
    }

    @Override
    public void updatePro(ProductDTO request) {
        var startTime = System.currentTimeMillis();
        log.info("Begin update product with request: {}.", gson.toJson(request));
        Optional<Products> optProducts = productRepository.findById(request.getId());
        Products products = optProducts.get();
        products.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        products.setPrice(request.getPrice());
        products.setSalePrice(request.getSalePrice());
        products.setQuantity(request.getQuantity());
        products.setImage(request.getImage());
        products.setThumbnail(request.getThumbnail());
        products.setDescription(request.getDescription());
        productRepository.save(products);
        log.info("Update product successfully Time handler: {} ms.", (System.currentTimeMillis() - startTime));
    }

    @Override
    public void updateProductQuantity(Long id, Long quantity) {
        var startTime = System.currentTimeMillis();
        Optional<Products> optProducts = productRepository.findById(id);
        Products products = optProducts.get();
        Long remainingQuantity = products.getQuantity() - quantity;
        products.setQuantity(remainingQuantity);
        productRepository.save(products);
        log.info("Update quantity product successfully Time handler: {} ms.", (System.currentTimeMillis() - startTime));
    }

    @Override
    public ProductsResponse listProductByType(String type) {
        var startTime = System.currentTimeMillis();
        log.info("Begin list product by type: {}.", type);
        List<Products> listProducts = productRepository.findByType(type);
        List<ProductDTO> productDTOList = productMapper.toDTOProduct(listProducts);
        log.info("End list product by type with response size {} and Time handler: {} ms.", productDTOList.size(), (System.currentTimeMillis() - startTime));
        return ProductsResponse.builder()
            .code("00")
            .message("List products successful").data(productDTOList)
            .build();
    }
}
