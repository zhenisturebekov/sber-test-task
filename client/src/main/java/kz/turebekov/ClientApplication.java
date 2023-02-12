package kz.turebekov;

import kz.turebekov.service.ServerRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class ClientApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);

        if (args.length == 0){
            log.error("File path should be defined");
            throw new IOException("File not found");
        }

        ServerRequestService service = context.getBean(ServerRequestService.class);
        service.execute(args[0]);
    }
}
