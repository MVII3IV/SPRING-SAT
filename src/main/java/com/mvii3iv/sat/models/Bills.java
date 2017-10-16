package com.mvii3iv.sat.models;


public class Bills {

    private String fiscalId;
    private String emisorRFC;
    private String emisorName;
    private String receiverRFC;
    private String receiverName;
    private String emitedDate;
    private String certificationDate;
    private String certifiedPAC;
    private Long total;
    private String voucherEffect;
    private String voucherStatus;

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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
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
