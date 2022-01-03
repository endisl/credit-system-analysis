package com.endiluamba.creditmanager.customers.controller;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.builder.JwtRequestBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.JwtRequest;
import com.endiluamba.creditmanager.customers.dto.JwtResponse;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.service.AuthenticationService;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.endiluamba.creditmanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private static final String CUSTOMERS_API_URL_PATH = "/api/v1/customers";

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTOBuilder customerDTOBuilder;

    private JwtRequestBuilder jwtRequestBuilder;

    @BeforeEach
    void setUp() {
        customerDTOBuilder = CustomerDTOBuilder.builder().build();
        jwtRequestBuilder = JwtRequestBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        CustomerDTO expectedCustomerToCreateDTO = customerDTOBuilder.buildCustomerDTO();
        String expectedCreationMessage = "Customer Endi Luamba with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(customerService.create(expectedCustomerToCreateDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(CUSTOMERS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCustomerToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        CustomerDTO expectedCustomerToCreateDTO = customerDTOBuilder.buildCustomerDTO();
        expectedCustomerToCreateDTO.setName(null);

        mockMvc.perform(post(CUSTOMERS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCustomerToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentStatusShouldBeInformed() throws Exception {
        CustomerDTO expectedCustomerToDeleteDTO = customerDTOBuilder.buildCustomerDTO();
        var expectedCustomerToDeleteId = expectedCustomerToDeleteDTO.getId();

        doNothing().when(customerService).delete(expectedCustomerToDeleteId);

        mockMvc.perform(delete(CUSTOMERS_API_URL_PATH + "/" + expectedCustomerToDeleteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
        CustomerDTO expectedCustomerToUpdateDTO = customerDTOBuilder.buildCustomerDTO();
        expectedCustomerToUpdateDTO.setName("Endi Tequeiro");
        String expectedUpdateMessage = "Customer Endi Tequeiro with ID 1 successfully updated";
        MessageDTO expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdateMessage).build();
        var expectedCustomerToUpdateId = expectedCustomerToUpdateDTO.getId();

        when(customerService.update(expectedCustomerToUpdateId, expectedCustomerToUpdateDTO)).thenReturn(expectedUpdateMessageDTO);

        mockMvc.perform(put(CUSTOMERS_API_URL_PATH + "/" + expectedCustomerToUpdateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCustomerToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedUpdateMessage)));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateCustomerThenOkStatusShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("mockToken").build();

        when(authenticationService.createAuthenticationToken(jwtRequest)).thenReturn(expectedJwtToken);

        mockMvc.perform(post(CUSTOMERS_API_URL_PATH + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", is(expectedJwtToken.getJwtToken())));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateCustomerWithoutPasswordThenBadRequestStatusShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        jwtRequest.setPassword(null);

        mockMvc.perform(post(CUSTOMERS_API_URL_PATH + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }
}
