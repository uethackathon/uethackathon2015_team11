package team.hidro.highschoolsupport.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import team.hidro.highschoolsupport.entities.ListClassDetail;
import team.hidro.highschoolsupport.entities.ScoreDetail;
import team.hidro.highschoolsupport.entities.StudentScoreDetail;
import team.hidro.highschoolsupport.entities.TeacherDetail;
import team.hidro.highschoolsupport.service.ClassService;
import team.hidro.highschoolsupport.service.ScoreService;
import team.hidro.highschoolsupport.service.TeacherService;

@Controller
public class ScoreController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ClassService classService;
	@Autowired
	private ScoreService scoreService;
	
	public @ResponseBody boolean createScore(@PathVariable("score") int score, @PathVariable("subjectYearId") int subjectYearId,
			@PathVariable("type") int type, @PathVariable("userId") int userId,  @PathVariable("ky") int ky) {
		
		ScoreDetail scoreDetail = new ScoreDetail(score, type, userId, subjectYearId, ky);
		return scoreService.save(scoreDetail);
	}
	
	@RequestMapping("/class/{idClass}/student/{subjectId}")
	public @ResponseBody List<StudentScoreDetail> _getClass(@PathVariable("idClass") int idClass, @PathVariable("subjectId") int idSubject){
		
		return classService.getListStudentScoreByClassAndSubject(idClass, idSubject, 1);
	}
	
	@RequestMapping("/class")
	public @ResponseBody List<ListClassDetail> classTeacher(HttpSession session){
		
		TeacherDetail teacher = (TeacherDetail) session.getAttribute("user");
		return teacherService.getListClassByTeacherId(teacher.getUserId());
		
	}
}
