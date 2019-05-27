package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.Transaction;
import com.agility.mapstruct.models.TransactionDTO;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Mapper
public abstract class TransactionMapper {

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setTotalInCents(transaction.getTotal()
            .multiply(new BigDecimal("100")).longValue());
        return transactionDTO;
    }

    public abstract List<TransactionDTO> toTransactionDTO(Collection<Transaction> transactions);
}
