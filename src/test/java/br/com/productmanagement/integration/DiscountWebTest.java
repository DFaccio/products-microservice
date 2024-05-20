package br.com.productmanagement.integration;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.frameworks.db.DiscountRepository;
import br.com.productmanagement.helper.DiscountTestHelper;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.utils.Constants;
import br.com.productmanagement.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
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
class DiscountWebTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountTestHelper discountTestHelper;

    private static final String REQUEST_MAPPING_ROOT = "/discount";

    private static final String MOCK_DIRECTORY = "discount/";

    private Discount discount;

    @BeforeEach
    void setUp() {
        discount = discountRepository.save(discountTestHelper.newDiscount());
    }

    @AfterEach
    void cleanUp() {
        discountRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste criação de desconto")
    void insert() throws Exception {

        DiscountDto body =discountTestHelper.newDiscountDto();

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        post(REQUEST_MAPPING_ROOT + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        DiscountDto result = (DiscountDto) getResponseBody(response, DiscountDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste update de desconto")
    void update() throws Exception {

        DiscountDto body = discountTestHelper.updateDiscountDto();

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/update" + "/" + discount.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        DiscountDto result = (DiscountDto) getResponseBody(response, DiscountDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste listar todos os descontos")
    void findAll() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "discounts.json");
        List<Discount> discounts = objectMapper.readValue(mock, new TypeReference<List<Discount>>() {
        });

        discounts = discountRepository.saveAll(discounts);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<DiscountDto> result = (PagedResponse<DiscountDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(5, result.getData().size());

    }

    @Test
    @DisplayName("Teste procurar um desconto por ID")
    void findById() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/id" + "/" + discount.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        DiscountDto result = (DiscountDto) getResponseBody(response, DiscountDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste procurar um desconto por cupom")
    void findByCoupon() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "discounts.json");
        List<Discount> discounts = objectMapper.readValue(mock, new TypeReference<List<Discount>>() {
        });

        discounts = discountRepository.saveAll(discounts);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/coupon" + "/" + "FIAP02")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        DiscountDto result = (DiscountDto) getResponseBody(response, DiscountDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste procurar um desconto por categoria")
    void findByProductCategory() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "discounts.json");
        List<Discount> discounts = objectMapper.readValue(mock, new TypeReference<List<Discount>>() {
        });

        discounts = discountRepository.saveAll(discounts);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/category" + "/" + ProductCategory.SAUDE)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        DiscountDto result = (DiscountDto) getResponseBody(response, DiscountDto.class);

        assertNotNull(result);

    }

}
