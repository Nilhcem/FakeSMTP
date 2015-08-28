FROM java:8
VOLUME /mail-data
ADD fakeSMTP-2.1-SNAPSHOT.jar /fakeSMTP.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/fakeSMTP.jar","--background", "--output-dir", "/mail-data", "--port", "25"]
