06.05.2021

Versions-Update. Entspricht dem Stand der ersten ReleaseVersion. Siehe Beitrag im Bürgerportal vom 05.05.2021
Synchronisation mit der Web-Applikation. Die Besoldungstabellen 2021 und 2022 wurden integriert. Für die Prognoseberechnung ist nun neben der automatischen Bestimmung der
anzuwendenden Besoldunbstabelle (basierend auf der Eingaben in den Feldern "Eintritt in den Ruhestand zum" und "Anzuwendendes Recht") auch die expliziten Auswahl aus einer
DropDown-Liste möglich. In dieser Liste werden alle Besoldungstablle angezeigt, die im System vorhanden sind.


16.12.2020

Versions-Update. 
Es wurden kleinere Bugfixes bei der Berechnung des Ruhegehaltsatzes vorgenommen. Die Rundungsvorschriften wurde nochmal geschärft, so dass nun die entsprechenden Vergleichsberechnungnen
mit Versorgungauskünften der BAnstPT zu denselben Ergebnissen beim errechneten Ruhegehaltssatz führen. Einige Rechtschreibfehler wurde berichtigt und der erläuternde Hinweistext in der Sub-Viewwurde
"Grunddaten" sowie in der Versorgungsprognose wurde an einigen Stelle konkretisiert. Außerdem wurde ein Hilfsystem inkl. eines Anwenderhandbuch in die Anwendung integriert. 
Das Hilfesystem ist als eigenständiges OSGi-Bundle implementiert. Die zentral Konfigurationin der Datei "Ruhegehalt-Prognose\configuration\config.ini" eurde entsprechend um dieses Plugin erweitert.
Das Hilfesystem ist im Hauptmenu unter dem Menupunkt "Hilfe" erreichbar.
Die Rich-Client-Version ist damit hinsichtlich der geplanten Feature-Liste inhaltlich vollständig umgesetzt.
Wer nicht die komplette Version neu auschecken möchten, hat auch die Möglichkeit nur die Änderungen in seine bereits lauffähige lokale Installation zu übernehmen.

Dies erfordert dann die Durchführung der folgenden Tätigkeiten:

- Austausch der Datei "Ruhegehalt-Prognose\configuration\config.ini"  
- Austausch aller Dateien im Order ""Ruhegehalt-Prognose\plugins", die mit dem Prefix "de.protin.support.pr" (insgesamt 7 Dateien) beginnen
- Hinzufügen der Datei  "Ruhegehalt-Prognose\plugins\de.protin.support.pr.help_1.0.0.jar" aus dem Github-Repo in die lokale Installation.




17.11.2020

In den Sub-Directories befinden sich folgende Artefakte:

Deploy: 

	In diesem Verzeichnis befinden sich alle erforderlichen Artefakte, um den Versorgungsprognose-Rechner zu starten.
	Die kompilierten OSGi-Bundles des Versorgungsprognose-Rechner sowie die erforderlichen OSGi-Bundles des Eclipse RCP Framework (aka Target-Platform)
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
 
 
 