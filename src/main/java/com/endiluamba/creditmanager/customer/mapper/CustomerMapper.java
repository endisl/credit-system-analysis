package com.endiluamba.creditmanager.customer.mapper;

import com.endiluamba.creditmanager.customer.dto.CustomerDTO;
import com.endiluamba.creditmanager.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toModel(CustomerDTO customerDTO);

    CustomerDTO toDTO(Customer customer);
}
