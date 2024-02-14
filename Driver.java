package pr1;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Driver {

	public static void main(String[] args) throws Exception, IllegalArgumentException {

		String fileName = "file.txt";

		Manager manager = new Manager();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter 1 to read from file or 2 to input from console:");
		int choice = input.nextInt();
		switch (choice) {

		case 1:
			fileReadWriteMenu(manager, input, fileName);
			break;

		case 2:
			// Read input data from console
			readFromConsole(manager, input);
			break;
		default:
			System.out.println("Invalid choice. Exiting...");
			break;
		}

		input.close();
	}

	private static void fileReadWriteMenu(Manager manager, Scanner input, String fileName)
			throws FileNotFoundException {
		int num;
		do {
			// Display menu options for reading or writing to a file
			System.out.println("1-Read from file");
			System.out.println("2-Write to file");
			System.out.println("3-Exit");

			num = input.nextInt();
			input.nextLine(); // Consume newline

			switch (num) {
			case 1:
				readFromFile(fileName, manager);
				break;
			case 2:
				writeToFile(fileName, manager);
				break;
			case 3:
				System.out.println("Exiting file read/write menu.");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		} while (num != 3);
	}

	private static void readFromFile(String fileName, Manager manager) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		File file = new File(fileName);

		int choice;

		do {
			System.out.println("1-Search Family by name");
			System.out.println("2-remove person by ID");
			System.out.println("3-delete family by name");
			System.out.println("4-total martyrs");
			System.out.println("5-total orphans");
			System.out.println("6-exit");

			choice = input.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter family name to search:");
				input.nextLine(); // Consume newline character
				String searchName = input.nextLine();

				boolean found = false;
				try (Scanner innerFileScanner = new Scanner(file)) {
					while (innerFileScanner.hasNextLine()) {
						String line = innerFileScanner.nextLine();
						if (line.contains("Family Name: " + searchName)) {
							found = true;
							break;
						}
					}

					if (!found) {
						throw new FamilyNotFoundException("Family not found in file: " + searchName);
					} else {
						System.out.println("Family found in file: " + searchName);
					}
				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
					throw e;
				} catch (FamilyNotFoundException e) {
					System.out.println("fmaily not found");
				}

				break;

			case 2:
				try {
					System.out.println("Enter member's ID to remove:");
					input.nextLine(); 
					String memberIdToRemove = input.nextLine().trim(); 

					ArrayList<String> lines = new ArrayList<>();
					boolean memberFound = false;

					try (Scanner fileScanner = new Scanner(file)) {
						while (fileScanner.hasNextLine()) {
							String line = fileScanner.nextLine();
							if (!line.contains(memberIdToRemove)) {
								lines.add(line);
							} else {
								memberFound = true;
							}
						}

						if (!memberFound) {
							throw new MemberNotFoundException(
									"Member with ID " + memberIdToRemove + "not found in the file.");
						} else {
							try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
								for (String line : lines) {
									writer.println(line);
								}
								System.out.println("Member with ID " + memberIdToRemove + " removed from the file.");
							} catch (IOException e) {
								System.out.println("Error writing to the file: " );
							}
						}
					} catch (MemberNotFoundException e) {
						System.out.println("member not found");
					}
				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
					throw e;
				}
				break;
			case 3:
				
				 try {
				        System.out.println("Enter family name to delete:");
				        input.nextLine();
				        String familyNameToDelete = input.nextLine().trim();

				        ArrayList<String> familyData = new ArrayList<>();
				        boolean familyFound = false;

				        try (Scanner fileScanner = new Scanner(file)) {
				            while (fileScanner.hasNextLine()) {
				                String line = fileScanner.nextLine();
				                if (line.contains("Family Name: " + familyNameToDelete)) {
				                    familyFound = true;
				                    int familyLines = 5; 
				                    for (int i = 0; i < familyLines; i++) {
				                        if (fileScanner.hasNextLine()) {
				                            line = fileScanner.nextLine();
				                        } else {
				                            break; 
				                        }
				                    }
				                } else {
				                    familyData.add(line);
				                }
				            }

				            if (!familyFound) {
				                throw new FamilyNotFoundException("Family not found in file: " + familyNameToDelete);
				            }
				        } catch (FileNotFoundException e) {
				            System.out.println("File not found: " + fileName);
				            throw e;
				        }

				        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
				            for (String line : familyData) {
				                writer.println(line);
				            }
				            System.out.println("Family '" + familyNameToDelete + "' deleted from the file.");
				        } catch (IOException e) {
				            System.out.println("Error writing to the file: " + e.getMessage());
				        }
				    } catch (FamilyNotFoundException e) {
				        System.out.println(e.getMessage());
				    }
				break;
			case 4:
				int totalMartyrs = manager.getTotalMartyrs();

				try (Scanner fileScanner = new Scanner(file)) {
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						if (line.contains("Martyr: Yes")) {
							manager.incrementTotalMartyrs();
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
					throw e;
				}

				
				totalMartyrs = manager.getTotalMartyrs();
				System.out.println("Total number of martyrs:" + totalMartyrs);

				break;
			case 5:
				int totalOrphans = 0;
				boolean dadMartyr = false;
				boolean momMartyr = false;

				try (Scanner fileScanner = new Scanner(file)) {
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						if (line.contains("Dad -") && line.contains("Martyr: Yes")) {
							dadMartyr = true;
						} else if (line.contains("Mom -") && line.contains("Martyr: Yes")) {
							momMartyr = true;
						} else if (line.startsWith("Family Name:")) {
							if (dadMartyr || momMartyr) {
								totalOrphans += 2; // If both parents are martyrs, add both children as orphans
							}
							dadMartyr = false;
							momMartyr = false;
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
					throw e;
				}

				System.out.println("Total number of orphans in the file: " + totalOrphans);
				break;
			case 6:
				System.out.println("exiting ");
				break;
			default:
				System.out.println("wrong number");
				break;

			}

		} while (choice != 6);
	}

	private static void writeToFile(String fileName, Manager manager) {

		Scanner input = new Scanner(System.in);

		int choice;
		do {
			System.out.println("1-add members");
			System.out.println("2-Exit");

			choice = input.nextInt();
			input.nextLine(); 

			switch (choice) {
			case 1:
				try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
					System.out.println("Enter family name:");
					String familyName = input.nextLine();
					writer.println("Family Name: " + familyName);

					System.out.println("Enter dad name:");
					String dadName = input.nextLine();
					System.out.println("Enter dad ID:");
					String dadId = input.nextLine();
					try {
						System.out.println("Enter dad age:");
						String dadAge = input.nextLine();

						int number = Integer.parseInt(dadAge);
						writer.println("Dad - Name: " + dadName + ", ID: " + dadId + ", Age: " + number);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input! Please enter a valid integer.");
					}

					System.out.println("Enter mom name:");
					String momName = input.nextLine();
					System.out.println("Enter mom ID:");
					String momId = input.nextLine();
					try {
						System.out.println("Enter mom age:");
						String userInput = input.nextLine();

						int number = Integer.parseInt(userInput);
						writer.println("Mom - Name: " + momName + ", ID: " + momId + ", Age: " + number);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input! Please enter a valid integer.");
					}

					System.out.println("Enter son name:");
					String sonName = input.nextLine();
					System.out.println("Enter son ID:");
					String sonId = input.nextLine();
					try {
						System.out.println("Enter son age:");
						String userInput = input.nextLine();

						int number = Integer.parseInt(userInput);
						writer.println("Son - Name: " + sonName + ", ID: " + sonId + ", Age: " + number);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input! Please enter a valid integer.");
					}

					System.out.println("Enter daughter name:");
					String daughterName = input.nextLine();
					System.out.println("Enter daughter ID:");
					String daughterId = input.nextLine();
					try {
						System.out.println("Enter daughter age:");
						String userInput = input.nextLine();

						int number = Integer.parseInt(userInput);
						writer.println("Daughter - Name: " + daughterName + ", ID: " + daughterId + ", Age: " + number);
					} catch (NumberFormatException e) {
						System.out.println("Invalid input! Please enter a valid integer.");
					}

					System.out.println("Information added and written to the file.");
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				System.out.println("exit");

			default:
				System.out.println("Invalid choice. Please choose again.");
				break;
			}
		} while (choice != 2);

	}

	// full menu for anything the user want to choose

	private static void readFromConsole(Manager manager, Scanner input) {

		System.out.println("1-Add Family");
		System.out.println("2-Remove member");
		System.out.println("3-Update Family");
		System.out.println("4-Search Family by name");
		System.out.println("5-Search person by ID");
		System.out.println("6-total martyrs");
		System.out.println("7-total orphans");
		System.out.println("8-Total live persons");
		System.out.println("9-Global statistics");
		System.out.println("10-delete family");
		System.out.println("11-equals");
		System.out.println("12-sort families by martyrs");
		System.out.println("13-sort families by orphans");
		System.out.println("14-exit");
		System.out.println("enter number");

		int num = input.nextInt();
		Family newFamily = null;
		Family updateFamily = null;
		while (num != 14) {
			switch (num) {
			case 1:
				Person person = null;

				System.out.println("enter the  number of families :");

				int numOfFamilies = input.nextInt();
				for (int i = 0; i < numOfFamilies; i++) {

					System.out.println("enter the family name");
					String familyName = input.next();
					Family family = new Family(familyName);
					family.setFamilyName(familyName);

					System.out.println("enter the numbers of members in the family :");

					int members = input.nextInt();

					for (int j = 0; j < members; j++) {
						System.out.println("enter the information:");

						System.out.println("You want to add ?(Mom ,Dad ,Son ,Daughter): ");
						String rolemember = input.next();

						System.out.println("iD:");
						String iD = input.next();

						System.out.println("Name:");

						String name = input.next();

						System.out.println("Age:");

						int age = input.nextInt();
						if (age < 0)
							System.out.println("age can not be negative");

						System.out.println("address:");
						String address = input.next();

						System.out.println("contactInfo");
						String contactinfo = input.next();

						System.out.println("Is " + name + " is still Alive?(Answer :yes/no): ");
						String isAlive = input.next();
						if (isAlive.equalsIgnoreCase("yes")) {
							person = new LivePerson(iD, name, age, address, contactinfo, rolemember, isAlive).deepCopy();
							person.setID(iD);
							person.setName(iD);
							person.setName(name);
							person.setAge(age);

							person.setContactInfo(contactinfo);
							family.addMember(person, rolemember);
							// Deep copy for the Live person class
							LivePerson livePerson = new LivePerson(iD, name, age, address, contactinfo, rolemember,
									isAlive);
							livePerson.setID(iD);
							livePerson.setName(iD);
							livePerson.setName(name);
							livePerson.setAge(age);

							livePerson.setContactInfo(contactinfo);

						} else {

							System.out.println("the date of death");
							String dateOfMartyrdom = input.next();
							System.out.println("the case of death");
							String causesOfDeath = input.next();
							System.out.println("the place of death");
							String placeOfDeath = input.next();
							person = new Martyr(iD, name, age, address, contactinfo, dateOfMartyrdom, causesOfDeath,
									placeOfDeath, rolemember, isAlive).deepCopy();
							// Deep copy for the Martyr class
							Martyr martyr = new Martyr(iD, name, age, address, contactinfo, dateOfMartyrdom,
									causesOfDeath, placeOfDeath, rolemember, isAlive);
							family.addMember(martyr, rolemember);
							martyr.setID(iD);
							martyr.setName(iD);
							martyr.setName(name);
							martyr.setAge(age);
							martyr.setContactInfo(contactinfo);
							martyr.setDateOfMatyrdom(dateOfMartyrdom);
							martyr.setCauseOfDeath(causesOfDeath);
							martyr.setPlaceOfDeath(placeOfDeath);

							if (rolemember.equals("son") || rolemember.equals("daughter") || rolemember.equals("mom")
									|| rolemember.equals("dad")) {
								family.removeMember(person);

							} else {
								family.removeMember(person);
								family.removeParent(person);

							}
						}

					}

					manager.addFamily(family);
					

				}
				break;

			case 2:

				// remove member
				input.nextLine();
				System.out.println("Family name:");
				String familyNameToRemoveFrom = input.nextLine();

				System.out.println("Enter member ID to remove:");
				String memberIDToRemove = input.nextLine();

				Family familyToRemoveFrom = manager.searchByName(familyNameToRemoveFrom);

				if (familyToRemoveFrom != null) {
					Person personToRemove = manager.searchPersonByID(memberIDToRemove);
					if (personToRemove != null) {
						familyToRemoveFrom.removeMember(personToRemove);
						System.out.println("Member removed from the family.");
					} else {
						System.out.println("Member not found.");
					}
				} else {
					System.out.println("Family not found.");
				}

				break;

			case 3:
				// update family
				System.out.println("Family name:");
				String updateFamilyName = input.nextLine();

				Family updateFamily1 = manager.searchByName(updateFamilyName);

				if (updateFamily1 != null) {
					// Perform necessary update actions on the family object
					// Example: updateFamily.updateSomeDetails();
					System.out.println("Family updated successfully.");
				} else {
					System.out.println("Family not found.");
				}
				break;

			case 4:
				// Search family by name
				System.out.println("Enter family name to search:");
				input.nextLine(); // Consume newline
				String searchName = input.nextLine();

				Family foundFamily = manager.searchByName(searchName);

				if (foundFamily != null) {
					System.out.println("Found family: " + foundFamily.getFamilyName());
					System.out.println("Members:");
					for (Person member : foundFamily.getMembers()) {
						System.out.println(member); // Display member details
					}

					System.out.println("Parents:");
					for (Person parent : foundFamily.getParents()) {
						System.out.println(parent); // Display parent details
					}
				} else {
					System.out.println("Family not found.");
				}
				break;
			case 5:
				// Search person by ID
				input.nextLine(); // Consume newline
				System.out.println("Enter person ID:");
				String personIDToSearch = input.nextLine();

				Person foundPerson = manager.searchPersonByID(personIDToSearch);

				if (foundPerson != null) {
					System.out.println("Person found: " + foundPerson);
				} else {
					System.out.println("Person not found.");
				}

				break;
			case 6:
				// total martyrs
				int totalMartyrs = manager.calculateTotalMartyrs();
				System.out.println("Total number of martyrs: " + totalMartyrs);
				break;
			case 7:
				// total orphans
				Person momObject = null;
				Person dadObject = null;
				int totalOrphans = manager.calculateTotalOrphans(momObject, dadObject);
				System.out.println("Total number of orphans: " + totalOrphans);
				break;
			case 8:
				// Total live persons
				int totalLivePersons = manager.calculateTotalLivePersons();
				System.out.println("Total number of live persons: " + totalLivePersons);
				break;
			case 9:
				// Global statistics
				ArrayList<Integer> globalStatistics = manager.calculateGlobalStatistics();
				int totalMartyrs1 = globalStatistics.get(0);
				int totalOrphans1 = globalStatistics.get(1);
				int totalLive1 = globalStatistics.get(2);

				System.out.println("Global Statistics:");
				System.out.println("Total number of martyrs: " + totalMartyrs1);
				System.out.println("Total number of orphans: " + totalOrphans1);
				System.out.println("Total number of live persons: " + totalLive1);
				break;
			case 10:
				System.out.println("Enter family name to delete:");
				input.nextLine(); // Consume newline
				String familyNameToDelete = input.nextLine();

				boolean isDeleted = manager.deleteFamily(familyNameToDelete);

				if (isDeleted) {
					System.out.println("Family " + familyNameToDelete + " deleted successfully.");
				} else {
					System.out.println("Family not found or deletion failed.");
				}

				break;

			case 11:
				Family family1 = null;
				Family family2 = null;

				System.out.println("Enter family name 1:");
				input.nextLine(); // Consume newline
				String familyName1 = input.nextLine();

				family1 = manager.searchByName(familyName1);

				System.out.println("Enter family name 2:");
				String familyName2 = input.nextLine();

				family2 = manager.searchByName(familyName2);

				if (family1 != null && family2 != null) {
					boolean areEqual = family1.equals(family2);
					System.out.println("Families are equal: " + areEqual);
				} else {
					System.out.println("Cannot compare families. One or both families not found.");
				}

				break;
			case 12:
				ArrayList<Family> sortedByMartyrs = manager.sortFamiliesByMartyrs();

				for (int i = 0; i < sortedByMartyrs.size(); i++) {
					Family f = sortedByMartyrs.get(i);
					System.out.println(
							"Family " + (i + 1) + ": " + f.getFamilyName() + " - Martyrs: " + f.countMartyrs());
				}
				break;
			case 13:
				// sort families by orphans
				ArrayList<Family> sortedByOrphans = manager.sortFamiliesByOrphans();
				for (int i = 0; i < sortedByOrphans.size(); i++) {
					Family f = sortedByOrphans.get(i);
					System.out.println(
							"Family " + (i + 1) + ": " + f.getFamilyName() + " - : Orphans:" + f.countMartyrs());
				}

				break;

			default:
				System.out.println("Wrong number");
				break;

			}
			System.out.println("1-Add Family");
			System.out.println("2-Remove member");
			System.out.println("3-Update Family");
			System.out.println("4-Search Family by name");
			System.out.println("5-Search person by ID");
			System.out.println("6-total martyrs");
			System.out.println("7-total orphans");
			System.out.println("8-Total live persons");
			System.out.println("9-Global statistics");
			System.out.println("10-delete family");
			System.out.println("11-equals");
			System.out.println("12-sort families by martyrs");
			System.out.println("13-sort families by orphans");
			System.out.println("14-exit");
			System.out.println("enter number");
			num = input.nextInt();

		}

	}

}