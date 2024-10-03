package ozzjpa.multidb.services;

import ozzjpa.multidb.domain.creditcard.CreditCard;

/**
 * Created by jt on 7/1/22.
 */
public interface CreditCardService {

    CreditCard getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard cc);

}
