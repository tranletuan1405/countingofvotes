package vn.edu.uit.models.json;

public class Staff {
	
	private static int ID = 0;
	private int id;
	private String name;
	private int age;
	private String position;

	public Staff() {

	}

	public Staff(String name, int age, String position) {
		this.name = name;
		this.age = age;
		this.position = position;
		this.id = ++ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
