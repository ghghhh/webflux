FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/webflux-0.0.1-SNAPSHOT.jar webapp.jar
RUN sh -c 'touch /webapp.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /webapp.jar" ]
