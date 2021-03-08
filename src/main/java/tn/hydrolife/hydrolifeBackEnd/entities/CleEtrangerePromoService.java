package tn.hydrolife.hydrolifeBackEnd.entities;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class CleEtrangerePromoService implements Serializable {
    private Service service;
    private Promotion promotion;
}
