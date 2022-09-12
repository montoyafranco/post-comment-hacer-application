package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository;


import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.generic.models.StoredEvent;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.CommentViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MongoViewRepository implements DomainViewRepository {
    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        /**Make the implementation, using the template, to find a post by its aggregateId*/
        var query = new Query(Criteria.where("aggregateId").is(aggregateId));
        //
        return template.findOne(query,PostViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        /**make the implementation, using the template, of the method find all posts that are stored in the db*/
        //aca deberia hacer alguna especie de findall
        //siempre pide una entity class
        return template.findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel post) {
        /** make the implementation, using the template, to save a post*/
        //simplemente lo guardo
        return template.save(post);
    }

    @Override
    public Mono<PostViewModel> addCommentToPost(CommentViewModel comment) {
        /** make the implementation, using the template, to find the post in the database that you want to add the comment to,
         * then add the comment to the list of comments and using the Update class update the existing post
         * with the new list of comments*/
        //existe un finandmodify en mongo template
        //primero busco el post con su id por el query aggregate es el postid
        // Video ver
        var query = new Query(Criteria.where("aggregateId").is(comment.getPostId()));
        Update update = new Update();

        return template.findOne(query,PostViewModel.class)
                .flatMap(postViewModel -> {
                    List<CommentViewModel> comments = postViewModel
                            .getComments();
                    comments.add(comment);
                    update.set("comments",comments);
                    return template
                            .findAndModify(query,update,PostViewModel.class);
                });


    }
}
