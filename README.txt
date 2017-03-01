Installationsinstruktioner
	Programmet behöver inte installeras, utan körs ifrån terminalen via jar-filer.

Användarinstruktioner
	För tidsregistrering:
    Starta registration-1.0.jar genom terminalen, med java -jar registration-1.0.jar.
    Välj start eller målgång i listan med stationstyper
    Ange vilken station du är på genom att mata in dess ID
    Gå vidare genom att klicka på Börja Registrera
    Skriv in varje deltagares tilldelade nummer allt eftersom de ska registreras vid din station
    I fönstret kan man följa de fem senaste registreringarna
    För att avsluta registreringen är det bara att kryssa ner fönstret
    Filerna som genererats med tidregistreringarna ligger i katalogen registered_times

	Masstart:
    För att registrera en masstart klickar man på knappen "Masstart".
    Alla registrerade deltagare får samma tid när sorteraren läser in det.

	För att generera en resultatlista:
	Starta sorter-1.0.jar genom terminalen, med java -jar sorter-1.0.jar.
    Resultatfilen genereras automatiskt

Filmanifest
	preregtimes - Sparade förregistrerade tider
	registration-1.0.jar - Registreringsprogrammet
	sorter-1.0.jar - Programmet som genererar resultatfiler
	release1b.html - Release notes
	style.css
	
main.java.registration.*
	Innehåller klasser för view och controller för registreringsprogrammet.

main.java.sorter
	Innehåller klasser för inläsning av textfiler från registreringsprogrammet, och klass för generering av resultatfil.
	Modellen representeras av klassen ParticipantList.
	
main.java.util
	Hjälpklass för framför allt konstanter som används i projektet
