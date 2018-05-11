package tyust.gjl.facerecognition.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tyust.gjl.facerecognition.entity.User;

/**
 * @author : coderWu
 * @date : Created on 20:48 2018/5/11
 */
public interface UserMapper {

    @Select("select * from user where username = #{username} and password = #{password}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    User loginSelect(User user);

    @Insert("insert into user(username, password) values (#{username}, #{password})")
    int insert(User user);

}
