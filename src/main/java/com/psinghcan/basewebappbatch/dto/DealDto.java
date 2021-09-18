package com.psinghcan.basewebappbatch.dto;

import lombok.Data;
@Data
public class DealDto {
    private Long id;
    private String number;
    private double amount1;
    private double amount2;
    private double amount3;
    private int status;
    private int processStatus;
    private String paymentNumber;
    private String receiptNumber;
}
