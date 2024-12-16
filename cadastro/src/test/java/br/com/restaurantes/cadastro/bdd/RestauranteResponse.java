package br.com.restaurantes.cadastro.bdd;

import br.com.restaurantes.cadastro.domain.Restaurante;
import lombok.Getter;

import java.util.List;

@Getter
public class RestauranteResponse {
    private List<Restaurante> restaurantes;
    private String message;
}
