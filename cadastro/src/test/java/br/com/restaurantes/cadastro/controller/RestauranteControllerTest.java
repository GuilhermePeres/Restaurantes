package br.com.restaurantes.cadastro.controller;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.usecase.GerenciarRestauranteUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GerenciarRestauranteUsecase gerenciarRestauranteUsecase;

    @Test
    public void deveCadastrarRestauranteComSucesso() throws Exception{
        RestauranteJson restauranteJson = new RestauranteJson(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
        "Italiana",
        "10:00 às 22:00"
        );

        Restaurante restaurante = new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        );

        ResponseEntity<String> responseEntity = ResponseEntity.ok("Restaurante cadastrado com sucesso!");

        verify(gerenciarRestauranteUsecase, times(1)).cadastrar(restaurante);

        mockMvc.perform(post("api/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restauranteJson)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

}
