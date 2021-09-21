package com.psinghcan.basewebappbatch.batch.receipt.step2;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ReceiptItemWriter implements ItemWriter<Deal> {
    public ReceiptItemWriter(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public void write(List<? extends Deal> list) throws Exception {
        for (Deal deal : list){
            System.out.println("writing--> " + deal.getNumber());
        }
    }

    private final ExecutionContext executionContext;
}
