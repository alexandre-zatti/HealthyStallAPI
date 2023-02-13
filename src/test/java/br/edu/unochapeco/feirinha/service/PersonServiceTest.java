package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.PersonRepository;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validator;
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
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private Validator validator;

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
    public void givenNewPerson_whenCreatePerson_thenReturnCreatedPerson() throws UniqueUsernameValidationException {

        given(this.personRepository.findByUsername(any(String.class)))
                .willReturn(Optional.empty());

        given(this.personRepository.save(any(Person.class)))
                .willReturn(this.person);

        var newPerson = this.personService.createPerson(this.person);

        assertThat(newPerson)
                .isNotNull()
                .isExactlyInstanceOf(Person.class);

        assertThat(newPerson.getId()).isNotNull()
                .isEqualTo(1L);
    }

    @Test
    public void givenNewPersonWithAlreadyUsedUsername_whenCreatePerson_thenThrowException(){
        given(this.personRepository.findByUsername(any(String.class)))
                .willReturn(Optional.of(this.person));

        org.junit.jupiter.api.Assertions.assertThrows(UniqueUsernameValidationException.class, () -> {
           this.personService.createPerson(this.person);
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
        given(this.personRepository.findById(any(Long.class)))
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
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(PersonNotFoundException.class, () -> {
           this.personService.getPersonById(1L);
        });
    }

    @Test
    public void givenPerson_whenUpdatePerson_thenReturnUpdatedPerson() throws PersonNotFoundException, UniqueUsernameValidationException {
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.person));

        given(this.personRepository.findByUsername(any(String.class)))
                .willReturn(Optional.empty());

        given(this.personRepository.save(any(Person.class)))
                .willReturn(this.person);

        given(this.validator.validate(any(Person.class)))
                .willReturn(Collections.emptySet());

        var oldUsername = this.person.getUsername();
        var updatedPerson = this.personService.updatePerson(this.person.getId(), Map.of("username", "Updated Username"));

        assertThat(updatedPerson.getUsername())
                .isEqualTo("Updated Username");

        assertThat(updatedPerson.getUsername())
                .isNotEqualTo(oldUsername);
    }

    @Test
    public void givenUpdatedPersonWithUsernameAlreadyInUse_whenUpdatePerson_thenThrowException(){
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.person));

        given(this.personRepository.findByUsername(any(String.class)))
                .willReturn(Optional.ofNullable(this.person));

        org.junit.jupiter.api.Assertions.assertThrows(UniqueUsernameValidationException.class, () -> {
            this.personService.updatePerson(this.person.getId(), Map.of("username", "Invalid Username"));
        });
    }

    @Test
    public void givenInvalidPersonId_whenUpdatePerson_thenThrowException(){
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(PersonNotFoundException.class, () -> {
            this.personService.updatePerson(this.person.getId(), Map.of("username", "Invalid Username"));
        });
    }

    @Test
    public void givenPerson_whenDeletePerson_thenReturnNothing() throws PersonNotFoundException {
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.person));

        willDoNothing()
                .given(this.personRepository)
                .deleteById(any(Long.class));

        this.personService.deletePerson(1L);

        verify(this.personRepository,times(1)).deleteById(1L);
    }

    @Test
    public void givenNonExistingPerson_whenDeletePerson_thenThrowException() {
        given(this.personRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(PersonNotFoundException.class, () -> {
            this.personService.deletePerson(1L);
        });
    }
}