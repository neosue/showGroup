package vip.easyde.hello.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class HelloVectorstoreCtrl {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HelloVectorstoreCtrl.class);

    @Autowired
    VectorStore vectorStore;

    @GetMapping("/hello")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public void hello() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        // Add the documents to Neo4j
        vectorStore.add(documents);
        // Retrieve documents similar to a query
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query("Spring").withTopK(7).getQuery());
        logger.info("results: {}", results);
    }
}
