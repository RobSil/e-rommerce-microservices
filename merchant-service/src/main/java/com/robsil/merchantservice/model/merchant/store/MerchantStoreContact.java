package com.robsil.merchantservice.model.merchant.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantStoreContact {

    private String name;
    private String value;

}
