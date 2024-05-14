package br.com.productmanagement.batch;

import br.com.productmanagement.entities.Discount;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class DiscountProcessor implements ItemProcessor<Discount, Discount> {


    @Override
    public Discount process(Discount item) throws Exception {

        item.setCreationDate(LocalDateTime.now());

        return item;

    }


}
