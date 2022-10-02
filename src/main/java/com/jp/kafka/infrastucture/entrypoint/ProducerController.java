package com.jp.kafka.infrastucture.entrypoint;

import com.jp.kafka.infrastucture.ports.in.ProduceTaskPort;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
public class ProducerController {

  private ProduceTaskPort produceTaskPort;

  public ProducerController(ProduceTaskPort produceTaskPort) {
    this.produceTaskPort = produceTaskPort;
  }

  @PostMapping(path = "producer/task")
  public ResponseEntity<String> produceTask() {

    return ResponseEntity.status(HttpStatus.CREATED).body(produceTaskPort.produceTask());
  }

}
