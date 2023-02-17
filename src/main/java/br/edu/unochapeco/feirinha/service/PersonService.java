package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.exception.InsuficientBalanceException;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.PersonRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final Validator validator;

    PersonService(PersonRepository personRepository, Validator validator){
        this.personRepository = personRepository;
        this.validator = validator;
    }

    private Boolean isUsernameAlreadyInUse(String username) {
        var person = this.personRepository.findByUsername(username);
        return person.isPresent();
    }

    public Person createPerson(Person newPerson) throws UniqueUsernameValidationException {
        if(this.isUsernameAlreadyInUse(newPerson.getUsername())){
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

    public Person updatePerson(Long id, Map<String, Object> fields)
            throws PersonNotFoundException, UniqueUsernameValidationException, ConstraintViolationException {

        var person = this.getPersonById(id);

        for (Map.Entry<String, Object> entry: fields.entrySet()) {
            var field = ReflectionUtils.findField(Person.class, entry.getKey());

            if (field != null){
                if(field.getName().equals("username") && this.isUsernameAlreadyInUse(entry.getValue().toString())){
                    throw new UniqueUsernameValidationException();
                }

                field.setAccessible(true);
                ReflectionUtils.setField(field, person, entry.getValue());
            }
        }

        var violations = validator.validate(person);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        return this.personRepository.save(person);
    }

    public void deletePerson(Long id) throws PersonNotFoundException {
        var person = this.personRepository.findById(id);

        if(!person.isPresent()){
            throw new PersonNotFoundException();
        }

        this.personRepository.deleteById(id);
    }

    public Person addToPersonBalace(Long id, Double value) throws PersonNotFoundException {
        var person = this.getPersonById(id);

        person.setBalance(person.getBalance() + value);

        return this.personRepository.save(person);
    }

    public Person withdrawFromPersonBalace(Long id, Double value) throws PersonNotFoundException, InsuficientBalanceException {
        var person = this.getPersonById(id);

        if (person.getBalance() - value < 0){
            throw new InsuficientBalanceException();
        }

        person.setBalance(person.getBalance() - value);

        return this.personRepository.save(person);
    }

}
