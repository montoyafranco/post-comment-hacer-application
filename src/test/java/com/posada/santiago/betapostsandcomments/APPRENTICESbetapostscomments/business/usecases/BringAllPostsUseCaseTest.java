package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;

import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BringAllPostsUseCaseTest {
    @Mock
    DomainViewRepository domainViewRepository;

    BringAllPostsUseCase bringAllPostsUseCase;

    @BeforeEach
    void init(){
        bringAllPostsUseCase = new BringAllPostsUseCase(domainViewRepository);
    }
    @Test
    void testGet(){

        Flux<PostViewModel> postExpecteds = Flux.just(new PostViewModel(),new PostViewModel());



        Mockito.when(this.domainViewRepository.findAllPosts()).thenReturn(postExpecteds);
        var accion = bringAllPostsUseCase.getPosts();

        StepVerifier.create(accion).expectNextMatches(
                Objects::nonNull
        ).expectNextCount(1).verifyComplete();

        Mockito.verify(domainViewRepository).findAllPosts();


    }

}