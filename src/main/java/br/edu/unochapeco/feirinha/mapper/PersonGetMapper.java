package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.PersonGetDto;
import br.edu.unochapeco.feirinha.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonGetMapper {

    PersonGetMapper INSTANCE = Mappers.getMapper(PersonGetMapper.class);

    List<PersonGetDto> toListPersonGetDto(List<Person> personList);

    PersonGetDto toPersonGetDto(Person person);

    Person toPerson(PersonGetDto personGetDto);

}
