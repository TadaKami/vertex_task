package first_verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class SumNumbersVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().consumer("sum.numbers",msg->{
            System.out.println(msg);
        });
    }
}
