package br.com.restaurantes.cadastro.exception;

import lombok.Getter;

@Getter
public class ErroAcessarRepositorioException extends SystemBaseException {
	private final String code = "usuario.erroAcessarRepositorio";
	private final String message = "Erro ao acessar repositorio de dados.";
	private final Integer httpStatus = 500;
}
