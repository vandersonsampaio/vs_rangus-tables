FROM tomcat:9
MAINTAINER vs-rangus-table
COPY tables.controller/build/libs/table.controller-* /usr/local/tomcat/webapps/rangus-table.war