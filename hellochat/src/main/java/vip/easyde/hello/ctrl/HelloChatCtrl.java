package vip.easyde.hello.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.springframework.ai.chat.model.Generation;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.http.codec.ServerSentEvent;

@RestController
@RequestMapping("/chat")
public class HelloChatCtrl {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HelloChatCtrl.class);

    private final OllamaChatModel chatModel;
    private final ChatClient chatClient;

    public HelloChatCtrl(OllamaChatModel chatModel, ChatClient.Builder chatClientBuilder) {
        this.chatModel = chatModel;
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/hello")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public String hello( @RequestParam(value = "message") String message) {
        return this.chatModel.call(message);
    }

    AtomicInteger count = new AtomicInteger(0);
    @GetMapping("/stream")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public Flux<ServerSentEvent<String>> stream(@RequestParam(value = "message") String message) {
        return chatClient.prompt(message).stream().chatResponse()
                .filter(Objects::nonNull)
                .filter(chatResponse -> chatResponse.getResults() != null)
                .flatMap(chatResponse -> Flux.fromIterable(chatResponse.getResults()))
                .filter(Objects::nonNull)
                .map(Generation::getOutput)
                .filter(Objects::nonNull)
                .filter(content -> Objects.nonNull(content.getContent()))
                .map(AssistantMessage::getContent)
                .filter(Objects::nonNull)
                .map(content -> ServerSentEvent.builder(content).id(count.incrementAndGet() + "").build())
                //.doOnNext(System.out::println)
                .concatWith(Flux.just(ServerSentEvent.builder("complete").event("done").id(count.incrementAndGet() + "").build()));
    }

}
