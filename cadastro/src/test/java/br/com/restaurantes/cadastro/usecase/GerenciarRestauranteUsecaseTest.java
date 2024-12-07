package br.com.restaurantes.cadastro.usecase;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GerenciarRestauranteUsecaseTest {
    @InjectMocks
    private GerenciarRestauranteUsecase gerenciarRestauranteUsecase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
    public void testeCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        );

        gerenciarRestauranteUsecase.cadastrar(restaurante);

        Mockito.verify(restauranteGateway, Mockito.times(1)).cadastrarRestaurante(restaurante);
    }

    @Test
    public void testeBuscarRestaurantePorNome_RestauranteEncontrado() {
        String nome = "Restaurante do Jonas";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorNome(nome)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome);

        assertEquals(restaurantesEsperados, restaurantesRetornados);
    }

    @Test
    public void testeBuscarRestaurantePorTipoCozinha_RestauranteEncontrado() {
        String tipoCozinha = "Italiana";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha);

        assertEquals(restaurantesEsperados, restaurantesRetornados);
    }

    @Test
    public void testeBuscarRestaurantePorLocalizacao_RestauranteEncontrado() {
        String localizacao = "Rua Alvez";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorLocalizacao(localizacao)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao);

        assertEquals(restaurantesEsperados, restaurantesRetornados);
    }

    @Test
    public void testeAtualizarRestaurante_RestauranteExistente() {
        Long id = 1L;

        Restaurante restauranteExistente = new Restaurante(
                id,
                "Restaurante do Jonas",
                100,
                "Rua Alvez",
                "Italiana",
                "10:00 às 22:00"
        );

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alvez 2",
                "Italiana 2",
                "12:00 às 20:00"
        );

        Mockito.when(restauranteGateway.atualizarRestaurante(id, restauranteAtualizado)).thenReturn(Optional.of(restauranteAtualizado));

        Optional<Restaurante> restauranteRetornado = gerenciarRestauranteUsecase.atualizarRestaurante(id, restauranteAtualizado);

        assertTrue(restauranteRetornado.isPresent());
        assertEquals(restauranteAtualizado, restauranteRetornado.get());
        Mockito.verify(restauranteGateway, Mockito.times(1)).atualizarRestaurante(id, restauranteAtualizado);
    }

}
