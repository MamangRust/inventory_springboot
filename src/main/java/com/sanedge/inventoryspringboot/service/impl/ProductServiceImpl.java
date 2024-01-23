package com.sanedge.inventoryspringboot.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.sanedge.inventoryspringboot.domain.request.product.CreateProductRequest;
import com.sanedge.inventoryspringboot.domain.request.product.UpdateProductRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Category;
import com.sanedge.inventoryspringboot.models.Product;
import com.sanedge.inventoryspringboot.repository.CategoryRepository;
import com.sanedge.inventoryspringboot.repository.ProductRepository;
import com.sanedge.inventoryspringboot.service.FileService;
import com.sanedge.inventoryspringboot.service.FolderService;
import com.sanedge.inventoryspringboot.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final FolderService folderService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
            FileService fileService, FolderService folderService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
        this.folderService = folderService;
    }

    @Override
    public MessageResponse getAllProduct() {
        try {
            List<Product> products = productRepository.findAll();
            return MessageResponse.builder().data(products).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all products: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            return MessageResponse.builder().data(product).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Product not found for id: {}", id);
            return MessageResponse.builder().message("Product not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching product: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createProduct(CreateProductRequest request) {
        try {
            MultipartFile myFile = request.getImage();

            if (myFile != null && !myFile.isEmpty()) {
                Product product = new Product();

                Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                product.setName(request.getName());
                product.setQty(request.getQty());
                product.setCategory(category);

                String folderPath = folderService.createFolder(request.getName());

                if (folderPath != null) {
                    String filePath = folderPath + File.separator + myFile.getOriginalFilename();

                    String createdFilePath = fileService.createFileImage(myFile, filePath);

                    if (createdFilePath != null) {
                        product.setImage(createdFilePath);
                        productRepository.save(product);
                        return MessageResponse.builder().data(product).message("Product created successfully")
                                .statusCode(200).build();
                    } else {
                        return MessageResponse.builder().message("Error while saving the image").statusCode(500)
                                .build();
                    }
                } else {
                    return MessageResponse.builder().message("Error while creating folder for product").statusCode(500)
                            .build();
                }
            } else {
                return MessageResponse.builder().message("Image file is null or empty").statusCode(400).build();
            }

        } catch (Exception e) {
            log.error("Error while creating product: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse updateProduct(Long id, UpdateProductRequest request) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                if (request.getCategoryId() != null) {
                    Category newCategory = categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new ResourceNotFoundException("New category not found"));

                    product.setCategory(newCategory);
                }

                if (request.getName() != null) {
                    product.setName(request.getName());
                }

                if (request.getQty() != null) {
                    product.setQty(request.getQty());
                }

                MultipartFile myFile = request.getImage();
                if (myFile != null && !myFile.isEmpty()) {
                    String folderPath = folderService.createFolder(product.getName());

                    if (folderPath != null) {
                        String filePath = folderPath + File.separator + myFile.getOriginalFilename();

                        String createdFilePath = fileService.createFileImage(myFile, filePath);

                        if (createdFilePath != null) {
                            product.setImage(createdFilePath);
                        } else {
                            return MessageResponse.builder().message("Error while saving the new image").statusCode(500)
                                    .build();
                        }
                    } else {
                        return MessageResponse.builder().message("Error while creating folder for product")
                                .statusCode(500).build();
                    }
                }
                productRepository.save(product);
                return MessageResponse.builder().data(product).message("Product updated successfully").statusCode(200)
                        .build();
            } else {
                return MessageResponse.builder().message("Product not found").statusCode(404).build();
            }

        } catch (Exception e) {
            log.error("Error while updating product: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    public MessageResponse exportProducts(HttpServletResponse response) {
        try {
            String csvFileName = "products.csv";
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + csvFileName);

            PrintWriter writer = response.getWriter();

            CSVWriter csvWriter = new CSVWriter(writer);

            String[] header = { "Id", "Name", "Qty", "Category", "Image" };
            csvWriter.writeNext(header);

            // Retrieve all products from the database
            List<Product> products = productRepository.findAll();

            // Write each product data row to the CSV file
            for (Product product : products) {
                String[] row = {
                        String.valueOf(product.getId()),
                        product.getName(),
                        String.valueOf(product.getQty()),
                        product.getCategory().getName(),
                        product.getImage()
                };
                csvWriter.writeNext(row);
            }

            csvWriter.close();

            return MessageResponse.builder().message("Success").statusCode(200).build();

        } catch (IOException e) {
            log.error("Error while exporting products: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    public MessageResponse importProducts(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            CSVReader csvReader = new CSVReader(reader);

            csvReader.readNext();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Long id = Long.parseLong(line[0]);
                String name = line[1];
                String qty = line[2];
                String categoryName = line[3];
                String image = line[4];

                Category category = categoryRepository.findByName(categoryName)
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(categoryName);
                            return categoryRepository.save(newCategory);
                        });

                Optional<Product> optionalProduct = productRepository.findById(id);
                Product product;
                if (optionalProduct.isPresent()) {
                    product = optionalProduct.get();
                } else {
                    product = new Product();
                    product.setId(id);
                }

                product.setName(name);
                product.setQty(qty);
                product.setCategory(category);
                product.setImage(image);

                productRepository.save(product);
            }

            csvReader.close();
            return MessageResponse.builder().message("Import successful").statusCode(200).build();
        } catch (IOException | CsvValidationException e) {
            log.error("Error while importing products from CSV: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred during import").statusCode(500).build();
        }
    }

    public MessageResponse exportProductsToPDF(HttpServletResponse response) {
        try {
            String pdfFileName = "products.pdf";
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + pdfFileName);

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(20, 700);

            // Write header
            contentStream.showText("Id\tName\tQty\tCategory\tImage");
            contentStream.newLine();

            List<Product> products = productRepository.findAll();

            int counter = 0;
            for (Product product : products) {
                String row = String.format("%d\t%s\t%d\t%s\t%s",
                        product.getId(),
                        product.getName(),
                        product.getQty(),
                        product.getCategory().getName(),
                        product.getImage());
                contentStream.showText(row);
                contentStream.newLine();

                counter++;
                if (counter >= 10) {
                    break;
                }
            }

            contentStream.endText();
            contentStream.close();

            document.save(response.getOutputStream());
            document.close();

            return MessageResponse.builder().message("Success").statusCode(200).build();

        } catch (IOException e) {
            log.error("Error while exporting products to PDF: {}", e.getMessage(), e);

            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            productRepository.delete(product);

            folderService.deleteFolder(product.getName());

            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Product not found for id: {}", id);
            return MessageResponse.builder().message("Product not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while deleting product: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

}
