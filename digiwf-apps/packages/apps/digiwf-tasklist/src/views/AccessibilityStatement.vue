<template>
  <v-container class="viewContainer">
    <div>
      <div>
        <h1 tabindex="0">Erklärung zur Barrierefreiheit nach BayDiV</h1>
        <p tabindex="0">Öffentliche Stellen sind gemäß der Richtlinie (EU) 2016/2102 des Europäischen Parlaments dazu verpflichtet, Webauftritte barrierefrei zugänglich zu machen.</p>
      </div>
      <div tabindex="0">
        <h2>Geltungsbereich</h2>
        <p>Die folgende Erklärung zur Barrierefreiheit dokumentiert den Umsetzungsstand dieses Webauftritts gemäß der gültigen Gesetzgebung. Sie gilt für die Website digiwf.muenchen.de.</p>
      </div>
      <div tabindex="0">
        <h2>Stand der Vereinbarkeit mit den Anforderungen</h2>
        <p>Diese Website ist mit der Bayerischen Digitalverordnung (BayDiV) teilweise vereinbar. Die Unvereinbarkeiten sind nachstehend aufgeführt.</p>
      </div>
      <div tabindex="0">
        <h3>Nicht barrierefrei zugängliche Inhalte:</h3>
        <ol>
          <li>Bei dem Multi-User-Input fehlen teilweise Texte für den Screenreader</li>
          <li>Bei dem Multi-File-Input fehlen teilweise Texte für den Screenreader oder sind auf Englisch</li>
          <li>Pflichtfelder sind teilweise nicht am Label und nicht für den Screenreader erkennbar</li>
          <li>Meldungen über erfolgte Aktionen fehlen sowohl optisch als auch für den Screenreader</li>
          <li>Fehlende Shortcuts für App-Elemente</li>
          <li>Teilweise fehlende Alternativtexte</li>
        </ol>
      </div>
      <div tabindex="0">
        <h3>Alternative Zugangswege</h3>
        <p>Bitte wenden Sie sich bei Problemen per Telefon oder E-Mail an Ihre zuständige Dienst- oder Personalstelle.</p>
      </div>
      <div tabindex="0">
        <h2>Hilfe und Kontaktaufnahme bei Problemen</h2>
        <p>Sie können Mängel bei der Einhaltung der Anforderungen an die Barrierefreiheit mitteilen oder Informationen, die nicht barrierefrei dargestellt werden müssen, barrierefrei anfordern.</p>
      </div>
      <div tabindex="0">
        <h2>Kontaktstelle</h2>
        <p>
          Landeshauptstadt München <br>
          IT-Referat, Team DigiWF <br>
          E-Mail: <a href="mailto:digiwf@muenchen.de">digiwf@muenchen.de</a>
        </p>
      </div>
    </div>
  </v-container>

</template>


<script setup lang="ts">

</script>

<style scoped>
.viewContainer {
  max-width: 900px;
  position: relative;
}

div {
  padding-bottom: 10px;
}

h1 {
  padding-bottom: 15px;
}

h2 {
  padding-bottom: 10px;
}

h3 {
padding-bottom: 8px;
}

li {
  padding-bottom: 10px;
  padding-left: 10px;
}
</style>
