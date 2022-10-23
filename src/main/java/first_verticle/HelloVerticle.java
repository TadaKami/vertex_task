package first_verticle;

import io.vertx.core.AbstractVerticle;

public class HelloVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request ->{
            request.response().end("Hello Vert.x World!");
        });
    }
}
