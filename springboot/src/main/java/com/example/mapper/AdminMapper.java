package com.example.mapper;
import com.example.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    int insert(Admin admin);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(int id);

    @Select("select * from `admin`")
    List<Admin> selectAll();

    List<Admin> selectByPage(Admin admin);

    void update(Admin admin);

    void deleteById(Integer id);

    Admin selectByUsername(String username);
}
