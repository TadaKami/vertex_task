package first_verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class ServerVerticle extends AbstractVerticle {
    private Router router;
    public static Logger logger = LoggerFactory.getLogger(ServerVerticle.class);
    private static final int httpPort = 8181;

    public ServerVerticle(Router router) {
        this.router = router;
    }

    @Override
    public void start(Promise<Void> promise) throws Exception {
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(httpPort)
                .onSuccess(suc->{
                    logger.info("http server is running: http://127.0.0.1:"+httpPort);
                     promise.complete();
                })
                .onFailure(promise::fail);
    }
}
