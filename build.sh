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

# Add Empty Folders
mkdir -p ./server/temp
mkdir -p ./server/tempapps
mkdir -p ./server/backend
mkdir -p ./server/logs

cd console
mvn clean install
mv console/target/console.war server/webapps

cd ../parser
mvn clean install
mv parser/target/*.jar server/lib

mv ui/assets.zip server/webapps/ROOT
