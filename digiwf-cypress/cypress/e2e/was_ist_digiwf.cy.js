import vorgangStarten from "../pages/vorgangStarten"
import startDigiWFErleben from "../pages/startDigiWFErleben"
import aktuelleVorgaenge from "../pages/aktuelleVorgaenge"
import aktuellDigiWFErleben from "../pages/aktuellDigiWFErleben"
import meineAufgaben from "../pages/meineAufgaben"
import aufgabeWasIstDigiWF from "../pages/aufgabeWasIstDigiWF"

describe('template spec', () => {
    it('passes', () => {
        cy.loginUser();

        //Test auf korrekten Startzustand
        meineAufgaben.checkStartConditions();

        //Vorgang starten
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess("DigiWF erleben");
        vorgangStarten.clickFirstElement();
        startDigiWFErleben.checkHeadline("DigiWF erleben");
        startDigiWFErleben.tickWasIstDigiWF();
        startDigiWFErleben.clickLosGehts();

        //Aktuelle Vorgeange pruefen
        meineAufgaben.clickNavbar();
        meineAufgaben.openAktuelleVorgaenge();
        aktuelleVorgaenge.taskIsCorrect(1, "DigiWF erleben");
        aktuelleVorgaenge.checkStatusElement(1,"Was ist DigiWF");
        aktuelleVorgaenge.clickElement(1);
        aktuellDigiWFErleben.taskIsCorrect(1,'Was ist DigiWF?');
        aktuellDigiWFErleben.isOpen(1);



        //Meine Aufgaben pruefen
        //Task1
        aktuellDigiWFErleben.clickNavbar();
        aktuellDigiWFErleben.openMeineAufgaben();
        meineAufgaben.elementIsCorrect(1,"Was ist DigiWF?");
        meineAufgaben.clickElement(1);

        aufgabeWasIstDigiWF.checkHeadline("Was ist DigiWF?");
        aufgabeWasIstDigiWF.finishTask();

        //Task2
        meineAufgaben.elementIsCorrect(1,"Auf Wiedersehen bei DigiWF!");
        meineAufgaben.clickElement(1);

        aufgabeWasIstDigiWF.checkHeadline("Auf Wiedersehen bei DigiWF!");
        aufgabeWasIstDigiWF.finishTask();

        //Aufgaben abgearbeitet
        meineAufgaben.tasksEmpty("Keine Aufgaben gefunden");

        //aktuelle Vorgaenge beendet
        meineAufgaben.clickNavbar();
        meineAufgaben.openAktuelleVorgaenge();
        // TODO: reload kann entfernt werden, wenn Daten automatisch nachgeladen werden
        // TODO: ladeanimation des aktualisierenbuttons entfernen wenn nichts geladen wird um klick zu ermoeglichen
        cy.reload(true);
        aktuelleVorgaenge.taskIsCorrect(1, "DigiWF erleben");
        aktuelleVorgaenge.checkStatusElement(1,"Beendet");
    })
})