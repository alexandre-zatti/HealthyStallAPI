package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person savePerson(Person newPerson) throws UniqueUsernameValidationException {
        var person = this.personRepository.findByUsername(newPerson.getUsername());

        if(person.isPresent()){
            throw new UniqueUsernameValidationException();
        }

        return this.personRepository.save(newPerson);
    }

    public List<Person> getAllPersons(){
        return this.personRepository.findAll();
    }

    public Person getPersonById(Long id) throws PersonNotFoundException {

        var person = this.personRepository.findById(id);

        if(person.isPresent()){
            return person.get();
        }

        throw new PersonNotFoundException();
    }

    public Person updatePerson(Long id, Map<String, Object> fields) throws PersonNotFoundException, UniqueUsernameValidationException {
        var person = this.getPersonById(id);

        fields.forEach((key, value) -> {
           var field = ReflectionUtils.findField(Person.class, key);

           if (field != null){
               field.setAccessible(true);
               ReflectionUtils.setField(field, person, value);
           }
        });

        return this.savePerson(person);
    }

    public void deletePerson(Long id){
        this.personRepository.deleteById(id);
    }
}
