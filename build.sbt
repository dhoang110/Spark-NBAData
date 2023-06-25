name := "Transform"

version := "1.0"

scalaVersion := "2.12.15"

libraryDependencies ++= Seq("com.lihaoyi" %% "requests" % "0.8.0", "com.lihaoyi" %% "upickle" % "3.1.0", "com.lihaoyi" %% "os-lib" % "0.9.0", "org.apache.spark" %% "spark-sql" % "3.3.1" % "provided")