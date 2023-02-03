package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setup() {
        this.person = Person.builder()
                .username("Person test")
                .balance(10.50)
                .profileImgPath("/img/path")
                .pixQrcode("pixqrcodetest")
                .build();

        personRepository.save(this.person);
    }

    @Test
    public void givenNewPerson_whenSavingNewPerson_thenReturnSavedPerson() {

        var savedPerson = personRepository.save(this.person);

        assertThat(savedPerson.getId())
                .isNotNull();
    }


    @Test
    public void givenTwoOrMorePersons_whenFetchingAll_thenReturnListWithAllPersons() {
        var anotherPerson = Person.builder()
                .username("Another Person")
                .balance(10.00)
                .profileImgPath("/img/path")
                .pixQrcode("pixqrcodetest")
                .build();

        personRepository.save(anotherPerson);

        var personsList = personRepository.findAll();

        assertThat(personsList)
                .hasSize(2)
                .hasOnlyElementsOfType(Person.class);

    }

    @Test
    public void givenUpdatedPersonInfo_whenSavingPerson_thenReturnUpdatedPerson() {

        var oldPerson = personRepository.findById(1L);

        oldPerson.ifPresent(
            value -> {
                assertThat(value.getUsername())
                        .isNotNull()
                        .isEqualTo("Person test");

                value.setUsername("Updated username!");

                var newPerson = personRepository.save(value);

                assertThat(newPerson.getUsername())
                        .isNotNull()
                        .isEqualTo("Updated username");

                assertThat(newPerson.getCreatedAt())
                        .isEqualTo(oldPerson.get().getCreatedAt());

                assertThat(newPerson.getId())
                        .isEqualTo(oldPerson.get().getId());
            }
        );
    }

    @Test
    public void givenAnPerson_whenDeletingPerson_thenRemoveFromDatabase(){
       var person = personRepository.findById(1L);

       person.ifPresent(
           value -> {
               assertThat(value)
                       .isNotNull();

               personRepository.deleteById(value.getId());

               var allPersons = personRepository.findAll();

               assertThat(allPersons)
                       .isEmpty();
           }
       );
    }
}
