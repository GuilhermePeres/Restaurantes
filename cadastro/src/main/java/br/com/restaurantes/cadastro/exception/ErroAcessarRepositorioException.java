package br.com.restaurantes.cadastro.exception;

import lombok.Getter;

@Getter
public class ErroAcessarRepositorioException extends SystemBaseException {
	private final String code = "restaurante.erroAcessarRepositorio";
	private final String message = "Erro ao acessar repositório.";
	private final Integer httpStatus = 500;
}
