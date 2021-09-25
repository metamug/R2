cd ui
npm install
npm run build
cd build
zip -r assets.zip .
cp assets.zip ../
rm -f assets.zip
#ls ../
