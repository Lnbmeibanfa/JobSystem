package com.example.mapper;
import com.example.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResumeMapper {
    int insert(Resume resume);

    @Select("select * from `resume`")
    List<Resume> selectAll();

    List<Resume> selectByPage(Resume resume);

    void update(Resume resume);

    void deleteById(Integer id);

}
