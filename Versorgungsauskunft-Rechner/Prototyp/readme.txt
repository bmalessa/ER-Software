01.09.2020

In den Sub-Directories befinden sich folgende Artefakte:

Deploy: 
	Die kompilierten Java-Files des Prototyp, gepackt als Java-Archiv-Files. 
	Die 3 Jar-Files entsprechend den Eclipse-Projekten im Sub-Directory "Sourcen".
	Die Jar-Files im Unterordner "Deploy/jars" sind 3th party utilities. Alle diese Jars sind OpenSource-Software.
        Das Batch-File "runApp.bat" kann unter Windows ohne weitere Anpassung zum Starten des Prototyp für den Versorgungsauskunft-Rechner verwendet werden.
	Das für die Ausführung erforderlich Java- Runtime-Environment muss mindest die Version 1.8 haben.
	Default ist das mit eingecheckte JAVA-JRE in der version 1.8.0-131. Sofern ein anderes JRE verwendet werden soll, muss das batch-Skript entsprechend angepasst werden.
	
Java-JRE
	Das Default-JAVA-JRE gegen das die Java-Sourcen kompiliert wurden. Kann auch zur Ausführung des Prototyp verwendet werden. Wenn keine weitere Anpassungen vorgenommen
	werden, sollte das Batch-Skript (s.o) ohne weitere Anpassung zumindest auf einem Windows-Betriebssystem ausgeführt werden können.
	
Sourcen
	Beinhaltet die Java-Sourcen und Eclipse-Projekt-Dateien für den Prototypen des "Versorgungsauskunft-Rechner".
	
Json
	Beinhaltet Beispieldateien zum Einlesen von bereits erstellten Versorgungsauskünfte. Diese können zur Laufzeit in den Versorgungsauskunft-Rechner eingelesen werden.
