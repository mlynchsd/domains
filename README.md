
Domains
===========
Domains is a toy service that tracks referrers to a website
and their counts by domain

Endpoints
-------------------------------
/referrals

  post:
    description:
    body:
      application/x-www-form-urlencoded:
        formParameters:
          url:
            type: string
            description: Referral url
            required: true
            example: http://www.cyclingnews.com
    responses:
      201:
        description: sucessfully recorded referral domain
        body:
          application/json:
            example:
      400:
        description: bad request
        body:
          application/json:
            example: { "errorMsg" : "Malformed url" }

Features
--------


Running
-------

mvn spring-boot:run

Sample requests:

curl -i -k -X POST -d "url=http://google.com" http://localhost:8080/referrals

curl -i -k -X GET http://localhost:8080/referrals?top=3
