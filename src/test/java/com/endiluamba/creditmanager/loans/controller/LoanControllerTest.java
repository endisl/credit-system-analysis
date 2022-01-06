package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.loans.dto.MessageDTO;
import com.endiluamba.creditmanager.loans.builder.LoanRequestDTOBuilder;
import com.endiluamba.creditmanager.loans.builder.LoanResponseDTOBuilder;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.service.LoanService;
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

import java.util.Collections;

import static com.endiluamba.creditmanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoanControllerTest {

    private static final String LOANS_API_URL_PATH = "/api/v1/loans";

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private LoanRequestDTOBuilder loanRequestDTOBuilder;

    private LoanResponseDTOBuilder loanResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        loanRequestDTOBuilder = LoanRequestDTOBuilder.builder().build();
        loanResponseDTOBuilder = LoanResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(loanController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        LoanRequestDTO expectedLoanToCreateDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        String expectedCreationMessage = "Loan with ID 1 successfully submitted";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(loanService.create(any(AuthenticatedUser.class), eq(expectedLoanToCreateDTO))).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(LOANS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedLoanToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeReturned() throws Exception {
        LoanRequestDTO expectedLoanToCreateDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        expectedLoanToCreateDTO.setLoanAmount(null);

        mockMvc.perform(post(LOANS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedLoanToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidBookIdThenOkStatusShouldBeInformed() throws Exception {
        LoanRequestDTO expectedLoanToFindDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        LoanResponseDTO expectedFoundLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();

        when(loanService.findByIdAndCustomer(any(AuthenticatedUser.class), eq(expectedLoanToFindDTO.getId()))).thenReturn(expectedFoundLoanDTO);

        mockMvc.perform(get(LOANS_API_URL_PATH + "/" + expectedLoanToFindDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedLoanToFindDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundLoanDTO.getId().intValue())))
                .andExpect(jsonPath("$.loanAmount", is(expectedFoundLoanDTO.getLoanAmount())))
                .andExpect(jsonPath("$.installments", is(expectedFoundLoanDTO.getInstallments())));
    }

    @Test
    void whenGETListIsCalledThenOkStatusShouldBeInformed() throws Exception {
        LoanResponseDTO expectedFoundLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();

        when(loanService.findAllByCustomer(any(AuthenticatedUser.class))).thenReturn(Collections.singletonList(expectedFoundLoanDTO));

        mockMvc.perform(get(LOANS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedFoundLoanDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].loanAmount", is(expectedFoundLoanDTO.getLoanAmount())))
                .andExpect(jsonPath("$[0].installments", is(expectedFoundLoanDTO.getInstallments())));
    }

    @Test
    void whenDELETEIsCalledWithValidBookIdThenNoContentStatusShouldBeInformed() throws Exception {
        LoanRequestDTO expectedLoanToDeleteDTO = loanRequestDTOBuilder.buildLoanRequestDTO();

        doNothing().when(loanService).deleteByIdAndCustomer(any(AuthenticatedUser.class), eq(expectedLoanToDeleteDTO.getId()));

        mockMvc.perform(delete(LOANS_API_URL_PATH + "/" + expectedLoanToDeleteDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedLoanToDeleteDTO)))
                .andExpect(status().isNoContent());
    }
}
