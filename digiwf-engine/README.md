## Camunda CE vs. Camunda EE

The engine service can be built including either Camunda EE Cockpit or Camunda CE Cockpit.
The easiest way to switch it, it to provide the command-line property `-Dcamunda-ee`. This 
property will activate the Maven profile `camunda-ee` and deactivate Maven profile `camunda-ce`.

Alternatively, you can specify `-Pcamunda-ee,!camunda-ce` to manually switch the profiles for Camunda EE build.