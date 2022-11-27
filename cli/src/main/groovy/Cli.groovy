@Grab('commons-cli:commons-cli:1.2')

import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


Options options = new Options();

options.addOption("h", "help", false, "Show help.");
options.addOption("v", "var", true, "Here you can set parameter.");
options.addOption("init", "initialize", false, "Create backend properties file.");
options.addOption("upl", "upload", false, "Upload files to your online backend.");
options.addOption("upd", "update", false, "Update files that are already present in your online backend.");
options.addOption("cfg", "config", false, "Configure your username and password and endpoint for this machine.");
options.addOption("rst", "reset", false, "Reset/delete your config.properties file for this machine.");
options.addOption("p", "path", false, "Gives the path for MTG_HOME/mtg-cli folder.");
options.addOption("uplcd", "uploadcode", false, "Upload code execution to backend.");
options.addOption("delcd", "deletecode", false, "Delete code execution from backend.");
options.addOption("dlres", "downloadresource", false, "Download resource file, need to pass filename as parameter.");


CommandLineParser parser = new BasicParser();

CommandLine cmd = null;

try {

	def MTG_HOME=new Global().checkForMtgHome()
	
	cmd = parser.parse(options, args);

	if (cmd.hasOption("h")){
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Help", options);
	}
	if (cmd.hasOption("init")){
		new Initialize().initFileWrite()
	}
	if(cmd.hasOption("upl")){
		if(checkInitialized()){
			switch(cmd.getArgs().size()) {
				case 0:
				new Http().uploadRes()
				break;
				case 1:
				new Http().uploadRes(cmd.getArgs()[0])
				break;
				default:
				println 'You can either Upload the whole Res folder or just one Resource.\nExample- Upload whole folder "mtg -upl",Upload specific resource "mtg -upl MyResource.xml"'
				break;
			}
		}else{
			println "Backend.properties file not found, please run -init first."
		}
	}
	if(cmd.hasOption("upd")){
		new Http().checkForUpdateInDir()
	}
	if(cmd.hasOption("cfg")){
		if(cmd.getArgs().size()==2 && MTG_HOME)
			new Config(MTG_HOME,cmd.getArgs()[0],cmd.getArgs()[1],cmd.getArgs()[2])
		else
			println 'You can only give username and password for config.\nExample: mtg -cfg Adam@metamug.com 123 https://api.metamug.com/console\nIf password/username has spaces enter in qoutes "mypassword 123".'
	}
	if(cmd.hasOption("rst")){
		if(new File("${MTG_HOME}/user/config.properties").delete()){
			println "Config.properties file deleted."
		}else{
			println "Error:config.properties file not found."
		}
	}
	if(cmd.hasOption("p")){
		if(MTG_HOME){
			println "MTG_HOME path is: "+MTG_HOME
		}else{
			println "MTG_HOME not detected as a system environment variable."
		}
	}
	if(cmd.hasOption("uplcd")){
		new Http().uploadCode(cmd.getArgs()[0]+".zip")
	}
	if(cmd.hasOption("delcd")){
		new Http().deleteCode(cmd.getArgs()[0])
	}
	if(cmd.hasOption("dlres")){
		new Http().viewResources()
	}

} catch (ParseException e) {
	e.printStackTrace();

	HelpFormatter formater = new HelpFormatter();
	formater.printHelp("bro", options);
	System.exit(0);
}

def checkInitialized(){
	Properties properties = new Properties()
	File propertiesFile = new File('./backend.properties')
	if(!propertiesFile.exists())
		return false
		propertiesFile.withInputStream {
			properties.load(it)
		}
	if(properties.backend !=null)
	{
		return true;
	}else{
		return false;
	}
	
}



