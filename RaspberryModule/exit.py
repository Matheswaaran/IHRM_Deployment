import RPi.GPIO as GPIO
import time
#inPin=23
#inPin1=3
waitTime=5
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(23,GPIO.IN)
GPIO.setup(3,GPIO.IN)
GPIO.setup(8,GPIO.OUT)
GPIO.setup(7,GPIO.OUT)
GPIO.setup(11,GPIO.OUT)
now=time.time()
while(time.time()-now<waitTime):
        if GPIO.input(3)==1:
                now1=time.time()
                waitTime=5
                while(time.time()-now1<waitTime):
                #&& GPIO.input(3)==1:
                #GPIO.output(11,GPIO.LOW)
                #GPIO.output(8,GPIO.HIGH)
                #time.sleep(2)
                #GPIO.output(8,GPIO.LOW)

                #while(time.time()-now<waitTime)
                        if GPIO.input(23)==1:
                                GPIO.output(8,GPIO.HIGH)
                                time.sleep(2)
                                GPIO.output(8,GPIO.LOW)
                                if GPIO.input(23)==1:
                                        GPIO.output(7,GPIO.HIGH)
                                        GPIO.output(11,GPIO.HIGH)
                                        time.sleep(2)
                                        GPIO.output(7,GPIO.LOW)
                                        GPIO.output(11,GPIO.LOW)
                        elif time.time()-now>waitTime:
                                GPIO.output(7,GPIO.HIGH)
                                GPIO.output(11,GPIO.HIGH)
                                time.sleep(2)
   				GPIO.output(7,GPIO.LOW)
                                GPIO.output(11,GPIO.LOW)
        elif time.time()-now>waitTime:
                GPIO.output(7,GPIO.HIGH)
                GPIO.output(11,GPIO.HIGH)
                time.sleep(2)
                GPIO.output(7,GPIO.LOW)
                GPIO.output(11,GPIO.LOW)

