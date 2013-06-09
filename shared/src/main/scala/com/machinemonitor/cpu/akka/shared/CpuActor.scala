package com.machinemonitor.cpu.akka.shared

import akka.actor.{ActorLogging, Actor}
import java.net.InetAddress
import com.jezhumble.javasysmon.JavaSysMon

trait CpuActor extends Actor with ActorLogging {

  val machineId = InetAddress.getLocalHost.getHostName

  val monitor = new JavaSysMon

  protected def receive = {

    // *** ACTIONS
    case cpuActionMessage: CpuActionMessage => cpuActionMessage match {

      case NumberCpuActionMessage() =>
        sender ! NumberCpuResultMessage(machineId, monitor.numCpus())

      case FrequencyCpuActionMessage() =>
        sender ! FrequencyCpuResultMessage(machineId, monitor.cpuFrequencyInHz())

    }

  }

}
