import ujson._ 

object Extract{
    def main(args: Array[String]){
        val url = "https://www.balldontlie.io/api/v1/games?seasons[]=2021&team_ids[]=24&page="
        val filename = "PhoenixSuns"
        val firstPage = requests.get(url + "1")
        val firstJson = ujson.read(firstPage.text)
        val totalPages = firstJson("meta")("total_pages").num.toInt

        for (page <- 1 until totalPages+1){
            val r = requests.get(url + s"$page")
            val json = ujson.read(r.text)
            val wd = os.pwd/"GamesData"
            os.write(wd/filename.concat(s"$page"), ujson.write(json))
        }     
    }       
}
   