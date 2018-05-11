package tyust.gjl.facerecognition.mapper;

import org.apache.ibatis.annotations.*;
import tyust.gjl.facerecognition.entity.Person;

/**
 * @author : coderWu
 * @date : Created on 16:38 2018/5/5
 */
public interface PersonMapper {

    @Insert("insert into person (username, image_name) values (#{userName}, #{imageName})")
    void insert(@Param("userName") String username,
                @Param("imageName") String imageName);

    @Select("select * from person where image_name = #{imageName}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "imageName", column = "image_name")
    })
    Person selectByImageName(@Param("imageName") String imageName);

}
