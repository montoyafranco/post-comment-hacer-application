package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases;




import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.repository.MongoViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.DomainViewRepository;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.gateways.model.PostViewModel;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.domain.Post;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BringPostById {
    // finish the implementation of this class using the functional interfaces
    private final DomainViewRepository domainViewRepository;

    public BringPostById(DomainViewRepository mongoViewRepository) {
        this.domainViewRepository = mongoViewRepository;
    }
    public Mono<PostViewModel> getPostById(String postId){
        return  this.domainViewRepository.findByAggregateId(postId);
    }

}
