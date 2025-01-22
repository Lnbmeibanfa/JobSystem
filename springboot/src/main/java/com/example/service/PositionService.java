package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.*;
import com.example.util.JWTUtil;
import com.example.util.UserCF;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PositionService {
    @Resource
    private PositionMapper positionMapper;
    @Resource
    private EmployMapper employMapper;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private SubmitMapper submitMapper;
    @Resource
    private UserMapper userMapper;

    public void add (Position position) {
        Account currentUser = JWTUtil.getCurAccount();
        if (currentUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Employ dbemploy = employMapper.selectById(currentUser.getId());
        position.setEmployId(dbemploy.getId());
        position.setIndustryId(dbemploy.getIndustryId());
        position.setStatus("待审核");
        positionMapper.insert(position);
    }

    public Position selectById (Integer id) {
        Position position = positionMapper.selectById(id);
        if (position == null) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        position.setTagList(tagsToList(position.getTag()));
        return position;
    }

    public PageInfo<Position> selectByPage(Position position, Integer pageNum, Integer pageSize) {
        Account currentUser = JWTUtil.getCurAccount();
        if (currentUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (currentUser.getRole().equals(RoleEnum.EMPLOY.name())) {
            position.setEmployId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Position> list = positionMapper.selectByPage(position);
        return PageInfo.of(list);
    }

    public void update(Position position) {
        positionMapper.update(position);
    }

    public void deleteById(Integer id) {
        positionMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            positionMapper.deleteById(id);
        }
    }

    /**
     * 将tags转为tagList
     */
    private List<String> tagsToList (String tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        String[] split = tags.split(",");
        List<String> list = Arrays.asList(split);
        if (list.size() > 3) {
            return list.subList(0,3);
        } else {
            return list;
        }
    }

    public List<Position> selectAll(Position position) {
        List<Position> positions = positionMapper.selectAll(position);
        for (Position dbposition : positions) {
            String tags = dbposition.getTag();
            dbposition.setTagList(tagsToList(tags));
        }
        return positions;
    }

    public List<Position> selectRecommend() {
        Account account = JWTUtil.getCurAccount();
        List<User> users = userMapper.selectAll();
        List<Position> positions = positionMapper.selectAll(new Position());
        List<Collect> collects = collectMapper.selectAll();
        List<Submit> submits = submitMapper.selectAll(new Submit());
        List<RelateDTO> data = new ArrayList<>();
        for (Position position : positions) {
            Integer positionId = position.getId();
            for (User user : users) {
                Integer userId = user.getId();
                int index = 1;
                // 如果用户收藏过该岗位，那么权重+1
                List<Collect> collectList = collects.stream()
                        .filter(collect -> collect.getPositionId().equals(positionId) && collect.getStudentId().equals(userId))
                        .collect(Collectors.toList());
                if(CollectionUtil.isNotEmpty(collectList)){
                    index++;
                }
                // 如果用户投递过该岗位，那么权重+2
                List<Submit> submitList = submits.stream()
                        .filter(submit -> submit.getPositionId().equals(positionId) && submit.getUserId().equals(userId))
                        .collect(Collectors.toList());
                if(CollectionUtil.isNotEmpty(submitList)){
                    index += 2;
                }
                if(index > 1) {
                    RelateDTO relateDTO = new RelateDTO(positionId, userId, index);
                    data.add(relateDTO);
                }
            }
        }
        // 调用基于用户行为的UserCF的推荐方法获取被推荐的岗位
        // 处理空指针异常
        List<Integer> recommendPositionIds = UserCF.recommend(Objects.requireNonNull(account).getId(), data);
        // 将获取到的id变成岗位信息
        List<Position> recommendPositions = positions.stream().filter(position -> recommendPositionIds.contains(position.getId())).collect(Collectors.toList());

        if (ObjectUtil.isEmpty(recommendPositions)) {
            recommendPositions = getRandomPositions(3, positions, null);
        }
        if(recommendPositions.size() < 3) {
            recommendPositions.addAll(getRandomPositions(3 - recommendPositionIds.size(), positions, recommendPositions));
        }
        for (Position position : recommendPositions) {
            position.setTagList(tagsToList(position.getTag()));
        }
        return recommendPositions;
    }
    public List<Position> getRandomPositions(int num, List<Position> positions, List<Position> result) {
        Collections.shuffle(positions);
        if (ObjectUtil.isNotEmpty(result)){
           positions.stream().filter(position -> !result.contains(position)).collect(Collectors.toList());
        }
        return positions.size() > num ? positions.subList(0, num) : positions;
    }
}
