package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.FeiranteGetDto;
import br.edu.unochapeco.feirinha.entity.Feirante;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeiranteGetMapper {

    FeiranteGetMapper INSTANCE = Mappers.getMapper(FeiranteGetMapper.class);

    FeiranteGetDto toFeiranteGetDto(Feirante feirante);

    List<FeiranteGetDto> toListFeiranteGetDto(List<Feirante> feirantes);

    Feirante toFeirante(FeiranteGetDto personGetDto);

}
