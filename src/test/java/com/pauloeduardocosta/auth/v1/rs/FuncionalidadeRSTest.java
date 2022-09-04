package com.pauloeduardocosta.auth.v1.rs;

import com.pauloeduardocosta.auth.service.impl.FuncionalidadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FuncionalidadeRSTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FuncionalidadeService funcionalidadeService;

    private final String BASE_URL = "/v1/funcionalidade";

    @Test
    @WithMockUser
    void buscarFuncionalidade() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgLSBBdXRoIiwic3ViIjoiMSAxYzc5MTZjYS0yYWZhLTRmYmQtYTE1ZC05NGEzZmUzYWMwNTAiLCJpYXQiOjE2NjIyNjE2MjUsImV4cCI6MTY2MjM0ODAyNX0.smR9kZOnaAq5Lvo4hfjRzFzQ6e6ljDuK749aUwuSLbI")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /*@Test
    void buscarFuncionalidadeId() {
    }

    @Test
    void criarFuncionalidade() {
    }

    @Test
    void atualizarFuncionalidade() {
    }

    @Test
    void excluirFuncionalidade() {
    }*/
}