from gmpy2 import *
global t,tlist
t = 3
#[0,1,2]
alist = range(t)
#[0,1]
blist = alist[0:t-1]
#function of calculate y[i]
def ff_m(s,m,c,p):
	yy = m
	for i in blist:
		pro = powmod(c,i+1,p)
		print('pro=',pro)
		pro = pro * s[i]
		print('pro=',pro)
		yy = yy + pro
		print('yy=',yy)
	yy =  yy % p
	print('yy=',yy)
	return yy
##
print('请输入要加密的信息 m=');
forencrypt = input()
m = mpz(forencrypt)
#素数p
status = random_state()
p = mpz_urandomb(status,100)
while is_prime(p)==False:
	p = mpz_urandomb(status,100)
#多项式系数
s = [0] * (t-1)
for i in blist:
	s[i] = mpz_urandomb(status,100)
#键值对
x = [0] * t
y = [0] * t

for i in alist:
	x[i] = mpz(i+1)
	y[i] = ff_m(s,m,x[i],p)
	print('(',x[i],',',y[i],')')
#重构算法，利用拉格朗日差值重构 m
sm = mpz(0)
for i in alist:
	pro = mpz(1)
	for j in alist:
		if j != i:
			tag = -x[j]
			l = x[i] - x[j]
			l = invert(l,p)
			l = tag * l
			pro = pro * l
	pro = pro * y[i]
	sm = sm + pro
sm =sm % p
print('m=',sm)
if sm == m:
	print('恢复密文成功')
else:
	print('恢复失败')
