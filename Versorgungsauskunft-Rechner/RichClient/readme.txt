17.11.2020

In den Sub-Directories befinden sich folgende Artefakte:

Deploy: 

	In diesem Verzeichnis befinden sich alle erforderlichen Artefakte, um den Versorgungsprognose-Rechner zu starten.
	Die kompilierten OSGi-Bundles des Versorgungsprognose-Rechner sowie die erforderlichen OSGi-Bundles des Eclipse RCP Framework (aka Targte-Platform)
	befinden sich unterhalb des Sub-Directory "Ruhegehalt-Prognose\plugins".
	Die zentral Konfiguration der Anwendung ist in der Datei "Ruhegehalt-Prognose\configuration\config.ini" festgeschrieben. Hier bitte nichts ändern.
	Gestartet wird das Programm über die Datei "rgPrognose.exe". Das für die Ausführung erforderlich Java-Runtime-Environment muss mindest die Version 1.8 haben.
	Per Default wird das JAVA-JRE in der Version 1.8.0-131 verwendet, welches im Pfad "Versorgungsauskunft-Rechner\Java-JRE" des Deployments liegt.
	Sofern ein anderes JRE verwendet werden soll, muss der neue Pfad in der Datei "Ruhegehalt-Prognose\eclipse.ini" entsprechend korrigiert und angepasst werden.
	

	
Json
	Beinhaltet Beispieldateien zum Einlesen von bereits erstellten Versorgungsauskünfte. Diese können zur Laufzeit in den Versorgungsprognose-Rechner eingelesen werden.
	


	
Nach dem ersten	Start der Anwendung werden unterhalb des Verzeichnis "Deploy\Ruhegehalt-Prognose" vom RCP-Framework weitere Verzeichnisse neu angelegt.
Diese Verzeichnisse speichern u.a. den Status der Anwendung, wenn die Anwendung geschlossen wird. Diese Verzeichnisse unterliegen nicht der Versionskontrolle
und können bei Bedarf jederzeit wieder gelöscht werden. Dabei handelt es sich um die folgende Verzeichnisse:

 - Deploy\Ruhegehalt-Prognose\workspace
 - Deploy\Ruhegehalt-Prognose\configuration\org.eclipse.core.runtime
 - Deploy\Ruhegehalt-Prognose\configuration\org.eclipse.e4.ui.css.swt.theme
 - Deploy\Ruhegehalt-Prognose\configuration\org.eclipse.equinox.app
 - Deploy\Ruhegehalt-Prognose\configuration\org.eclipse.osgi
 
 
 