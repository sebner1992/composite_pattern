package averkova_ebner;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.Scanner;
import assignment4_int.Assignment4;

public class XMLParser implements Assignment4 {
	private static final String inFileName = "input.xml";
	private Composite rootList;
	private double itemPrice = 0;
	private static boolean itemFound = false;

	public static void main(String[] args) {
		try {
			XMLParser parser = new XMLParser();
			File input = new File(inFileName);
			parser.loadXml(input);

			Scanner in = new Scanner(System.in);
			String item;
			do {
				System.out.println("Enter search item!");
				System.out.print("input= ");
				item = in.nextLine();

				if (item.equals("q")) {
					System.out.print("Program is terminated!");
					in.close();
					return;
				}

				try {
					System.out.println("output= " + parser.getPrice(item));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			} while (!itemFound);
			in.close();
		}

		catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

	}

	/** loads the xml file or throws an Exception if anything goes wrong */
	public void loadXml(File input) throws Exception {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(input);
			Element root = doc.getDocumentElement();

			NodeList nl = root.getChildNodes();

			rootList = new Composite(root.getAttribute("name"));// root
																// composite
			populateItemList(nl, rootList);
		} catch (Exception e) {
			throw e;
		}
	}

	/** returns the price of an item (cd, book, or list) */
	public double getPrice(String item) {
		if (item.equals(rootList.getName())) {
			itemFound = true;
			return rootList.obtainPrice();
		}

		// if the item in getPrice does not exist
		if (!findItem(item, rootList)) {
			throw new IllegalArgumentException(
					"Item \"" + item + "\" not found! Try another item or press 'q' to quit.");
		}

		return itemPrice;
	}

	private boolean findItem(String item, Composite list) {
		for (Item i : list.getItemList()) {
			if (i.getName().equals(item)) {
				itemPrice = i.obtainPrice();
				itemFound = true;
				return itemFound;
			}
			if (i instanceof Composite) {
				findItem(item, (Composite) i);
			}
		}
		return itemFound;
	}

	private void populateItemList(NodeList nl, Composite list) {
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			int type = node.getNodeType();

			if (type == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getAttribute("name");
				String price = element.getAttribute("price");

				switch (element.getTagName()) {
				case "book":
					list.add(new Book(name, toDouble(price)));
					break;
				case "cd":
					list.add(new Cd(name, toDouble(price)));
					break;
				default:
					addList(name, node, list);
				}
			}
		}
	}

	private void addList(String name, Node node, Composite list) {
		Composite newList = new Composite(name);
		list.add(newList);

		if (node.hasChildNodes()) {
			populateItemList(node.getChildNodes(), newList);
		}
	}

	private double toDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("ERROR! Could not cast input string " + "\"" + s + "\" to double.");
		}
	}
}
