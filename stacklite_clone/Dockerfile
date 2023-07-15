FROM ubuntu:latest
RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y
#add jar
ADD ./target/stacklite_clone-0.0.1-SNAPSHOT.jar stacklite_clone.jar
EXPOSE 3000
CMD java -jar stacklite_clone.jar