package com.psinghcan.basewebappbatch.service;

import com.psinghcan.basewebappbatch.model.primary.Deal;
import com.psinghcan.basewebappbatch.model.secondary.Payment;
import com.psinghcan.basewebappbatch.model.secondary.Receipt;
import com.psinghcan.basewebappbatch.repository.primary.DealRepository;
import com.psinghcan.basewebappbatch.repository.secondary.PaymentRepository;
import com.psinghcan.basewebappbatch.repository.secondary.ReceiptRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class LoadDataService {

    public LoadDataService(DealRepository dealRepository, PaymentRepository paymentRepository, ReceiptRepository receiptRepository) {
        this.dealRepository = dealRepository;
        this.paymentRepository = paymentRepository;
        this.receiptRepository = receiptRepository;
    }

    public void init(){
        for (int i=0; i<500; i++){
            loadPayment(i);
            loadReceipt(i);
            loadDeal(i);
        }
    }

    private void loadDeal(int i){
        Deal deal = new Deal();
        deal.setNumber("D_" + i);
        deal.setAmount1(100.00);
        deal.setAmount2(150.00);
        deal.setAmount3(200.00);
        deal.setStatus(10);
        deal.setProcessStatus(0);
        deal.setPaymentNumber("P_" +i);
        deal.setReceiptNumber("R_" + i);
        dealRepository.save(deal);
    }

    private void loadPayment(int i){
        Payment payment = new Payment();
        payment.setNumber("P_" + i);
        payment.setAmount1(100);
        payment.setTax(20);
        payment.setTotal(120);
        payment.setStatus(10);
        payment.setProcessStatus(0);
        paymentRepository.save(payment);
    }

    private void loadReceipt(int i){
        Receipt receipt = new Receipt();
        receipt.setNumber("R_" + i);
        receipt.setStatus(10);
        receipt.setProcessStatus(0);
        receipt.setAmount1(100);
        receipt.setTax(20);
        receipt.setTotal(120);
        receiptRepository.save(receipt);
    }

    private final DealRepository dealRepository;
    private final PaymentRepository paymentRepository;
    private final ReceiptRepository receiptRepository;
}
