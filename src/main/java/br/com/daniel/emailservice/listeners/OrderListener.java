package br.com.daniel.emailservice.listeners;

import br.com.daniel.emailservice.services.EmailService;
import br.com.userservice.commonslib.model.dtos.OrderCreatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderListener {

    private final EmailService emailService;

    // When you define a topic for a RabbitMq exchange, you want to specify the type in @RabbitListener annotation,
    // because the default value (Without declaring the type) is direct.
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "newhelpdesk"),
                    exchange = @Exchange(value = "newhelpdesk", type = "topic"),
                    key = "rk.orders.create"
            )
    )
    public void listener(final OrderCreatedMessage orderCreatedMessage) {
        log.info("Ordem de serviço recebida: {}", orderCreatedMessage);
        emailService.sendMail(orderCreatedMessage);    }
}
