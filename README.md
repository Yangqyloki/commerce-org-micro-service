commerce-org micro service



env prepare:
1. install mongodb and create db using MongoDB.sql
2. install rabbitMQ
https://www.jianshu.com/p/14ffe0f3db94


test:
1. get token for asagent from b2b commerce
2. generate certification for commerce or else there's security exception when unitservice try to call commerce api
https://wiki.hybris.com/display/cloudss/Smartedit+is+not+working+after+Hybris+1811+upgrade


------run unit service------
3. get units for user with asagent token
GET http://127.0.0.1:10086/unitservice/v1/powertools/users/linda.wolf@rustic-hw.com/units 

4. create units for user with asagent token
POST http://127.0.0.1:10086/unitservice/v1/powertools/users/linda.wolf@rustic-hw.com/units 新增一个unit
{
	"unitId":"unitIdTest",
	"unitName":"unitNameTest",
	"parentUnit":"Rustic",
	"approvalProcess":"approvalProcess"
}


------run user service------
