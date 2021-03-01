public class Item {

	public String name;
	public double cost;


	public Item(String name, double cost){
		this.name = name;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Item{" +
				"name='" + name + '\'' +
				", cost=" + cost +
				'}';
	}
}
