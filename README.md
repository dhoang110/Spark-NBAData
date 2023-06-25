During my Data Engineering course, I have worked on this project to practice all the contexts I have learnt about Scala and ScalaSpark. 
The goal of this Repository is to make a simple ETL using this API (https://app.balldontlie.io/#introduction) giving data on the NBA with Scala, then processing the data collected with Spark to have CSV files. There is no key required to requests the API.

REQUIREMENTS:

Collect the matches for the 2021-2022 season of the following teams :

Phoenix Suns

Atlanta Hawks

Los Angeles Clippers

Milwaukee Bucks

Then collect the statistics of the selected matches, choose only relevants statistics. 

Perform transform and write the DataFrame to .csv files.

PREREQUISITES: 
- sbt package manager: install sbt
  to check sbt version, run the command 'sbt -version'
  to enter the console of sbt, run 'sbt'. This instruction also created the folder project and target
- Java JMV
- Scala
- Spark

1- Data Extraction: 

A) your built.sbt file should look like this:

    name := "Extract"

    version := "1.0"

    scalaVersion := "2.12.15"

    libraryDependencies ++= Seq("com.lihaoyi" %% "requests" % "0.8.0", "com.lihaoyi" %% "upickle" % "3.1.0",
    "com.lihaoyi" %% "os-lib" % "0.9.0")

B) compile the Extract.scala file using sbt package command

C) run the scala file to write .json files to the directory using spark-submit: 
spark-submit --class "Extract" --master "local[*]" --packages com.lihaoyi:requests_2.12:0.8.0,com.lihaoyi:upickle_2.12:3.1.0,com.lihaoyi:os-lib_2.12:0.9.0 target/scala-2.12/extract_2.12-1.0.jar

#Note: create the GamesData directory to store all the .json files of the matches

D) change the URL & filename accordingly to get games of a specific team: 
    
    Phoenix Suns: "https://www.balldontlie.io/api/v1/games?seasons[]=2021&team_ids[]=24"
    Atlanta Hawks: "https://www.balldontlie.io/api/v1/games?seasons[]=2021&team_ids[]=1"
    Los Angeles Clippers: "https://www.balldontlie.io/api/v1/games?seasons[]=2021&team_ids[]=13"
    Milwaukee Bucks: "https://www.balldontlie.io/api/v1/games?seasons[]=2021&team_ids[]=17"

E) change the URL to get stats of season 2021-2022:
    "https://www.balldontlie.io/api/v1/stats?seasons[]=2021&per_page=100&page=" 
    
#Note: create the Stats directory to store all the .json files of the statistics
    
2- Data Transformation: 

A) add spark-sql package to your build.sbt: 

    name := "Transform"
    
    version := "1.0"
    
    scalaVersion := "2.12.15"
    
    libraryDependencies ++= Seq("com.lihaoyi" %% "requests" % "0.8.0", "com.lihaoyi" %% "upickle" % "3.1.0", 
    "com.lihaoyi" %% "os-lib" % "0.9.0", "org.apache.spark" %% "spark-sql" % "3.3.1" % "provided")

B) compile the Transform.scala file using sbt package command

C) run the scala file to show DataFrame and write the final .csv files to the directory using spark-submit: 
    spark-submit --class "Transform" --master "local[*]" target/scala-2.12/transform_2.12-1.0.jar
