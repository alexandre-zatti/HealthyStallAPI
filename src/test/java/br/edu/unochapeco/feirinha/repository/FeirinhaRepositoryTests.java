package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Feirante;
import br.edu.unochapeco.feirinha.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FeirinhaRepositoryTests {

    @Autowired
    private FeiranteRepository feiranteRepository;

    @Autowired
    private PersonRepository personRepository;

    private Feirante feirante;

    private Person person;

    @BeforeEach
    public void setup() {

        this.person = personRepository.save(Person.builder()
                .username("Person test")
                .balance(10.00)
                .pixQrcode("qrcodepixteste")
                .profileImgPath("/path/test")
                .build());

        this.feirante = Feirante.builder()
                .person(this.person)
                .active(true)
                .build();

        feiranteRepository.save(this.feirante);
    }

    @Test
    public void givenNewFeirante_whenSavingNewFeirante_thenReturnSavedFeirante() {

        var savedFeirante = feiranteRepository.save(this.feirante);

        assertThat(savedFeirante.getId())
                .isNotNull();
    }

    @Test
    public void givenTwoOrMoreFeirantes_whenFetchingAll_thenReturnListWithAllFeirantes() {
        var anotherProduct = Feirante.builder()
                .person(this.person)
                .active(true)
                .build();

        feiranteRepository.save(anotherProduct);

        var feiranteList = feiranteRepository.findAll();

        assertThat(feiranteList)
                .hasSize(2)
                .hasOnlyElementsOfType(Feirante.class);

    }

    @Test
    public void givenUpdatedFeiranteInfo_whenSavingFeirante_thenReturnUpdatedFeirante() {

        var oldFeirante = feiranteRepository.findById(1L);

        oldFeirante.ifPresent(
            value -> {
                assertThat(value.getActive())
                        .isNotNull()
                        .isEqualTo(true);

                value.setActive(false);

                var newFeirante = feiranteRepository.save(value);

                assertThat(newFeirante.getActive())
                        .isNotNull()
                        .isEqualTo(false);

                assertThat(newFeirante.getAssignAt())
                        .isEqualTo(oldFeirante.get().getAssignAt());

                assertThat(newFeirante.getId())
                        .isEqualTo(oldFeirante.get().getId());
            }
        );
    }

    @Test
    public void givenAnFeirante_whenDeletingFeirante_thenRemoveFromDatabase(){
       var feirante = feiranteRepository.findById(1L);

       feirante.ifPresent(
           value -> {
               assertThat(value)
                       .isNotNull();

               feiranteRepository.deleteById(value.getId());

               var allFeirantes = feiranteRepository.findAll();

               assertThat(allFeirantes)
                       .isEmpty();
           }
       );
    }
}
