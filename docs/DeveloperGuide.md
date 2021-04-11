# Developer Guide
## Table of Contents

1. [Preface](#1-preface)
   
2. [How to use this document](#2-how-to-use-this-document)
   
3. [Setting up](#3-setting-up)
    
    3.1 [Prerequisites](#31-prerequisites)
   
    3.2 [Setting up the project in your computer](#32-setting-up-the-project-in-your-computer)
   
    3.3 [Verifying the setup](#33-verifying-the-setup)
    3.4 [Configure coding style](#34-configure-the-coding-style)
   
4. [Design](#4-design)
    
    4.1 [Architecture: High Level View](#41-architecture-high-level-view)
    
    4.2 [UI component](#42-ui-component)
    
    4.3 [Logic component](#43-logic-component)
    
    4.4 [Model component](#44-model-componenet)
    
    4.5 [Sorter component](#45-sorter-component)
    
    4.6 [Storage component](#46-storage-component)
    
5. [Implementation](#5-implementation)

    5.1 [Mode Switch Feature](#51-mode-switch-feature)
   
    5.2 [Review Mode](#52-review-Mode)

    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.1 [Add a Review Feature](#521-add-a-review-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.2 [List Reviews Feature](#522-list-reviews-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.3 [Sort Reviews Feature](#523-sort-reviews-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.4 [View a Review Feature](#524-view-a-review-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.5 [Edit a Review Feature](#525-edit-a-review-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.2.6 [Delete a Review Feature](#526-delete-a-review-feature)
   
   5.3 [Recommendation Mode](#53-recommendation-mode)
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.3.1 [Add a Recommendation Feature](#531-add-a-recommendation-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.3.2 [List Recommendation Feature](#532-list-recommendation-feature) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.3.3 [Edit a Recommendation Feature](#533-edit-a-recommendation-feature) 
   
    5.4 [Storage](#54-storage)
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.4.1 [Storage Format](#541-storage-format) 
   
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5.4.2 [Implementation](#542-implementation) 
   
    5.5 [Error handling](#55-error-handling) 
   
    5.6 [Personalised Messages](#56-personalised-messages) 
   
6. [Planned Features](#6-planned-features) 
   
7. [Documentation](#7-documentation) 
   
    7.1 [Setting up and maintaining the project website](#71-setting-up-and-maintaining-the-project-website) 
   
    7.2 [Style guidance](#72-style-guidance) 
    
    7.3 [Diagrams](#73-diagrams)
    
8. [Testing](#8-testing) 

    8.1 [Running tests](#81-running-tests) 
    
    8.2 [Types of tests](#82-types-of-tests) 
    
9. [Appendix](#appendix) 
    
    Appendix A: [Product Scope](#appendix-a-product-scope) 
       
    Appendix B: [User Stores](#appendix-b-user-stories)
    
    Appendix C: [Use Cases](#appendix-c-use-cases) 
       
    Appendix D: [Non-Functional Requirements](#appendix-d-non-functional-requirements)
       
    Appendix E: [Glossary](#appendix-e-glossary) 
       
    Appendix F: [Instructions for manual testing](#appendix-e-glossary)
   
## 1. Preface
Connoisseur is a desktop application for managing and storing a list of personal reviews on experiences, and a list of 
recommendations to try next.

The Developer Guide for Connoisseur v2.1 is designed for developers intending to improve Connoisseur, by fixing bugs, 
or perhaps adding entirely new features. It explains how the project is set up, the architecture used, and the code 
style you should adopt when contributing code to the project.

## 2. How to use this document

//TODO//

## 3. Setting up
The following section describes how to set up the coding environment on your own computer, in order to start writing 
code to improve Connoisseur.

### 3.1 Prerequisites
1. JDK 11 <br>
   [Download JDK 11](#https://www.oracle.com/sg/java/technologies/javase-jdk11-downloads.html)
2.  *Recommended integrated development environment for coding* : IntelliJ IDEA<br>
   [Download IntelliJ IDEA](#https://www.jetbrains.com/idea/)
   
### 3.2 Setting up the project in your computer
><p>&#10071 Follow the steps in the following guide precisely. Things will not work out if you deviate in some steps.

1. **Fork** this repo, and **clone** the fork into your computer.
2. Open IntelliJ (if you are not in the welcome screen, click **`File`** > **`Close Project`** to close the existing project dialog first).
3. Set up the correct JDK version for Gradle  
   a. Click **`Configure`** > **`Project Defaults`** > **`Project Structure`**  
   b. Click **`New...`** and find the directory of the JDK.
4. Click **`Import Project`**.
5. Locate the **`build.gradle`** file and select it. Click **`OK`**.
6. Click **`Open as Project`**.
7. Click **`OK`** to accept the default settings.

### 3.3 Verifying the setup

### 3.4 Configure Coding style
If using IDEA, follow the guide [[se-edu/guides] IDEA: Configuring the code style](https://se-education.org/guides/tutorials/intellijCodeStyle.html)
to set up IDEA’s coding style to match ours.

>Optionally, you can follow the guide [[se-edu/guides] Using Checkstyle](https://se-education.org/guides/tutorials/checkstyle.html)
>to find how to use the CheckStyle within IDEA e.g., to report problems as you write code.

## 4. Design
The following section describes the design and implementation of the product. We use UML diagrams and code snippets
to explain some aspects of the code. If you are unfamiliar with UML, the diagrams should still be fairly
understandable. However, you may wish to consult [[CS2113/T] Modeling](https://nus-cs2113-ay2021s1.github.io/website/se-book-adapted/chapters/modeling.html) for a quick introduction to UML.

### 4.1 Architecture: High Level View

**How the architecture components interact with each other**

The following Figure 1, provides a rough overview of how **Connoisseur** is built.<br>

![Architecture.png](./diagrams/Architecture.png)<br>
Figure 1. Architecture of Connoisseur <br>

As shown in Figure 1, the user interacts with `UI`, which takes in commands and displays output to the user. 

The main `Connoisseur` class initializes the other components in the application. It passes the input received from the user to the logic component, which consists of `Parser` and `Commands`. 

`Parser` will decipher the input and make the corresponding method calls in `Commands`. Depending on whether Connoisseur is in review or recommendation mode, the `Commands` class will execute the respective commands in the `ReviewList` and `RecommendationList` classes of the `Model` component. 

In the case of storing data, `Commands` will interact directly with the `Storage` class to save the data. 

The `ReviewList` class has sorting functions which requires `Sorter`. All the reviews will be passed to `Sorter` to be sorted, which then returns the sorted reviews back to `ReviewList`. 

`RecommendationList` also interacts with `ReviewList` for converting Recomendations to Reviews. 

### 4.2 UI component

The UI component of Connoisseur consists of the classes `UI` and `Messages`.
It is instantiated in the connoisseur() method and serves two main purposes:
* Read user input from the console.
* Print program output to the console. 

The *ui.readCommand()* method reads the input which is then passed on to the `Logic` component. 

The *ui.println(output)* method prints output to the console. Default output messages are stored as static String constants in the `Messages` class, while commonly used output messages are made methods by themselves. *ui.printGreeting()* is one such method and is called to display the welcome message to the user. 


### 4.3. Logic component

![LogicComponent.png](./diagrams/LogicComponent.png)<br>
Figure 2. Logic Component of Connoisseur <br>

The Logic component of Connoisseur consists of the classes `Parser` and `Commands`. 
It is instantiated in the connoisseur() method and serves to translate user input into commands which are recognised by the application. 

The *determineCommand()* method of the `Parser` class deciphers the command word of the input, calling the respective command's method in `Commands`. 

Each method in `Commands` will then check the arguments provided with the commands to make sure that they are valid, before executing the command in `Storage`, `ReviewList` or `RecommendationList`. 


### 4.4. Model component

![ModelComponent.png](./diagrams/ModelComponent.png)<br>
Figure 2. Model Component of Connoisseur <br>

The Model component of Connoisseur consists of the classes `ReviewList` and `RecommendationList`. 
It is instantiated in the connoissuer() method and serves to store data as the program runs. 

Both the classes in the Model component contain methods which modify the content of their respective ArrayLists, which consist of either Reviews or Recommendations. 

### 4.5. Sorter component

![SorterComponent.png](./diagrams/SorterComponent.png)<br>
Figure 3. Sorter Component of Connoisseur <br>

The Sorter component is a separate component which serves to sort the reviews based on a few sorting methods. 

When the *sortReviews()* method is called in the `ReviewList`, a sortMethod parameter is passed together with it to the `Sorter`, which then determines which of the Sorts to sort the reviews by. If the sortMethod parameter is empty, `Sorter` will use the default sortMethod saved. The sorted reviews are then passed back to `ReviewList`. 

### 4.6. Storage component

The Storage component serves to implement the storage functions of Connoisseur. It saves and loads data represented as a JSON file in the *./data* folder so that data can be retained after exiting Connoisseur. 

On startup, Connoisseur checks if there is a *connoisseur.json* file in the *./data* folder. If they exist, the data will be loaded via the *loadConnoisseurData()* method and used to initialize classes in the `Model` component. If the file and folder do not exist, they will be created and new instances of the classes in the `Model` component will be initialized instead. 

Before exiting, Connoissuer will save the data from the `Model` component and write them to *connoisseur.json* located in the *./data* folder. 

## 5. Implementation
### 5.1 Mode Switch Feature
### 5.2 Review Mode
### 5.2.1 Add a Review Feature
### 5.2.2 List Reviews Feature 
### 5.2.3 Sort Reviews Feature
### 5.2.4 View a Review Feature
### 5.2.5 Edit a Review Feature
###5.2.6 Delete a Review Feature


### 5.3 Recommendation Mode
This section provides details on the implementation of the various commands that occurs in the recommendation mode.
This mode allows users to keep a list of recommendations that they have not tried/completed.
This mode implements the following features:

*`add`/`new` - Add a Recommendation
*`list` - List Recommendations
*`edit [TITLE_OF_RECOMMENDATION]` - Edit a Recommendation
*`delete [TITLE_OF_RECOMMENDATION]` - Delete a Recommendation
*`done [TITLE_OF_RECOMMENDATION]` - Review a Recommendation
### 5.3.1 Add a Recommendation Feature
This feature allows user to add a recommendation for any of the activities that they have not completed.

<span>&#10071;</span> Title of a new recommendation cannot exist in current list of reviews. An error message would be printed out.

The mechanism to add a recommendation is facilitated by the `RecommendationList` class. The user is able to add in a new recommendation using `new` or `add` command.

The following is the Sequence diagram to `add a recommendation`.

![add reco seq](./diagrams/Add_Reco_Sequence_Diagram.png)
<p align="center">Figure !!. Sequence Diagram for add recommendations</p>

A general explanation of add recommendation works:
1. `Ui` will read in user command, calls `Paser#determineCommand()` to determine the input.

2. `Paser` calls for `Commands#add` if input enter is `new` or `add` command.

3. `Commands` calls for `RecommendationList#addReccomendation` to prompt and read respective information such as  `title`, `category`, `price range`,`recommendedby` and `location`.
   1.`title` - string with 20char limit, that will call [`RecommendationList#checkAndPrintDuplicateRecommendation`]() to check for duplicates in both ArrayLists `reviews` and `recommendations`.
   2. `category`, `recommendedby` and `location` - string with 15char limit, that cannot be whitespace or null.
   3. `price range`- string, that will call `RecommendationList#checkPriceValidity` and restrict it to 2 decimal places.

4. The violation of any constraints for each attribute will print an error message from `ui` and be promoted to re-enter a valid input.

5. When all fields are valid, fields are added into a `Recommendation` Class.

6. The new `Recommendation` is added into an ArrayList `recommendations`.

### 5.3.2 List Recommendation Feature
This feature prints out `title`, `category`, `price range`,`recommendedby` and `location` String attributes for each `Recommendation` class in the ArrayList `recommendations`.

The mechanism to list recommendations is facilitated by the `RecommendationList` class. The user is able to list all recommendations using `list` command.

The following is the Sequence diagram to `list a recommendation`.
![List_Reco_seq](./diagrams/List_Reco_Sequence_Diagram.png)

A general explanation of how this feature works:

1. `Commands` calls `RecommendationList#listRecommendations` to retrieve `Recommendation` in `recommendations`
2. `RecommendationList` calls itself `displayRecommendations` to iterate through and print `Recommendation` in `recommendations`.

### 5.3.3 Edit a Recommendation Feature
This feature allows the user to change the fields in a specific `Recommendation` Class in the ArrayList `recommendations`.

The mechanism to edit a recommendation is facilitated by the `RecommendationList` class. The user is able to edit in an existing recommendation using `edit [TITLE]` command.

The following is the Sequence diagram to `edit a recommendation`.
![Edit_Reco_seq](./diagrams/Edit_Reco_Sequence_Diagram.png)
 
A general explaination of how this feature works:

1. `Commands` will call `RecommendationList#editRecommendation` to determine which `Recommendation` from the ArrayList `recommendatios` should be editied.

2. `RecommendationList` then calls itself, `editRecommendationFields`.
Similar to [adding a recommendation](#531-add-a-recommendation-feature), edit will take in either `title`, `category`, `price range`,`recommendedby` and `location` String attributes for that specific `Recommendation` class and replace previous String stored with new user input.
After checking for duplicates, the violation of any constraints for each attribute will print an error message from `ui` and prompt for a valid input.

### 5.3.4 Delete a Recommendation Feature
This feature allows the user to remove a specific `Recommendation` from the ArrayList `recommendations`.

The mechanism to remove a recommendation is facilitated by the `RecommendationList` class. The user is able to delete in an existing recommendation using `delete [TITLE]` command.

The following is the Sequence diagram to `delete a recommendation`.
![Delete_Reco_seq](./diagrams/Delete_Reco_Sequence_Diagram.png)

A general explanation of how this feature works:

1. `Commands` calls for `RecommendationList#deleteRecommendation`, Connoisseur will check if the title given exists.
2. If title exists, Connoisseur will remove it from the ArrayList `recommendations`.

### 5.3.5 Review a Recommendation Feature
This feature allows the user to change a specific `Recommendation` to a `Review` class, removing it from the ArrayList `recommendations` and adding it to the ArrayList `reviws`.

The mechanism to review a recommendation is facilitated by the `RecommendationList` class. The user is able to review in an existing recommendation using `done [TITLE]` command.

The following is the Sequence diagram to `review a recommendation`.
![Review_a_Reco_seq](./diagrams/Done_Sequence_Diagram.png)

A general explanation of how this feature works:

1. `Commands` calls for `RecommendationList#convertRecommendation`, which retrieves fields from the `Recommendation` that is required by and creates a new `Review` Object.
2. `RecommendationList` then calls for `ReviewList#receiveConvert` with the new `Review` and inserts it into the ArrayList `reviews`.
3. `RecommendationList#convertRecommendation` continues with the chosen `Recommendation` object and removes it from the ArrayList `recommendations`.

### 5.4 Storage
The `Storage` class is responsible for loading data from memory and saving data to memory. The location of this data can be found at *./data/connoisseur.json* and serves to retain all the information of the user after the application has exited. Other than the reviews and recommendations, it also saves the preferences of the user such as the sorting method as well as the display type. 
### 5.4.1 Storage Format
The information is saved as a JSON Object in the data file. It consists of two strings, the sort method and display type, as well as two JSON Arrays which hold the reviews and recommendations. 
To facilitate easier conversion between the raw data and JSON object, a `ConnoisseurData` class was created to store all the data as a single object before conversion. 
### 5.4.2 Implementation
The following is a Sequence diagram to illustrate how Connoisseur saves data on exit. 
![saving_sequence](./diagrams/Save_Sequence_Diagram.png)

1. When the `exit()` method is called, `saveConnoisseurData()` will be called to save the data before exiting. 
2. `Storage#saveReviews` is called to convert the reviews to a JSON Array. 
3. `Storage#saveRecommendations` is called to convert the recommendations to a JSON Array. 
4. These are then combined with the sort method and display type and written to *connoisseur.json*. 
5. If there is an error writing to the file, an exception will be raised and the user will be notified. 
6. Finally, the exit message will be printed and connoisseur will exit. 


### 5.5 Error handling
#### Invalid Input Format
#### Invalid File
### 5.6 Personalised Messages
## 6. Planned Features
## 7. Documentation
### 7.1 Setting up and maintaining the project website
### 7.2 Style guidance
### 7.3 Diagrams
## 8. Testing
### 8.1 Running tests
### 8.2 Types of tests
## Appendix
### Appendix A: Product Scope
### Appendix B: User Stories
|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v1.0|user|see the number of recommendations I have|keep track of the number of reviews I've made|
|v1.0|user|be able to save my previous recommendations|refer to the old entries that I have|
|v1.0|busy user|be able to do quick ratings|save time|
|v1.0|user|delete selected items that I no longer wish to recommend|edit my list according to my liking|
|v1.0|busy user|have a template to guide my reviews|input my reviews quickly|
|v2.0|indecisive user|change my review and opinnions on things|record my opinions accurately at all times|
|v2.0|forgetful user|be prompted of an existing review|avoid duplicates in my list|
|v2.0|lazy user|have my sorting preferences saved|avoid having to input my preferred sorting method all the time|
### Appendix C: Use Cases
### Appendix D: Non-Functional Requirements
### Appendix E: Glossary
### Appendix F: Instructions for manual testing