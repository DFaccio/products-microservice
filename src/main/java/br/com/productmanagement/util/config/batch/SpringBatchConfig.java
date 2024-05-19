package br.com.productmanagement.util.config.batch;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.frameworks.db.ProductRepository;
import br.com.productmanagement.usercase.ProductBatchProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableScheduling
public class SpringBatchConfig {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public Job job(JobRepository jobRepository, Step insertProduct) {
        return new JobBuilder("productsJob", jobRepository)
                .start(insertProduct)
                .build();
    }

    @Bean
    public Step insertProduct(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                              ItemProcessor<Product, Product> processor, ItemWriter<Product> writer) {
        return new StepBuilder("insertProduct", jobRepository)
                .<Product, Product>chunk(10, platformTransactionManager)
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Product> reader(@Value("#{jobParameters['fileName']}") String fileName) {
        FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(fileName));
        itemReader.setName("productsReader");
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Product> lineMapper() {
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name", "description", "productCategory", "model", "available", "availableQuantity", "color", "size", "imageURL", "supplier", "brand", "price", "productHeight", "productWidth", "productDepth","productWeight", "packagingHeight", "packagingWidth", "packagingDepth", "packagingWeight");

        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public ProductBatchProcessor processor() {
        return new ProductBatchProcessor();
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");
        return writer;
    }

}
