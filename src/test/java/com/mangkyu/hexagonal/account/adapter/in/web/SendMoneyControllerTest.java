package com.mangkyu.hexagonal.account.adapter.in.web;

import com.mangkyu.hexagonal.account.application.port.in.SendMoneyCommand;
import com.mangkyu.hexagonal.account.application.port.in.SendMoneyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SendMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SendMoneyCommand sendMoneyCommand;

    @Test
    void testSendMoney() throws Exception {

        mockMvc.perform(post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                        41L, 42L, 500)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(sendMoneyCommand).should()
                .sendMoney(any(SendMoneyRequest.class));
    }

}