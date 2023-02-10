package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.PersonRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;

    @BeforeEach
    public void setup(){
       this.person = Person.builder()
               .Id(1L)
               .username("Person test")
               .balance(10.00)
               .profileImgPath("/path/test")
               .build();
    }

    @Test
    public void givenNewPerson_whenSavePerson_thenReturnSavedPerson() throws UniqueUsernameValidationException {

        given(this.personRepository.findByUsername(this.person.getUsername()))
                .willReturn(Optional.empty());

        given(this.personRepository.save(this.person))
                .willReturn(this.person);

        var newPerson = this.personService.savePerson(this.person);

        assertThat(newPerson)
                .isNotNull()
                .isExactlyInstanceOf(Person.class);

        assertThat(newPerson.getId()).isNotNull()
                .isEqualTo(1L);
    }

    @Test
    public void givenNewPersonWithAlreadyUsedUsername_whenSavePerson_thenThrowException(){
        given(this.personRepository.findByUsername(this.person.getUsername()))
                .willReturn(Optional.of(this.person));

        org.junit.jupiter.api.Assertions.assertThrows(UniqueUsernameValidationException.class, () -> {
           this.personService.savePerson(this.person);
        });

        verify(this.personRepository, never()).save(any(Person.class));
    }

    @Test
    public void givenListOfPersons_whenGetAllPersons_thenReturnListWithAllPersons(){
        given(this.personRepository.findAll())
                .willReturn(List.of(this.person, this.person));

        var listOfPersons = this.personService.getAllPersons();

        assertThat(listOfPersons)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .hasOnlyElementsOfType(Person.class);
    }

    @Test
    public void givenEmptyListOfPersons_whenGetAllPersons_thenReturnEmptyList(){
        given(this.personRepository.findAll())
                .willReturn(Collections.emptyList());

        var listOfPersons = this.personService.getAllPersons();

        assertThat(listOfPersons)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void givenExistingPerson_whenGetPersonById_thenReturnPerson() throws PersonNotFoundException {
        given(this.personRepository.findById(1L))
                .willReturn(Optional.ofNullable(this.person));

        var person = this.personService.getPersonById(1L);

        assertThat(person)
                .isNotNull()
                .isExactlyInstanceOf(Person.class);

        assertThat(person.getId())
                .isNotNull()
                .isEqualTo(1);
    }

    @Test
    public void givenNonExistingPerson_whenGetPersonById_thenThrowException() {
        given(this.personRepository.findById(1L))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(PersonNotFoundException.class, () -> {
           this.personService.getPersonById(1L);
        });
    }

    @Test
    public void givenPerson_whenUpdatePerson_thenReturnUpdatedPerson() throws PersonNotFoundException {
        given(this.personService.getPersonById(1L))
                .willReturn(this.person);
    }
}