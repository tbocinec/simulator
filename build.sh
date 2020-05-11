if [ -n "$JAVA_HOME" ]; then
  echo $JAVA_HOME;
else
  echo "JAva home is not set"
  exit 1
fi
if ! [ -x "$(mvn -v )" ]; then
  echo 'Error: maven is not install.' >&2
  exit 1
fi
mvn clean package