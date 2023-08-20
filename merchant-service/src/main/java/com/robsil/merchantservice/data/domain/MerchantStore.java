package com.robsil.merchantservice.data.domain;

import com.robsil.merchantservice.model.merchant.store.MerchantStoreContact;
import com.robsil.userservice.data.domain.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant_stores")
public class MerchantStore extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    @Column(unique = true)
    private String name;

    @Builder.Default
    @Type(JsonBinaryType.class)
    @Column
    private List<MerchantStoreContact> contacts = new ArrayList<>();

    @Column
    private boolean isNotBlocked;
}
