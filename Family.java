package pr1;

import java.util.ArrayList;

public class Family implements Sortable, Cloneable {
	int parentMartyr = 0;
	int parentLive = 0;
	private String familyName;
	private ArrayList<Person> members = new ArrayList<>(); // this arraylist for adding sons and daughters
	private ArrayList<Person> parents = new ArrayList<>(); // this arraylist for adding mom and dad
	Manager manager = new Manager();

	public Family(String familyName) {
		this.familyName = familyName;
		this.members = new ArrayList<>();
		this.parents = new ArrayList<>();
	}

	public boolean addMember(Person member, String roleInFamily) {
		if (roleInFamily.equalsIgnoreCase("mom") || roleInFamily.equalsIgnoreCase("dad")
				|| roleInFamily.equalsIgnoreCase("son") || roleInFamily.equalsIgnoreCase("daughter")) {
			members.add(member);

			return true;
		}
		return false;
	}

	public boolean removeMember(Person member) {
		return members.remove(member);

	}

	public ArrayList<Person> getMembers() {
		return members;

	}

	public void addParent(Person parent) {
		if (parent instanceof Martyr) {
			parentMartyr++;

		}

		else if (parent instanceof LivePerson) {
			parentLive++;
		}

		parents.add(parent);

	}

	public String getFamilyName() {
		return familyName;

	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;

	}

	public boolean removeParent(Person parent) {
		return parents.remove(parent);

	}

	public ArrayList<Person> getParents() {
		return parents;
	}

	@Override
	public String toString() {
		return familyName + members + parents;
	}

	public boolean equals(Object obj) {
		int n1 = 0;
		int n2 = 0;
		if (obj instanceof Family) {
			ArrayList<Person> person = ((Family) obj).getMembers();
			for (int i = 0; i < person.size(); i++) {
				Person p = person.get(i);
				if (p instanceof Martyr) {
					n1++;
				}
			}

		}
		for (int i = 0; i < members.size(); i++) {
			Person p1 = members.get(i);
			if (p1 instanceof Martyr) {
				n2++;
			}
		}
		return n1 == n2;

	}

	public int countMartyrs() {
		int count = 0;
		for (Person member : members) {
			if (member instanceof Martyr) {
				count++;
			}
		}
		return count;
	}

	public int compareTo(Family otherFamily) {
		return this.countOrphans() - otherFamily.countOrphans();
	}

	public int countOrphans() {
		int count = 0;

		boolean hasMartyrParent = false;
		for (Person parent : parents) {
			if (parent instanceof Martyr) {
				hasMartyrParent = true;
				break;
			}
		}

		if (!hasMartyrParent) {
			for (Person member : members) {
				if (member instanceof LivePerson && !parents.contains(member)) {
					count++;
				}
			}
		}

		return count;
	}

	public int compareToMartyrs(Family obj) {
		return (int) (this.countMartyrs() - obj.countMartyrs());

	}

	public int compareToOrphans(Family otherFamily) {
		return this.compareTo(otherFamily);
	}

	public ArrayList<Family> sortByMartyrs(ArrayList<Family> families) {

		for (int i = 1; i < families.size(); i++) {

			Family f = families.get(i);

			int j = i - 1;
			while (j >= 0 && f.compareToMartyrs(families.get(j)) < 0) {

				families.set(j + 1, families.get(j));
				j--;
			}

			families.set(j + 1, f);
		}

		return families;

	}

	public ArrayList<Family> sortByOrphans(ArrayList<Family> families) {
		for (int i = 1; i < families.size(); i++) {
			Family f = families.get(i);
			int j = i - 1;
			while (j >= 0 && f.compareToOrphans(families.get(i)) < 0) {
				families.set(j + 1, families.get(j));
				j--;
			}
			families.set(j + 1, f);
		}
		return families;
	}

}