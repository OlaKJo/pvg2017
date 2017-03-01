## Instruktioner

### För tidsregistrering:
- Starta `pvg17_registration.jar` genom terminalen, med `java -jar pvg17_registration.jar`.
- Välj start eller målgång i listan med stationstyper
- Ange vilken station du är på genom att mata in dess ID
- Gå vidare genom att klicka på *Börja Registrera*
- Skriv in varje deltagares tilldelade nummer allt eftersom de ska registreras vid din station
- I fönstret kan man följa de fem senaste registreringarna
- För att avsluta registreringen är det bara att kryssa ner fönstret
- Filerna som genererats med tidregistreringarna ligger i katalogen `registered_times`

#### Masstart
- För att registrera en masstart skickar man in start med startnummer `-100`.
- Alla registrerade deltagare får samma tid när sorteraren läser in det.
- ***Om man råkar skicka in -100 måste man ändra det manuellt i resultatfilten innan den läses in.***

### För att generera en resultatlista:

- Starta `pvg17_sorter.jar` genom terminalen, med `java -jar pvg17_sorter.jar`.
- Resultatfilen genereras automatiskt och även den är tillgänglig `registered_times`
