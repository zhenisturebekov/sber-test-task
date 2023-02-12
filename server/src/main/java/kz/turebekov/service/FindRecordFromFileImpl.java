package kz.turebekov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
@Slf4j
public class FindRecordFromFileImpl implements FindRecordFromFile{

    @Value("classpath:airport.dat")
    private Resource resource;

    @Override
    public String findRecordById(String id) {
        try (Stream<String> lines = Files.lines(Path.of(resource.getURI()), StandardCharsets.UTF_8)) {
            return lines.filter(str -> str.startsWith(id)).findFirst().orElse("");
        } catch (IOException ex) {
            log.error("IOException occurred cause {} trace:\n {}", ex.getCause(), ex.toString());
        }

        return "";
    }
}
