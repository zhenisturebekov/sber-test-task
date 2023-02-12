package kz.turebekov.payload.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ServerResponse {
    private String id;
    private String requestId;
    private Instant timestamp;
    private Instant requestTimestamp;
    private String result;

}
