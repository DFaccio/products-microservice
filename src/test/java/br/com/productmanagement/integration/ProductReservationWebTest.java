package br.com.productmanagement.integration;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.frameworks.db.ProductRepository;
import br.com.productmanagement.frameworks.db.ProductReservationRepository;
import br.com.productmanagement.helper.ProductReservationTestHelper;
import br.com.productmanagement.helper.ProductTestHelper;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceadapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.util.enums.ReservationStatus;
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
class ProductReservationWebTest extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductReservationRepository productReservationRepository;

    @Autowired
    private ProductReservationTestHelper productReservationTestHelper;

    @Autowired
    private ProductTestHelper productTestHelper;

    @Autowired
    private ProductRepository productRepository;

    private static final String REQUEST_MAPPING_ROOT = "/reservation";

    private static final String MOCK_DIRECTORY = "reservation/";

    private ProductReservation productReservation;

    private Product product;

    @BeforeEach
    void setUp() {
        productReservation = productReservationRepository.save(productReservationTestHelper.newReservation());
        product = productRepository.save(productTestHelper.newProduct());
    }

    @AfterEach
    void cleanUp() {
        productReservationRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste criação de reserva de produto")
    void newReservation() throws Exception {

        ReservationsDto body = productReservationTestHelper.newReservationDto();

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        post(REQUEST_MAPPING_ROOT + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ReservationsDto result = (ReservationsDto) getResponseBody(response, ReservationsDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste procurar uma reserva por ID")
    void findById() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/id" + "/" + productReservation.getReservationId())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ProductReservationDto result = (ProductReservationDto) getResponseBody(response, ProductReservationDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste listar todas as reservas")
    void findAll() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservations.json");
        List<ProductReservation> reservations = objectMapper.readValue(mock, new TypeReference<List<ProductReservation>>() {
        });

        reservations = productReservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductReservationDto> result = (PagedResponse<ProductReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(4, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todas as reservas criadas")
    void findAllCreated() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservations.json");
        List<ProductReservation> reservations = objectMapper.readValue(mock, new TypeReference<List<ProductReservation>>() {
        });

        reservations = productReservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("reservationStatus", ReservationStatus.CREATED.toString())
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductReservationDto> result = (PagedResponse<ProductReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(2, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todas as reservas confirmadas")
    void findAllConfirmed() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservations.json");
        List<ProductReservation> reservations = objectMapper.readValue(mock, new TypeReference<List<ProductReservation>>() {
        });

        reservations = productReservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("reservationStatus", ReservationStatus.CONFIRMED.toString())
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductReservationDto> result = (PagedResponse<ProductReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(1, result.getData().size());

    }

    @Test
    @DisplayName("Teste listar todas as reservas canceladas")
    void findAllCancelled() throws Exception {

        File mock = getFile(MOCK_DIRECTORY + "reservations.json");
        List<ProductReservation> reservations = objectMapper.readValue(mock, new TypeReference<List<ProductReservation>>() {
        });

        reservations = productReservationRepository.saveAll(reservations);

        MockHttpServletResponse response = mockMvc.perform(
                        get(REQUEST_MAPPING_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("reservationStatus", ReservationStatus.CANCELLED.toString())
                                .param("pageSize", "10")
                                .param("initialPage", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        PagedResponse<ProductReservationDto> result = (PagedResponse<ProductReservationDto>) getResponseBody(response, PagedResponse.class);

        assertEquals(0, result.getPage().getPage());
        assertEquals(10, result.getPage().getPageSize());
        assertEquals(1, result.getPage().getTotalPages());
        assertEquals(1, result.getData().size());

    }

    @Test
    @DisplayName("Teste update de reserva de produto")
    void reservationUpdate() throws Exception {

        ReservationsDto body = productReservationTestHelper.updateReservationDto(productReservation.getReservationId());

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ReservationsDto result = (ReservationsDto) getResponseBody(response, ReservationsDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste confirmação de reserva de produto")
    void reservationConfirmation() throws Exception {

        ReservationsDto body = productReservationTestHelper.reservationConfirmCancelDto(productReservation.getReservationId());

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/confirmation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ReservationsDto result = (ReservationsDto) getResponseBody(response, ReservationsDto.class);

        assertNotNull(result);

    }

    @Test
    @DisplayName("Teste cancelamento de reserva de produto")
    void reservationCancellation() throws Exception {

        ReservationsDto body = productReservationTestHelper.reservationConfirmCancelDto(productReservation.getReservationId());

        String requestBody = objectMapper.writeValueAsString(body);

        MockHttpServletResponse response = mockMvc.perform(
                        put(REQUEST_MAPPING_ROOT + "/cancellation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                ).andExpect(status().isOk())
                .andReturn().getResponse();

        ReservationsDto result = (ReservationsDto) getResponseBody(response, ReservationsDto.class);

        assertNotNull(result);

    }

}
