package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers;


import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.BringAllPostsUseCase;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.BringPostById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryHandler {
    @Bean
    public RouterFunction<ServerResponse> getAllPosts(BringAllPostsUseCase
                                                                  bringAllPostsUseCase){
        //recordar que el BodyInserter from publisher ejecuta el repositorio como en el repo de mishel y luego
        //el segundo parametro es la clase que deberia esperar,en este caso espero modelos

        return route(GET("/getAllPosts"),
                request-> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(
                                BodyInserters.fromPublisher(bringAllPostsUseCase.getPosts(), PostViewModel.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).build()));

    }

    @Bean
    public RouterFunction<ServerResponse> getPostById(BringPostById bringPostById){

        return route(GET("/getAllPostsId/{postId}"),
                request-> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(
                                BodyInserters.fromPublisher(
                                        bringPostById.getPostById
                                                (request.pathVariable("postId")), PostViewModel.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).build()));




    //Create a route that allows you to make a Get Http request that brings you all the posts and also a post by its id
}}
