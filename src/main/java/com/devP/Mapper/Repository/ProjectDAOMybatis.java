package com.devP.Mapper.Repository;

import com.devP.VO.ProjectVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDAOMybatis {

    @Autowired
    private SqlSessionTemplate mybatis;

    public int insertProject(ProjectVO vo) {return mybatis.insert("ProjectDAO.insertProject", vo);}
}
