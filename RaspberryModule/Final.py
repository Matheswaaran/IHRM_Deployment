from flask import Flask
from flask import jsonify

import RPi.GPIO as GPIO
import time
#inPin=23
#inPin1=3
#waitTime=5
#GPIO.setwarnings(False)
#GPIO.setmode(GPIO.BOARD)
#GPIO.setup(23,GPIO.IN)
#GPIO.setup(3,GPIO.IN)
#GPIO.setup(8,GPIO.OUT)
#GPIO.setup(7,GPIO.OUT)
#GPIO.setup(11,GPIO.OUT)
#now=time.time()

app = Flask(__name__)


@app.route("/")
def hello_world():
    return "Hello, World!"

@app.route("/entry")
def entry():
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
		if GPIO.input(23)==1:
			now1=time.time()
			waitTime=5
			while(time.time()-now1<waitTime): 		
				if GPIO.input(3)==1:
					GPIO.output(8,GPIO.HIGH)
					time.sleep(2)
					GPIO.output(8,GPIO.LOW)
					return jsonify(status=1)
					#worker entered
				elif time.time()-now>waitTime:
					GPIO.output(7,GPIO.HIGH)
					GPIO.output(11,GPIO.HIGH)
					time.sleep(2)
					GPIO.output(7,GPIO.LOW)
					GPIO.output(11,GPIO.LOW)
					return jsonify(status=0)
					#worker exit
		elif time.time()-now>waitTime:
			GPIO.output(7,GPIO.HIGH)
			GPIO.output(11,GPIO.HIGH)
			time.sleep(2)
			GPIO.output(7,GPIO.LOW)
			GPIO.output(11,GPIO.LOW)
			#worker didnt came
			return jsonify(status=0)



# start the development server using the run() method
if __name__ == "__main__":
    app.run(host="192.168.1.100", debug=True, port=5000)
