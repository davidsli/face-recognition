package tyust.gjl.facerecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tyust.gjl.facerecognition.entity.Person;
import tyust.gjl.facerecognition.mapper.PersonMapper;

import java.util.List;

/**
 * @author : coderWu
 * @date : Created on 16:35 2018/5/5
 */
@Service
public class PersonService {

    @Autowired
    PersonMapper personMapper;

    public void add(Person person) {
        personMapper.insert(person);
    }

    public List<Person> getPersons(int type) {
        return personMapper.selectByType(type);
    }

    public List<Person> getSlider() {
        return personMapper.selectSlider();
    }

}
