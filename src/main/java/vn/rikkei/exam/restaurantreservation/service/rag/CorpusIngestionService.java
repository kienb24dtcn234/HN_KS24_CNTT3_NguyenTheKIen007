package vn.rikkei.exam.restaurantreservation.service.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorpusIngestionService {

    public static final String CORPUS_FILE = "tai_lieu_noi_bo.md"; 

    private final VectorStore vectorStore;

    public int ingest() throws IOException {
        var resource = new ClassPathResource(CORPUS_FILE);
        String content = resource.getContentAsString(StandardCharsets.UTF_8);
        List<Document> docs = new TokenTextSplitter().apply(List.of(new Document(content)));
        if (!docs.isEmpty()) {
            vectorStore.add(docs);
        }
        return docs.size();
    }
}
