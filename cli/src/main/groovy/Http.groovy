@Grab('com.squareup.okhttp3:okhttp:3.9.0')
//@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7' )

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import groovy.json.JsonSlurper
import groovy.json.JsonParserType
import okhttp3.MultipartBody 
import okhttp3.MediaType;

import groovy.io.FileType 
import groovy.util.XmlSlurper

import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.nio.file.Paths;
import java.nio.file.Files;


class Http{

	String ENDPOINT = 'https://api.metamug.com/console'
	private final OkHttpClient client = new OkHttpClient();
	public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
	Properties configProperties = new Properties()
	Properties backendProperties = new Properties()
	Properties filesLogProperties = new Properties()
	String token = "";
	def validator = new Validator()
	def MTG_HOME
	Global global=new Global()
	
	//if user hasn't setted up backend yet
	Http(){
		MTG_HOME=global.checkForMtgHome()
		new File("./.mtg").mkdir()
		new File("./res").mkdir()
		new File("./code").mkdir()
		backendProperties=global.readFileProps('./backend.properties')
		configProperties=global.readFileProps("${MTG_HOME}/user/config.properties")
		ENDPOINT=configProperties.endpoint
	}

	//if backend is already setup
	Http(args){
		MTG_HOME=global.checkForMtgHome()
		new File("./.mtg").mkdir()
		new File("./res").mkdir()
		new File("./code").mkdir()
		configProperties=global.readFileProps("${MTG_HOME}/user/config.properties")
		ENDPOINT=configProperties.endpoint
	}

	def login(){
		String encoded = configProperties.password.bytes.encodeBase64().toString().split("==")[0]
		RequestBody formBody = new FormBody.Builder()
		.add("username", configProperties.username)
		.add("password", encoded)
		.build();

		Request request = new Request.Builder()
		.url(ENDPOINT+"/accesstoken")
		.post(formBody)
		.build();

		Response response = client.newCall(request).execute()
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(response.body().string())

		token = object.token
		if(MTG_HOME)
			new File("${MTG_HOME}/user/config.properties").
			write("username="+configProperties.username+"\npassword="+configProperties.password+"\ntoken="+token)
		
	}

	def uploadRes() {
		if(configProperties && backendProperties){
		def dir = new File("./res")
		def filesLog=new File("./.mtg/filesLog.properties")
		if(!dir.exists()){
			println 'There is no "res" folder in your directory.'
			return
		}

		dir.eachFileRecurse (FileType.FILES) { file ->

			RequestBody formBody = new FormBody.Builder()
			.add("filename", file.name)
			.add("data", file.text)
			.build();

			Request request = new Request.Builder()
			.url(ENDPOINT+"/app/${backendProperties.backend}/rpx")
			.addHeader('Authorization', configProperties.token)
			.post(formBody)
			.build();

			if(validator.validateResource(file)){	
				Response response = client.newCall(request).execute()
				//file already exists
				if (!response.isSuccessful() && response.code == 409){
					request = updateResource(file)
					client.newCall(request).execute()
				}
			}

			def b = Files.readAllBytes(Paths.get(file.path));
			def is=new FileInputStream(file)
			DigestInputStream dis = 
			new DigestInputStream(is, MessageDigest.getInstance('MD5'))
			def md5code=dis.getMessageDigest().digest(b).encodeHex().toString()
			if(filesLog.exists())
				filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
			filesLogProperties.setProperty(file.name, md5code)
			filesLogProperties.store(filesLog.newWriter(), null)
			filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
		}
		println "Successfully uploaded."
		}
		
	}	
	
	def updateResource(file){

		if(configProperties && backendProperties){
		RequestBody body = RequestBody.create(XML, file.text);
		println file.text
		def resource = new XmlSlurper().parseText(file.text)
		
		Request request = new Request.Builder()
		.url(ENDPOINT+"/app/${backendProperties.backend}/rpx/v${resource.'@v'}/${file.name.split('\\.')[0]}")
		.addHeader('Authorization', configProperties.token)
		.put(body)
		.build();

		return request;
		}
	}

	def uploadFile(){

		if(backendProperties){
		def builder = new MultipartBody.Builder().setType(MultipartBody.FORM)

		def dir = new File("./res")
		dir.eachFileRecurse (FileType.FILES) { file ->

			if(validator.validateResource(file)){
				builder.addFormDataPart(
					"uploaded_file", 
					file.name, 
					RequestBody.create(XML, file))
			}
		}
		
		RequestBody requestBody = builder.build();

		Request request = new Request.Builder()
		.url(ENDPOINT+"/app/${backendProperties.backend}/rpx")
		.post(requestBody)
		.build();

		Response response = client.newCall(request).execute()
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		System.out.println(response.body().string());
		}
	}

	def getBackends(){
		if(configProperties){
			Request request = new Request.Builder()
			.url(ENDPOINT+"/app")
			.addHeader('Authorization',configProperties.token)
			.build();
			Response response = client.newCall(request).execute()
			def parser = new JsonSlurper().setType(JsonParserType.LAX)
			def jsonResp = parser.parseText(response.body().string())
			return jsonResp
		}
	}

	def uploadRes(filename){

		def file=new File("./res/"+filename)
		def filesLog=new File("./.mtg/filesLog.properties")
		
		if(file.exists() && backendProperties && configProperties){
			RequestBody formBody = new FormBody.Builder()
			.add("filename", file.name)
			.add("data", file.text)
			.build();

			Request request = new Request.Builder()
			.url(ENDPOINT+"/app/${backendProperties.backend}/rpx")
			.addHeader('Authorization', configProperties.token)
			.post(formBody)
			.build();

			if(validator.validateResource(file)){	
				Response response = client.newCall(request).execute()
				//file already exists
				if (!response.isSuccessful() && response.code == 409){
					request = updateResource(file)
					client.newCall(request).execute()
				}
			}
			def b = Files.readAllBytes(Paths.get(file.path));
			def is=new FileInputStream(file)
			DigestInputStream dis = 
			new DigestInputStream(is, MessageDigest.getInstance('MD5'))
			def md5code=dis.getMessageDigest().digest(b).encodeHex().toString()
			if(filesLog.exists())
				filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
			filesLogProperties.setProperty(file.name, md5code)
			filesLogProperties.store(filesLog.newWriter(), null)
			filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
			println "Successfully uploaded."
			}else{
				println "File not found."
			}
	}

	def uploadCode(filename){

		def file=new File("./code/"+filename)
		
		if(file.exists() && backendProperties && configProperties){
			RequestBody formBody = new MultipartBody.Builder()
			.addFormDataPart("filename", filename)
			.addFormDataPart("data", filename, 
				RequestBody.create(MediaType.parse("multipart/form-data"),file))
			.build();

			Request request = new Request.Builder()
			.url(ENDPOINT+"/execute/${backendProperties.backend}")
			.addHeader('Authorization', configProperties.token)
			.post(formBody)
			.build();

			Response response = client.newCall(request).execute()
			println response
		}
	}

	def deleteCode(filename){
		
		if( backendProperties && configProperties){

			Request request = new Request.Builder()
			.url(ENDPOINT+"/execute/${backendProperties.backend}/"+filename)
			.addHeader('Authorization', configProperties.token)
			.delete()
			.build();

			Response response = client.newCall(request).execute()
			println response
		}
	}

	def viewResources(){
		if( backendProperties && configProperties){

			Request request = new Request.Builder()
			.url(ENDPOINT+"/app/${backendProperties.backend}/rpx")
			.addHeader('Authorization', configProperties.token)
			.get()
			.build();

			Response response = client.newCall(request).execute()
			def parser = new JsonSlurper().setType(JsonParserType.LAX)
			def jsonResp = parser.parseText(response.body().string())
			if(jsonResp){

				println "Please type one of these Resource's number to download it to your directory."
			def resourceNames = new HashMap()
			def i=0
			for(resp in jsonResp.data) {
				i++
				resourceNames.put(i,resp.name + ":" +resp.version)
				println i+" - "+resp.name + ":" +resp.version
			}
			retry:
			def chosenResource = System.console().readLine()
			if(chosenResource!=null && chosenResource.isInteger() && i!=0 
				&& chosenResource.toInteger()>=1 && chosenResource.toInteger()<=i)
			{
				def tmp=resourceNames.get(chosenResource.toInteger()).split(":");
				downloadResource(tmp[1],tmp[0])
				}else{
					println "Please choose one of the given Resources.";
					continue retry;
				}
			}
			}
		}

	def downloadResource(version,filename){
		if( backendProperties && configProperties){

			Request request = new Request.Builder()
			.url(ENDPOINT+"/app/${backendProperties.backend}/rpx/"+version+"/"+filename+".xml")
			.addHeader('Authorization', configProperties.token)
			.get()
			.build();

			Response response = client.newCall(request).execute()

			def parser = new JsonSlurper().setType(JsonParserType.LAX)
			def jsonResp = parser.parseText(response.body().string())
			def file=new File("./res/"+filename)
			if(file.exists())
			{
				String decision = System.console().readLine 'There is already a similar name/version resource file in this directory, do you want to overwrite? (Y/N)'
			if(decision!=null){
				decision=decision.toLowerCase()
				}
			
			switch(decision) {
				case 'y':
				file.write(jsonResp.toString())
				break
				case 'n':
				break
				default:
				println 'Input can only be y "yes" or n "no".'
				break
			}
			}else{
				file.write(jsonResp.toString())
			}

		}
	}

	def checkForUpdateInDir(){
		def dir = new File("./res")
		def filesLog=new File("./.mtg/filesLog.properties")
		filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
		if(!filesLogProperties)
		{
			println "Error:filesLog.properties not found. You might have not uploaded any resource file yet."
			return
		}
		def set=filesLogProperties.stringPropertyNames()
		dir.eachFileRecurse (FileType.FILES) { file ->
			if(set.contains(file.name))
			{
				def b = Files.readAllBytes(Paths.get(file.path));
				def is=new FileInputStream(file)
				DigestInputStream dis = 
				new DigestInputStream(is, MessageDigest.getInstance('MD5'))
				def md5code=dis.getMessageDigest().digest(b).encodeHex().toString()
				filesLogProperties=global.readFileProps("./.mtg/filesLog.properties")
				if(!(md5code == filesLogProperties.getProperty(file.name)))
				{
					uploadRes(file.name)
				}
			}
		}
	}
}
