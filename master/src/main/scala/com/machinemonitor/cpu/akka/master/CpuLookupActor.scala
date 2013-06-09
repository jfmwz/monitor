package com.machinemonitor.cpu.akka.master

import akka.actor.{ActorRef, ActorSystem, ActorLogging, Actor}
import com.machinemonitor.cpu.akka.shared.{FrequencyCpuResultMessage, NumberCpuResultMessage, CpuResultMessage, CpuActionMessage}

class CpuLookupActor extends Actor with ActorLogging {

  protected def receive = {

    // *** ACTIONS
    case (actorForActions: Map[ActorRef, List[CpuActionMessage]]) =>
      actorForActions.foreach {
        case (actor, actions) =>
          for (action <- actions) {
            actor ! action
          }
      }

    // *** RESULTS
    case cpuResultMessage: CpuResultMessage => cpuResultMessage match {

      case NumberCpuResultMessage(machineId, numberCpu) =>
        log.info("Machine={}. CPUs={}", machineId, numberCpu)

      case FrequencyCpuResultMessage(machineId, frequencyCpu) =>
        log.info("Machine={}. CPU frecuency={}", machineId, frequencyCpu)


      case _ =>
        log.error("Error in result message")

    }

    case _ =>
      log.error("Error in action")

  }

}
