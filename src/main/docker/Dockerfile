FROM java:8
VOLUME /tmp
ADD springboot-1.jar cceg.jar
RUN sh -c 'touch /cceg.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /cceg.jar" ]