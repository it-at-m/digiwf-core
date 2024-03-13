# Migration von Element-Templates

## Einführung optionaler Topics

Ab Release 1.7 werden die Integrations-Topics automatisch bestimmt, dadurch müssen diese in den
Element-Templates nicht mehr angegeben werden.

Um diese Funktionalität zu ermöglichen wurde in den Element-Templates der neue Wert `app_integration_name` eingeführt.
Im gleichen Zuge wurden die alten nicht mehr benötigten Werte `app_message_name` und `app_topic_name` entfernt.

Die Schnittstelle wurde dabei so verändert, dass alle Prozesse die noch die alten Werte verwenden keine Probleme haben.
Um die neue Funktionalität zu nutzen, muss der Prozess aber entsprechend angepasst werden. In der normalen UI werden die
neuen Werte durch Anpassung der Element-Templates bereits angezeigt. Leider werden diese aber nicht automatisch in den
Prozess-Code übernommen. Somit muss, wie nachfolgend beschrieben, manuell korrigiert werden.

Hierzu müssen die beiden Werte `app_message_name` und `app_topic_name` entfernt und der zur Integration passende
neue Wert `app_integration_name` hinzugefügt werden. Der Wert für `app_integration_name` kann entweder aus dem bereits
angezeigten Element-Template, der nachfolgenden Liste oder von den [Integrations-Seiten](../../../integrations)
übernommen werden. Auf den einzelnen Integrations-Seiten ist der entsprechende Wert jeweils als Badge dokumentiert.

- addressIntegration
- alwIntegration
- cosysIntegration
- emailIntegration
- s3Integration
- dmsIntegration

![Element-Template-Migration Beispiel](~@source/modeling/guides/element-template-migration/migrate_et_topics_example.png)
