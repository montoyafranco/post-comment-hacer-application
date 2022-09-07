package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;

public interface EventBus {
    void publish(DomainEvent event);
    void publishPost(PostViewModel model);
    void publishComment(CommentViewModel model);

    void publishError(Throwable errorEvent);
}
