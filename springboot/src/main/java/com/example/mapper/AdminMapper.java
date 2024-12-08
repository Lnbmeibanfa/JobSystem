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

    List<Admin> selectAll(Admin admin);

    void update(Admin admin);

    void deleteById(Integer id);
}
