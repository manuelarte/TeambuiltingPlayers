package org.manuel.teambuilting.players.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Configuration
public class RabbitMQConfig  {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        final ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        jsonObjectMapper.findAndRegisterModules();
        return jsonObjectMapper;
    }

    @Bean(name = "eventMessageConverter")
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

}