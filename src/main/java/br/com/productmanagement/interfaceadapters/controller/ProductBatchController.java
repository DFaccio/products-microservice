package br.com.productmanagement.interfaceadapters.controller;

import br.com.productmanagement.util.time.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;

@Component
@Slf4j
public class ProductBatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private static final int MINUTE_PLUS = 2;

    private final Path root = Paths.get("products/uploads");

    public ResponseEntity<?> scheduleProductJob(MultipartFile file, LocalDateTime timeToSchedule) {

        try {

            if (file.isEmpty()) {

                return ResponseEntity.badRequest().body("Favor anexar um arquivo.");

            }

            if( timeToSchedule == null || timeToSchedule.isBefore(LocalDateTime.now())){

                timeToSchedule = TimeUtils.now().plusMinutes(MINUTE_PLUS);

            }

            Path path = getPath(file, timeToSchedule);

            Files.copy(file.getInputStream(), path);

            ZonedDateTime saoPauloDateTime = LocalDateTime.now().atZone(TimeUtils.getZoneId());

            Instant instant = saoPauloDateTime.toInstant();

            long scheduledTime = instant.toEpochMilli();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", path.toString())
                    .addLong("startAt", scheduledTime).toJobParameters();

            jobLauncher.run(job, jobParameters);

            return ResponseEntity.ok("Arquivo enviado com sucesso. Job agendado para execução em " + timeToSchedule);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar o arquivo.");
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            throw new RuntimeException(e);
        }

    }

    private Path getPath(MultipartFile file, LocalDateTime timeToSchedule) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String schedule = timeToSchedule.toString().replaceAll("[-:.]", "_");

        String fileNameToSave = schedule + "_products" + getFileExtension(originalFilename);

        Files.createDirectories(root);

        Path path = this.root.resolve(fileNameToSave);

        return path;

    }

    private String getFileExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i == -1) {
            return "";
        }
        return filename.substring(i);
    }

}
