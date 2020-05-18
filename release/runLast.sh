if [ -n "$JAVA_HOME" ]; then
  echo $JAVA_HOME;
else
  echo "JAva home is not set"
  exit 1
fi
java -jar simulator-1.5-jar-with-dependencies.jar