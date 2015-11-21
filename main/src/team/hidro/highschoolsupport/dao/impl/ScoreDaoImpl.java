package team.hidro.highschoolsupport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Repository;

import team.hidro.highschoolsupport.dao.AutoWireJdbcDaoSupport;
import team.hidro.highschoolsupport.dao.ScoreDao;
import team.hidro.highschoolsupport.entities.ScoreDetail;
import team.hidro.highschoolsupport.entities.StudentDetail;
import team.hidro.highschoolsupport.entities.StudentScoreDetail;

@Repository
public class ScoreDaoImpl extends AutoWireJdbcDaoSupport implements ScoreDao {

	@Override
	public List<ScoreDetail> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScoreDetail getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(ScoreDetail item) {
		
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "insert into score value(`id`, `subject_year_id`, `user_id`, `type`, `score`, `ky`)";
			smt = conn.prepareStatement(sql);
			
			smt.setInt(2, item.getSubjectYearId());
			smt.setInt(3, item.getUserId());
			smt.setInt(4, item.getType());
			smt.setInt(5, item.getScore());
			smt.setInt(6, item.getKy());
			
			return (smt.executeUpdate(sql) > 0) ? true : false;

		} catch (Exception e) {
			logger.error("addPost",e);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(smt);
			DbUtils.closeQuietly(conn);
		}
		return false;
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(ScoreDetail item) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "update score set subject_year_id= ? value user_id = ?  type = ? score = ? ky= ? where id?";
			smt = conn.prepareStatement(sql);
			
			smt.setInt(2, item.getSubjectYearId());
			smt.setInt(3, item.getUserId());
			smt.setInt(4, item.getType());
			smt.setInt(5, item.getScore());
			smt.setInt(6, item.getKy());
			
			return (smt.executeUpdate(sql) > 0) ? true : false;

		} catch (Exception e) {
			logger.error("addPost",e);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(smt);
			DbUtils.closeQuietly(conn);
		}
		return false;
	}

	@Override
	public List<StudentScoreDetail> getListStudentScoreByListStudent(List<StudentDetail> students, int ky) {
		List<StudentScoreDetail> studentScoreDetails = new ArrayList<StudentScoreDetail>();
		for (StudentDetail studentDetail : students) {
			StudentScoreDetail studentScoreDetail = getStudentStoreDetailByStudent(studentDetail, ky);
			if (studentScoreDetail != null) {
				studentScoreDetails.add(studentScoreDetail);
			}

		}
		return studentScoreDetails;

	}

	private StudentScoreDetail getStudentStoreDetailByStudent(StudentDetail studentDetail, int ky) {
		Connection conn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "Select * from score where user_id = ? and ky= ?";
			smt = conn.prepareStatement(sql);
			smt.setInt(1, studentDetail.getUserId());
			smt.setInt(2, ky);

			rs = smt.executeQuery();
			List<ScoreDetail> scoreDetails = new ArrayList<ScoreDetail>();
			while (rs.next()) {
				int id1 = rs.getInt("id");
				int score = rs.getInt("score");
				int type = rs.getInt("type");
				ScoreDetail scoreDetail = new ScoreDetail(id1,score, type);
				scoreDetails.add(scoreDetail);
			}
			scoreDetails = fixScore(scoreDetails);
			return new StudentScoreDetail(scoreDetails, studentDetail);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(smt);
			DbUtils.closeQuietly(conn);
		}
		return null;
	}

	private List<ScoreDetail> fixScore(List<ScoreDetail> scoreDetails) {

		List<ScoreDetail> scoreDetails2 = new ArrayList<ScoreDetail>();
		int i=0;
		for (ScoreDetail scoreDetail : scoreDetails) {
			if(scoreDetail.getType()==1) {
				scoreDetails2.add(scoreDetail);
				i++;
			}
		}
		while(i++!=3)
			scoreDetails2.add(new ScoreDetail(-1,1));
		i=0;
		for (ScoreDetail scoreDetail : scoreDetails) {
			if(scoreDetail.getType()==2) {
				scoreDetails2.add(scoreDetail);
				i++;
			}
		}
		while(i++!=3)
			scoreDetails2.add(new ScoreDetail(-1,2));
		i=0;
		for (ScoreDetail scoreDetail : scoreDetails) {
			if(scoreDetail.getType()==3) {
				scoreDetails2.add(scoreDetail);
				i++;
			}
		}
		while(i++!=3)
			scoreDetails2.add(new ScoreDetail(-1,3));
		i=0;
		for (ScoreDetail scoreDetail : scoreDetails) {
			if(scoreDetail.getType()==4) {
				scoreDetails2.add(scoreDetail);
				i++;
			}
		}
		if(i!=1) scoreDetails2.add(new ScoreDetail(-1,4));
		return scoreDetails2;
	}

	@Override
	public boolean addOrUpdate(ScoreDetail scoreDetail) {
		return false;
	}

}