package com.tlacaelelsoftware.tochtlimail.common;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class MailMessage {

    @NotBlank
    @Email
    private String fromAddress;

    @NotBlank
    private String toRecipients;

    private String ccRecipients;

    private String bccRecipients;

    @NotBlank
    private String subject;

    @NotBlank
    private String contentType = "text/plain; charset=utf-8";

    @NotBlank
    private String text;

    @JsonCreator
    public MailMessage(@JsonProperty("fromAddress") String fromAddress,
                       @JsonProperty("toRecipients") String toRecipients,
                       @JsonProperty("subject") String subject,
                       @JsonProperty("text") String text) {
        this.fromAddress = fromAddress;
        this.toRecipients = toRecipients;
        this.subject = subject;
        this.text = text;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToRecipients() {
        return toRecipients;
    }

    public void setToRecipients(String toRecipients) {
        this.toRecipients = toRecipients;
    }

    public String getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(String ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public String getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(String bccRecipients) {
        this.bccRecipients = bccRecipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
