package altai.testToDoList.servicies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.restful-api.dev").build();
    }

    public Mono<String> fetchExternalData() {

        Mono<String> asss =  webClient.get()
                .uri("/objects")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Response from external API: {}", response))
                .doOnError(e -> log.error("Error while calling external API", e));

        log.info("this not res:{}", asss);

        return asss;
    }
}
