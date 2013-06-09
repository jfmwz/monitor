package com.machinemonitor.cpu.akka.server

import akka.actor.{Props, ActorSystem}
import akka.kernel.Bootable
import com.machinemonitor.cpu.akka.shared.CpuActor
import com.typesafe.config.ConfigFactory

class Server extends Bootable {

  val system = ActorSystem("CpuMachineMonitorServer", ConfigFactory.load.getConfig("cpuMachineMonitorServer"))

  def startup = system.actorOf(Props[CpuServerActor], ConfigFactory.load.getString("cpuMachineMonitorServer.akka.context.name"))

  def shutdown = system.shutdown

}

class CpuServerActor extends CpuActor {

}
