# Design di dettaglio

## Architettura
Come spiegato nella sezione precedente, il pattern architetturale utilizzato
è **Model-View-Update** (MVU), è stato inoltre introdotto il **Cake Pattern**
per una migliore modellazione delle dipendenze.

Sono stati progettati dei _trait_ che rappresentano le componenti del pattern MVU, 
i quali incapsulano al loro interno gli _abstract type member_ relativi a Model, View e Update.

<img src="../diagrams/mvu/mvu-detailed.png" alt="Diagramma Model-View-Update dettagliato">

## Model
Il Model viene concretizzato utilizzando un _trait_ **State** contenete tre tipi astratti: **Expression**, **Solution** e **Problem**.

## View

## Update

