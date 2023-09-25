//package uz.fastfood.dashboard.handler;
//
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.naming.AuthenticationException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(AuthenticationException.class)
//    public GenericResponseBean handleAuthenticationException(AuthenticationException ex, HttpServletResponse response){
//        GenericResponseBean genericResponseBean = GenericResponseBean.build(MessageKeys.UNAUTHORIZED);
//        genericResponseBean.setError(true);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        return genericResponseBean;
//    }
//}
