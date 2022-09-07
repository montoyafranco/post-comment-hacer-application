package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus.RabbitMqEventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.generic.DomainUpdater;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ViewUpdater extends DomainUpdater {

    //extiende de DomainUpdater
    //Complete the implementation of the view updater
    private final RabbitMqEventBus bus;

    //tarea de ayer
    //esto seria como un escuchador de eventos que crea un ViewModel
    // , y lo manda al repository para afectar ya mongo
    private final DomainViewRepository repository;

    public ViewUpdater(DomainViewRepository repository ,RabbitMqEventBus bus) {
        this.bus = bus;
        this.repository = repository;
        listen((PostCreated event)->{
            PostViewModel post = new PostViewModel(event.aggregateRootId()
                    , event.getAuthor(), event.getTitle(), new ArrayList<>());
            repository.saveNewPost(post).subscribe();
            bus.publishPost(post);

        });
        listen((CommentAdded event)->{
            CommentViewModel comment = new CommentViewModel(event.getId()
                    , event.aggregateRootId(), event.getAuthor(), event.getContent());
            repository.addCommentToPost(comment).subscribe();
            bus.publishComment(comment);
        });
    }
}
