package vip.easyde.hello;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class NowTimeService implements Function<NowTimeService.Request, NowTimeService.Response> {
    public record Request() {}
    public record Response(String date) {}

    public Response apply(Request request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Response(sdf.format(new Date()));
    }
}
