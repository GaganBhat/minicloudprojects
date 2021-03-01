import java.util.HashMap;

public class VendingMachine {


	public HashMap<String, Item> vendMap;
	public VendingMachine(String input) {
		vendMap = new HashMap<>();

		String[] lineItems = input.split("[,]");
		for(String lineItem : lineItems){
			String[] components = lineItem.split("[:]");
			double cost = Double.parseDouble(components[2].trim());
			String name = components[1].trim();
			String locValue = components[0].trim();
			Item item = new Item(name, cost);

			vendMap.put(locValue, item);
		}
	}


	public double processItemBuying(String location, double totalMoney) {
		if (!vendMap.containsKey(location)) {
			System.out.println("Location empty!");
			return 0;
		}

		Item item = vendMap.get(location);
		vendMap.remove(location);

		return totalMoney - item.cost;
	}


	public static void main(String[] args) {
		VendingMachine machine = new VendingMachine("a1 : Mars Bar : 0.75, a2 : Snickers Bar : 0.87, a3 : Skittles : 1.05, b1 : Twizzlers : 0.85, b2 : Fritos : 1.15, b3 : Cheetos : 1.15, b4 : Doritos : 1.15, c1 : Hershey's Milk Chocolate : 1.00, c2 : Mint Gum : 0.75, c3 : Mints : 0.35");

		System.out.println(machine.vendMap.toString());
		machine.processItemBuying("a1", 1.00);
		System.out.println(machine.vendMap.toString());

	}

//	a1 : Mars Bar : 0.75,
//
//	a2 : Snickers Bar : 0.87,
//
//	a3 : Skittles : 1.05,
//
//	b1 : Twizzlers : 0.85,
//
//	b2 : Fritos : 1.15,
//
//	b3 : Cheetos : 1.15,
//
//	b4 : Doritos : 1.15,
//
//	c1 : Hershey's Milk Chocolate : 1.00,
//
//	c2 : Mint Gum : 0.75,
//
//	c3 : Mints : 0.35

}
