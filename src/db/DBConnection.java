package db;

import java.util.List;
import java.util.Set;

import entity.Item;

public interface DBConnection {
	/**
	 * 1.CLose the connection.
	 */
	public void close();
	
	/**
	 * 2.Insert the favorite items for a user
	 * 
	 * @param userId
	 * @param itemIds
	 */
	public void setFavoriteItems(String userId, List<String> itemIds);
	
	/**
	 * 3.Delete the favorite items for a user
	 * 
	 * @param userId
	 * @param itemIds
	 */
	public void unsetFavoriteItems(String userId, List<String> itemIds);
	
	/**
	 * 4.Get the favorite item id for a user
	 * 
	 * @param userId
	 * @return itemIds
	 */
	public Set<String> getFavoriteItemIds(String userId);
	
	/**
	 * 5.Get the favorite items for a user
	 * 
	 * @param userId
	 * @return items
	 */
	public Set<Item> getFavoriteItems(String userId);
	
	/**
	 * 6.Get categories based on item id
	 * 
	 * @param itemId
	 * @return set of categories
	 */
	public Set<String> getCategories(String itemId);
	
	/**
	 * 7.Search items near a geolocation and a term
	 * 
	 * @param lat
	 * @param lon
	 * @param term
	 * @return list of items
	 */
	public List<Item> searchItems(double lat, double lon, String term);
	
	/**
	 * 8.Save item into db.
	 * 
	 * @param item
	 */
	public void saveItem(Item item);
	
	/**
	 * 9.Get full name of a user.
	 * 
	 * @param userId
	 * @return full name of the user
	 */
	public String getFullname(String userId);
	
	/**
	 * 10.Return whether the credential is correct.
	 * 
	 * @param userId
	 * @param password
	 * @return boolean
	 */
	public boolean verifyLogin(String userId, String password);
}
