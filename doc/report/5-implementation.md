# Implementazione

In questa sezione ogni componente del gruppo descriverà il lavoro svolto per la realizzazione del progetto.

## Luca Fabri

## Mattia Matteini
Inizialmente mi sono dedicato alla preparazione del repository e
alle tasks riguardanti le operazioni di DevOps, quindi ho impostato la Continuous Integration,
SBT, ScalaTest e ScalaFMT.

Dopodiché mi sono occupato della progettazione e implementazione dell'architettura di base del software (MVU).
Ho deciso di utilizzare il Cake Pattern, nonostante in MVU non ci sono troppe dipendenze, per dare comunque robustezza, sostituibilità e flessibilità.

In particolare quindi ho sfruttato i meccanismi di _Self-Type Annotation_ e _Mixin_ offerti da Scala per poter 
gestire le dipendenze tra le componenti del pattern a compile-time e per creare un istanza dell'applicazione in maniera semplice e leggibile.

```scala
trait MVU extends ModelComponent with ViewComponent with UpdateComponent

object Main extends App with MVU
```

## Alberto Paganelli

Inizialmente il mio compito era quello di analizzare in maniera approfondita l'algoritmo per effettuare la trasformazione di Tseitin. 
Non avendo una sufficiente conoscenza ho dedicato del tempo alla comprensione dell'algoritmo e delle sue fasi intermedie cosi' da poter approcciare lo sviluppo in maniera incrementale. 
Avevo supposto di metterci più tempo per la comprensione dell'algoritmo, ma in realtà le sue sottoparti sono relativamente semplici. La trasformazione infatti consiste nella suddivisione della formula in sotto-clausole, una parte di introduzione di nuove variabili e una riscrittura di queste ultime in CNF.

Ho quindi scelto di definire 3 metodi che rappresentassero le fasi dell'algoritmo e combinarli insieme in un oggetto concreto Expression. 

Quando ho iniziato a sviluppare la prima fase dell'algortimo, ovvero la suddivisione della formula in input in sotto-clausole, è stata necessaria una prima modellazione molto semplice di come avremmo costituito l'espressione all'interno del programma per motivi di coordinamento con gli altri membri del team.


