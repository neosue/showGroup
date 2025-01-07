package vip.easyde.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ServiceConfig {
    @Bean
    @Description("获取系统时间") // function description
    public Function<NowTimeService.Request, NowTimeService.Response> now() {
        return new NowTimeService();
    }
}
