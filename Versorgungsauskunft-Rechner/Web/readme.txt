17.03.2021


Deploy: 

		In diesem Verzeichnis befinden sich die WAR-Archive der verschiedenen Versionen des Versorgungsprognose-Rechner. 
		Diese WAR-Archiv beinhaltet alles, was für die Web-Version des Versorgungsprognose-Rechner benötigt wird.
		Diese Webapplikation basieren auf dem JEE-Standard und kann in einem JEE kompatiblen Web-Conatiner deployed und ausgeführt werden.
		
	
apache-tomcat-8.0.36
	
		Achtung: Dieses Verzeichnis ist nur für das Betriebssystem Windows relevant. Andere Betriebssysteme (Linux, Unix, MacOS etc.) werden nicht unterstützt.
		
		In diesem Verzeichnis befindet sich die Version 8.0.36 des Apache Tomcat Server für das Windows-Betriebssystem. 
		Dieser Server kann u.a. JEE konforme WAR-Archive verwalten und ausführen.
		Der Tomcat-Server ist in der Programmiersprache Java geschrieben und benötigt dementsprechend eine Java-Runtime. Die im Github-Repo hinterlegte Java-Runtme kann dafür verwendet werden.
		In der Datei "Web\apache-tomcat-8.0.36\bin\setenv.bat" wird mittels relativen Pfad auf diese Java-Runtime verwiesen.
		Der Tomcat-Server kann mittels Batch-Skript "Web\apache-tomcat-8.0.36\bin\startup.bat" gestartet werden. 
		Ein Stop des Server kann durch Schliessen (<STRTG + C> )der geöffneter DOS-Console(n) erfolgen.
		Die aktuelle Web-Version des Versorgungsprognose-Rechner ist im Unterordner "apache-tomcat-8.0.36\webapps" als WAR-Archiv (demo.war) abgelegt.
		Beim Starten des Server entpackt sich diese Datei und ein neues Unterzeichnis "demo" wird angelegt.
		Gleichzeitig wird im Unterordner "apache-tomcat-8.0.36\work\Catalina\localhost" das Verzeichnis "demo" neu angelegt. 
		In diesen Unterorder werden nur einmal neu angelegt und können bei manuell Bedarf gelöscht werden. Beim nächsten Neustart werden sie dann wieder neu angelegt, sofern die datei "demo.war" noch
		im Unterordner "apache-tomcat-8.0.36\webapps" vorhanden ist.
		

		Wenn der Tomcat-Server erfolgreich gestartet wurde, kann im Browser überprüft werden, ob der Server nun HTTP-Request verarbeiten kann. 
		Per Default können nur HTTP-Request vom lokalen Rechner verarbeitet werden.
		HTTP-Request von anderen Rechner im lokalen Netzwerk können nur nach Anpassung der Configuration verarbeitet werden.
		Per Default-Einstellungen kann der Tomcat-Server nur HTTP-Request verarbeiten, die über den Port 8080 eingehen.


Mit dem Aufruf der URL "http://localhost:8080" kann getestet werden, ob der Tomcat-Server erfolgreich gestartet wurde und nun HTTP-Reuqest verarbeiten kann.
Die Web-Applikation des Versorgungsprognose-Rechner wurde unter dem sog. Web-Context "demo" deployed. Für den Aufruf des Prognose-Rechner im Browser muss daher folgende URL im Browser aufgerufen werden.

http://localhost:8080/demo/versorgungsprognose



	

 
 
 