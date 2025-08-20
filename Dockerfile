FROM debian
LABEL authors="iulian"

ARG JAVA_VERSION=17
ARG MAVEN_VERSION=3.9.0

ENV JAVA_HOME /usr/lib/jvm/java-$JAVA_VERSION-openjdk-arm64
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

#INSTALL JAVA17 and GIT
RUN apt update  \
    apt install -y openjdk-$JAVA_VERSION-jdk \
    apt install -y git

RUN export JAVA_HOME

#INSTALL MAVEN
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Define working directory.
WORKDIR /data

#4. git clone project /COPY project
#5. generate project dependencies /COPY dependecies

ENTRYPOINT ["top", "-b"]
