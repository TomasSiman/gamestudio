package Gamestudio.service.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Rating;
import Gamestudio.entity.Score;
import Gamestudio.service.ScoreService;

public class SORM1 {
	public String getCreateTableString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(getTableName(clazz));
		sb.append(" (");

		boolean isFirst = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isFirst)
				sb.append(", ");
			sb.append(getColumnName(field));
			sb.append(" ");
			sb.append(getSQLType(field));
			isFirst = false;
		}

		sb.append(")");
		return sb.toString();
	}

	public String getDeleteString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(getTableName(clazz));
		sb.append(" WHERE ");
		return sb.toString();
	}

	public String getInsertString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(getTableName(clazz));
		sb.append(" (");

		boolean isFirst = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isFirst)
				sb.append(", ");
			sb.append(getColumnName(field));
			isFirst = false;
		}

		sb.append(") VALUES (");

		isFirst = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isFirst)
				sb.append(", ");
			if ("ident".equals(field.getName()))
				sb.append("nextval('ident_seq')");
			else
				sb.append("?");
			isFirst = false;
		}

		sb.append(")");

		return sb.toString();
	}

	public String getUpdateString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(getTableName(clazz));
		sb.append(" SET ");

		boolean isFirst = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isFirst)
				sb.append(", ");
			sb.append(getColumnName(field) + " = ? ");
			isFirst = false;
		}
sb.append(" WHERE ident = ? ");

		return sb.toString();
	}

	public String getSelectString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");

		boolean isFirst = true;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isFirst)
				sb.append(", ");
			sb.append(getColumnName(field));
			isFirst = false;
		}

		sb.append(" FROM ");
		sb.append(getTableName(clazz));
		return sb.toString();
	}
	public String getAVGString(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT AVG(");
		sb.append(")");
		sb.append(" FROM ");
		sb.append(getTableName(clazz));
		return sb.toString();
		
	}
	
	public double average(Object object, String condition) {
		Class clazz = object.getClass();
		String command = getAVGString(clazz) + (condition != null ? " " + condition : "");

		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD);
				PreparedStatement ps = connection.prepareStatement(command)) {
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble(1);


				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}



	public void delete(Object object, String condition) {
		Class clazz = object.getClass();
		String command = getDeleteString(clazz) + (condition != null ? " " + condition : "");

		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(command)) {

			// int index = 1;
			// for (Field field : clazz.getDeclaredFields()) {
			// if (!"ident".equals(field.getName())) {
			// field.setAccessible(true);
			// Object value = field.get(object);
			// ps.setObject(index, value);
			// index++;
			// }
			// }

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Object object) {
		Class clazz = object.getClass();
		String command = getInsertString(clazz);

		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(command)) {

			int index = 1;
			for (Field field : clazz.getDeclaredFields()) {
				if (!"ident".equals(field.getName())) {
					field.setAccessible(true);
					Object value = field.get(object);
					ps.setObject(index, value);
					index++;
				}
			}

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Object object) {
		Class clazz = object.getClass();
		String command = getUpdateString(clazz);

		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(command)) {

			int index = 1;
			for (Field field : clazz.getDeclaredFields()) {
				if (!"ident".equals(field.getName())) {
					field.setAccessible(true);
					Object value = field.get(object);
					ps.setObject(index, value);
					index++;
				}
			}

			Field field = clazz.getDeclaredField(command);
			field.setAccessible(true);
			Object value = field.get(object);
			ps.setObject(1, value);
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public <T> List<T> select(Class<T> clazz) {
		return select(clazz, null);
	}

	public <T> List<T> select(Class<T> clazz, String condition) {
		String command = getSelectString(clazz) + (condition != null ? " " + condition : "");

		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD);
				Statement ps = connection.createStatement();
				ResultSet rs = ps.executeQuery(command)) {

			List<T> list = new ArrayList<>();
			while (rs.next()) {
				T object = clazz.newInstance();
				int index = 1;
				for (Field field : clazz.getDeclaredFields()) {
					Object value = rs.getObject(index);
					field.setAccessible(true);
					field.set(object, value);
					index++;
				}
				list.add(object);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getTableName(Class clazz) {
		return clazz.getSimpleName().toLowerCase();
	}

	private String getColumnName(Field field) {
		return field.getName();
	}

	private String getSQLType(Field field) {
		String type = field.getType().getCanonicalName();
		switch (type) {
		case "int":
			return "INTEGER";
		case "java.lang.String":
			return "VARCHAR(32)";
		case "java.util.Date":
			return "TIMESTAMP";
		default:
			throw new IllegalArgumentException("Not supported Java type " + type);
		}
	}

	public static void main(String[] args) {
		SORM1 sorm = new SORM1();

		CommentServiceSORM commentServiceSORM = new CommentServiceSORM();
		RatingServiceSORM ratingServiceSORM = new RatingServiceSORM();

		// Comment comment = new Comment();
		// comment.setUsername("Jakobo");
		// comment.setGame("mines");
		// comment.setContent("dfgdfgdfgdghg ... ");
		// java.util.Date utilDate = new java.util.Date();
		// java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		// comment.setCreatedOn(sqlDate);
		//
		// ratingServiceSORM.addComment(comment);
		// System.out.println(sorm.getDeleteString(Score.class));

		//
		// System.out.println(sorm.getCreateTableString(Score.class));
		// System.out.println(sorm.getCreateTableString(Rating.class));
		// System.out.println(sorm.getCreateTableString(Comment.class));
		//
		// System.out.println(sorm.getInsertString(Score.class));
		// System.out.println(sorm.getInsertString(Rating.class));
		// System.out.println(sorm.getInsertString(Comment.class));
		//
		Score score = new Score();
		// score.setUsername("nikus");
		// score.setGame("mines");
		// score.setValue(100);
		//
		// sorm.insert(score);

		// sorm.delete(score, "WHERE username = 'nikus' AND game = 'mines'");

		// System.out.println(commentServiceSORM.getComments("mines"));

		//
		// sorm.insert(comment);

		Rating rating = new Rating();
		 rating.setUsername("jakobo");
		 rating.setGame("mines");
		 rating.setValue(2);
		 sorm.insert(rating);
		 
		 System.out.println(ratingServiceSORM.getAverageRating("mines"));

		 
		 
		// sorm.delete(rating," WHERE username = 'jakub' AND game = 'mines'");

		// ScoreService scoreService = new ScoreServiceSORM();
	




	}
}
