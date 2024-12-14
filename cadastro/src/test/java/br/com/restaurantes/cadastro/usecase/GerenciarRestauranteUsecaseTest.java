package br.com.restaurantes.cadastro.usecase;

import br.com.restaurantes.cadastro.domain.Restaurante;
import br.com.restaurantes.cadastro.gateway.RestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
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
    public void deveCadastrarRestaurante() {
        Restaurante restaurante = new Restaurante(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );

        gerenciarRestauranteUsecase.cadastrarRestaurante(restaurante);

        Mockito.verify(restauranteGateway, Mockito.times(1)).cadastrarRestaurante(restaurante);
    }

    @Test
    public void deveBuscarRestaurantePorNome() {
        String nome = "Restaurante do Jonas";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorNome(nome)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorNome(nome);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteGateway, Mockito.times(1)).buscarRestaurantePorNome(nome);
    }

    @Test
    public void deveBuscarRestaurantePorTipoCozinha() {
        String tipoCozinha = "Italiana";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorTipoCozinha(tipoCozinha)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorTipoCozinha(tipoCozinha);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteGateway, Mockito.times(1)).buscarRestaurantePorTipoCozinha(tipoCozinha);
    }

    @Test
    public void deveBuscarRestaurantePorLocalizacao() {
        String localizacao = "Rua Alves";

        List<Restaurante> restaurantesEsperados = Arrays.asList(new Restaurante(
                1L,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        ));

        Mockito.when(restauranteGateway.buscarRestaurantePorLocalizacao(localizacao)).thenReturn(restaurantesEsperados);

        List<Restaurante> restaurantesRetornados = gerenciarRestauranteUsecase.buscarRestaurantePorLocalizacao(localizacao);

        assertEquals(restaurantesEsperados.get(0).getId(), restaurantesRetornados.get(0).getId());
        assertEquals(restaurantesEsperados.get(0).getNome(), restaurantesRetornados.get(0).getNome());
        assertEquals(restaurantesEsperados.get(0).getQuantidadeLugares(), restaurantesRetornados.get(0).getQuantidadeLugares());
        assertEquals(restaurantesEsperados.get(0).getLocalizacao(), restaurantesRetornados.get(0).getLocalizacao());
        assertEquals(restaurantesEsperados.get(0).getTipoCozinha(), restaurantesRetornados.get(0).getTipoCozinha());
        assertEquals(restaurantesEsperados.get(0).getHorarioFuncionamento(), restaurantesRetornados.get(0).getHorarioFuncionamento());
        Mockito.verify(restauranteGateway, Mockito.times(1)).buscarRestaurantePorLocalizacao(localizacao);
    }

    @Test
    public void deveVerificarDisponibilidadeDeLugaresDoRestaurante(){
        Long id = 1L;
        String dataReserva = "2024-12-11T19:00";
        int lugaresDisponiveisEsperados = 50;

        Mockito.when(restauranteGateway.verificarDisponibilidadeLugares(id, dataReserva)).thenReturn(lugaresDisponiveisEsperados);

        int lugaresDisponiveis = gerenciarRestauranteUsecase.verificarDisponibilidadeLugares(id, dataReserva);

        assertEquals(lugaresDisponiveisEsperados, lugaresDisponiveis);
        Mockito.verify(restauranteGateway, Mockito.times(1)).verificarDisponibilidadeLugares(id, dataReserva);
    }

    @Test
    public void deveAtualizarRestaurante() {
        Long id = 1L;

        Restaurante restauranteAtualizado = new Restaurante(
                id,
                "Restaurante do Jonas 2",
                122,
                "Rua Alves 2",
                "Italiana 2",
                "06:00 às 20:00"
        );

        Mockito.when(restauranteGateway.atualizarRestaurante(id, restauranteAtualizado)).thenReturn(Optional.of(restauranteAtualizado));

        Optional<Restaurante> restauranteRetornado = gerenciarRestauranteUsecase.atualizarRestaurante(id, restauranteAtualizado);

        assertTrue(restauranteRetornado.isPresent());
        assertEquals(restauranteAtualizado.getId(), restauranteRetornado.get().getId());
        assertEquals(restauranteAtualizado.getNome(), restauranteRetornado.get().getNome());
        assertEquals(restauranteAtualizado.getQuantidadeLugares(), restauranteRetornado.get().getQuantidadeLugares());
        assertEquals(restauranteAtualizado.getLocalizacao(), restauranteRetornado.get().getLocalizacao());
        assertEquals(restauranteAtualizado.getTipoCozinha(), restauranteRetornado.get().getTipoCozinha());
        assertEquals(restauranteAtualizado.getHorarioFuncionamento(), restauranteRetornado.get().getHorarioFuncionamento());
        Mockito.verify(restauranteGateway, Mockito.times(1)).atualizarRestaurante(id, restauranteAtualizado);
    }

    @Test
    public void deveRemoverRestaurante() {
        Long id = 1L;

        gerenciarRestauranteUsecase.removerRestaurante(id);

        Mockito.verify(restauranteGateway, Mockito.times(1)).removerRestaurante(id);
    }
}
