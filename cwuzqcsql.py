#!/usr/bin/env python

# 集合
coll = 'windcube'
# 塔ID
towerId = 111
# 更新前的时间
u_time = 1555333200000
# 更新后的时间
t_time = 1555430400000
# 生成多少条数据
step = 432


# db.getCollection('{}').update({'towerId': {}, 'date': {}}, {$set:{'date': NumberLong({})}});

# 标准更新语句
a = "db.getCollection('"
b = "').update({'towerId': "
c = ", 'date': "
d = "}, {$set:{'date': NumberLong("
e = ")}});"

for i in range(step):
	print(a + coll + b + str(towerId) + c + str(u_time) + d +str(t_time)+ e)
	u_time -= 600000
	t_time -= 600000