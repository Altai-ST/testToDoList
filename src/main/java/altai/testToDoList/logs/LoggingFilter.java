package altai.testToDoList.logs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter implements Filter {

//    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        // Можно оставить пустым
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Логирование запроса
        log.info("Incoming Request: Method={}, URI={}", httpRequest.getMethod(), httpRequest.getRequestURI());

        // Обернем запрос и ответ, чтобы можно было повторно читать их содержимое
        CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper(httpRequest);
        CustomHttpServletResponseWrapper wrappedResponse = new CustomHttpServletResponseWrapper(httpResponse);

        // Логирование тела запроса
        String requestBody = wrappedRequest.getBody();
        log.info("Request Body: {}", requestBody);

        // Продолжение выполнения запроса
        chain.doFilter(wrappedRequest, wrappedResponse);

        // Логирование ответа
        String responseBody = wrappedResponse.getBody();
        log.info("Response Body: {}", responseBody);
        log.info("Response Status: {}", httpResponse.getStatus());

        httpResponse.getWriter().write(responseBody);
        httpResponse.getWriter().flush();
    }
}
