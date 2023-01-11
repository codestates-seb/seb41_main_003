package com.mainproject.server.auth.handler;

import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.utils.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /* 인증 Flow에서 예외 발생시 핸들링 메소드 */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        Object exception = request.getAttribute("exception");
        if (exception instanceof ServiceLogicException) {
            ErrorResponder.sendErrorResponse(
                    response,
                    ((ServiceLogicException) exception).getErrorCode());
        } else {
            ErrorResponder.sendErrorResponse(
                    response,
                    HttpStatus.UNAUTHORIZED);
        }
        logExceptionMessage(
                authException,
                (Exception) exception);
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message);
    }
}
