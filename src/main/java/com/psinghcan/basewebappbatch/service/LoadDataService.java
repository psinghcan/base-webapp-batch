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
        for (int j=0; j< 10; j++){
            for (int i=0; i<25; i++){
                loadPayment(j,i);
                loadReceipt(j,i);
                loadDeal(j,i);
            }
        }

    }

    private void loadDeal(int j, int i){
        Deal deal = new Deal();
        deal.setOffice("O_" + j);
        deal.setNumber("O_" + j + "_D_" + i);
        deal.setAmount1(100.00);
        deal.setAmount2(150.00);
        deal.setAmount3(200.00);
        deal.setStatus(10);
        deal.setProcessStatus(0);
        deal.setPaymentNumber("O_" + j + "_P_" +i);
        deal.setReceiptNumber("O_" + j + "_R_" + i);
        dealRepository.save(deal);
    }

    private void loadPayment(int j, int i){
        Payment payment = new Payment();
        payment.setNumber("O_" + j + "_P_" +i);
        payment.setAmount1(100);
        payment.setTax(20);
        payment.setTotal(120);
        payment.setStatus(10);
        payment.setProcessStatus(0);
        paymentRepository.save(payment);
    }

    private void loadReceipt(int j, int i){
        Receipt receipt = new Receipt();
        receipt.setNumber("O_" + j + "_R_" + i);
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
