# Analizador d'etiquetes de tasques en models BPM

L'analitzador es un servidor en C++ que corre en Linux.
Per usar-lo des de windows, l'he posat en un docker.

Adjnuto un programa Client.java que mostra com s'envia
una peticio al servidor i com s'agafen els resultats.
Els resultats son un string en JSON. Pots usar alguna
llibreria de JSON per Java per convertir-lo a un Map.


###  Baixar i instal·lar docker, seguint les instruccions de :

   https://www.docker.com/

### Baixar-se la imatge del servidor
 
   http://www.cs.upc.edu/~padro/docker-label-parser.tar.gz

### Carregar la imatge al docker

   docker load --input docker-label-parser.tar.gz

   Un cop fet aixo, ja es pot esborrar el fixter .tar.gz, perque
   la imatge ja esta carregada al docker.

   Amb aixo, ja tenim el servidor instal·lat

   
## Executar el servidor

     docker run  --rm label-parser


## Enviar peticions al servidor:

     java Client

Text to analyze: Send finished documents to Marketing Department.

[{"sentence" : "Send finished documents to Marketing Department.",
  "actions" : [{"predW" : "send",
                "predF" : "send",
                "predL" : "send",
                "predPoS" : "VB",
                "predMSD" : "pos=verb|vform=infinitive",
                "objW" : "finished documents",
                "objF" : "documents",
                "objL" : "document",
                "objPoS" : "NN",
                "objMSD" : "pos=noun|type=common|num=plural",
                "compW" : "to marketing department",
                "compF" : "department",
                "compL" : "department",
                "compPoS" : "NN",
                "compMSD" : "pos=noun|type=common|num=singular"}]
}]


   Pots enviar varies frases de cop al servidor (separades per "\n"), i et tornara una llista
   d'estructures json.
   Per cada frase, tens una llista de "actions" (normalment nomes sera una, pero a vegades
   hi ha BPMNs que posa dues tasques a la mateixa caixa, p.e. "Send invoice and archive receipt")

   Per cada action, tens molta informacio, pero basicament nomes necessites:

    "predL":  El lema (o sigui, l'infinitiu del verb) de l'accio.

    "objL": El lema de l'objecte (p.e. si la frase es "send documents" el lema
            sera "document".  Si vols la paraula original ("documents") pots agafar "objF".
            Si vols el sintagma sencer ("finished documents") agafa "objW")

    "complL" : El lema del complement. Si el complement es "to marketing department"
               el lema sera "department". Igual que amb el objecte, agafa complF o
               complW segons vulguis la paraula original, o el complement sencer
     
