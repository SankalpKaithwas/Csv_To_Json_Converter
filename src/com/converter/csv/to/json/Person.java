package com.converter.csv.to.json;

public class Person {
	private String name;
	private int age;
	private String email;
	
	public Person() {
    }

	/** when not using annotations default constructor is required*/
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    /** can use Annotation to achieve same functionality without need of default constructor*/

//	// Constructor, getters and setters
//	public Person(@JsonProperty("name") String name, @JsonProperty("age") int age,
//			@JsonProperty("email") String email) {
//		this.name = name;
//		this.age = age;
//		this.email = email;
//	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
