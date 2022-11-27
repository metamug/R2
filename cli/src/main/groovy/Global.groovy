class Global{
	def readFileProps(args){
		try {
			File propertiesFile = new File(args)
			if(propertiesFile.exists()){
				Properties properties = new Properties()
				propertiesFile.withInputStream {
					properties.load(it)
				}
				return properties
			}else{
				if(args.contains("backend")){
					println "Could not find backend.properties, please use the -init command to create a backend.properties file."
				}
				if(args.contains("config")){
					println "Could not find config.properties, please use the -cfg command to create a config.properties file, config.properties is a primary requirement to run any command."
				}
				return null
			}
		}
		catch(Exception e) {
			println e
			System.exit(0)
		}
	}

	def checkForMtgHome(){
		try {
			if(System.getenv("MTG_HOME"))
				return System.getenv("MTG_HOME")
			else
				println "MTG_HOME isn't present as an environment variable."
				return null
		}
		catch(Exception e) {
			println e
			System.exit(0)
		}
	}
}