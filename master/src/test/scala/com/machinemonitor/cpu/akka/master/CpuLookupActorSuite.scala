package com.machinemonitor.cpu.akka.master

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}
import akka.actor.{Props, ActorRef, ActorSystem}
import com.machinemonitor.cpu.akka.shared.{FrequencyCpuActionMessage, CpuActionMessage, NumberCpuActionMessage}

import akka.util.duration._
import akka.dispatch.Await
import akka.pattern.ask
import scala._
import java.util.concurrent.TimeoutException
import com.typesafe.config.ConfigFactory
import akka.util.Timeout

@RunWith(classOf[JUnitRunner])
class CpuLookupActorSuite extends FunSuite with BeforeAndAfter {

  val system = ActorSystem("CpuMachineMonitorMaster", ConfigFactory.load.getConfig("cpuMachineMonitorMaster"))

  // *** CPU LOOKUP ACTOR
  val cpuLookupActor = system.actorOf(Props[CpuLookupActor], ConfigFactory.load.getString("cpuMachineMonitorMaster.akka.context.name"))

  // *** LOCAL ACTOR
  val localActor = system.actorOf(Props[CpuLocalActor])

  // *** REMOTE ACTORS
  val laptopStudelaIngeniaActor = system.actorFor("akka://CpuMachineMonitorServer@192.168.16.187:2553/user/ingeniaStudelaLaptop")

  val actorForActions = Map[ActorRef, List[CpuActionMessage]](
    localActor -> List(NumberCpuActionMessage(), FrequencyCpuActionMessage()),
    laptopStudelaIngeniaActor -> List(NumberCpuActionMessage(), FrequencyCpuActionMessage())
  )

  test("number cpu") {

    implicit val timeout = Timeout(1 seconds)

    try {
      Await.result((cpuLookupActor ? (actorForActions)), timeout.duration)
    } catch {
      case e: TimeoutException => {}
    }

  }

}
