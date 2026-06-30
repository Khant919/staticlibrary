eBookStore (Java SE)

Java SE eBookStore is a small desktop application for managing and browsing an eBook collection. It is implemented in Java (Maven) with a Swing-based GUI and simple MVC-like structure.
Features

    Browse books and view details
    Simple user registration/login flow
    Admin pages for managing books and users
    Example dataset shown from Controllers

Stack / Requirements

    Java JDK 22 (the pom sets compiler source/target to 22)
    Maven 3.6+
    Platform: Desktop (Java SE/Swing)

Quick start (build & run)

    Build with Maven:

    mvn package
mvn -Dexec.mainClass="org.example.Main" exec:java
eBookStore/
  pom.xml
  src/main/java/org/example/
    Main.java                # application entry point
    Controller/*             # controllers that load Models and bookModels
    Model/*                  # data models
    Book/                    # BookPage UI
    homePage/                # EbookGUI home screen
  HomePage.png               # screenshots used below
  homeImg.png
  ...
