package tyust.gjl.facerecognition.mapper;

import org.apache.ibatis.annotations.*;
import tyust.gjl.facerecognition.entity.Person;

import java.util.List;

/**
 * @author : coderWu
 * @date : Created on 16:38 2018/5/5
 */
public interface PersonMapper {

    @Insert("insert into person (user_id, photo_name, phone, photo_address, photo_time, person_name, type, age, note) " +
            "values (#{userId}, #{photoName}, #{phone}, #{photoAddress}, #{photoTime}, #{personName}, #{type}, #{age}, #{note})")
    void insert(Person person);

    @Select("select * from person where photo_name = #{photoName}")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "personName", column = "person_name"),
            @Result(property = "photoAddress", column = "photo_address"),
            @Result(property = "photoTime", column = "photo_time"),
            @Result(property = "photoName", column = "photo_name"),
            @Result(property = "type", column = "type"),
            @Result(property = "age", column = "age"),
            @Result(property = "note", column = "note")
    })
    Person selectByImageName(@Param("photoName") String imageName);

    @Select("select * from person where type = #{type}")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "personName", column = "person_name"),
            @Result(property = "photoAddress", column = "photo_address"),
            @Result(property = "photoTime", column = "photo_time"),
            @Result(property = "photoName", column = "photo_name"),
            @Result(property = "type", column = "type"),
            @Result(property = "age", column = "age"),
            @Result(property = "note", column = "note")
    })
    List<Person> selectByType(@Param("type") int type);
    @Select("select * from person where type = 1 order by add_time desc")
    @Results({
            @Result(property = "personId", column = "person_id"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "personName", column = "person_name"),
            @Result(property = "photoAddress", column = "photo_address"),
            @Result(property = "photoTime", column = "photo_time"),
            @Result(property = "photoName", column = "photo_name"),
            @Result(property = "type", column = "type"),
            @Result(property = "age", column = "age"),
            @Result(property = "note", column = "note"),
            @Result(property = "addTime", column = "add_time")
    })
    List<Person> selectSlider();
}
