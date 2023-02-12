package kz.turebekov.service;

import kz.turebekov.payload.request.ClientRequest;
import kz.turebekov.payload.response.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ServerRequestService {
    private static final String SERVER_URI = "http://localhost:8080/api/1.0/find";
    private static final int THREAD_POOL = Runtime.getRuntime().availableProcessors();
    private final ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL);

    @Autowired
    private RestTemplate restTemplate;

//    @Value("classpath:airport.dat")
//    private Resource resource;

    public void execute(String path) {
        try (Stream<String> lines = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {

            lines.map(stringLine -> stringLine.split(",", 1)[0]).forEach(id -> service.submit(() -> {

                ClientRequest request = createClientRequest(id);

                ServerResponse response = getServerResponse(request);

                comparingRequestResponseThenLogging(request, response);

            }));
        } catch (IOException ex) {
            log.error("Exception occurred cause: {}", ex.toString());
        } finally {
            service.shutdown();
        }
    }

    private void comparingRequestResponseThenLogging(ClientRequest request, ServerResponse response) {
        log.info("Response info -> {}", response);

        var isIdsSimilar = request.getId().equals(response.getRequestId());

        log.info("Checking equality of Ids. Requested id {} and Response id {} are equal {}",
                request.getId(), response.getRequestId(), isIdsSimilar);
    }

    private ClientRequest createClientRequest(String id) {
        ClientRequest request = new ClientRequest();
        request.setId(UUID.randomUUID().toString());
        request.setIdRecord(id);
        request.setThreadName(Thread.currentThread().getName());
        request.setTimeStamp(Instant.now());

        return request;
    }

    private ServerResponse getServerResponse(ClientRequest request) {
        return restTemplate.postForObject(SERVER_URI, request, ServerResponse.class);
    }


}
