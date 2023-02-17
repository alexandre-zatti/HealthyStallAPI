package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.TransactionGetDto;
import br.edu.unochapeco.feirinha.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionGetMapper {

    public abstract List<TransactionGetDto> toListTransactionGetDto(List<Transaction> transactionList);

    public Page<TransactionGetDto> toPageTransactionGetDto(Page<Transaction> transactionPage){
        return transactionPage.map(this::toTransactionGetDto);
    }

    public abstract TransactionGetDto toTransactionGetDto(Transaction transaction);

    public abstract Transaction toTransaction(TransactionGetDto transactionGetDto);

}
