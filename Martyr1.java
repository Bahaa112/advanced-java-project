package pr1;

public class Martyr extends Person implements Cloneable {
	private String dateOfMatyrdom;
	private String causeOfDeath;
	private String placeOfDeath;

	public Martyr(String ID, String name, int age, String gender, String address, String contactInfo,
			String dateOfMartyrdom, String causeOfDeath, String placeOfDeath, String isAlive) {

		super(ID, name, age, gender, address, contactInfo, isAlive);
		this.dateOfMatyrdom = dateOfMartyrdom;
		this.causeOfDeath = causeOfDeath;
		this.placeOfDeath = placeOfDeath;

	}
	public Martyr deepCopy() {
        try {
            Martyr clonedMartyr = (Martyr) super.clone();
           
            clonedMartyr.setDateOfMatyrdom(new String(this.dateOfMatyrdom)); 
            clonedMartyr.setCauseOfDeath(new String(this.causeOfDeath));
            clonedMartyr.setPlaceOfDeath(new String(this.placeOfDeath));
            System.out.println("Deep copy of Martyr: " + clonedMartyr);

            return clonedMartyr;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); 
        }
    }

	public void setDateOfMatyrdom(String dateOfMatyrdom) {
		this.dateOfMatyrdom = dateOfMatyrdom;
	}

	public String getDateOfMatyrdom() {
		return dateOfMatyrdom;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	@Override
	public String toString() {
		return dateOfMatyrdom + causeOfDeath + placeOfDeath;
	}

}