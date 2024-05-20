package br.com.productmanagement.integration;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.frameworks.db.ProductRepository;
import br.com.productmanagement.helper.ProductTestHelper;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableWebMvc
class  ProductWebTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTestHelper productTestHelper;

    private static final String REQUEST_MAPPING_ROOT = "/products";

    private static final String MOCK_DIRECTORY = "product/";

    private Product product;

    @BeforeEach
    void setUp() {
        product = productRepository.save(productTestHelper.newProduct());
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste criação de produto")
    void insert() throws Exception {

        ProductDto body = productTestHelper.newProductDto();

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        post(REQUEST_MAPPING_ROOT + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ProductDto result = (ProductDto) getResponseBody(response, ProductDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste update de produto")
    void update() throws Exception {

        ProductDto body = productTestHelper.productUpdDto();

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/update" + "/" + product.getSku())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ProductDto result = (ProductDto) getResponseBody(response, ProductDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste listar todos os produtos")
    void findAll() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "products.json");
        List<Product> products = objectMapper.readValue(mock, new TypeReference<List<Product>>() {
        });

        products = productRepository.saveAll(products);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductDto> result = (PagedResponse<ProductDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(5, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todos os produtos por categoria")
    void findAllByCategory() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "products.json");
        List<Product> products = objectMapper.readValue(mock, new TypeReference<List<Product>>() {
        });

        products = productRepository.saveAll(products);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("category", ProductCategory.SAUDE.toString())
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductDto> result = (PagedResponse<ProductDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(1, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todos os produtos por nome")
    void findAllByName() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "products.json");
        List<Product> products = objectMapper.readValue(mock, new TypeReference<List<Product>>() {
        });

        products = productRepository.saveAll(products);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("name", "stanley")
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductDto> result = (PagedResponse<ProductDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(4, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todos os produtos por fornecedor")
    void findAllBySupplier() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "products.json");
        List<Product> products = objectMapper.readValue(mock, new TypeReference<List<Product>>() {
        });

        products = productRepository.saveAll(products);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("supplier", "stanley")
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductDto> result = (PagedResponse<ProductDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(4, result.getData().size());

    }

    @Test
    @DisplayName("Teste procurar um produto por SKU")
    void findBySku() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/sku" + "/" + product.getSku())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ProductDto result = (ProductDto) getResponseBody(response, ProductDto.class);

        assertNotNull(result);

    }

}
