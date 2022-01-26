package io.github.hossensyedriadh.springbootjwtauthentication.controller_advice;

import io.github.hossensyedriadh.springbootjwtauthentication.controller.v1.authentication.AuthenticationController;
import io.github.hossensyedriadh.springbootjwtauthentication.exception.GenericErrorResponse;
import io.github.hossensyedriadh.springbootjwtauthentication.exception.InvalidCredentialsException;
import io.github.hossensyedriadh.springbootjwtauthentication.exception.InvalidRefreshTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackageClasses = {AuthenticationController.class})
public class AuthenticationControllerAdvice {
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public AuthenticationControllerAdvice(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GenericErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), e.getPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<GenericErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), e.getPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
