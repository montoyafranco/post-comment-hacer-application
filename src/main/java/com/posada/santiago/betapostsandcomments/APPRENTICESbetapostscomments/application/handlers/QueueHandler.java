package com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.handlers;



import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.application.adapters.bus.Notification;
import com.posada.santiago.betapostsandcomments.APPRENTICESbetapostscomments.business.usecases.UpdateViewUseCase;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class QueueHandler implements Consumer<String> {
    private final Gson gson = new Gson();
    private final UpdateViewUseCase useCase;

    public QueueHandler(UpdateViewUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void accept(String received) {
        //recibo notificacion
        Notification notification = gson.fromJson(received,Notification.class);
        //obtengo el type con la notificacion que es received transformado por gson y la remplazo abajo
        String type = notification.getType()
                .replace("alphapostsandcomments","betapostsandcomments.APPRENTICESbetapostscomments");
        //aca debo mandar este evento al usecase.accept(evento)

        try {
            DomainEvent event = (DomainEvent) gson.fromJson(notification.getBody(),Class.forName(type));
            //string del evento que paso en alpha
            // class.for name me lo encuentra en una clase de este evento por el path. devuelve el objeto class
            useCase.accept(event);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
