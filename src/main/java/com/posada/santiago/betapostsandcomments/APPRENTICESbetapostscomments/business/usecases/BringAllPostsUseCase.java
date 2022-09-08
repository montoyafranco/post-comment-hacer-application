package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;



import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BringAllPostsUseCase {
    private final MongoViewRepository mongoViewRepository;

    public BringAllPostsUseCase(MongoViewRepository mongoViewRepository) {
        this.mongoViewRepository = mongoViewRepository;
    }
    public Flux<PostViewModel> getPosts(){
        return this.mongoViewRepository.findAllPosts();
    }
    //Finish the implementation of this class using the functional interfaces

}
