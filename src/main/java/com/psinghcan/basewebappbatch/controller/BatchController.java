package com.psinghcan.basewebappbatch.controller;

import com.psinghcan.basewebappbatch.batch.receipt.ReceiptJobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    private final ReceiptJobService receiptJobService;

    public BatchController(ReceiptJobService receiptJobService) {
        this.receiptJobService = receiptJobService;
    }

    @GetMapping("/start-receipt-job")
    public String startReceiptBatchJob(){
        receiptJobService.startReceiptBatchJob();
        return "receipt-job has been started";
    }
}
