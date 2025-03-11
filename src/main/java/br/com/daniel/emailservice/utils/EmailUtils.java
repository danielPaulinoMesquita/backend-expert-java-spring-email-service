package br.com.daniel.emailservice.utils;

import br.com.userservice.commonslib.model.dtos.OrderCreatedMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.experimental.UtilityClass;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@UtilityClass
public class EmailUtils {

    public static void getMimeMessage(
            MimeMessage message, String process, OrderCreatedMessage orderDTO, String subject
    ) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(orderDTO.getCustomer().email());
        helper.setFrom(new InternetAddress("daniel-paulino@hotmail.com"));
        helper.setSubject(subject);
        helper.setText(process, true);
    }

    public static Context getContextToCreatedOrder(OrderCreatedMessage orderDTO) {
        Context context = new Context();

        context.setVariable("customerName", orderDTO.getCustomer().name());
        context.setVariable("orderId", orderDTO.getOrderResponse().id());
        context.setVariable("title", orderDTO.getOrderResponse().title());
        context.setVariable("description", orderDTO.getOrderResponse().description());
        context.setVariable("creationDate", orderDTO.getOrderResponse().createdAt());
        context.setVariable("status", orderDTO.getOrderResponse().status());
        context.setVariable("responsibleTechnician", orderDTO.getRequester().name());

        return context;
    }
}