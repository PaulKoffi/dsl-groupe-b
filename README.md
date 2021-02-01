## <img src="https://icon-icons.com/icons2/907/PNG/64/group-of-people-in-a-formation_icon-icons.com_70476.png"/>  Dsl-groupe-B

* Auteurs: **Team B**
    * AINADOU Florian
    * DJEKINOU Paul-Marie
    * KOFFI Paul
    * NABAGOU Djotiham
* Version actuelle : Basic scenarios + Signaling stuff by using sound + Temporal transitions
* Livrables :
    * [delivery-first](https://github.com/pns-si5-soa/box-20-21-team-f/releases/tag/delivery-first) : Première livraison

## <img src="https://icon-icons.com/icons2/1147/PNG/64/1486486316-arrow-film-movie-play-player-start-video_81236.png"/>  Démos rapide de nos fonctionnalités
* `Very Simple Alarm` : [ici](https://drive.google.com/file/d/19RyqV8oVMZ8SNIQ71oXbLt3mdt5Y3ou1/view?usp=sharing)
* `Dual-check alarm` :  [ici](https://drive.google.com/file/d/1m-97sSkBXMuvHhR0sY43K60d9z-EbGZW/view?usp=sharing)
* `State-based alarm`  : [ici](https://drive.google.com/file/d/1XQG8X36FR4e23ONn1-RCl5AVm3z95tvY/view?usp=sharing)
* `Multi-state alarm` : [ici](https://drive.google.com/file/d/1Gk-Z64GswuqCTPo54cjSjKeaVHRz1ffB/view?usp=sharing)
* `Signaling stuff by using sounds` : [ici](https://drive.google.com/file/d/1lv3JnBRAOmwbYhtBqW3b_fPNmovczSaQ/view?usp=sharing)
* `Temporal transitions` : [ici](https://drive.google.com/file/d/1I46yfE0j3oK-w-emfjAi7bt3pc_IjSZB/view?usp=sharing)


## <img src="https://icon-icons.com/icons2/933/PNG/64/help-button-speech-bubble-with-question-mark_icon-icons.com_72707.png"/>  Comment utiliser ce repository

* La branche `main` (la branche par défaut) représente la dernière version stable du système.
* La branche `external` représente le système en cours de développement spécifique au DSL externe en Antlr.
* La branche `feature/internalDSLab` représente le système en cours de développement spécifique au DSL interne en groovy.

## <img src="https://icon-icons.com/icons2/1369/PNG/64/-get-app_90101.png"/>  Récupération du projet

  Effectuer un clone classique du projet en faisant ```git clone https://github.com/wak-nda/dsl-groupe-b.git``` ou en récupérant le zip depuis cette page.

## <img src="https://icon-icons.com/icons2/7/PNG/64/runbuild_1068.png"/>  Compilation
1 - Effectuez à la racine du projet `mvn clean install`  
2 -`cd dsl-antlr` puis ensuite `mvn clean compile assembly:single` pour la génération avec Antlr  
3 - `cd dsl-groovy` puis ensuite `mvn clean compile assembly:single` pour la génération avec Groovy  

## <img src="https://cdn0.iconfinder.com/data/icons/octicons/1024/git-compare-48.png"/> Générer le code Arduino à partir du DSL
1 - Pour générer avec Groovy (exemple de VerySimpleAlarm.groovy):   
* `runInternal.sh VerySimpleAlarm.groovy VerySimpleAlarm` , le fichier généré se trouvera dans le dossier `scripts/ouputs`

2 - Pour générer avec Antlr (exemple de VerySimpleAlarm.groovy):
* `runExternal.sh VerySimpleAlarm.groovy VerySimpleAlarm` , le fichier généré se trouvera dans le dossier `scripts/ouputsExternal` 

3 - Dans le cas d'un nouveau script groovy, placez le fichier dans le dossier `scripts`, puis suivez les deux précédentes étapes selon votre choix. Un fichier `.ino` sera généré dans le dossier approprié.



## <img src="https://cdn2.iconfinder.com/data/icons/flat-ui-icons-24-px/24/new-24-48.png"/> Utiliser le DSL

### Syntaxe
La syntaxe du DSL est la suivante : 

## <img src="https://icon-icons.com/icons2/1145/PNG/64/codeoutlinedprogrammingsigns_81143.png"/>  Pile Technologique
  <p align="center">
    <img src="./docs/img/techno.jpg"/>
  </p>