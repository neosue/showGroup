package vip.easyde.hellofunction.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class HelloFunctionCtrl {

    private final ChatClient chatClient;

    public HelloFunctionCtrl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("/hello")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public String hello( @RequestParam(value = "message") String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

}
