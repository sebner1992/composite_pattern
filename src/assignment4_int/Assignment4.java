package assignment4_int;

public interface Assignment4 {
   /** loads the xml file or throws an Exception if anything goes wrong */
   public void loadXml(File input) throws Exception;
   
   /** returns the price of an item (cd, book, or list) */
   public double getPrice(String item);
}
