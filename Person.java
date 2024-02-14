package pr1;

public abstract class Person {
	private String ID;
	private String name;
	private int age;
	private String gender;
	private String address;
	private String contactInfo;
	private boolean isMartyr;

	public Person(String ID, String name, int age, String gender, String address, String contactInfo,
			String rolemember) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.contactInfo = contactInfo;

	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setGender(String gender) {
		
			this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setMartyr(boolean isMartyr) {
		this.isMartyr = isMartyr;
	}

	public boolean isMartyr() {
		return isMartyr;
	}// for the Martyr people

	@Override
	public String toString() {
		return ID + name + age + gender + address + contactInfo;
	}

}