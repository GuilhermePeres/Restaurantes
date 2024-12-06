package br.com.restaurantes.cadastro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class RestauranteNaoEncontradoException extends SystemBaseException {
	private final String code = "restaurante.RestauranteNaoEncontradoException";
	private final String message = "Restaurante não encontrado.";
	private final Integer httpStatus = 404;
}
