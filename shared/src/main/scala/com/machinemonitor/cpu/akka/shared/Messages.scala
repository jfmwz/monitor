package com.machinemonitor.cpu.akka.shared

sealed trait CpuActionMessage
case class NumberCpuActionMessage() extends CpuActionMessage
case class FrequencyCpuActionMessage() extends CpuActionMessage

sealed trait CpuResultMessage {
  def machineId: String
}
case class NumberCpuResultMessage(machineId: String = "localhost", numberCpu: Int) extends CpuResultMessage
case class FrequencyCpuResultMessage(machineId: String = "localhost", frequencyCpu: Long) extends CpuResultMessage