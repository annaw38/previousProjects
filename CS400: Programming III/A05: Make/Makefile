run: App.class
	java App.java

test: TestApp.class
	java -jar ../junit5.jar -cp .:../junit5.jar --select-class=TestApp

TestApp.class: TestApp.java App.class
	javac -cp ../junit5.jar:../ *.java 
	
App.class: App.java
	javac -cp ../junit5.jar:../ *.java

Mercury:
	@echo "Hello Mercury"
