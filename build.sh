# remove parser
rm -f ./server/lib/openapi-rest-model-*.jar

# remove console webapp
rm -rf ./server/webapps/console
rm -f ./server/webapps/console.war

# remove root webapp
rm -rf ./server/webapps/ROOT/dist
rm -rf ./server/webapps/ROOT/assets
rm -rf ./server/webapps/ROOT/static
rm -f ./server/webapps/ROOT/index.html

ls ./server/webapps/ROOT/

# Add Empty Folders
mkdir -p ./server/temp
mkdir -p ./server/tempapps
mkdir -p ./server/backend
mkdir -p ./server/logs
