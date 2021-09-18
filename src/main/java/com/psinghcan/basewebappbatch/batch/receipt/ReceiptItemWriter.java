package com.psinghcan.basewebappbatch.batch.receipt;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ReceiptItemWriter implements ItemWriter<Deal> {
    @Override
    public void write(List<? extends Deal> list) throws Exception {
        for (Deal deal : list){
            System.out.println("writing--> " + deal.getNumber());
        }
    }
}
