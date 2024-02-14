package pr1;

import java.util.ArrayList;
import java.io.*;

public class Manager {
	private ArrayList<Family> families = new ArrayList<>();
	private int totalMartyrs;
	private int totalLivePersons;

	// Method to add a family to the list
	public boolean addFamily(Family family) {
		return families.add(family);
	}

	// Method to update family data based on the family name
	public boolean updateFamily(String familyName, Family updateFamily) {
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				families.remove(i);
				families.add(updateFamily);
				return true;
			}
		}
		return false;
	}

	// Method to delete a family based on the family name
	public boolean deleteFamily(String familyName) {
		for (int i = 0; i < families.size(); i++) {
			if (families.get(i).getFamilyName().equalsIgnoreCase(familyName)) {
				families.remove(i);
				return true;
			}
		}
		return false;
	}

	// Method to search for a family by its name
	public Family searchByName(String familyName) {
		for (int i = 0; i < families.size(); i++) {
			Family family = families.get(i);
			if (family.getFamilyName().equalsIgnoreCase(familyName)) {
				return family;
			}
		}
		 return null;
	}

	// Method to search for a person by their ID
	public Person searchPersonByID(String personID) {
		for (Family family : families) {
			for (Person person : family.getMembers()) {
				if (person.getID().equals(personID)) {
					return person;
				}
			}
		}
		return null;
	}

	// Method to calculate the total number of martyrs across all families
	public int calculateTotalMartyrs() {
		int numMartyrs = 0;
		for (int i = 0; i < families.size(); i++) {
			Family family = families.get(i);
			for (Person member : family.getMembers()) {
				if (member instanceof Martyr) {
					numMartyrs++;
				}
			}
		}
		return numMartyrs;
	}

	// Method to increment the total number of martyrs
	public void incrementTotalMartyrs() {
		totalMartyrs++;
	}

	// Method to get the total number of martyrs
	public int getTotalMartyrs() {
		return totalMartyrs;
	}

	// Method to calculate the total number of orphans across all families
	public int calculateTotalOrphans(Person mom, Person dad) {
		int totalOrphans = 0;

	    for (Family family : families) {
	        ArrayList<Person> parents = family.getParents();
	        ArrayList<Person> members = family.getMembers();

	        boolean hasMartyrParent = false;

	        for (Person parent : parents) {
	            if (parent instanceof Martyr) {
	                hasMartyrParent = true;
	                break;
	            }
	        }

	        if (!hasMartyrParent) {
	            for (Person member : members) {
	                if (member instanceof LivePerson) {
	                    // Check if the LivePerson is not a child of the provided mom and dad
	                    if (!member.equals(mom) && !member.equals(dad)) {
	                        totalOrphans++;
	                    }
	                }
	            }
	        }
	    }

	    return totalOrphans;
	}

	// Method to calculate the total number of live persons across all families
	public int calculateTotalLivePersons() {
		int totalLivePerson = 0;
		for (int i = 0; i < families.size(); i++) {
			Family family = families.get(i);
			for (Person member : family.getMembers()) {
				if (member instanceof LivePerson) {
					totalLivePerson++;
				}
			}
		}
		return totalLivePerson;
	}

	// Method to calculate statistics for a specific family
	public ArrayList<Integer> calculateFamilyStatistics(String familyName) throws Exception {
		// Implementation to calculate specific family statistics
		ArrayList<Integer> statistics = new ArrayList<>();
		Family family = searchByName(familyName);
		if (family != null) {
			int martyrs = 0;
			int orphans = 0;
			int livePersons = family.getMembers().size();
			for (Person person : family.getMembers()) {
				if (person.isMartyr()) {
					martyrs++;
				}
			}
			if (family.getParents().size() < 2) {
				orphans = livePersons;
			}
			statistics.add(martyrs);
			statistics.add(orphans);
			statistics.add(livePersons);
		}
		return statistics;
		
		 
	}

	
	Person mom;
	Person dad;
	public ArrayList<Integer> calculateGlobalStatistics() {
		// Implementation to calculate global statistics
		ArrayList<Integer> list = new ArrayList<>();
		System.out.println("total number of martyr is" + calculateTotalMartyrs());
		System.out.println("total  live person" + calculateTotalLivePersons());
		System.out.println("total orphans is " + calculateTotalOrphans(mom,dad));

		list.add(calculateTotalMartyrs());
		list.add(calculateTotalLivePersons());
		list.add(calculateTotalOrphans(mom,dad));

		return list;

		
		 
	}
	public ArrayList<Family> sortFamiliesByMartyrs() {
        Family family = new Family(""); 
        return family.sortByMartyrs(families);
    }

    
    public ArrayList<Family> sortFamiliesByOrphans() {
        Family family = new Family(""); 
        return family.sortByOrphans(families);
    }

}
