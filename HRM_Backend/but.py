from Tkinter import *
import tkMessageBox
root =Tk()
#frame=Frame(root)
#frame.pack()
def callback():
	tkMessageBox.showinfo( "hello oriental","hai")
frame=Frame(root)
frame.pack()
bottomframe=Frame(root)
bottomframe.pack(side=TOP)
#blackbutton.config(width=200,height=200)
blackbutton=Button(bottomframe,text="Create",fg="black",command=callback)
blackbutton.pack(side=TOP)
blackbutton.config(width=40,height=3)
#b=Button(root,text="Create",command=callback)
#b.pack()
blackbutton=Button(bottomframe,text="Recognize",fg="black",command=callback)
blackbutton.pack(side=TOP)
blackbutton.config(width=40,height=3)
#c=Button(root,text="Recognize",command=callback)
#c.pack()
blackbutton=Button(bottomframe,text="Train",fg="black",command=callback)
blackbutton.pack(side=TOP)
blackbutton.config(width=40,height=3)
#d=Button(root,text="Train",command=callback)
#d.pack()
blackbutton=Button(bottomframe,text="Quit",fg="black",command=callback)
blackbutton.pack(side=TOP)
blackbutton.config(width=40,height=3)
#e=Button(root,text="Quit",command=callback)
#e.pack()
root.mainloop()

