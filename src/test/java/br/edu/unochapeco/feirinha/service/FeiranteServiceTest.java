package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Feirante;
import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.exception.NoActiveFeiranteException;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.repository.FeiranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FeiranteServiceTest {

    @Mock
    private FeiranteRepository feiranteRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private FeiranteService feiranteService;

    private Feirante feirante;

    private Person person;

   @BeforeEach
    public void setup(){

       this.person = Person.builder()
               .Id(1L)
               .username("Person test")
               .balance(10.00)
               .profileImgPath("/test/path")
               .build();

       this.feirante = Feirante.builder()
               .Id(1L)
               .person(this.person)
               .active(true)
               .build();
    }

    @Test
    public void givenActiveFeirante_whenGetCurrentFeirante_thenReturnCurrentActiveFeirante() throws NoActiveFeiranteException {
       given(this.feiranteRepository.getCurrentFeirante())
               .willReturn(this.feirante);

       var currentFeirante = this.feiranteService.getCurrentFeirante();

        assertThat(currentFeirante)
                .isNotNull()
                .isEqualTo(this.feirante);
    }

    @Test
    public void givenNoActiveFeirante_whenGetCurrentFeirante_thenThrowException(){
        given(this.feiranteRepository.getCurrentFeirante())
                .willReturn(null);

        org.junit.jupiter.api.Assertions.assertThrows(NoActiveFeiranteException.class, () -> {
            this.feiranteService.getCurrentFeirante();
        });
    }

    @Test
    public void givenNewFeirante_whenSetNewFeirante_thenReturnNewFeirante() throws PersonNotFoundException {
        given(this.feiranteRepository.getCurrentFeirante())
                .willReturn(this.feirante);

        given(this.feiranteRepository.save(any(Feirante.class)))
                .willReturn(this.feirante);

        given(this.personService.getPersonById(any(Long.class)))
                .willReturn(this.person);

        var newFeirante = this.feiranteService.setNewFeirante(1L);

        assertThat(newFeirante)
                .isNotNull()
                .isEqualTo(this.feirante);
    }

    @Test
    public void givenListOfFeirantes_whenGetFeirantesHistory_thenReturnListWithAllFeirantes(){
        given(this.feiranteRepository.findAll())
                .willReturn(List.of(this.feirante));


        var feirantesHistory = this.feiranteService.getFeirantesHistory();

        assertThat(feirantesHistory)
                .isNotEmpty()
                .hasSize(1);
    }
}