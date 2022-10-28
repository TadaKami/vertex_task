package first_verticle;

import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class SumNumbersVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().consumer("sum.numbers",msg->{
            Map<String, Object> publicNotice = createPublicNotice("localhost", ServerVerticle.httpPort, msg.body().toString());
            vertx.eventBus().publish("sum.numbers.logs", new Gson().toJson(publicNotice));
        });
    }

    private Map<String, Object> createPublicNotice(String host, int port, String message) {
        Date time = Calendar.getInstance().getTime();

        Map<String, Object> notice = new TreeMap<>();
        notice.put("type", "publish");
        notice.put("time", time.toString());
        notice.put("host", host);
        notice.put("port", port);
        notice.put("message", message);
        return notice;
    }
}
