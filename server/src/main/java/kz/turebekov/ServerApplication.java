package kz.turebekov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class ServerApplication {
public static String filePath = null;
    public static void main(String[] args) throws IOException{
        SpringApplication.run(ServerApplication.class, args);

        if (args.length == 0){
            log.error("File path should be defined");
            throw new IOException("File not found");
        }

        filePath = args[0];
    }

}
