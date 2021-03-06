package com.psinghcan.basewebappbatch.batch.receipt.step2;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;

public class ReceiptDealItemReader implements ItemReader<Deal>, StepExecutionListener {

    public ReceiptDealItemReader(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }


    @Override
    public Deal read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (receiptDealIterator != null && receiptDealIterator.hasNext()){
            return receiptDealIterator.next();
        } else {
            return null;
        }
    }

    private Iterator<Deal> receiptDealIterator;

    private final DealRepository dealRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("query the primary database and retrieve all the marked deals");
        receiptDealIterator = dealRepository.findAllByStatus(21).iterator();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("done reading all the records");
        return null;
    }
}
