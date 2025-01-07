package vip.easyde.hello.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class HelloChatCtrl {

    private final OllamaChatModel chatModel;

    public HelloChatCtrl(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/hello")
    @Operation(summary = "接收文字消息，返回大模型响应内容")
    public String hello( @RequestParam(value = "message") String message) {
        return this.chatModel.call(message);
    }

}
