package com.example.mapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);

    @Select("select * from `user` where id = #{id}")
    User selectById(int id);

    @Select("select * from `user`")
    List<User> selectAll();

    List<User> selectByPage(User user);

    void update(User user);

    void deleteById(Integer id);

    User selectByUsername(String username);
}
