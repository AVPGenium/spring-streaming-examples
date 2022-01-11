package com.example.springstreamingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.log.LogMessage;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Locale;
import java.util.function.Consumer;

@SpringBootApplication
@EnableBinding(Processor.class)
public class SpringStreamingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStreamingDemoApplication.class, args);
	}

	// deprecated: https://stackoverflow.com/questions/69216229/spring-cloud-streamlistener-condition-deprecated-what-is-the-alternative
	// https://docs.spring.io/spring-cloud-stream/docs/Brooklyn.SR1/reference/htmlsingle/#_customizing_channel_names
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public LogMessage enrichLogMessage(LogMessage log) {
		return LogMessage.format("[1]: %s", log.toString());
	}

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Object transform(String message) {
		return message.toUpperCase(Locale.ROOT);
	}
}
