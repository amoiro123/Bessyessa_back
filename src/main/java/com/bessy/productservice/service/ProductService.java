package com.bessy.productservice.service;

import com.bessy.productservice.client.FileStorageClient;
import com.bessy.productservice.enums.ProductType;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.repository.ProductRepository;
import com.bessy.productservice.request.ProductRequestDTO;
import com.bessy.productservice.response.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

}
