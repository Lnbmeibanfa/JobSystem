package com.example.mapper;
import com.example.entity.Advertise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdvertiseMapper {
    int insert(Advertise advertise);

    @Select("select * from `advertise`")
    List<Advertise> selectAll();

    List<Advertise> selectByPage(Advertise advertise);

    void update(Advertise advertise);

    void deleteById(Integer id);

    Advertise selectByTitle(Advertise advertise);

    @Select("select * from `advertise` where location = #{location}")
    List<Advertise> selectByLocation(String location);
}
