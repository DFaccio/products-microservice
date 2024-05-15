package br.com.productmanagement.batch.controller;

import br.com.productmanagement.util.time.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ProductsInsertBatchController {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("ProductsJob")
    private Job productsJob;

    private static final int MINUTE_PLUS = 1;

    private static final String UPLOAD_DIR = "./uploads/products/";

    public ResponseEntity scheduleProductJob(MultipartFile file, LocalDateTime timeToSchedule) {

        try {

//            if (file.isEmpty()) {
//
//                return ResponseEntity.badRequest().body("Favor anexar um arquivo.");
//
//            }

            byte[] bytes = file.getBytes();

            Path path = getPath(file);

//            ARRUMAR -criar um dockerfile com a criação do diretório onde o arquivo deve ser gravado

            file.transferTo(path);
//            Files.write(path, bytes);

            if(timeToSchedule != null || timeToSchedule.isBefore(LocalDateTime.now())){

                timeToSchedule = TimeUtils.now().plusMinutes(MINUTE_PLUS);

            }

            Instant instantNow = TimeUtils.now().toInstant(ZoneOffset.UTC);

            Instant instant = timeToSchedule.toInstant(ZoneOffset.UTC);

            Duration duration = Duration.between(instantNow, instant);

            long scheduledTime = instant.toEpochMilli();

//            long currentTimeMillis = System.currentTimeMillis();

//            Instant instant = Instant.ofEpochMilli(currentTimeMillis);

//            Instant tenMinutesLater = instant.plus(Duration.ofMinutes(timeToSchedule));
//
//            String scheduledTime = cron(timeToSchedule);

//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("fileName", path.toString())
//                    .addString("startAt", scheduledTime).toJobParameters();
//                    .addLong("startAt", scheduledTime).toJobParameters();

//            jobLauncher.run(job, jobParameters);

//            long scheduledTimeMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(timeToSchedule);
//            long scheduledTimeMillis = System. TimeUnit.valueOf(String.valueOf(timeToSchedule));

            TriggerContext context;
//            PeriodicTrigger trigger = new PeriodicTrigger(scheduledTime, TimeUnit.MINUTES);
            PeriodicTrigger trigger = new PeriodicTrigger(duration);
            trigger.setInitialDelay(duration);
//            trigger.nextExecution(scheduledTime);

            taskScheduler.schedule(() -> runJob(path), Instant.ofEpochSecond(scheduledTime));

            return ResponseEntity.ok("Arquivo enviado com sucesso. Job agendado para execução em " + scheduledTime);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar o arquivo.");
        }

    }

    private String cron(LocalDateTime timeToSchedule){

        String seconds = String.valueOf(timeToSchedule.getSecond());
        String minutes = String.valueOf(timeToSchedule.getSecond());
        String hours = String.valueOf(timeToSchedule.getSecond());
        String day = String.valueOf(timeToSchedule.getSecond());
        String month = String.valueOf(timeToSchedule.getSecond());
        String year = String.valueOf(timeToSchedule.getSecond());

        String cron = seconds + " " + minutes + " " + hours + " " + day + " " + month + " " + year;

        return cron;
    }

    private Path getPath(MultipartFile file){

        String originalFilename = file.getOriginalFilename();

        String fileNameToSave = "products" + getFileExtension(originalFilename);

        Path path = Paths.get(UPLOAD_DIR + fileNameToSave);

        return path;

    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return filename.substring(lastIndex);
    }

    private void runJob(Path path) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", path.toString())
                    .toJobParameters();

            jobLauncher.run(productsJob, jobParameters);
        } catch (JobExecutionException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao executar o job.");
        }
    }

}
