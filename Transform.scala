import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Transform{
    def main(args: Array[String]){
        val spark = SparkSession.builder().appName("Transform").getOrCreate()
        import spark.implicits._
        val games = spark.read
                         .option("inferSchema","true")
                         .option("multiline","true")
                         .json("GamesData") //read all .json file in the directoty GamesData

       val games_flatted = games.select(explode($"data")) //exploding the array data
                                    .withColumn("game_id", $"col.id")
                                    .withColumn("home_team", $"col.home_team")
                                    .withColumn("home_team_score", $"col.home_team_score")
                                    .withColumn("visitor_team", $"col.visitor_team")
                                    .withColumn("visitor_team_score", $"col.visitor_team_score")
                                    .drop($"col")

        val temp_games = games_flatted.select($"game_id", $"home_team.id" as "home_team_id", $"home_team.full_name" as "home_team_name", $"home_team_score", $"visitor_team.id" as "visitor_team_id", $"visitor_team.full_name" as "visitor_team_name", $"visitor_team_score")
                                            .drop($"home_team").drop($"visitor_team")

        val teamId = List(1,17,24,13) 

        val df_games = temp_games.select($"*").filter($"home_team_id".isin(teamId: _*) || $"visitor_team_id".isin(teamId: _*))
        
        val stats = spark.read
                         .option("inferSchema","true")
                         .option("multiline","true")
                         .json("Stats")


        val stats_flatted = stats.select(explode($"data")) //exploding the array data
                                    .withColumn("game", $"col.game")
                                    .withColumn("fg_pct", $"col.fg_pct")
                                    .withColumn("min", $"col.min")
                                    .withColumn("oreb", $"col.oreb")
                                    .withColumn("pf", $"col.pf")
                                    .withColumn("pts", $"col.pts")
                                    .withColumn("reb", $"col.reb")
                                    .withColumn("stl", $"col.stl")
                                    .drop($"col")

        val df_stats = stats_flatted.select($"game.id" as "game_id_2", $"fg_pct", $"min", $"oreb", $"pf", $"pts", $"reb", $"stl").drop($"game")

        val dfNBA = df_games.join(df_stats, df_games("game_id")===df_stats("game_id_2")).drop("game_id_2")

        dfNBA.show()

        dfNBA.coalesce(1).write.option("header",true).option("delimiter",",").csv("NBA2021-2022")

    }        
}

