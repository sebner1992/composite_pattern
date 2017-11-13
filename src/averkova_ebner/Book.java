package averkova_ebner;

public class Book implements Item {
	private String name;
	private double price;

	public Book(String name, double price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double obtainPrice() {
		return price;
	}
}
