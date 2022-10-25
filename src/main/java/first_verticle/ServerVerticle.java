package first_verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;

public class ServerVerticle extends AbstractVerticle {
    private Router router;
    public static Logger logger = LoggerFactory.getLogger(ServerVerticle.class);
    private static final int httpPort = 8181;

    public ServerVerticle(Router router) {
        this.router = router;
    }

    @Override
    public void start(Promise<Void> promise) throws Exception {
        router.route("/resources/*").handler(StaticHandler.create("templates/resources"));
//        TemplateEngine engine = HandlebarsTemplateEngine.create(vertx);
//        TemplateHandler handler = TemplateHandler.create(engine,"resources/templates","text/html");

        TemplateEngine templateEngine = ThymeleafTemplateEngine.create(vertx);
        TemplateHandler handler = TemplateHandler.create(templateEngine, "webapp/templates","text/html");
        router.route("/templates/*").handler(handler);
        router.route().handler(h->h.reroute("/templates/index.html"));

//        router.route("/index").handler(context->{
//            HttpServerResponse response = context.response();
//            response.putHeader(HttpHeaders.CONTENT_TYPE,"text/html").end("/templates/index.html");
//            context.reroute("/templates/index.html");
//        });

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
