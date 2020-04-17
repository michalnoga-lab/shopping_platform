package com.app.integration.webUser;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CartDTO;
import com.app.dto.DeliveryAddressDTO;
import com.app.mappers.DeliveryAddressMapper;
import com.app.mappers.UserMapper;
import com.app.model.DeliveryAddress;
import com.app.model.User;
import com.app.repository.DeliveryAddressRepository;
import com.app.service.CartService;
import com.app.service.DeliveryAddressService;
import com.app.service.SecurityService;
import com.app.validators.DeliveryAddressDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@ExtendWith(SpringExtension.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PrimaPlatformaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.tests.properties")
public class DeliveryAddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private DeliveryAddressRepository deliveryAddressRepository;

    @MockBean
    private DeliveryAddressService deliveryAddressService;

    @MockBean
    private CartService cartService;

    @MockBean
    private SecurityService securityService;

    @SpyBean
    private DeliveryAddressDtoValidator deliveryAddressDtoValidator;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("all")
    void test10() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/deliveryAddress/all").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("add - GET")
    void test20() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/deliveryAddress/add").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("add - POST")
    void test30() throws Exception {

        User user = User.builder().id(2L).build();

        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder()
                .id(1L)
                .street("street")
                .hidden(false)
                .phone("123456")
                .userDTO(UserMapper.toDto(user))
                .build();

        Mockito
                .when(securityService.getLoggedInUserId())
                .thenReturn(user.getId());

        Mockito
                .when(deliveryAddressService.add(deliveryAddressDTO, user.getId()))
                .thenReturn(deliveryAddressDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/deliveryAddress/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("street", "street")
                        .param("phone", "1234567890")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("remove/{id}")
    void test40() throws Exception {

        Optional<DeliveryAddress> deliveryAddress = Optional.of(DeliveryAddress.builder().id(1L).build());

        Mockito
                .when(deliveryAddressRepository.findById(1L))
                .thenReturn(deliveryAddress);

        Mockito
                .when(deliveryAddressService.hide(1L))
                .thenReturn(DeliveryAddressMapper.toDto(deliveryAddress.get()));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/deliveryAddress/remove/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("pick/{id}")
    void test50() throws Exception {

        DeliveryAddress deliveryAddress = DeliveryAddress.builder().id(1L).build();
        CartDTO cartDTO = CartDTO.builder().id(3L).build();

        Mockito
                .when(deliveryAddressRepository.findById(1L))
                .thenReturn(Optional.of(deliveryAddress));

        Mockito
                .when(securityService.getLoggedInUserId())
                .thenReturn(2L);

        Mockito
                .when(cartService.setAddressToCart(1L, 2L))
                .thenReturn(cartDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/deliveryAddress/pick/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}