package com.devP.Controller;

import com.devP.Service.ProjectService;
import com.devP.VO.ProjectVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;import com.devP.Mapper.Repository.MemberDAOMybatis;
import com.devP.Service.IssueService;
import com.devP.VO.IssueVO;

@Controller
@RequestMapping("/issue")
public class IssueController {
	
	@Autowired
	private IssueService issueService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MemberDAOMybatis MemberDAO;
	
	
	//이슈 등록 페이지
	@RequestMapping(value="/write.do", method= RequestMethod.GET)
    public String issueView(@RequestParam int projectId, Model model){
		model.addAttribute("menuId", "issueMenu");
		model.addAttribute("projectId", projectId);
		model.addAttribute("memberList", MemberDAO.getMemberList(projectId));
		
        return "issueWrite";
    }
	
	//이슈 등록
	@RequestMapping(value="/write.do", method= RequestMethod.POST)
    public String issueInsert(@ModelAttribute IssueVO issue, Model model){
		model.addAttribute("menuId", "issueMenu");
		issueService.insertIssue(issue);
        return "redirect:/issue/list.do?projectId=" + issue.getProjectId();
    }
	//이슈 목록
	@RequestMapping(value="/list.do", method= RequestMethod.GET)	
    public String getIssuelist(@RequestParam int projectId, Model model){
		ProjectVO projectVO = new ProjectVO();
		projectVO.setProjectId(projectId);
		model.addAttribute("menuId", "issueMenu");
		model.addAttribute("project", projectService.getProject(projectVO));
		issueService.getIssuelist(projectId, model);
        return "issueList";
    }
	//이슈 삭제
	@RequestMapping(value="/delete.do", method= RequestMethod.POST)
    public String deleteIssue(@ModelAttribute IssueVO issue, Model model){
		model.addAttribute("menuId", "issueMenu");
		issueService.deleteIssue(issue);
        return "redirect:/issue/list.do?projectId=" + issue.getProjectId();
	}
	//이슈 상세
	@RequestMapping(value="/detail.do", method= RequestMethod.GET)
    public String getIssuedetail(@RequestParam int issueId, Model model){
		model.addAttribute("menuId", "issueMenu");
		issueService.getIssue(issueId, model);
        return "issueDetail";
	}
	//이슈 해결
	@RequestMapping(value="/solve.do", method= RequestMethod.POST)
    public String solveIssue(@ModelAttribute IssueVO issue, Model model){
		model.addAttribute("menuId", "issueMenu");
		issueService.updateIssueStatus(issue);
        return "redirect:/issue/list.do?projectId=" + issue.getProjectId();
	}
	//이슈 수정 페이지
	@RequestMapping(value="/modify.do", method= RequestMethod.GET)
	public String getmodifyIssue(@RequestParam int issueId, Model model){
		model.addAttribute("menuId", "issueMenu");
		issueService.getIssue(issueId, model);
	    return "issueModify";
	}
	//이슈 수정
	@RequestMapping(value="/modify.do", method= RequestMethod.POST)
    public String modifyIssue(@ModelAttribute IssueVO issue, Model model){
		model.addAttribute("menuId", "issueMenu");
		System.out.println(issue.getProjectId());
		issueService.updateIssue(issue);
        return "redirect:/issue/list.do?projectId=" + issue.getProjectId();
	}
}
