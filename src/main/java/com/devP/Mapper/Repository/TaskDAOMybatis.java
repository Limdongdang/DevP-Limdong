package com.devP.Mapper.Repository;

import com.devP.VO.IssueVO;
import com.devP.VO.TaskListVO;
import com.devP.VO.TaskVO;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDAOMybatis {

    @Autowired
    private SqlSessionTemplate mybatis;

    public int getTask(TaskVO vo) {return mybatis.selectOne("TaskDAO.getTask", vo);}
    
    public List<TaskVO> getTask(String userId) {
    	return mybatis.selectList("TaskDAO.getTask", userId);
    }

    public List<TaskListVO> getUserTaskList(String userId){
        return mybatis.selectList("TaskDAO.getTaskList", userId);
    }
}
