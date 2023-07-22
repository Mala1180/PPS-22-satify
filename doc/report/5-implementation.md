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
