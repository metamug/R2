class Initialize{
	def overwrite=false
	File properties=new File("backend.properties")

	Initialize(){
		if(properties.exists()){
			String decision = System.console().readLine 'There is already a backend.properties file in this directory, do you want to overwrite? (Y/N)'
			if(decision!=null){
				decision=decision.toLowerCase()
				}
			
			switch(decision) {
				case 'y':
				overwrite=true	
				break
				case 'n':
				overwrite=false
				break
				default:
				println 'Input can only be y "yes" or n "no".'
				break
			}
		}else{
			overwrite=true
		}
	}

	def initFileWrite(){
		if(overwrite){
			Http http = new Http(1);
			def backends = http.getBackends()
			if(!backends)
				return
			println "Please type one of these backends to set it as your backend for this directory."
			def backendNames = new HashMap()
			def i=0
			for(backend in backends.data) {
				i++
				backendNames.put(i,backend.app_name)
				println i+" - "+backend.app_name
			}
			retry:
			def chosenBackend = System.console().readLine()
			if(chosenBackend!=null && chosenBackend.isInteger() && i!=0 
				&& chosenBackend.toInteger()>=1 && chosenBackend.toInteger()<=i)
			{
				properties.write("backend="+backendNames.get(chosenBackend.toInteger()))
				}else{
					println "Please choose one of the given backends.";
					continue retry;
				}
			}else{
				return
			}
	}
}