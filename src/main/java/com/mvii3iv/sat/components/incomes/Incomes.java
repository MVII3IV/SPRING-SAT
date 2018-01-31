package com.mvii3iv.sat.components.incomes;


import org.springframework.data.annotation.Id;

public class Incomes {

    @Id
    private String fiscalId;
    private String emisorRFC;
    private String emisorName;
    private String receiverRFC;
    private String receiverName;
    private String emitedDate;
    private String certificationDate;
    private String certifiedPAC;
    private String total;
    private String voucherEffect;
    private String voucherStatus;

    @Override
    public String toString(){
        return String.format("Incomes[fiscalId=%s, emisorRFC='%s', emisorName='%s', receiverRFC='%s', emitedDate='%s', certificationDate='%s', certifiedPAC='%s', total='%s', voucherEffect='%s', voucherStatus='%s' ]",
                fiscalId, emisorRFC, emisorName, receiverRFC, receiverName, emitedDate, certificationDate, certifiedPAC, total, voucherEffect, voucherStatus);
    }

    public Incomes(){

    }

    public Incomes(String fiscalId, String emisorRFC, String emisorName, String receiverRFC, String receiverName, String emitedDate, String certificationDate, String certifiedPAC, String total, String voucherEffect, String voucherStatus) {
        this.fiscalId = fiscalId;
        this.emisorRFC = emisorRFC;
        this.emisorName = emisorName;
        this.receiverRFC = receiverRFC;
        this.receiverName = receiverName;
        this.emitedDate = emitedDate;
        this.certificationDate = certificationDate;
        this.certifiedPAC = certifiedPAC;
        this.total = total;
        this.voucherEffect = voucherEffect;
        this.voucherStatus = voucherStatus;
    }

    public String getFiscalId() {
        return fiscalId;
    }

    public void setFiscalId(String fiscalId) {
        this.fiscalId = fiscalId;
    }

    public String getEmisorRFC() {
        return emisorRFC;
    }

    public void setEmisorRFC(String emisorRFC) {
        this.emisorRFC = emisorRFC;
    }

    public String getEmisorName() {
        return emisorName;
    }

    public void setEmisorName(String emisorName) {
        this.emisorName = emisorName;
    }

    public String getReceiverRFC() {
        return receiverRFC;
    }

    public void setReceiverRFC(String receiverRFC) {
        this.receiverRFC = receiverRFC;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getEmitedDate() {
        return emitedDate;
    }

    public void setEmitedDate(String emitedDate) {
        this.emitedDate = emitedDate;
    }

    public String getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(String certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getCertifiedPAC() {
        return certifiedPAC;
    }

    public void setCertifiedPAC(String certifiedPAC) {
        this.certifiedPAC = certifiedPAC;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getVoucherEffect() {
        return voucherEffect;
    }

    public void setVoucherEffect(String voucherEffect) {
        this.voucherEffect = voucherEffect;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }
}
