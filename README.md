# Software_Enginnering_Group66
> Virtual Bank System for Kids

The objective of our virtual bank system is to educate children about the value of money and the concept of a bank, encouraging them to engage in tasks to earn pocket money, save for goals as well as spending it responsibly.

**Basic Functions:**
- Child End:
	- check account information, including name, role, current account, saving account (and their interest rate), transaction limit.
	- edit basic information.
	- deposit and withdraw money.
	- check transaction record.
	- manage tasks and saving goals.
- Parent End:
	- check account information, including name, role, current child, child's current and saving account, child's account limit.
	- edit basic information.
	- check child's transaction record.
	- set limit for each transaction.
	- set and confirm tasks for child.
	
## How to Run our Application
### Project Information
- Group ID: com.Virtual_Bank_G66
- Artifact ID: Virtual_Bank_G66
- Version: 1.0-SNAPSHOT
- Name: Virtual_Bank_G66
### Build Properties
- Project Encoding: UTF-8
- JUnit Version: 5.9.2
### Dependencies (Libraries)
- JavaFX Controls: org.openjfx:javafx-controls:19.0.2
- JavaFX FXML: org.openjfx:javafx-fxml:19.0.2
- ControlsFX: org.controlsfx:controlsfx:11.1.2
- BootstrapFX Core: org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0
- JUnit Jupiter API: org.junit.jupiter:junit-jupiter-api:${junit.version} (test scope)
- JUnit Jupiter Engine: org.junit.jupiter:junit-jupiter-engine:${junit.version} (test scope)
- Mockito JUnit Jupiter: org.mockito:mockito-junit-jupiter:3.12.4 (test scope)
- Mockito Core: org.mockito:mockito-core:3.12.4 (test scope)
- Mockito Inline: org.mockito:mockito-inline:3.12.4 (test scope)
### Build Plugins (Tools)
- Maven Jar Plugin: org.apache.maven.plugins:maven-jar-plugin:3.2.0
- Maven Compiler Plugin: org.apache.maven.plugins:maven-compiler-plugin:3.11.0
	- Source Version: 19
	- Target Version: 19
- JavaFX Maven Plugin: org.openjfx:javafx-maven-plugin:0.0.8
	- Main Class: com.virtual_bank_g66.virtual_bank_g66/com.virtual_bank_g66.demo.HelloApplication
	- Configuration Items: app, jlinkZipName, jlinkImageName, noManPages, stripDebug, noHeaderFiles

### **Steps**
We recommend you to use `IDEA` as IDE to run our project.
1. Import this project into `IDEA`, and open `Virtual_Bank_G66` folder in it.
2. Make sure your Project SDK configuration is `19`.
   
![image1](https://github.com/Cccc0111/ImageRepo/raw/main/ImageRepo/image/jdkversion.png)  

4. Check your version configuration and current startup class in `Build and run`, making sure that you use `jdk 19` and `HelloApplication` as your startup class.
5. Check your `Working directory`, making sure that `Virtual_Bank_G66` folder is set as your root directory.

![image1](https://github.com/Cccc0111/ImageRepo/raw/main/ImageRepo/image/root.png)  

7. Apply all your changes and click to run our project.
	- Programme Entrance:
	```javascript
	src/main/java/com.virtual_bank_g66.demo/HelloApplication.java
	```
### Running in Command Line
In the following path to find compiled `.class` file: `target/classes`
Run the following command in command line:
```javascript
java HelloApplication
```
## How to Test our Functions
We have already created a child account and a parent account for you to do some tests.<br>
(Parent account has related to the child)
- **Child:** <br>
Name: child1<br>
Password: 123abc
- **Parent:** <br>
Name: parent1<br>
Password: 123abc
## Note
-   Date Format in this program: `"dd-MM-yyyy HH:mm:ss"`
