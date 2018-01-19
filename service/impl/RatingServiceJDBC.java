package Gamestudio.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Gamestudio.entity.Rating;
import Gamestudio.service.RatingService;

public class RatingServiceJDBC implements RatingService {
	private static final String INSERT_COMMAND = "INSERT INTO rating(ident, username, game, value)  VALUES(nextval('ident_seq'),?,?,?)";
	private static final String DELETE_COMMAND = "DELETE FROM rating WHERE username = ? AND game = ?";
	private static final String SELECTAVG_COMMAND = "SELECT AVG(value) FROM rating WHERE game = ?";
	@Override
	public void setRating(Rating rating) {
		
		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,JDBCConnection.PASSWORD)){
			
		try(PreparedStatement ps = connection.prepareStatement(DELETE_COMMAND)){
			ps.setString(1, rating.getUsername());
			ps.setString(2, rating.getGame());
			ps.executeUpdate();
		}	
		try(PreparedStatement ps1 = connection.prepareStatement(INSERT_COMMAND)) {
			ps1.setString(1, rating.getUsername());
			ps1.setString(2, rating.getGame());
			ps1.setInt(3, rating.getValue());
			ps1.executeUpdate();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}

		
	

	@Override
	public double getAverageRating(String game) {
		try (Connection connection = DriverManager.getConnection(JDBCConnection.URL, JDBCConnection.USER,
				JDBCConnection.PASSWORD); PreparedStatement ps = connection.prepareStatement(SELECTAVG_COMMAND)) {
			ps.setString(1, game);
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


}
