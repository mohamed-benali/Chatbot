Per poder utilitzar dialogflow és necessari crear-se un compte per obtenir unes credencials 
les quals permeten fer funcionar l’API de dialogflow.



Primer de tot cal anar a "https://dialogflow.cloud.google.com/" i crear-se un compte com a desenvolupador. Dialogflow proprociona
unes credencials que s'hauran d'utiltizar en comptes de les que hi ha per defecte.

S'ha de substituit les antigues credencials per les noves. Les antigues es poden trobar en la carpeta
BpmnParser(l'arrel) amb el nom de "greetingsbot-qtakwv-c046349de531.json".


Finalment, s'ha de substituir el nom de les noves credencials en el fitxer:

/BpmnParser/src/main/java/main/Entity/DialogFlow/

al fitxer

* IntentManagment.java "