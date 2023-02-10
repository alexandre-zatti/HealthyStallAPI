package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.PersonPostDto;
import br.edu.unochapeco.feirinha.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonPostMapper {

    PersonPostMapper INSTANCE = Mappers.getMapper(PersonPostMapper.class);

    PersonPostDto toPersonPostDto(Person person);

    Person toPerson(PersonPostDto personPostDto);

}
