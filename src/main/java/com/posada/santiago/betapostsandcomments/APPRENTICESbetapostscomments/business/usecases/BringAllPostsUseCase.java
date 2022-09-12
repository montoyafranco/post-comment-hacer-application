package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;



import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BringAllPostsUseCase {
    private final DomainViewRepository domainViewRepository;

    public BringAllPostsUseCase(DomainViewRepository domainViewRepository) {
        this.domainViewRepository = domainViewRepository;
    }
    public Flux<PostViewModel> getPosts(){
        return this.domainViewRepository.findAllPosts();
    }
    //Finish the implementation of this class using the functional interfaces

}
