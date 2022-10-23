package first_verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HelloWithNameVerticle extends AbstractVerticle {
    private double temp = 21;
    private Router router;

    public HelloWithNameVerticle(Router router){
        this.router = router;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        router.get("/hello/:name").handler(this::helloWithName);
    }

    private void helloWithName(RoutingContext context) {
        String name = context.pathParam("name");
        JsonObject jsonObject = createObjectWithName(name);
        context.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end("Hello, " + name);

        vertx.eventBus().publish("user.names", jsonObject);
    }

    private JsonObject createObjectWithName(String name){
        return new JsonObject().put("user_name",name);
    }
}
