package pr1;

public class LivePerson extends Person implements Cloneable {
	private boolean isAlive;

	LivePerson(String iD, String name, int age, String gender, String address, String contactinfo , String rolemember) {
		super(iD, name, age, gender, address, contactinfo , rolemember);

	}
	public LivePerson deepCopy() {
        try {
            LivePerson clonedLivePerson = (LivePerson) super.clone();
            System.out.println("Deep copy of LivePerson: " + clonedLivePerson);
            return clonedLivePerson;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }

	public void setAlive(boolean alive) {
		isAlive = alive;
	}

	public boolean isAlive() {
		return isAlive;
	}

}