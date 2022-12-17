# **INSTRUCTIONS FOR CARD GAME SIMULATION RUNNING AND TESTING** 

## **INTRODUCTION**
This program is designed to simulate a card game. 
The game works by having n players and n decks, each player has a hand of cards. 
The purpose of the game is to have a hand of cards with the same face value as your player number. 
Each player will sift through their hand and their assigned decks until this requirement is met. The 
first player to declare they have a winning set is the winner of the game. 

Below are instructions on how to execute the simulation and complete various tests we have created with the use of JUnit4. 

## **PREREQUISITES:** 
- Java version 19.0.1
- JUnit4 

## **EXECUTION INSTRUCTIONS**
For the execution of the simulation use the command "java -jar cards.jar" in the directory of the jar file.

## **TESTS EXECUTION INSTRUCTIONS**
- Our tests can be executed using an IDE, we recommend InetlliJ IDEA. 
- Unzip the cardsTest.zip, and open the project in InetlliJ. 
- Make sure JUnit4.*.* is added to the project structure. 
  - To ensure this, follow these steps: 
    - File -> Project Structure -> Libraries -> From Maven -> search junit:junit:4.13.2 -> Apply -> Ok.
- Now that the correct JUnit4 library had been installed, you can right-click on the folder called tests and execute.
- <img src="img/Screenshot 2022-11-20 at 15.28.06.png" width="296.5" height="400.5" />
- Now the all tests for the simulation should execute.

  

