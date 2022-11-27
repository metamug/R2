import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import org.xml.sax.SAXException

class Validator{

	def xsdUrl = 'http://xml.metamug.net/schema/resource.xsd'
		
	def validateResource(file){
		try{
			new URL( xsdUrl ).withInputStream { xsd ->
			
			SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI )
			.newSchema( new StreamSource( xsd ) )
			.newValidator()
			.validate( new StreamSource( file ) )

			}
		 	return true;
		} catch (SAXException e) {
			println "Error in Resource ${file.name}"
			println e.getMessage().split(": ")[1]
			return false;
		} catch (IOException e) {
			e.printStackTrace()
			return false;
		}		
		
	}
}