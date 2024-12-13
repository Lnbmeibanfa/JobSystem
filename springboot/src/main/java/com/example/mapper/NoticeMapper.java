package com.example.mapper;
import com.example.entity.Admin;
import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int insert(Notice notice);

    @Select("select * from `notice`")
    List<Notice> selectAll();

    List<Notice> selectByPage(Notice notice);

    void update(Notice notice);

    void deleteById(Integer id);

    Notice selectByTitle(Notice notice);
}
