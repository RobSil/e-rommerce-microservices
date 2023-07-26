package com.robsil.erommerce.model;

import jakarta.mail.Message;
import lombok.Getter;

@Getter
public enum RecipientType {
    TO(Message.RecipientType.TO),
    CC(Message.RecipientType.CC),
    BCC(Message.RecipientType.BCC);

    private final Message.RecipientType jakartaType;

    RecipientType(Message.RecipientType jakartaType) {
        this.jakartaType = jakartaType;
    }
}
