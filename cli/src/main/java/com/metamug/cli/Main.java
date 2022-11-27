import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import java.io.File;

/**
 *
 * @author Deepak
 */
public class Main {

    
    public static void main(String[] args) {
        try {

            String dir = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            GroovyScriptEngine engine = new GroovyScriptEngine( dir + File.separator + "scripts" );

            //Map<String, String> request = new HashMap();
            //request.put("param1", "Hello");
            //Map<String, String> response = new HashMap();
            //response.put("param1", "World");
            Binding binding = new Binding();
            binding.setVariable("args", args);
            //binding.setVariable("response", response);
            engine.run("Cli.groovy", binding);

            //System.out.println("output: " + response.get("param1") );
        } catch (IOException | SecurityException | ResourceException | ScriptException | IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CompilationFailedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}