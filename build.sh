if [ -n "$JAVA_HOME" ]; then
  echo $JAVA_HOME;
else
  echo "JAva home is not set"
  exit 1
fi
builder/apache-maven-3.6.3/bin/mvn.cmd clean package