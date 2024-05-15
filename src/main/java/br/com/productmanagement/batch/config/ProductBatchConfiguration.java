package br.com.productmanagement.batch.config;

import br.com.productmanagement.batch.ProductProcessor;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.frameworks.db.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@Profile("spring-boot")
public class ProductBatchConfiguration {

    @Value("#{jobParameters['fileName']}")
    private Resource productsCsv;

    private PlatformTransactionManager platformTransactionManager;

    private ProductRepository productRepository;

//    private String fileName;
//
//    @Value("#{jobParameters['startAt']}")
//    private static final String START = getCron(null);
//
//    private String getFileName(@Value("#{jobParameters['fileName']}") String fileName){
//
//        return fileName;
//
//    }
//
//    @Value("#{jobParameters['startAt']}")
//    private static String getCron(@Value("#{jobParameters['startAt']}") String startAt){
//
//        return startAt;
//
//    }

    @Bean(name = "ProductsJob")
    public Job runJob(Step insertProduct, JobRepository jobRepository) {
        return new JobBuilder("importProducts", jobRepository)
                .start(insertProduct)
                .build();

    }

    @Bean
    public Step insertProduct(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                              ItemProcessor<Product, Product> processor, ItemWriter<Product> writer) {
        return new StepBuilder("insertProduct", jobRepository)
                .<Product, Product>chunk(10, platformTransactionManager)
                .reader(reader(productsCsv))
                .processor(processor())
                .writer(writer())
                .build();
    }

//    @Bean
//    @StepScope
    public FlatFileItemReader<Product> reader(Resource prouctsFile) {
        FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(prouctsFile);
        itemReader.setName("productsReader");
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Product> lineMapper() {
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name", "description", "productCategory", "model", "availableQuantity", "color", "size", "imageURL", "supplier", "brand", "price", "productHeight", "productWidth", "productDepth","productWeight", "packagingHeight", "packagingWidth", "packagingDepth", "packagingWeight", "discount.id");

        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");
        return writer;
    }

//    @Bean
//    public Job productProcessor(JobRepository jobRepository, Step step){
//        return new JobBuilder("processarProdutos", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//
//    @Bean
//    public Step step(JobRepository jobRepository,
//                     PlatformTransactionManager platformTransactionManager,
//                     ItemReader<Product> itemReader,
//                     ItemProcessor<Product, Product> itemProcessor,
//                     ItemWriter<Product> itemWriter){
//        return new StepBuilder("step", jobRepository)
//                .<Product, Product>chunk(20, platformTransactionManager)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .writer(itemWriter)
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Product> itemReader(){
//        BeanWrapperFieldSetMapper<Product> productSetMapper = new BeanWrapperFieldSetMapper<>();
//        productSetMapper.setTargetType(Product.class);
//        return new FlatFileItemReaderBuilder<Product>()
//                .name("productItemReader")
//                .resource(new ClassPathResource("product.csv"))
//                .delimited()
//                .names("name", "description", "productCategory", "model", "availableQuantity", "color", "size", "imageURL", "supplier", "brand", "price", "productHeight", "productWidth", "productDepth","productWeight", "packagingHeight", "packagingWidth", "packagingDepth", "packagingWeight", "discount")
//                .fieldSetMapper(productSetMapper)
//                .build();
//    }
//    @Bean
//    public FlatFileItemReader<Product> productReader(){
//        FlatFileItemReader<Product> productReader = new FlatFileItemReader<>();
//        productReader.setResource(new FileSystemResource("./products/uploads/"));
//        productSetMapper.setTargetType(Product.class);
//        return new FlatFileItemReaderBuilder<Product>()
//                .name("productItemReader")
//                .resource(new ClassPathResource("product.csv"))
//                .delimited()
//                .names("name", "description", "productCategory", "model", "availableQuantity", "color", "size", "imageURL", "supplier", "brand", "price", "productHeight", "productWidth", "productDepth","productWeight", "packagingHeight", "packagingWidth", "packagingDepth", "packagingWeight", "discount")
//                .fieldSetMapper(productSetMapper)
//                .build();
//    }
//
//    @Bean
//    public ItemWriter<Product> itemWriter(DataSource dataSource){
//        return new JdbcBatchItemWriterBuilder<Product>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .dataSource(dataSource)
//                .sql("")
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Product, Product> itenProcessor(){
//        return new ProductProcessor();
//    }

}
