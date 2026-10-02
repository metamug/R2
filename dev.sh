#!/usr/bin/env bash
# R2 Dev server helper (Linux/macOS/Git Bash). Same commands as dev.cmd:
#   ./dev.sh build | start | stop | status | mason
#   mason: build Mason (MASON_HOME, default ../mason) with Maven and put its jar into the app template
set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"
SERVER="$ROOT/server"

if [ -z "$JAVA_HOME" ]; then
  JAVA_BIN="$(command -v java || true)"
  [ -n "$JAVA_BIN" ] || { echo "JAVA_HOME is not set and java is not on PATH"; exit 1; }
  export JAVA_HOME="$(cd "$(dirname "$(readlink -f "$JAVA_BIN")")/.." && pwd)"
fi
mkdirs() { for d in temp tempapps backend logs; do mkdir -p "$SERVER/$d"; done; }

case "$1" in
  build)
    # the bundled Maven 3.6 needs these to run on JDK 16+
    export MAVEN_OPTS="--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED"
    MVN="$SERVER/maven/bin/mvn"; [ -x "$MVN" ] || MVN="mvn"
    "$MVN" -B -f "$ROOT/parser/pom.xml" clean install -DskipTests
    "$MVN" -B -f "$ROOT/console/pom.xml" clean package -DskipTests
    # Kotlin script engine for KotlinRunner (git-ignored jars)
    "$MVN" -B -q -f "$SERVER/kotlin/pom.xml" dependency:copy-dependencies -DoutputDirectory="$SERVER/lib"
    rm -f "$SERVER"/lib/openapi-rest-model-*.jar
    cp "$ROOT"/parser/target/openapi-rest-model-*.jar "$SERVER/lib/"
    rm -rf "$SERVER/webapps/console"
    cp "$ROOT/console/target/console.war" "$SERVER/webapps/console.war"
    mkdirs
    echo "Built. Run: ./dev.sh start" ;;
  mason)
    MASON_HOME="${MASON_HOME:-$ROOT/../mason}"
    [ -f "$MASON_HOME/taglib/pom.xml" ] || { echo "Mason checkout not found at $MASON_HOME. Set MASON_HOME."; exit 1; }
    export MAVEN_OPTS="--add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED"
    MVN="$SERVER/maven/bin/mvn"; [ -x "$MVN" ] || MVN="mvn"
    "$MVN" -B -f "$MASON_HOME/taglib/pom.xml" clean package -DskipTests
    TPL="$(mktemp -d)"; mkdir -p "$TPL/WEB-INF/lib"
    cp "$MASON_HOME"/taglib/target/mason-*.jar "$TPL/WEB-INF/lib/"
    (cd "$TPL" && "$JAVA_HOME/bin/jar" uf "$ROOT/console/src/main/resources/app-template-v0.1.zip" WEB-INF/lib)
    rm -rf "$TPL"
    echo "App template updated. Run: ./dev.sh build (rebuilds the console with it)" ;;
  start)
    export CATALINA_HOME="$SERVER" CATALINA_BASE="$SERVER"
    mkdirs
    [ -e "$SERVER/webapps/console.war" ] || [ -d "$SERVER/webapps/console" ] || { echo "No console build found. Run: ./dev.sh build"; exit 1; }
    chmod +x "$SERVER"/bin/*.sh
    "$SERVER/bin/catalina.sh" start
    echo "Starting... console: http://localhost:7000/console  (login admin/admin)" ;;
  stop)
    export CATALINA_HOME="$SERVER" CATALINA_BASE="$SERVER"
    "$SERVER/bin/catalina.sh" stop ;;
  status)
    if (exec 3<>/dev/tcp/127.0.0.1/7000) 2>/dev/null; then echo "running on :7000"; else echo "not running"; fi ;;
  *) echo "usage: $0 build|start|stop|status"; exit 1 ;;
esac
