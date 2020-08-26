package de.protin.support.pr.domain.besoldung;



public class SonstigeZulage {
	
	private String description;
	private int type;
	private float salary;

	public SonstigeZulage(String description, int type, float salary) {
		this.description = description;
		this.type = type;
		this.salary = salary;
	}
	
	
	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

		
}
