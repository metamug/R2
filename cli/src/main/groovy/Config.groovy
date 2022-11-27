class Config{
	def MTG_HOME

	Config(path,uname,pass,endpoint) {
		MTG_HOME=path
		new File(MTG_HOME+"/user").mkdir()
		def file=new File("${MTG_HOME}/user/config.properties")
		if(file.exists()){
			println "You have already configured your username and password on this machine, in case you want to re-configure, you will have to use the -rst command to reset first."
		}else{
			println "Configuring, please wait..."
			file.write("username="+uname+"\npassword="+pass+"\nendpoint="+endpoint)
			try {
				new Http(1).login()
				println "Configuration completed."
			}
			catch(Exception e) {
				println "Error: Please retry configuring. Please check your username and password."
				println e.toString()
				file.delete()
			}
		}
	}
}