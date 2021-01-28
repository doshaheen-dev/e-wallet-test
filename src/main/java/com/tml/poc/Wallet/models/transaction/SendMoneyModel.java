package com.tml.poc.Wallet.models.transaction;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sendMoney")
public class SendMoneyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "send_money_id",  nullable = false, updatable = false)
    private long id;

    @Column(name = "sender_user_id")
    private long senderuserID;

    @Column(name = "receiver_user_id")
    private long receiveruserID;

    @Column(name = "send_money_type")
    @Size( max = 50)
    private String transactionType;

    @Column(name = "send_money_amount")
    private float transactionAmount;

    @Column(name = "send_money_status")
    private int status;

    @Column(name = "send_money_MPIN")
    @Size( max = 250)
    private String mpin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderuserID() {
        return senderuserID;
    }

    public void setSenderuserID(long senderuserID) {
        this.senderuserID = senderuserID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }
}
