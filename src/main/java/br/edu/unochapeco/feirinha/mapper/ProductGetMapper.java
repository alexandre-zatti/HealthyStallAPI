package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.ProductGetDto;
import br.edu.unochapeco.feirinha.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductGetMapper {

    ProductGetMapper INSTANCE = Mappers.getMapper(ProductGetMapper.class);

    List<ProductGetDto> toListProductGetDto(List<Product> productList);

    ProductGetDto toProductGetDto(Product product);

    Product toProduct(ProductGetDto productGetDto);

}
