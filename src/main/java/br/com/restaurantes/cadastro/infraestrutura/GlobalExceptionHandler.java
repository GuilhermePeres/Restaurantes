package br.com.restaurantes.cadastro.infraestrutura;

import br.com.restaurantes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.restaurantes.cadastro.exception.IllegalArgumentException;
import br.com.restaurantes.cadastro.exception.RestauranteNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestauranteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarErroRestauranteNaoEncontradoException(RestauranteNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus())).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ErroAcessarRepositorioException.class)
    public ResponseEntity<ErrorResponse> tratarErroAcessarRepositorioException(ErroAcessarRepositorioException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus())).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tratarErroIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus())).body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) { }
}
