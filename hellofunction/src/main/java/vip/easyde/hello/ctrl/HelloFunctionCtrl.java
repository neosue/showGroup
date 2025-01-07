package vip.easyde.hello.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class HelloFunctionCtrl {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HelloFunctionCtrl.class);

    private final ChatClient chatClient;

    public HelloFunctionCtrl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("/hello")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public String hello( @RequestParam(value = "message") String message) {
        ChatResponse response = this.chatClient.prompt("现在的时间是?")
                .functions("now")
                .call()
                .chatResponse();
        logger.info("Response: {}", response);
        return  response.getResult().toString();
    }

}
