/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class BookJPA {
    /**
     * You will likely need the entityManager in a great many functions throughout your application.
     * Rather than make this a global variable, we will make it an instance variable within the CarClub
     * class, and create an instance of CarClub in the main.
     */
    private EntityManager entityManager;

    /**
     * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
     * We use it because it is easy to control how much or how little logging gets done without having to
     * go through the application and comment out/uncomment code and run the risk of introducing a bug.
     * Here also, we want to make sure that the one Logger instance is readily available throughout the
     * application, without resorting to creating a global variable.
     */
    private static final Logger LOGGER = Logger.getLogger(BookJPA.class.getName());

    /**
     * The constructor for the CarClub class.  All that it does is stash the provided EntityManager
     * for use later in the application.
     * @param manager    The EntityManager that we will use.
     */
    public BookJPA(EntityManager manager) {
        this.entityManager = manager;
    }

    public static void main(String[] args) {
        LOGGER.fine("Creating EntityManagerFactory and EntityManager");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookJPA");
        EntityManager manager = factory.createEntityManager();
        // Create an instance of Book and store our new EntityManager as an instance variable.
        BookJPA books = new BookJPA(manager);

        LOGGER.fine("Begin of Transaction");
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        List<AuthoringEntities> authorings = new ArrayList<>();
        authorings.add(new AuthoringEntities("Anne Frank", "annefrank@gmail.com", "Individual Author"));
        authorings.add(new AuthoringEntities("Susan Cain", "susancain@gmail.com", "Individual Author"));
        books.createEntity(authorings);
        System.out.println("1. Add new objects");
        System.out.println("2. List all the information about a specific Object:");
        System.out.println("3. Delete a book");
        System.out.println("4. Update a book");
        System.out.println("5. List the the primary key of all rows of: ");

        Scanner input = new Scanner(System.in);
        int choice = Integer.parseInt(input.next());
        int subChoice = 0;
        switch (choice) {
            case 1: {
                tx.begin();
                String name;
                String email;

                System.out.println("1. Add a Writing Group");
                System.out.println("2. Add an Individual Author");
                System.out.println("3. Add an Ad Hoc Team");
                subChoice = Integer.parseInt(input.next());
                switch (subChoice) {
                    case 1:
                        System.out.println("Enter authoring entity name:");
                        name = getUserString();
                        System.out.println("Enter authoring entity email:");
                        email = getUserString();


                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            }

            case 2: {
                System.out.println("1. Add a Writing Group");
                System.out.println("2. Add an Individual Author");
                System.out.println("3. Add an Ad Hoc Team");
                subChoice = Integer.parseInt(input.next());
                switch (subChoice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
                break;
            }

            case 3: {
                System.out.println("Please enter the candidate keys in order to delete a book ");
                // check for candidate keys


            }
            break;

            case 4: {
                System.out.println("Change the book's authoring entities: ");
            }
            break;

            case 5: {
                System.out.println("Show primary key for Publishers");
                System.out.println("Show primary key for Books");
                System.out.println("Show primary key for Authoring Entities");
                subChoice = Integer.parseInt(input.next());
                switch (subChoice) {
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                break;
            }
        }

        List<IndividualAuthors> idvAuthors = new ArrayList<>();
        books.createEntity(idvAuthors);

        List<WritingGroups> writingGrp = new ArrayList<>();
        books.createEntity(writingGrp);

        List<AdHocTeams> adHocTeams = new ArrayList<>();
        books.createEntity(adHocTeams);

        List<AdHocTeamMembers> adHocMems = new ArrayList<>();
        books.createEntity(adHocMems);

        List<Publishers> publishers = new ArrayList<>();
        publishers.add(new Publishers("Penguine House Publisher", "714-666-777", "penguinepublisher@gmail.com"));
        books.createEntity((publishers));

        //books.prompts();
        tx.commit();

        // Create an instance of CarClub and store our new EntityManager as an instance variable.
//      Books carclub = new Books(manager);
//
//
//      // Any changes to the database need to be done within a transaction.
//      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions
//
//      LOGGER.fine("Begin of Transaction");
//      EntityTransaction tx = manager.getTransaction();
//
//      tx.begin();
//      // List of owners that I want to persist.  I could just as easily done this with the seed-data.sql
//      List <Owners> owners = new ArrayList<Owners>();
//      // Load up my List with the Entities that I want to persist.  Note, this does not put them
//      // into the database.
//      owners.add(new Owners("Reese", "Mike", "714-892-5544"));
//      owners.add(new Owners("Leck", "Carl", "714-321-3729"));
//      owners.add(new Owners("Guitierez", "Luis", "562-982-2899"));
//      // Create the list of owners in the database.
//      carclub.createEntity (owners);
//
//      // Commit the changes so that the new data persists and is visible to other users.
//      tx.commit();
        LOGGER.fine("End of Transaction");

    } // End of the main method

    /**
     * Gathers a string from the user.
     * @return the string the user put in
     */
    public static String getUserString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }


    /**
     * Create and persist a list of objects to the database.
     * @param entities   The list of entities to persist.  These can be any object that has been
     *                   properly annotated in JPA and marked as "persistable."  I specifically
     *                   used a Java generic so that I did not have to write this over and over.
     */
    public <E> void createEntity(List <E> entities) {
        for (E next : entities) {
            LOGGER.info("Persisting: " + next);
            // Use the CarClub entityManager instance variable to get our EntityManager.
            this.entityManager.persist(next);
        }

        // The auto generated ID (if present) is not passed in to the constructor since JPA will
        // generate a value.  So the previous for loop will not show a value for the ID.  But
        // now that the Entity has been persisted, JPA has generated the ID and filled that in.
        for (E next : entities) {
            LOGGER.info("Persisted object after flush (non-null id): " + next);
        }
    } // End of createEntity member method
}// End of the getStyle method

