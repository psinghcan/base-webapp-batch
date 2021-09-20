package com.psinghcan.basewebappbatch.batch.receipt.step2;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class ReceiptItemProcessor implements ItemProcessor<Deal, Deal> {
    @Override
    public Deal process(Deal deal) throws Exception {
        LocalDate today = LocalDate.now();
        System.out.println(today + ": processing deal" + deal.getNumber());
        return deal;
    }
}
