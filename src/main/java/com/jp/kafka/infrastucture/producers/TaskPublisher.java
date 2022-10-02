package com.jp.kafka.infrastucture.producers;

import com.jp.kafka.infrastucture.config.topics.TopicNames;
import com.jp.kafka.infrastucture.ports.out.PublishTaskPort;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Getter
public class TaskPublisher implements PublishTaskPort {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  private final KafkaTemplate<String, String> kafkaTemplate;

  public TaskPublisher(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void publishTask(String task) {

    ListenableFuture<SendResult<String, String>> future = getKafkaTemplate().send(
        TopicNames.TOPIC_TASK,
        task);

    logger.info("Task created!!");

    future.addCallback(getCallback(task));
  }

  private ListenableFutureCallback<SendResult<String, String>> getCallback(String task) {

    return new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("Sent message=[" + task + "] "
            + "with offset=[" + result.getRecordMetadata().offset() + "]");
      }

      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=[" + task + "] due to : " + ex.getMessage());
      }
    };

  }
}
