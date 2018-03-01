package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.DBConnection;
import entity.Item;
import external.TicketMasterAPI;

public class MySQLConnection implements DBConnection {

	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setFavoriteItems(String userId, List<String> itemIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsetFavoriteItems(String userId, List<String> itemIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getFavoriteItemIds(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Item> getFavoriteItems(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getCategories(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> searchItems(double lat, double lon, String term) {
		TicketMasterAPI tmAPI = new TicketMasterAPI();
		// DEBUG: 测试坐标是否正确获取 | 结果:可以获取
		// System.out.println("lat = " + lat + "lon = " + lon);
		List<Item> items = tmAPI.search(lat, lon, term);
		// DEBUG: 测试items是否为null | 结果:item不为null
		// System.out.println("items = " + items);
		for (Item item : items) {
			// Save the item into your own db
			saveItem(item);
		}
		return items;
	}

	@Override
	public void saveItem(Item item) {
		if (conn == null) {
			return;
		}
		try {
			// First, insert into items table
			String sql = "INSERT IGNORE INTO items VALUES (?,?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, item.getItemId());
			statement.setString(2, item.getName());
			statement.setDouble(3, item.getRating());
			statement.setString(4, item.getAddress());
			statement.setString(5, item.getImageUrl());
			statement.setString(6, item.getUrl());
			statement.setDouble(7, item.getDistance());
			statement.execute();

			// DEBUG: 是否能从item里解析数据
			// System.out.println("ItemId = " + item.getItemId());
			// System.out.println("Name = " + item.getName());
			// System.out.println("Rating = " + item.getRating());
			// System.out.println("Address = " + item.getAddress());
			// System.out.println("Url = " + item.getUrl());
			// System.out.println("Distance = " + item.getDistance());
			
			// DEBUG: 显示不出图片的原因也已经定位完毕，getImageUrl()返回了null
			// 解决方法: 在TicketMasterAPI里，填满getImageUrl() helper function
			// 修改了helper function，解决了原先helper function无法返回image url的问题，因为原先打字打错了把images打成了image
			// System.out.println("ImageUrl = " + item.getImageUrl());
			
			// DEBUG: 问题定位完成，原因在于item.getCategories()返回了null
			// 解决方法: 在TicketMasterAPI里，填满getCategories() helper function
			// System.out.println("Categories = " + item.getCategories());

			// Second, update categories table for each category
			sql = "INSERT IGNORE INTO categories VALUES (?,?)";
			for (String category : item.getCategories()) {
				statement = conn.prepareStatement(sql);
				statement.setString(1, item.getItemId());
				statement.setString(2, category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getFullname(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyLogin(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
