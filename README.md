# MeowMed

## Buildanleitung:

### Vorraussetzung:
Docker oder Docker Desktop muss installiert und gestartet sein.
Unter Linux muss auch Docker-Compose installiert sein.

### Inhalt der docker-compose:
- Projekt von EDA (UI-Port 80, Customer-Port 8071, Policy-Port 8072)
- Projekt von RDA (UI-Port 80, Customer-Port 8081, Policy-Port 8082)
- Swagger Oberfläche auf Port 81

### Erzeugen vom EDA Projekt:
```bash
docker compose --profile eda up -d --build
```
### Erzeugen vom RDA Projekt:
```bash
docker compose --profile rda up -d --build
```

### Häufige Fehler:
"mvnw: Not found" : Zeichenset von der mvnw-Datei ist dann auf CRLF. LF wird benötigt. GitBash öffnen und dos2unix.exe auf alle mvnw-Dateien nutzen ("dos2unix.exe Projektordner/service/mvnw")

## Backlog

| **ID** | **Name**                                                             | **Beschreibung**                                                                                                                                                  | **Priorität** | **Sprint** | **Status** |
| ------ | -------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- | ---------- | ---------- |
| 1      | Policy und Customer Services aufsetzen                               | Damit die Entwicklung starten kann, müssen die Services grundsätzlich aufgesetzt werden                                                                           | Hoch          | 1          | Done       |
| 2      | Grundlegende Funktionalität implementieren                           | Die ersten Methoden, wie eine grundsätzliche Preisberechnung oder erstellen eines Kunden/Vertrages, sollten bei Policy- und Customer Service implementiert werden | Hoch          | 1          | Done       |
| 3      | Basic UI erstellen                                                   | Als Mitarbeiter möchte ich eine grafische Oberfläche haben, damit ich darüber die Software nutzen kann                                                            | Hoch          | 1          | Done       |
| 4      | Grundlegende Preisberechnung erstellen                               | Im PolicyService sollen die Preise grundlegend berechnet werden können. Die Berechnung braucht noch nicht vollständig sein.                                       | Hoch          | 1          | Done       |
| 5      | UI mit dem Backend verknüpfen                                        | Die UI soll mit dem Backend verbunden sein                                                                                                                        | Hoch          | 2          | Done       |
| 6      | Notification Service aufsetzen                                       | Kunden sollen bei Accountkreierung und Vertragsabschluss eine Email mit allen nötigen Daten und mit korrekter Anrede bekomme                                      | Hoch          | 2          | Done       |
| 7      | Notification Service sendet Email                                    | Die Grundlegende Funktion des Notification Service soll implementiert sein. Moritz empfiehlt eine Google anbindung                                                | Hoch          | 2          | Done       |
| 8      | Kommunikation zwischen Services                                      | Damit das MVP vernünftig funktioniert, müssen die Services sich untereinander erreichen können                                                                    | Hoch          | 2          | Done       |
| 9      | Kunde erstellen                                                      | Über das UI sollen neue Kunden erstellt werden können                                                                                                             | Hoch          | 2          | Done       |
| 10     | Vertrag erstellen                                                    | Über die UI sollen Verträge für Kunden angelegt werden können                                                                                                     | Hoch          | 2          | Done       |
| 11     | Webseite gestalten                                                   | Die Webseite sollte etwas dem Mockup ähneln, wenn das MVP stehen soll                                                                                             | Hoch          | 2          | Done       |
| 12     | Preisberechnung abklären                                             | Mit Capgemini in Verbindung setzen und klären, nach was für Bedingungen die Preise ermittelt werden                                                               | Hoch          | 2          | Done       |
| 13     | Team EDA: AQMP Präsi machen                                          | Koschel möchte gerne eine AQMP Präsi. 3-4 schöne Folien                                                                                                           | Mittel        | 3          |            |
| 14     | Kostenfaktor Hund                                                    | Wenn der Besitzer bei Vertragsbeginn einen Hund besitzt, werden 30% des Grundpreises draufgeschlagen.                                                             | Hoch          | 3          |            |
| 15     | Preise korrekt implementieren                                        | Die Vorgegebenen Preise sollen akkurat errechnet werden. Dafür ist die Excel als Orientierungshilfe da.                                                           | Hoch          | 3          |            |
| 16     | Jahresdeckung nachträglich ändern                                    | Die Jahresdeckung von Verträgen soll im nachhinein über die GUI änderbar sein.                                                                                    | Hoch          | 3          | Done       |
| 17     | Altersprüfung des Kunden                                             | Bei Vertragsabschluss soll geprüft werden, ob der Kunde 18 oder älter ist. Sonst kommt kein Vertrag zustande.                                                     | Hoch          | 3          |            |
| 18     | Team UI: Umzug auf das neue Framework                                | Team UI hat sich ein Web-Framework ausgesucht. In diesem Sprint soll das System darauf umziehen                                                                   | Hoch          | 3          | Done       |
| 19     | Team UI: Usability des "Versicherungspreis berechnen" Button erhöhen | Der Button zum berechnen des Versicherungspreises ist nicht sehr intuitiv. Das soll geändert werden                                                               | Hoch          | 3          | Done       |
| 20     | Team UI: Wahl eines Frontend Frameworks                              | Um die aufhübschung zu erleichtern soll ein Frontend Framework zur Hilfe gezogen werden, damit nicht alles per Hand gemacht werden muss                           | Hoch          | 3          | Done       |
| 21     | Team UI: Aufhübschung der UI                                         | Die UI soll etwas aufgehübscht werden, damit sie moderner aussieht                                                                                                | Hoch          | 4          | Done       |
| 22     | Preisberechnung abklären                                             | Mit Capgemini in Verbindung setzen und klären, nach was für Bedingungen die Preise ermittelt werden                                                               | Hoch          | X          |            |
| 23     | Auslieferbares Produkt                                               | Das Produkt soll an den Kunden auslieferbar sein. Dementsprechend soll die Software funktionieren und die Doku soweit fertig sein                                 | Hoch          | 4          |            |
| 24     | MeowMed Logo auf der Webseite                                        | Das Logo soll auf der Webseite erscheinen                                                                                                                         | Mittel        | 4          | Done       |
| 25     | Attribute validieren                                                 | Attribute sollen bei der Eingabe validiert werden. Bei ungültigen Daten kommt kein Vertrag zustande                                                               | Hoch          | 4          |            |
| 26     | Gedanken über Load Balancing                                         | Ein paar Folien zu geplantem Load Balancing sollen gemacht werden, welche dann zum nächsten Sprint umgesetzt werden können                                        | Hoch          | 4          |            |
| 27     | Katzenwesen ändern                                                   | Bei Katzen soll nun auch das Wesen geändert werden                                                                                                                | Hoch          | 4          | Done       |
| 28     | Preisänderung automatisch ansetzen                                   | Wenn sich Preisrelevante Daten ändern, soll der Monatspreis automatisch angepasst werden                                                                          | Hoch          | 4          |            |
| 29     | Arbeitsstatus des Kunden ändern                                      | Beim Kunden soll der Arbeitsstatus änderbar sein                                                                                                                  | Hoch          | 4          | Done       |
| 30     | Kunde bei Preisänderung benachrichtigen                              | Wenn sich der Preis ändert, soll der Kunde benachrichtigt werden.                                                                                                 | Hoch          | 4          |            |
| 31     | Vertrag automatisch kündigen                                         | Wenn nach Datenänderung ein Vertrag ungültig wird (Verspieltes Wesen oder Arbeitsloser Kunde), sollen die betroffenen Verträge gekündigt werden                   | Hoch          | 4          |            |
| 32     | Load Balancing umsetzen                                              | Die Planung für das Load Balancing soll umgesetzt werden.                                                                                                         | Mittel        | 5          |            |
| 33     |                                                                      |                                                                                                                                                                   |               |            |            |
