REM at minimum a JRE/JDK of version >= 1.8 - download it from "https://jdk.java.net" and point to your local installation
REM or take the default JRE
set JAVA_HOME=.\jre

REM this 3 jar files for Domain, Gui and Customized SWT-Componets must be available in classpath
set PROJECT_JAR_FILES=Gui.jar;Domain.jar;CustomizedSWT.jar
REM this additinal 3 party jar files must be available in classpath
set ADDITIONAL_JAR_FILES=./jars/commons-lang3-3.10.jar;./jars/gson-2.8.6.jar;./jars/org.eclipse.core.commands.jar;./jars/org.eclipse.jface.text.jar;./jars/org.eclipse.jface.jar;./jars/org.eclipse.swt.win32.jar;./jars/org.eclipse.swt.jar;./jars/org.eclipse.equinox.common.jar
REM this is the class with the main methode to start the application
set MAIN_CLASS=de.protin.support.pr.gui.PensionGUI


%JAVA_HOME%\bin\java.exe -Dfile.encoding=Cp1252 -classpath %PROJECT_JAR_FILES%;%ADDITIONAL_JAR_FILES% %MAIN_CLASS%