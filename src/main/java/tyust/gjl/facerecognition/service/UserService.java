package tyust.gjl.facerecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tyust.gjl.facerecognition.entity.User;
import tyust.gjl.facerecognition.mapper.UserMapper;

/**
 * @author : coderWu
 * @date : Created on 21:22 2018/5/11
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User login(User user) {
        return userMapper.loginSelect(user);
    }

    public boolean register(User user) {
        return userMapper.insert(user) > 0;
    }

}
