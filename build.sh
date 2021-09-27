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

# Build parser and console
mvn -f console/pom.xml clean install
mvn -f parser/pom.xml clean install

# Move console, parser and UI assets into server
mv console/target/console.war server/webapps
mv parser/target/*.jar server/lib
mv ui/src/assets server/webapps/ROOT
