import sbt._
import Keys._
import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin._

object MachineMonitorCpuVersions {

  lazy val scalaVersion = "2.9.2"

  lazy val akkaVersion = "2.0.2"

  lazy val scalaTestVersion = "2.0.M4"

  lazy val junitVersion = "4.8.1"

}

object MachineMonitorCpuSettings {

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    version := "1.0.0",
    scalaVersion := MachineMonitorCpuVersions.scalaVersion)

  lazy val sharedSettings: Seq[Setting[_]] = commonSettings ++ Seq(
    name := "machine-monitor-cpu-akka-shared",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor" % MachineMonitorCpuVersions.akkaVersion,
      "org.scalatest" % "scalatest_2.9.2" % MachineMonitorCpuVersions.scalaTestVersion % "test",
      "org.specs2"    % "specs2_2.9.1"    % "1.5" % "test"   withSources(),
      "junit" % "junit" % MachineMonitorCpuVersions.junitVersion % "test"))

  lazy val serverSettings: Seq[Setting[_]] = commonSettings ++ AkkaKernelPlugin.distSettings ++ Seq(
    name := "machine-monitor-cpu-akka-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-remote" % MachineMonitorCpuVersions.akkaVersion,
      "com.typesafe.akka" % "akka-kernel" % MachineMonitorCpuVersions.akkaVersion),
    dist <<= dist.dependsOn(Keys.`package` in(MachineMonitorCpuBuild.shared, Compile)))

  lazy val masterSettings: Seq[Setting[_]] = commonSettings ++ sharedSettings ++ Seq(
    name := "machine-monitor-cpu-akka-master",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-remote" % MachineMonitorCpuVersions.akkaVersion))

}

object MachineMonitorCpuBuild extends Build {
  lazy val root = Project(id = "machine-monitor-cpu", base = file(".")) aggregate(shared, server, master)

  lazy val shared = Project(id = "machine-monitor-cpu-akka-shared", base = file("shared")) settings (MachineMonitorCpuSettings.sharedSettings: _*)

  lazy val server = Project(id = "machine-monitor-cpu-akka-server", base = file("server")) settings (MachineMonitorCpuSettings.serverSettings: _*) dependsOn (shared)

  lazy val master = Project(id = "machine-monitor-cpu-akka-master", base = file("master")) settings (MachineMonitorCpuSettings.masterSettings: _*) dependsOn (shared)


}
