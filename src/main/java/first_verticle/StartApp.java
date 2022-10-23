package first_verticle;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;

public class StartApp {

    public static void main(String[] args) {
        Vertx  vertx= Vertx.vertx();
        Router router = Router.router(Vertx.vertx());
        vertx.deployVerticle(new ServerVerticle(router));           // start server and listen port (8181)
        vertx.deployVerticle(new HelloWithNameVerticle(router));    // address: /hello/<name>

        router.get("/hello").handler(rc->{
            rc.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                    .end("Hi router");
        });

        vertx.eventBus().consumer("user.names", message -> {
            ServerVerticle.logger.info(">>> "+ message.body());
        });

    }
}
