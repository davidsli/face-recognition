package tyust.gjl.facerecognition.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tyust.gjl.facerecognition.entity.Person;

/**
 * @author : coderWu
 * @date : Created on 16:38 2018/5/5
 */
public interface PersonMapper {

    @Insert("insert into person (username, imagename) values (#{userName}, #{imageName})")
    void insert(@Param("userName") String username,
                @Param("imageName") String imageName);

    @Select("select * from person where imagename = #{imageName}")
    Person selectByImageName(@Param("imageName") String imageName);

}
