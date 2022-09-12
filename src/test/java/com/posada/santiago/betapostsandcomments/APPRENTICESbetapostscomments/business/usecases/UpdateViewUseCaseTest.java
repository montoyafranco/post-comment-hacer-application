package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus.RabbitMqEventBus;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.CommentAdded;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateViewUseCaseTest {
    @Mock
    RabbitMqEventBus eventBus;

    ViewUpdater viewUpdater;

    UpdateViewUseCase useCase;

    @Mock
    DomainViewRepository domainViewRepository;

    @BeforeEach
    void init() {
        viewUpdater = new ViewUpdater(domainViewRepository, eventBus);
        useCase = new UpdateViewUseCase(eventBus, viewUpdater);
    }

    @Test
    void createPostAndAddCommentTest() {
        var postCreated = new PostCreated("agus", "test");
        postCreated.setAggregateRootId("1");

        var commentAdd = new CommentAdded("1", "agus", "test");
        commentAdd.setAggregateRootId("1");

        var comment = new CommentViewModel(commentAdd.getId(),
                commentAdd.aggregateRootId(), commentAdd.getAuthor(), commentAdd.getContent());

        ArrayList<CommentViewModel> lista = new ArrayList<>();

        lista.add(comment);

        var post = new PostViewModel(postCreated.aggregateRootId()
                , postCreated.getAuthor(), postCreated.getTitle(), new ArrayList<>());
        post.setComments(lista);


        Mockito.when(domainViewRepository.addCommentToPost(Mockito.any(CommentViewModel.class))).thenReturn(Mono.just(post));

        Mockito.when(domainViewRepository.saveNewPost(Mockito.any(PostViewModel.class))).thenReturn(Mono.just(post));

        //llamado al usecase
        useCase.accept(commentAdd);
        useCase.accept(postCreated);


        //No me anda los asserts
//        StepVerifier.create(aplicar).expectNextMatches(
//                Objects::nonNull
//        ).expectNextCount(1).verifyComplete();

        //pruebo de postview event bus
        Mockito.verify(domainViewRepository, Mockito.times(1)).saveNewPost(Mockito.any(PostViewModel.class));
        Mockito.verify(eventBus, Mockito.times(1)).publishPost(Mockito.any(PostViewModel.class));

        //pruebo de repositorio de comment
        Mockito.verify(domainViewRepository, Mockito.times(1))
                .addCommentToPost(Mockito.any(CommentViewModel.class));
        Mockito.verify(eventBus, Mockito.times(1)).publishComment(Mockito.any(CommentViewModel.class));

    }

}