if [ -n "$JAVA_HOME" ]; then
  echo $JAVA_HOME;
else
  echo "JAva home is not set"
  exit 1
fi
java -jar simulator-1.4-jar-with-dependencies.jar