# import the necessary packages
from Tkinter import *
from PIL import ImageTk
import tkFileDialog
import cv2
import os
import numpy as np
from PIL import Image
import os
import requests
import httplib2


cam = cv2.VideoCapture(0)
detector=cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

def sendRquesttorasp(id):
	print "sending request to pi"
	resp, content = httplib2.Http().request("http://192.168.1.100:5000/entry")
	print content
	return


def facecreation():
	print ("**********Worker Creation**********")
	Id=raw_input('enter your id: ')
	sampleNum=0

	while(True):
	    ret, img = cam.read()
	    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	    faces = detector.detectMultiScale(gray, 1.3, 5)
	    for (x,y,w,h) in faces:
	        cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
	        #incrementing sample number
	        sampleNum=sampleNum+1
	        #saving the captured face in the dataset folder
	        cv2.imwrite("dataSet/User."+Id +'.'+ str(sampleNum) + ".jpg", gray[y:y+h,x:x+w]) #

	        cv2.imshow('frame',img)
	    #wait for 100 miliseconds
	    if cv2.waitKey(100) & 0xFF == ord('q'):
	        break
	    # break if the sample number is morethan 20
	    elif sampleNum>30:
	        break

	cam.release()
	cv2.destroyAllWindows()
	train()
	print ("**********Face Creation Successful**********")
	return

def getImagesAndLabels(path):
    #get the path of all the files in the folder
    imagePaths=[os.path.join(path,f) for f in os.listdir(path)]
    #create empth face list
    faceSamples=[]
    #create empty ID list
    Ids=[]
    #now looping through all the image paths and loading the Ids and the images
    for imagePath in imagePaths:
        #loading the image and converting it to gray scale
        pilImage=Image.open(imagePath).convert('L')
        #Now we are converting the PIL image into numpy array
        imageNp=np.array(pilImage,'uint8')
        #getting the Id from the image
        Id=int(os.path.split(imagePath)[-1].split(".")[1])
        # extract the face from the training image sample
        faces=detector.detectMultiScale(imageNp)
        #If a face is there then append that in the list as well as Id of it
        for (x,y,w,h) in faces:
            faceSamples.append(imageNp[y:y+h,x:x+w])
            Ids.append(Id)
    return faceSamples,Ids

def train():
	if os.path.isfile("dataSet/.DS_Store"):
		os.remove("dataSet/.DS_Store")
	print ("**********Training the dataset**********")
	recognizer = cv2.face.createLBPHFaceRecognizer()
	detector= cv2.CascadeClassifier("haarcascade_frontalface_default.xml");
	faces,Ids = getImagesAndLabels('dataSet')
	recognizer.train(faces, np.array(Ids))
	recognizer.save('trainner/trainner.yml')
	print ("**********Training Completed**********")
	return

def recognise():
	recognizer = cv2.face.createLBPHFaceRecognizer()
	recognizer.load('trainner/trainner.yml')
	cascadePath = "haarcascade_frontalface_default.xml"
	faceCascade = cv2.CascadeClassifier(cascadePath);
	cam = cv2.VideoCapture(0)
	#font = cv2.cv.InitFont(cv2.cv.CV_FONT_HERSHEY_SIMPLEX, 1, 1, 0, 1, 1)
	try:
	    while True:
	        ret, im =cam.read()
	        gray=cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)
	        faces=faceCascade.detectMultiScale(gray, 1.2,5)
	        for(x,y,w,h) in faces:
	            cv2.rectangle(im,(x,y),(x+w,y+h),(225,0,0),2)
	            Id = recognizer.predict(gray[y:y+h,x:x+w])
	            print (Id)
	            sendRquesttorasp(Id)
	            return
	            #cv2.cv.PutText(cv2.cv.fromarray(im),str(Id), (x,y+h),font, 255)
	        cv2.imshow('im',im)
	        if cv2.waitKey(10) & 0xFF==ord('q'):
	            break
	except Exception, e:
	    pass

	cam.release()
	cv2.destroyAllWindows()
	return

def pin():
	print ("**********PIN System Start**********")
	PIN = raw_input('enter your PIN: ')
	sendRquesttorasp(PIN)
	print ("**********PIN System End**********")
	return



if __name__ == '__main__':
	print ("**********Welcome to project HRM**********\n")

	option = 1
	while option < 4:
		print ("Select the option\n")
		print ("1)Create a worker\n")
		print ("2)Recognise\n")
		print ("3)Train the dataset\n")
		print ("4)PIN System\n")
		print ("5)Quit\n")
		option = int(raw_input('Enter the option : '))

		if option < 5:
			if (option == 1):
				facecreation()
			if (option ==2):
				recognise()
			if (option ==3):
				train()
			if (option ==4):
				pin()
		pass
	print ("**********Quiting the application**********")


cam.release()
cv2.destroyAllWindows()
