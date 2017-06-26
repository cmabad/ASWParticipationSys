package es.uniovi.asw.model;

public class Category {
	
	private int id;
	private String name;

	public Category(String name){
		this.name = name;
	}
	
	public Category(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}


}
