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

        List<IndividualAuthors> idvAuthors = new ArrayList<>();
        books.createEntity(idvAuthors);

        List<WritingGroups> writingGrp = new ArrayList<>();
        books.createEntity(writingGrp);

        List<AdHocTeams> adHocTeams = new ArrayList<>();
        books.createEntity(adHocTeams);

        List<AdHocTeamMembers> adHocMems = new ArrayList<>();
        books.createEntity(adHocMems);

        List<Publishers> publishers = new ArrayList<>();
        publishers.add(new Publishers("Penguin House Publisher", "714-666-777", "penguinepublisher@gmail.com"));
        books.createEntity((publishers));

        List<Books> bookPhys = new ArrayList<>();
        books.createEntity(bookPhys);
        tx.commit();

        //Prompt Menu
        System.out.println("1. Add new objects");
        System.out.println("2. List all the information about a specific Object:");
        System.out.println("3. Delete a book");
        System.out.println("4. Update a book");
        System.out.println("5. List the the primary key of all rows of: ");

        Scanner input = new Scanner(System.in);
        int choice = Integer.parseInt(input.next());
        int subChoice = 0;
        switch (choice) {
            // Add a new object to the db
            case 1:
                tx.begin();
                System.out.println("Select a new object type to create:\n1. Authoring Entity\n2. Publisher\n3. Book");
                int typeChoice = Integer.parseInt(getUserString());

                // Creating a new authoring entity
                if (typeChoice == 1) {
                    // Variables to choose an authoring entity/type of entity
                    String email;
                    String name;
                    String type = "null";
                    int typeInt = 0;
                    // selecting the type
                    while (typeInt < 1 || typeInt > 3) {
                        System.out.println("Select authoring entity type: \n1. Individual Author\n2. Ad Hoc Team\n3. Writing Group");
                        typeInt = Integer.parseInt(getUserString());
                        switch (typeInt) {
                            case 1:
                                type = "Individual Author";
                                break;
                            case 2:
                                type = "Ad Hoc Team";
                                break;
                            case 3:
                                type = "Writing Group";
                                break;
                            default:
                                System.out.println("invalid input");
                                break;
                        }
                    }
                    System.out.println("Enter authoring entity name:");
                    name = getUserString();
                    System.out.println("Enter authoring entity email:");
                    email = getUserString();
                    if (type.equals("Individual Author")) {
                        authorings.add(new IndividualAuthors(name, email));
                    } else if (type.equals("Ad Hoc Team")) {
                        authorings.add(new AdHocTeams(name, email));
                    } else if (type.equals("Writing Group")) {
                        System.out.println("Enter the name of the head writer.");
                        String headWriter = getUserString();
                        System.out.println ("Enter the year the writing group was formed:");
                        int yearFormed = Integer.parseInt(getUserString());
                        authorings.add(new WritingGroups(name, email, headWriter, yearFormed));
                    }
                    books.createEntity (authorings); // persisting new authoring entity
                }
                // New publisher
                else if (typeChoice == 2) {
                    // Variables for new publisher.
                    String name;
                    String email;
                    String phone;
                    // Gathering variables.
                    System.out.println("Enter the name of the publisher:");
                    name = getUserString();
                    System.out.println("Enter the email of the publisher:");
                    email = getUserString();
                    System.out.println("Enter the phone number of the publisher:");
                    phone = getUserString();

                    publishers.add(new Publishers(name, phone, email));
                    books.createEntity(publishers);
                }
                // New book.
                else if (typeChoice == 3) {
                    // Variables to use in initializing our new book.
                    Publishers bookPublisher = new Publishers();
                    AuthoringEntities author = new AuthoringEntities();
                    String title;
                    String publisherName;
                    String authorEmail;
                    int isbn;
                    int yearPublished;
                    // Gathering variables.
                    System.out.println("Enter the book title:");
                    title = getUserString();
                    System.out.println("Enter the book isbn:");
                    isbn = Integer.parseInt(getUserString());
                    System.out.println("Enter the year published:");
                    yearPublished = Integer.parseInt(getUserString());
                    System.out.println("LIST OF PUBLISHERS:");
                    for (Publishers p : publishers) {
                        System.out.println(p);
                    }
                    System.out.println("Select the publisher's name:");
                    publisherName = getUserString();
                    for (Publishers p : publishers) {
                        if (p.getName().equals(publisherName)) {
                            bookPublisher = p;
                        }
                    }
                    System.out.println("LIST OF AUTHORING ENTITIES:");
                    for (AuthoringEntities a : authorings) {
                        System.out.println(a.toString());
                    }
                    System.out.println("Select the author's email");
                    authorEmail = getUserString();
                    for (AuthoringEntities a : authorings) {
                        if (a.getEmail().equals(authorEmail)) {
                            author = a;
                        }
                    }
                    bookPhys.add(new Books(bookPublisher, author, isbn, title, yearPublished));
                    books.createEntity(bookPhys);
                } else {
                    System.out.println("Invalid input.");
                }
                tx.commit();
                break;

            //Display Object's info
            case 2: {
                System.out.println("1. Display all information for a specific Publisher.\n" +
                        "2. Display all information for a specific Book.\n" +
                        "3. Display all information for a specific Writing Group.\n" +
                        "4. Finish");
                Scanner in2 = new Scanner(System.in);
                int choice2 = Integer.parseInt(in2.next());
                Scanner in3 = new Scanner(System.in);
                switch(choice2) {
                    case 1: //Display information for specific publisher
                        System.out.println("Select a publisher by entering their name:");
                        String name = in3.nextLine();
                        for (Publishers p : publishers) {
                            if (p.getName().equals(name)) {
                                System.out.println(p);
                            }
                        }
                        break;
                    case 2: //Display information for specific book
                        System.out.println("Select a book by entering its isbn:");
                        int isbn = in3.nextInt();
                        for (Books b : bookPhys) {
                            if (b.getIsbn() == isbn) {
                                System.out.println(b);
                            }
                        }
                        break;
                }
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
                        List<Publishers> publishersKeys= manager.createQuery("SELECT p FROM Publishers p", Publishers.class).getResultList();
                        for(int i = 0; i < publishersKeys.size(); i++)
                        {
                            System.out.println("Publisher's name " + publishersKeys.get(i).getName());
                        }
                        break;
                    case 2:
                        List<Books> booksKeys= manager.createQuery("SELECT b FROM Books b", Books.class).getResultList();
                        for(int i = 0; i < booksKeys.size(); i++)
                        {
                            System.out.println("Title" + booksKeys.get(i).getTitle() + ", ISBN: " + booksKeys.get(i).getIsbn());
                        }
                        break;
                    case 3: {
                        List<AuthoringEntities> authoringKeys= manager.createQuery("SELECT ent FROM AuthoringEntities ent", AuthoringEntities.class).getResultList();
                        for(int i = 0; i < authoringKeys.size(); i++)
                        {
                            System.out.println("Authoring Entities Email " + authoringKeys.get(i).getEmail());
                        }
                    }
                    break;
                }
                break;
            }
        }


        //books.prompts();

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

