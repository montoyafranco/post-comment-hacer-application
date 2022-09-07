package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus;


import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.config.RabbitMqConfig;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.EventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventBus implements EventBus {
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );
//        if (event.getClass().getTypeName() == "com.posada.santiago.alphapostsandcomments.domain.events.CommentAdded"){
//
//            //Find a way to send this notification through the predefined queues in the rabbitMq configuration,
//            //To that specific exchange and queues bases on the type of event)
//        }
//        if (event.getClass().getTypeName() == "com.posada.santiago.alphapostsandcomments.domain.events.PostCreated"){
//
//        }


    }

    @Override
    public void publishPost(PostViewModel model) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE
                , RabbitMqConfig.PROXY_ROUTING_KEY_POST_CREATED, gson.toJson(model).getBytes());

    }

    @Override
    public void publishComment(CommentViewModel model) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.PROXY_ROUTING_KEY_COMMENT_ADDED, gson.toJson(model).getBytes());

    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
