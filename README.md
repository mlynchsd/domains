
Domains
===========
Domains is a toy service that tracks referrers to a website
and their counts by domain

Running
-------

mvn spring-boot:run

Sample requests:

curl -i -k -X POST -d "url=http://google.com" http://localhost:8080/referrals

curl -i -k -X GET http://localhost:8080/referrals?top=3
