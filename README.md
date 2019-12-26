# commerce-org micro service



## env prepare:
1. install mongodb and create db using MongoDB.sql
2. install rabbitMQ
https://www.jianshu.com/p/14ffe0f3db94


## using steps:
1. get token for asagent from b2b commerce

2. generate certification for commerce or else there's security exception when unitservice try to call commerce api.

	**Note** 
	the certificate is issued for 127.0.0.1, so should run the commerce at local and add the cert to keystore at local.  the same cert installed at demo server(vm) doesn't work.
	https://wiki.hybris.com/display/cloudss/Smartedit+is+not+working+after+Hybris+1811+upgrade

	-------------------------------------------

	brief steps to trust the cert at local:

	- get cert from commerce server

		`openssl s_client -showcerts -connect localhost:9002`

		You will see details of the commerce certificate. You have to copy its content to notepad (from -----BEGIN CERTIFICATE----- to -----END CERTIFICATE-----):

	- create empty file named hybris.pem, then paste the content of copied certificate to that file

	- optional step. Check the "hybris.pem" file to check if it was copied properly:

		`sudo keytool -printcert -v -file hybris.pem`

	- Import new certificate to java keystore:

		```sudo keytool -import -alias hybris -file hybris.pem -keystore /usr/lib/jvm/java/jre/lib/security/cacerts```

	-------------------------------------------


### run unit service

3. get units for user with asagent token
	GET http://127.0.0.1:10086/unitservice/v1/powertools/users/linda.wolf@rustic-hw.com/units 

4. create units for user with asagent token
	POST http://127.0.0.1:10086/unitservice/v1/powertools/users/linda.wolf@rustic-hw.com/units 新增一个unit

	```
	{
		"unitId":"unitIdTest",
		"unitName":"unitNameTest",
		"parentUnit":"Rustic",
		"approvalProcess":"approvalProcess"
	}
	```

5. create user for unit (this step will trigger connect to rabbitMQ and create queue)
	http://127.0.0.1:10086/unitservice/v1/powertools/users/linda.wolf@rustic-hw.com/units/unitIdTest/customers
	```
	{
		"title":"mr",
		"firstName":"Test firstName",
		"lastName":"Test lastName",
		"email":"1@qq.com",
		"mobile":"13013001300",
		"parentUnit":"Rustic",
		"roles":["customergroup","b2bgroup"]
	}
	```


### run user service

**Should wait step 5 above is run or else there's error for trying to get message queue which is not exist!!!**
