package br.com.restaurantes.cadastro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalArgumentException extends SystemBaseException{
    private final String code = "restaurante.dateTimeParseException";
    private final Integer httpStatus = 500;
    private String message;
}
