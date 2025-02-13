<?php
		// error_reporting(E_ALL);
		// ini_set('display_errors', 1);
		$fileName = basename(parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH));
		$fileName = pathinfo($fileName)["filename"]; //remove .html
		
		
		$confluencePage = str_replace("-","+",$fileName);
		header("HTTP/1.1 301 Moved Permanently");
		header("Location: https://docs.metamug.com/display/CON/".$confluencePage,TRUE,301);

		
		//echo $fileName;
		//die();
		// if($fileName == 'server-side-scripting-with-node-js') {
		// 	header("Location: https://metamug.com/article/api-integration/node-js-publish-rest-api");
		// }

		// if($fileName == 'jira-rest-api-integration-postgresql') {
		// 	header("Location: https://metamug.com/article/api-integration/jira-postgresql-update-issue-custom-field.html");
		// }

		// $markdownLocation = '../../docs/examples/markdown/';
		// $relativity = "../..";
		// include "../leap-docgen.php";
?>
