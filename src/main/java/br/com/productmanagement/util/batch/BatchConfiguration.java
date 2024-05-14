package br.com.productmanagement.util.batch;

import br.com.productmanagement.batch.DiscountProcessor;
import br.com.productmanagement.entities.Discount;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
//
//    @Bean
//    public Job discountProcessor(JobRepository jobRepository, Step step){
//        return new JobBuilder("processarDesconto", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//
//    @Bean
//    public Step step(JobRepository jobRepository,
//                     PlatformTransactionManager platformTransactionManager,
//                     ItemReader<Discount> itemReader,
//                     ItemProcessor<Discount, Discount> itemProcessor,
//                     ItemWriter<Discount> itemWriter){
//        return new StepBuilder("step", jobRepository)
//                .<Discount, Discount>chunk(20, platformTransactionManager)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .writer(itemWriter)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Discount> itemReader(){
//        BeanWrapperFieldSetMapper<Discount> discountSetMapper = new BeanWrapperFieldSetMapper<>();
//        discountSetMapper.setTargetType(Discount.class);
//        return new FlatFileItemReaderBuilder<Discount>()
//                .name("discountItemReader")
//                .resource(new ClassPathResource("discount.csv"))
//                .delimited()
//                .names("coupon", "discountType", "description", "discountValue", "creationDate", "discountStartDate", "discountFinishDate", "minimumQuantityToDiscount", "productCategory", "active")
//                .fieldSetMapper(discountSetMapper)
//                .build();
//    }
//
//    @Bean
//    public ItemWriter<Discount> itemWriter(DataSource dataSource){
//        return new JdbcBatchItemWriterBuilder<Discount>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .dataSource(dataSource)
//                .sql("")
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Discount, Discount> itenProcessor(){
//        return new DiscountProcessor();
//    }


}
