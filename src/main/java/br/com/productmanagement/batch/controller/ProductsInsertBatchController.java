package br.com.productmanagement.batch.controller;

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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class ProductsInsertBatchController {

//    @Autowired
//    private TaskScheduler taskScheduler;

    @Autowired
    private JobLauncher jobLauncher;

    //    @Qualifier("productsJob")
    @Autowired
    private Job job;

    private static final int MINUTE_PLUS = 2;

//    private static final String UPLOAD_DIR = "./uploads/products/";

    private final Path root = Paths.get("products/uploads");

    public ResponseEntity scheduleProductJob(MultipartFile file, LocalDateTime timeToSchedule) {

        try {

            if (file.isEmpty()) {

                return ResponseEntity.badRequest().body("Favor anexar um arquivo.");

            }

//            final Job productsJob = (Job) ;

            if( timeToSchedule == null || timeToSchedule.isBefore(LocalDateTime.now())){

                timeToSchedule = TimeUtils.now().plusMinutes(MINUTE_PLUS);

            }

//            byte[] bytes = file.getBytes();

            Path path = getPath(file, timeToSchedule);

            Files.copy(file.getInputStream(), path);

//            ARRUMAR -criar um dockerfile com a criação do diretório onde o arquivo deve ser gravado

//            file.transferTo(path);
//            Files.write(path, bytes);
            ZonedDateTime saoPauloDateTime = LocalDateTime.now().atZone(TimeUtils.getZoneId());

            // Converter para Instant
            Instant instant = saoPauloDateTime.toInstant();

//            Instant instantNow = TimeUtils.now().toInstant(ZoneOffset.of("America/Sao_Paulo"));

//            Instant instant = timeToSchedule.toInstant(ZoneOffset.of("America/Sao_Paulo"));

//            Duration duration = Duration.between(instantNow, instant);

//            long scheduledTime = instant.toEpochMilli();

//            long currentTimeMillis = System.currentTimeMillis();

//            Instant instant = Instant.ofEpochMilli(currentTimeMillis);

//            Instant tenMinutesLater = instant.plus(Duration.ofMinutes(timeToSchedule));
//
            String scheduledTime = cron(timeToSchedule);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", path.toString())
                    .addString("startAt", scheduledTime).toJobParameters();
//                    .addLong("startAt", scheduledTime).toJobParameters();
//

            jobLauncher.run(job, jobParameters);


//            final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//            context.getEnvironment().addActiveProfile("spring");
//            context.register(ProductBatchConfiguration.class);
//
//            context.refresh();

            // Spring xml config
            // ApplicationContext context = new ClassPathXmlApplicationContext("spring-batch-intro.xml");

//            runJob(context, "productsJob", path, scheduledTime);

//            long scheduledTimeMillis = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(timeToSchedule);
//            long scheduledTimeMillis = System. TimeUnit.valueOf(String.valueOf(timeToSchedule));

//            TriggerContext context;
//            PeriodicTrigger trigger = new PeriodicTrigger(scheduledTime, TimeUnit.MINUTES);
//            PeriodicTrigger trigger = new PeriodicTrigger(duration);
//            trigger.setInitialDelay(duration);
//            trigger.nextExecution(scheduledTime);

//            taskScheduler.schedule(() -> runJob(path), Instant.ofEpochSecond(scheduledTime));

            return ResponseEntity.ok("Arquivo enviado com sucesso. Job agendado para execução em " + timeToSchedule);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar o arquivo.");
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            throw new RuntimeException(e);
        }

    }

    private static String cron(LocalDateTime timeToSchedule){

        String seconds = String.valueOf(timeToSchedule.getSecond());
        String minutes = String.valueOf(timeToSchedule.getMinute());
        String hours = String.valueOf(timeToSchedule.getHour());
        String day = String.valueOf(timeToSchedule.getDayOfMonth());
        String month = String.valueOf(timeToSchedule.getMonth());
        String year = String.valueOf(timeToSchedule.getYear());

        String cron = seconds + " " + minutes + " " + hours + " " + day + " " + month + " " + year;

        return cron;
    }

    private Path getPath(MultipartFile file, LocalDateTime timeToSchedule) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String schedule = timeToSchedule.toString().replaceAll("[-:.]", "_");

        String fileNameToSave = schedule + "_products" + getFileExtension(originalFilename);

//        fileNameToSave = fileNameToSave.replaceAll("[-:.]", "_");

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

//    private void runJob(Path path) {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("fileName", path.toString())
//                    .toJobParameters();
//
//            jobLauncher.run(productsJob, jobParameters);
//        } catch (JobExecutionException e) {
//            log.info("Falha ao executar o job.");
//            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao executar o job.");
//        }
//    }

//    private static void runJob(AnnotationConfigApplicationContext context, String batchJobName, Path path, long scheduledTime) {
////        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
////        final Job job = (Job) context.getBean(batchJobName);
//
//        log.info("Starting the batch job: {}", batchJobName);
//
//        try {
//            // To enable multiple execution of a job with the same parameters
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("fileName", path.toString())
//                    .addLong("startAt", scheduledTime).toJobParameters();
//
//            final JobExecution execution = jobLauncher.run(job, jobParameters);
//
//            log.info("Job Status : {}", execution.getStatus());
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//            log.error("Job failed {}", e.getMessage());
//            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao executar o job.");
//        }
//    }

}
