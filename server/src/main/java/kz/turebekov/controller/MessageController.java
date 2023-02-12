package kz.turebekov.controller;

import kz.turebekov.payload.request.ClientRequest;
import kz.turebekov.payload.response.ServerResponse;
import kz.turebekov.service.FindRecordFromFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/1.0/")
public class MessageController {
    @Autowired
    private FindRecordFromFileImpl findRecordFromFile;

    @PostMapping("find")
    public ResponseEntity<ServerResponse> getMessage(@RequestBody ClientRequest data) {
        log.info("Request from client {}", data);

        String searchingRecordResult = findRecordFromFile.findRecordById(data.getIdRecord());

        ServerResponse result = new ServerResponse();
        result.setId(UUID.randomUUID().toString());
        result.setRequestId(data.getId());
        result.setTimestamp(Instant.now());
        result.setRequestTimestamp(data.getTimeStamp());
        result.setResult(searchingRecordResult);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
