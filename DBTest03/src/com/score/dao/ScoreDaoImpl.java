package com.score.dao;

import static com.score.db.JDBCTemplate.close;
import static com.score.db.JDBCTemplate.commit;
import static com.score.db.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.score.dto.ScoreDto;

public class ScoreDaoImpl implements ScoreDao {

	@Override
	public List<ScoreDto> selectList() {
		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rs = null;

		List<ScoreDto> list = new ArrayList<ScoreDto>();

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery(SELECT_LIST_SQL);
			while (rs.next()) {
				ScoreDto sdto = new ScoreDto();

				sdto.setS_name(rs.getString(1));
				sdto.setS_kor(rs.getInt(2));
				sdto.setS_eng(rs.getInt(3));
				sdto.setS_math(rs.getInt(4));
				sdto.setS_sum(rs.getInt(5));
				sdto.setS_avg(rs.getDouble(6));
				sdto.setS_grade(rs.getString(7));

				list.add(sdto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
		return list;
	}

	@Override
	public ScoreDto selectOne(String s_name) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		ResultSet rs = null;

		ScoreDto sdto = null;

		try {
			pstm = con.prepareStatement(SELECT_ONE_SQL);
			pstm.setString(1, s_name);

			rs = pstm.executeQuery();
			while (rs.next()) {
				sdto = new ScoreDto();

				sdto.setS_name(rs.getString(1));
				sdto.setS_kor(rs.getInt(2));
				sdto.setS_eng(rs.getInt(3));
				sdto.setS_math(rs.getInt(4));
				sdto.setS_sum(rs.getInt(5));
				sdto.setS_avg(rs.getDouble(6));
				sdto.setS_grade(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}

		return sdto;
	}

	@Override
	public int insert(ScoreDto dto) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		int res = 0;

		try {
			pstm = con.prepareStatement(INSERT_SQL);

			pstm.setString(1, dto.getS_name());
			pstm.setInt(2, dto.getS_kor());
			pstm.setInt(3, dto.getS_eng());
			pstm.setInt(4, dto.getS_math());
			pstm.setInt(5, dto.getS_sum());
			pstm.setDouble(6, dto.getS_avg());
			pstm.setString(7, dto.getS_grade());

			res = pstm.executeUpdate();
			if (res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}

		return res;
	}

	@Override
	public int update(ScoreDto dto) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		int res = 0;

		try {
			pstm = con.prepareStatement(UPDATE_SQL);

			pstm.setInt(1, dto.getS_kor());
			pstm.setInt(2, dto.getS_eng());
			pstm.setInt(3, dto.getS_math());
			pstm.setInt(4, dto.getS_sum());
			pstm.setDouble(5, dto.getS_avg());
			pstm.setString(6, dto.getS_grade());
			pstm.setString(7, dto.getS_name());

			res = pstm.executeUpdate();
			if (res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}

		return res;
	}

	@Override
	public int delete(String s_name) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		int res = 0;

		try {
			pstm = con.prepareStatement(DELETE_SQL);
			pstm.setString(1, s_name);

			res = pstm.executeUpdate();
			if (res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
		}

		return res;
	}

	@Override
	public ScoreDto topNproc(int n) {
		Connection con = getConnection();

		PreparedStatement pstm = null;
		ResultSet rs = null;
		ScoreDto dto = null;

		try {
			pstm = con.prepareStatement(TOP_N_SQL);
			pstm.setInt(1, n);

			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new ScoreDto();

				dto.setS_name(rs.getString(1));
				dto.setS_kor(rs.getInt(2));
				dto.setS_eng(rs.getInt(3));
				dto.setS_math(rs.getInt(4));
				dto.setS_sum(rs.getInt(5));
				dto.setS_avg(rs.getDouble(6));
				dto.setS_grade(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
		}

		return dto;
	}
}
