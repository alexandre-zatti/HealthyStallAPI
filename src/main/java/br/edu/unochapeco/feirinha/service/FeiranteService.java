package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Feirante;
import br.edu.unochapeco.feirinha.exception.NoActiveFeiranteException;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.repository.FeiranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeiranteService {

    private final FeiranteRepository feiranteRepository;

    private final PersonService personService;

    FeiranteService(FeiranteRepository feiranteRepository, PersonService personService){
        this.feiranteRepository = feiranteRepository;
        this.personService = personService;
    }

    public Feirante getCurrentFeirante() throws NoActiveFeiranteException {
        var currentFeirante = this.feiranteRepository.getCurrentFeirante();

        if (currentFeirante == null){
            throw new NoActiveFeiranteException();
        }

        return currentFeirante;
    }

    public Feirante setNewFeirante(Long personId) throws PersonNotFoundException {

        try{
            var currentFeirante = this.getCurrentFeirante();

            if (currentFeirante != null){
                currentFeirante.setActive(false);
                this.feiranteRepository.save(currentFeirante);
            }
        }catch (NoActiveFeiranteException ignored){}

        var person = this.personService.getPersonById(personId);

        var newFeirante = Feirante
                .builder()
                .person(person)
                .active(true)
                .build();

        return this.feiranteRepository.save(newFeirante);
    }

    public List<Feirante> getFeirantesHistory(){
        return this.feiranteRepository.findAll();
    }
}
