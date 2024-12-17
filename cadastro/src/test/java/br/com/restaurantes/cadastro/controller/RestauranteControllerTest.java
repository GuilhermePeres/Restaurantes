package br.com.restaurantes.cadastro.controller;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.usecase.GerenciarRestauranteUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestauranteController.class)
 class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GerenciarRestauranteUsecase gerenciarRestauranteUsecase;

    @Test
     void deveCadastrarRestaurante() throws Exception {
        RestauranteJson restauranteJson = new RestauranteJson(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );

        mockMvc.perform(post("/api/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restauranteJson)))
                .andExpect(status().isOk());

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).cadastrarRestaurante(any(Restaurante.class));
    }

    @Test
     void deveBuscarRestaurantePorNome() throws Exception {
        String nome = "Restaurante do Jonas";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome)).thenReturn(restaurantesEsperados);

        mockMvc.perform(get("/api/v1/restaurantes/{nome}", nome)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].nome").value("Restaurante do Jonas"))
                .andExpect(jsonPath("$.[0].quantidadeLugares").value(100))
                .andExpect(jsonPath("$.[0].localizacao").value("Rua Alves"))
                .andExpect(jsonPath("$.[0].tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.[0].horarioFuncionamento").value("10:00 às 22:00"));

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).buscarRestaurantePorNome(nome);
    }

    @Test
     void deveBuscarRestaurantePorTipoCozinha() throws Exception {
        String tipoCozinha = "Italiana";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(restaurantesEsperados);

        mockMvc.perform(get("/api/v1/restaurantes/tipoCozinha/{tipoCozinha}", tipoCozinha)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].nome").value("Restaurante do Jonas"))
                .andExpect(jsonPath("$.[0].quantidadeLugares").value(100))
                .andExpect(jsonPath("$.[0].localizacao").value("Rua Alves"))
                .andExpect(jsonPath("$.[0].tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.[0].horarioFuncionamento").value("10:00 às 22:00"));

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).buscarRestaurantePorTipoCozinha(tipoCozinha);
    }

    @Test
     void deveBuscarRestaurantePorLocalizacao() throws Exception {
        String localizacao = "Rua Alves";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao)).thenReturn(restaurantesEsperados);

        mockMvc.perform(get("/api/v1/restaurantes/localizacao/{localizacao}", localizacao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].nome").value("Restaurante do Jonas"))
                .andExpect(jsonPath("$.[0].quantidadeLugares").value(100))
                .andExpect(jsonPath("$.[0].localizacao").value("Rua Alves"))
                .andExpect(jsonPath("$.[0].tipoCozinha").value("Italiana"))
                .andExpect(jsonPath("$.[0].horarioFuncionamento").value("10:00 às 22:00"));

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).buscarRestaurantePorLocalizacao(localizacao);
    }

    @Test
     void deveVerificarDisponibilidadeDeLugaresDoRestaurante() throws Exception {
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";
        int lugaresDisponiveisEsperados = 100;

        Mockito.when(gerenciarRestauranteUsecase.verificarDisponibilidadeLugares(id, dataReserva)).thenReturn(lugaresDisponiveisEsperados);

        mockMvc.perform(get("/api/v1/restaurantes/disponibilidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("restauranteId", id.toString())
                        .param("dataReserva", dataReserva))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(lugaresDisponiveisEsperados));

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).verificarDisponibilidadeLugares(id, dataReserva);
    }

    @Test
     void deveAtualizarRestaurante() throws Exception {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        RestauranteJson restauranteAtualizadoJson = new RestauranteJson(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Restaurante> restauranteCaptor = ArgumentCaptor.forClass(Restaurante.class);

        Mockito.when(gerenciarRestauranteUsecase.atualizarRestaurante(idCaptor.capture(), restauranteCaptor.capture())).thenReturn(Optional.of(restauranteAtualizado));

        mockMvc.perform(put("/api/v1/restaurantes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteAtualizadoJson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Restaurante do Jonas 2"))
                .andExpect(jsonPath("$.quantidadeLugares").value(122))
                .andExpect(jsonPath("$.localizacao").value("Rua Alvez 2"))
                .andExpect(jsonPath("$.tipoCozinha").value("Italiana 2"))
                .andExpect(jsonPath("$.horarioFuncionamento").value("06:00 às 20:00"));

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).atualizarRestaurante(idCaptor.capture(), restauranteCaptor.capture());
    }

    @Test
     void deveRemoverRestaurante() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/restaurantes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(gerenciarRestauranteUsecase, Mockito.times(1)).removerRestaurante(id);
    }
}
