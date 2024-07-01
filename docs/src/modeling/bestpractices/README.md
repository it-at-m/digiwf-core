# Umgang mit JSON-Arrays

## Elementzugriff

Um auf die Elemente eines JSON-Arrays zugreifen zu können, kann die
Klasse [SpinList](https://javadoc.io/doc/org.camunda.spin/camunda-spin-core/latest/org/camunda/spin/SpinList.html) von
Camunda genutzt werden. Das unten stehende Beispiel aus einem Script-Task zeigt den Zugriff auf das erste Element einer
Liste, das in eine neue Variable gespeichert wird.

```
/*
* Erstes Element aus der Liste der DMS-COOs
* an eine Variable des Execution-Context uebergeben.
*/
const fileCoos = execution.getVariable('sachakten');
const spinListFileCoos = S(execution.getVariable('sachakten')).elements(); //SpinList
const firstCoo = spinListFileCoos.get(0);
execution.setVariable('firstCoo', firstCoo);
```

## Konkatenieren

Um zwei Listen zusammenzuführen und sie wiederum an eine Prozessvariable zu übergeben, kann die Methode `addAll()`
der [SpinList](https://javadoc.io/doc/org.camunda.spin/camunda-spin-core/latest/org/camunda/spin/SpinList.html)
verwendet werden. Da für Listen von COO-Nummern pures JSON-Array verwendet wird, was valides JSON ist, muss die Liste
zunächst mit `toString` ausgegeben und mit `S()` wieder in ein JSON-Objekt umgewandelt werden. Eine direktere
Variante ist leider nicht bekannt.

```
const coos_1 = execution.getVariable('coos_1');
const coos_2 = execution.getVariable('coos_2');

const coosSpinList_1 = S(coos_1).elements(); //SpinList
const coosSpinList_2 = S(coos_2).elements(); //SpinList

coosSpinList_1.addAll(coosSpinList_2);

execution.setVariable('contentCoos', S(coosSpinList_1.toString()));
```

Die Variable 'contentCoos' ist nun vom Typ `Json`.