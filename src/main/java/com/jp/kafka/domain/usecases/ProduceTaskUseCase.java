package com.jp.kafka.domain.usecases;

import com.jp.kafka.infrastucture.ports.in.ProduceTaskPort;
import com.jp.kafka.infrastucture.ports.out.PublishTaskPort;
import java.time.Instant;
import java.util.Random;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProduceTaskUseCase implements ProduceTaskPort {

  private final PublishTaskPort publishTaskPort;

  public ProduceTaskUseCase(PublishTaskPort publishTaskPort) {
    this.publishTaskPort = publishTaskPort;
  }

  @Override
  public String produceTask() {
    final String task = "Task: " + Instant.now().getEpochSecond();
    publishTaskPort.publishTask(task);
    return task;
  }
}
