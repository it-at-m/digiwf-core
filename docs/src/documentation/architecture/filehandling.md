# Umgang mit Dateien

Der Umgang mit Dateien stellt eine Prozess- und Integrationsplattform naturgemäß vor Herausforderungen. Dateien
können sehr groß sein, oder in großer Anzahl auftreten und stellen somit ein potenzielles Ressourcenproblem dar.
Deshalb werden bei DigiWf grundsätzlich keine Dateien in den Speicher geladen oder durch Prozess geschleift. Das
File Handling findet in den Integrations Layern (entweder GUI oder Backend statt).

![Es sind die drei Schichten GUI Integration, DigiWF Core und Application Integration Layer dargestellt.
Dort ist eingezeichnet, dass nur in den beiden Integrationsschichten File Handling stattfindet.](~@source/images/platform/architecture/filehandling/digiwf_file_handling_in_integration_layers.png)

Ein grundlegendes Element bei der Behandlung von Dateien (unabhängig davon, ob sie über die GUI oder angebundene
Backend Komponenten in das System gelangen) ist der Datei Speicher. Im Fall von DigiWF ist das ein `S3-Service`.
Hier kann eine Cloudlösung wie AWS oder ein on prem Dienst verwendet werden. Die Kommunikation mit dem Dienst an
sich wird über eine generisch gültige Schnittstelle abstrahiert.

Grundsätzlich ist es möglich (und bei mehrfacher Nutzung der Plattform empfehlenswert), mehr als einen `S3 Bucket`
als Dateispeicher bereitzustellen. Allerdings nicht im selben `S3 Service` - hier gibt es eine 1:1 Beziehung zwischen
Service und `S3 Bucket`. Trotzdem kann - je nach
Fachlichkeit - hier nach Domäne, Prozess oder
Abteilung ein eigener `S3
Bucket` mit einem eigenen `S3 Service` an die Plattform angebunden werden. Das heißt bei einer größeren Installation
(beispielsweise Unternehmensweit) wird man in der Regel n `S3 Services` angebunden haben.


## Datei Handling im Application Integration Layer
Klassische Fälle, in denen Dateien im `Application Integration Layer` behandelt werden müssen, sind:
- E-Mail (mit Anhängen)
- Datei und Aktenablage in einem Document Management System
- Erzeugen von Dateien, wie beispielsweise PDF Generierung

### Eingehende Dateien

![Es wird gezeigt, wie die Datei Behandlung bei eingehenden Dateien funktioniert.](~@source/images/platform/architecture/filehandling/digiwf_incoming_files.png)

In der Abbildung oben ist zu sehen, wie mit eingehenden Dateien umgegangen wird.

1. Eine Datei wird erzeugt oder von außen empfangen.
2. Um die Dateien zu speichern, wird eine `presigned URL` [^1] am `S3 Service` für die Speicherung abgefragt und
   erzeugt. Eine `presigned
   URL` ist eine zeitlich begrenzt gültige URL, für eine bestimmte Operation (z.B. `POST`, `PUT`, usw.) die verwendet
   werden kann um Dateien direkt an einen `S3 Bucket`zu schicken, ohne eingeloggt sein zu müssen. Wie lange eine solche URL gültig kann eingestellt werden. Für den
   hier dargestellten Anwendungsfall funktionieren auch sehr kurze Gültigkeitszeiträume, da es sich hier um eine
   rein maschinelle Verarbeitung handelt. Zusätzlich zur URL wird an dieser Stelle noch eine Referenz- oder Datei ID
   erzeugt und zurückgegeben.
3. Mit der URL wird die Datei nun direkt in den S3 Speicher geschrieben. Das geht ohne Umweg über einen weiteren
   Service.
4. Die Datei Referenz wird über den `Event Bus` an den Prozess übergeben. Ab dieser Stelle ist der Prozess dafür
   verantwortlich diese Datei ID sorgsam aufzubewahren, denn nur mit dieser Referenz kann man die Datei noch über
   den `S3 Service` finden und entsprechend laden.

### Ausgehende Dateien
![Es wird gezeigt, wie die Datei Behandlung bei ausgehenden Dateien funktioniert.](~@source/images/platform/architecture/filehandling/digiwf_outgoing_files.png)

In der Abbildung oben ist zu sehen, wie mit ausgehenden Dateien umgegangen wird.

1. Grundvoraussetzung um eine Datei (oder einen ganzen Ordner) aus dem `S3 Bucket` zu holen ist die Referenz- oder
   Datei ID. Will man mehrere
   Dateien / Ordner laden - und beispielsweise an eine E-Mail anhängen - so werden auch entsprechend viele Referenz IDs
   benötigt. D.h. die Kardinalität zwischen Datei / Ordner und Referenz ID ist immer 1:1.
2. Mit der Referenz ID kann am `S3 Service` eine `presigned URL` für die Operation `GET` erfragt werden. Auch hier
   gilt - für jede Datei wird eine eigene `presigned URL` benötigt.
3. Mit der `presigned URL` kann wiederum die Datei direkt aus dem `S3 Bucket` geladen werden.
4. Die Datei(en) werden an die Mail gehängt und verschickt.

::: warning
Es ist übrigends davon abzuraten, eine presigned URL direkt heraus zu geben (beispielsweise per Mail zu 
verschicken). Wie oben beschrieben ist eine solche URL nur eine bestimmte Zeit gültig. D.h. wenn dieser Zeitraum 
abgelaufen ist, dann kann über die URL nicht mehr auf die Datei zugegriffen werden. Wenn dann auch noch die 
Prozessinstanz beendet wurde, hat man auch nicht mehr so einfach Zugriff auf die Referenz ID.
:::

## Datei Handling im GUI Integration Layer
Dateien kommen natürlich nicht nur aus angebundenen Verfahren, sondern können auch über die grafische
Benutzeroberfläche ins System gelangen. Auch hier wird das Filehandling über den S3 Service abgewickelt. Wichtig ist
hierbei, dass die Kommunikation nicht - wie bei den Integrationsartefakten - über den `Event Bus` erfolgt, sondern
per `REST` Aufruf, da die Oberflächenkomponenten keinen direkten Zugang zum `Event Bus` haben.

![Es wird gezeigt, wie das Speichern von Dateien aus dem Frontend heraus funktioniert.](~@source/images/platform/architecture/filehandling/digiwf_frontend_save_file.png)

Hier wird beschrieben, wie aus der Formularkomponente `File Upload Control` heraus eine Datei gespeichert wird. Der
Weg ist aber prinzipiell auch für eigene Formulare oder andere Oberflächen nutzbar.

1. Es wird eine `presigned URL` am `S3 Service` angefragt. Dazu werden folgende Parameter übergeben:
    - `refID` Die Referenz, unter der die Datei später abgerufen werden kann
    - `filename` Der Name der Datei, die gespeichert werden soll.
    - `endOfLife` Es kann ein Löschdatum mitgegeben werden. Zu diesem Zeitpunkt wird die Datei dann automatisch aus
      dem S3 Speicher gelöscht. Das ist vor allem dann sinnvoll, wenn Daten nur eine bestimmte Zeit aufbewahrt werden
      dürfen.
    - `expiredInMinutes` Die Zeitspanne in Minuten, für die der Link gültig ist. Nach ablauf dieser Zeit kann er
      nicht mehr verwendet werden.
2. Der `S3 Service` erzeugt über den S3 Storage eine entsprechende `presigned URL`. Was dort genau passiert, ist im
   Sequenzdiagramm unten ersichtlich. Diese URL wird an das `File Upload Control`zurückgegeben.
3. Über die `presigned URL` kann nun die Oberflächenkomponente die Datei direkt an den S3 Storage übergeben.

Wenn man mehr als eine Datei speichern will, so muss dieser Vorgang natürlich entsprechend oft wiederholt werden.

### Authorization beim Umgang mit Dateien aus dem Frontend

Während in der Integrationsschicht relativ klar ist, wer auf welche Datei zugreifen kann, ist dies im Frontend
deutlich schwieriger zu entscheiden, da hier potenziell erst einmal jeder auf die Schnittstelle zugreifen kann.

![Es wird gezeigt, wie das Speichern von Dateien aus dem Frontend heraus funktioniert.](~@source/images/platform/architecture/filehandling/digiwf_save_file_sequence_diagram.png)


![Es wird gezeigt, wie das Speichern von Dateien aus dem Frontend heraus funktioniert (Sequenzdiagramm).](~@source/images/platform/architecture/filehandling/digiwf_load_file_sequence_diagram.png)


[^1]: siehe https://docs.aws.amazon.com/AmazonS3/latest/userguide/using-presigned-url.html
