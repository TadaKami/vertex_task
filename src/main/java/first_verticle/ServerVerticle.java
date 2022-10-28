package first_verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;

public class ServerVerticle extends AbstractVerticle {
    private Router router;
    public static Logger logger = LoggerFactory.getLogger(ServerVerticle.class);
    public static final int httpPort = 8080;

    public ServerVerticle(Router router) {
        this.router = router;
    }

    @Override
    public void start(Promise<Void> promise) throws Exception {
        TemplateEngine templateEngine = ThymeleafTemplateEngine.create(vertx);
        TemplateHandler handler = TemplateHandler.create(templateEngine, "src/main/resources/templates","text/html");
        TemplateHandler handlerJs = TemplateHandler.create(templateEngine, "src/main/resources/js","application/javascript");
        TemplateHandler handlerCss = TemplateHandler.create(templateEngine, "src/main/resources/css","text/css");
        router.route("/templates/*").handler(handler);
        router.route("/js/*").handler(handlerJs);
        router.route("/css/*").handler(handlerCss);
        router.route("/indexroute").handler(h->h.reroute("/templates/index.html"));

        SockJSBridgeOptions opts = new SockJSBridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("sum.numbers"))
                .addOutboundPermitted(new PermittedOptions().setAddress("sum.numbers"))
                .addInboundPermitted(new PermittedOptions().setAddress("sum.numbers.logs"))
                .addOutboundPermitted(new PermittedOptions().setAddress("sum.numbers.logs"));

        // Create the event bus bridge and add it to the router.
        router.mountSubRouter("/eventbus", SockJSHandler.create(vertx).bridge(opts));
        router.route().handler(StaticHandler.create());

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
