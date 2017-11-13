package averkova_ebner;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Item {
	private String name;
	private double price;

	// list of objects of type Item, means that you can insert
	// Book type, Cd type or Composite type objects in the list
	private List<Item> itemList;

	public Composite(String name) {
		this.name = name;
		itemList = new ArrayList<Item>();
	}

	public void add(Item item) {
		itemList.add(item);
	}

	public List<Item> getItemList() {
		return itemList;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double obtainPrice() {
		price = 0;
		for (Item i : itemList) {
			price = price + i.obtainPrice();
		}
		return price;
	}

}
