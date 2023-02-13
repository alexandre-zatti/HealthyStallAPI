package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.ProductPostDto;
import br.edu.unochapeco.feirinha.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductPostMapper {

    ProductPostMapper INSTANCE = Mappers.getMapper(ProductPostMapper.class);

    ProductPostDto toProductPostDto(Product product);

    Product toProduct(ProductPostDto productPostDto);

}
