package com.example.mapper;
import com.example.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResumeMapper {
    int insert(Resume resume);

    List<Resume> selectAll(Integer userId);

    List<Resume> selectByPage(Resume resume);

    void update(Resume resume);

    void deleteById(Integer id);

    @Select("select resume.*, u.avatar as avatar from resume left join `system`.user u on u.id = resume.user_id where resume.id = #{id}")
    List<Resume> selectById(Integer id);
}
