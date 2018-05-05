package tyust.gjl.facerecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tyust.gjl.facerecognition.mapper.PersonMapper;

/**
 * @author : coderWu
 * @date : Created on 16:35 2018/5/5
 */
@Service
public class PersonService {

    @Autowired
    PersonMapper personMapper;

    public void add(String username, String imageName) {
        personMapper.insert(username, imageName);
    }

}
