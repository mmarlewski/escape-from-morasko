
echo "script: adding execution permission to gradlew..."
chmod +x ./gradlew
echo "script: deleting desktop and android build directories..."
rm -rf ./desktop/build
rm -rf ./android/build
echo "script: deleting jar, apk and abb in root directory..."
rm -rf ./desktop.jar
rm -rf ./android.apk
rm -rf ./android.abb
echo "script: running gradle task desktop:dist..."
./gradlew desktop:dist
echo "script: running gradle task android:assembleDebug..."
./gradlew android:assembleDebug
echo "script: running gradle task android:bundleRelease..."
./gradlew android:bundleRelease
echo "script: copying jar, apk and abb to root directory..."
cp ./desktop/build/libs/*.jar ./desktop.jar
cp ./android/build/outputs/apk/debug/*.apk ./android.apk
cp ./android/build/outputs/bundle/release/*.aab ./android-release.aab
echo "fin"
