package com.example.mapper;
import com.example.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    int insert(Admin admin);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(int id);

}
