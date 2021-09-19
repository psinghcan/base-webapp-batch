package com.psinghcan.basewebappbatch.model.primary;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Deal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String office;
    private String number;
    private double amount1;
    private double amount2;
    private double amount3;
    private int status;
    private int processStatus;
    @Column(name = "payment_number")
    private String paymentNumber;
    @Column(name = "receipt_number")
    private String receiptNumber;
}
