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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BringPostByIdTest {

    @Mock
    DomainViewRepository domainViewRepository;

    BringPostById bringPostByIdUseCase;

    @BeforeEach
    void init(){
        bringPostByIdUseCase = new BringPostById(domainViewRepository);

    }
    @Test
    void testID(){
        PostViewModel postViewModel = new PostViewModel(
                "1",
                "agus",
                "pogramador",
                new ArrayList<>()

        ) ;
        Mockito.when(domainViewRepository.findByAggregateId("1")).thenReturn(Mono.just(postViewModel));

        var bringPost = bringPostByIdUseCase.getPostById(postViewModel.getAggregateId());

        StepVerifier.create(bringPost).expectNext(postViewModel)
                .expectComplete()
                .verify();
        Mockito.verify(domainViewRepository).findByAggregateId("1");



    }





}