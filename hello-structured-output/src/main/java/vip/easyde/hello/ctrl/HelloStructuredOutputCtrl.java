package vip.easyde.hello.ctrl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class HelloStructuredOutputCtrl {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HelloStructuredOutputCtrl.class);

    private final OllamaChatModel chatModel;


    public HelloStructuredOutputCtrl(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public record Subject(String subjectId, String subjectName) {
    }

    public record SubjectResponse(List<Subject> subjects) {
    }

    BeanOutputConverter<SubjectResponse> beanOutputConverter = new BeanOutputConverter<>(SubjectResponse.class);
    private final static String template = """
            获取5个会计一级科目.
            {format}
            """;

    @GetMapping("/hello")
    public String hello() {
        String format = this.beanOutputConverter.getFormat();

        Generation generation = chatModel.call(
                new PromptTemplate(template, Map.of("format", format)).create()).getResult();
        String msg = generation.getOutput().getContent();
        logger.info("Response: {}", msg);
        SubjectResponse subject = this.beanOutputConverter.convert(msg);
        return subject.subjects().toString();
    }

}
