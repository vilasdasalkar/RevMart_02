package com.revature.user.service;

import com.revature.user.model.ProductRequest;
import com.revature.user.model.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Locale.Category;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock the WebClient.Builder methods
        doReturn(webClient).when(webClientBuilder).build();
    }

    // Test for getAllProducts (Success)
    @Test
    public void testGetAllProducts_Success() {
        List<ProductResponse> mockProductList = Arrays.asList(new ProductResponse(), new ProductResponse());

        // Mocking WebClient behavior using doReturn
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/products");
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Flux.fromIterable(mockProductList)).when(responseSpec).bodyToFlux(ProductResponse.class);

        // Call the method
        List<ProductResponse> products = productService.getAllProducts();

        // Verify and assert
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(webClient, times(1)).get();
    }

    // Test for getAllProducts (Failure)
    @Test
    public void testGetAllProducts_Failure() {
        // Mocking WebClient behavior to throw an exception
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/products");
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Flux.error(new RuntimeException("Failed to fetch products"))).when(responseSpec).bodyToFlux(ProductResponse.class);

        // Call the method and expect exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getAllProducts();
        });

        // Assert the exception message
        assertEquals("Failed to fetch products.", exception.getMessage());
    }

    // Test for getProductById (Success)
    @Test
    public void testGetProductById_Success() {
        Long productId = 1L;
        ProductResponse mockProduct = new ProductResponse();

        // Mocking WebClient behavior using doReturn
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/products/" + productId);
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just(mockProduct)).when(responseSpec).bodyToMono(ProductResponse.class);

        // Call the method
        ProductResponse product = productService.getProductById(productId);

        // Verify and assert
        assertNotNull(product);
        verify(webClient, times(1)).get();
    }

    // Test for getProductById (Failure)
    @Test
    public void testGetProductById_Failure() {
        Long productId = 1L;

        // Mocking WebClient behavior to throw an exception
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/products/" + productId);
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.error(new RuntimeException("Failed to fetch product"))).when(responseSpec).bodyToMono(ProductResponse.class);

        // Call the method and expect exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(productId);
        });

        // Assert the exception message
        assertEquals("Failed to fetch product.", exception.getMessage());
    }

    // Test for addProduct (Success)
    @Test
    public void testAddProduct_Success() {
        ProductRequest mockProductRequest = new ProductRequest();
        String mockResponse = "Product added successfully";

        // Mocking WebClient behavior using doReturn
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri("http://localhost:8087/products");
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(mockProductRequest);
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.just(mockResponse)).when(responseSpec).bodyToMono(String.class);

        // Call the method
        String response = productService.addProduct(mockProductRequest);

        // Verify and assert
        assertEquals("Product added successfully", response);
        verify(webClient, times(1)).post();
    }

    // Test for addProduct (Failure)
    @Test
    public void testAddProduct_Failure() {
        ProductRequest mockProductRequest = new ProductRequest();

        // Mocking WebClient behavior to throw an exception
        doReturn(requestBodyUriSpec).when(webClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri("http://localhost:8087/products");
        doReturn(requestHeadersSpec).when(requestBodySpec).bodyValue(mockProductRequest);
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Mono.error(new RuntimeException("Failed to add product"))).when(responseSpec).bodyToMono(String.class);

        // Call the method and expect exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.addProduct(mockProductRequest);
        });

        // Assert the exception message
        assertEquals("Failed to add product.", exception.getMessage());
    }

    // Test for getAllCategories (Success)
    @Test
    public void testGetAllCategories_Success() {
        List<Category> mockCategoryList = Arrays.asList(Category.FORMAT, Category.DISPLAY);

        // Mocking WebClient behavior using doReturn
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/categories");
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Flux.fromIterable(mockCategoryList)).when(responseSpec).bodyToFlux(Category.class);

        // Call the method
        List<Category> categories = productService.getAllCategories();

        // Verify and assert
        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(webClient, times(1)).get();
    }

    // Test for getAllCategories (Failure)
    @Test
    public void testGetAllCategories_Failure() {
        // Mocking WebClient behavior to throw an exception
        doReturn(requestHeadersUriSpec).when(webClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri("http://localhost:8087/categories");
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Flux.error(new RuntimeException("Failed to fetch categories"))).when(responseSpec).bodyToFlux(Category.class);

        // Call the method and expect exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getAllCategories();
        });

        // Assert the exception message
        assertEquals("Failed to fetch categories.", exception.getMessage());
    }
}
