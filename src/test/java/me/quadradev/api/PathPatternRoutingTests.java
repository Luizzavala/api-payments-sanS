package me.quadradev.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ApiApplication.class, PathPatternRoutingTests.TestController.class})
@AutoConfigureMockMvc
class PathPatternRoutingTests {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    static class TestController {
        @GetMapping("/payments/{id}")
        String getPayment(@PathVariable String id) {
            return id;
        }
    }

    @Test
    void requestWithDotInPathIsHandled() throws Exception {
        mockMvc.perform(get("/payments/123.456"))
                .andExpect(status().isOk())
                .andExpect(content().string("123.456"));
    }
}
