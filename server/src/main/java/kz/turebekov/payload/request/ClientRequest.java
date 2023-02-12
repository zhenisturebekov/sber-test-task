package kz.turebekov.payload.request;

import lombok.Data;

import java.time.Instant;

@Data
public class ClientRequest {
    private String id;
    private String threadName;
    private String idRecord;
    private Instant timeStamp;
}
