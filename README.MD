How to run the tests
1. Install IntelliJ (Community version will do). It comes with an embedded Maven version, which will help in compiling the project for the first time.
2. Open IntelliJ and import the project from GitHub (or clone the project to your machine and import it from there). Please see the following link for instructions on how to import the project: https://www.jetbrains.com/help/idea/import-project-from-maven-page-1.html#step3
3. Once the project was imported, on the lower right hand side you will see an overlay with an option to 'Auto-Import' the project dependencies. Click on this option.
4. On the right-hand side, click the 'Maven' menu.
5. Simultaneously select (multi-select) the clean and install goals. Also click the 'Toggle 'Skip Tests' Mode'.
6. Run the select goals. This will compile the project.
7. Go to the test class you want to run. 
8. Just run the class as you would any JUnit test class. For example, from the left hand menu, right-click the name of the class you want to run. Select 'Run' from this menu.
9. Analyze the results.