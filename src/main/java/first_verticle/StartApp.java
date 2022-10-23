package first_verticle;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;

public class StartApp {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(Vertx.vertx());
        vertx.createHttpServer().requestHandler(router).listen(8888,http->{});
        router.get("/hello").handler(rc->{
            rc.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE,"text/plain")
                    .end("Hello?");
        });



    }
}
