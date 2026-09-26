package vn.rikkei.exam.restaurantreservation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.rikkei.exam.restaurantreservation.service.rag.CorpusIngestionService;

@Configuration
@RequiredArgsConstructor
public class RagConfig {

    private final CorpusIngestionService ingestionService;

    @Bean
    public ApplicationRunner corpusIngestionRunner() {
        return args -> ingestionService.ingest();
    }
}
