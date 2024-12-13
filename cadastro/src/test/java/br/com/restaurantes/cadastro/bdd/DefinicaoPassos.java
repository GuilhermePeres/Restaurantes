package br.com.restaurantes.cadastro.bdd;

import br.com.restaurantes.cadastro.controller.json.RestauranteJson;
import br.com.restaurantes.cadastro.domain.Restaurante;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefinicaoPassos {
    private Response response;

    private RestauranteJson restauranteJson;

    private String endpoint = "http://localhost:8080/api/v1/restaurantes";

    @Quando("criar novo restaurante")
    public void criarNovoRestaurante() {
        RestauranteJson restauranteJson = new RestauranteJson(
                null,
                "Restaurante do Jonas",
                100,
                "Rua Alves",
                "Italiana",
                "10:00 às 22:00"
        );

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(restauranteJson).when().post(endpoint);

        this.restauranteJson = response.then().extract().as(RestauranteJson.class);
    }

    @Entao("o restaurante é criado com sucesso")
    public void restauranteCriado() {
        this.restauranteJson = response.then().statusCode(HttpStatus.CREATED.value()).body("$", hasKey("id")).extract().as(RestauranteJson.class);
        System.out.println();
    }

//    @Quando("requisitar atualização da reserva")
//    public void requisitarAtualizacaoDaReserva() {
//        AtualizarReservaJson atualizarReservaJson = new AtualizarReservaJson(reserva.id(),
//                1L, 10,
//                "João Silva", LocalDateTime.parse("2024-11-20T15:30:00"),
//                Reserva.Status.INICIADA);
//
//        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(atualizarReservaJson).when().put(endpoint);
//    }
//
//    @Então("a reserva é atualizada com sucesso")
//    public void atualizarReserva() {
//        response.then().statusCode(HttpStatus.OK.value()).body("$", hasKey("reserva"));
//    }
//
//    @Dado("que mais de uma reserva exista")
//    public void criarMultiplasReservas() {
//        criarNovaReserva();
//        criarNovaReserva();
//        criarNovaReserva();
//    }
//
//    @Quando("listar as reservas")
//    public void listarReservas() {
//        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).when().get(endpoint + "?restauranteId=2&dataReserva=2024-12-01T18:00:00");
//    }
//
//    @Então("as reservas são listadas")
//    public void conferirListaDeReservas() {
//        ListarReservasResponseJson reservas = response.then().extract().as(ListarReservasResponseJson.class);
//
//        assertFalse(reservas.reservas().isEmpty());
//        assertTrue(reservas.reservas().size() > 1);
//    }
//
//    @Dado("que uma reserva esteja iniciada")
//    public void iniciarReservaPendente() {
//        criarNovaReserva();
//        requisitarAtualizacaoDaReserva();
//    }
//
//    @Quando("avaliar a reserva")
//    public void avaliarReserva() {
//        CriarAvaliacaoJson criarAvaliacaoJson = new CriarAvaliacaoJson(
//                reserva.id(),
//                Avaliacao.Satisfacao.PERFEITO,
//                "Comentario Teste");
//
//        response = given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(criarAvaliacaoJson)
//                .when()
//                .post(endpoint + "/avaliar");
//    }
//
//    @Então("a reserva é avaliada com sucesso")
//    public void conferirAvaliacao() {
//        response.then()
//                .statusCode(HttpStatus.CREATED.value())
//                .body("$", hasKey("id"));
//    }
}
