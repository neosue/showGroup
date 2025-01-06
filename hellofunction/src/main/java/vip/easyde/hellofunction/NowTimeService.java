package vip.easyde.hellofunction;

import java.util.Date;
import java.util.function.Function;

public class NowTimeService implements Function<NowTimeService.Request, NowTimeService.Response> {
    public record Request() {}
    public record Response(Date date) {}

    public Response apply(Request request) {
        return new Response(new Date());
    }
}
