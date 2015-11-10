__author__ = 'debasish'

from pandac.PandaModules import *
from direct.gui.DirectGui import *
import direct.directbase.DirectStart
from random import random
from net.ConnectionManager import ConnectionManager
import finalhw1
import time
from common.Constants import Constants

class DirectButtonHandler:
    def __init__(self, type, zPos, parentObj=None, buttonFrame=None):
        self.parentObj = parentObj
        if type == "Register":
            self.asset = DirectButton(text=type, scale=.1, pos=(0,0, zPos), command=self.registerUser)
        elif type == "Login":
            self.asset = DirectButton(text=type, scale=.1, pos=(0,0, zPos), command=self.loginUser)
        elif type == "Select Character":
            self.asset = DirectButton(parent = buttonFrame, text = ("Submit", "Submit", "Submit", "disabled"),scale=.1, pos=Vec3(0,0,0), command=self.getCharChoice)

    def getCharChoice(self):
        #print "GetCharChoice"
        #tempChoice = self.charHandle.getChoice()
        self.parentObj.chosenCharId = self.charHandle.getChoice()
        #print self.parentObj.chosenCharId
        self.parentObj.clearEverything()
        self.parentObj.worldManager.runWorld(self.parentObj.playerId, self.parentObj.chosenCharId, self.parentObj.x, self.parentObj.y, self.parentObj.h)

    def getAsset(self):
        return self.asset
    def setUidPwd(self, uid, pwd):
        self.de = uid
        self.de2 = pwd
    def setCharHandle(self, inHandle):
        self.charHandle = inHandle
    def registerUser(self):
        self.username = self.de.getAsset().get()
        self.password = self.de2.getAsset().get()
        #print username, password
        self.parentObj.connectionManager.sendRequest(Constants.CMSG_REGISTER, [self.username, self.password])

        taskMgr.add(self.waitForRegister, "waitForRegister")

    def waitForLogin(self, task):
        if self.parentObj.loginState == 1:
            #print "test"
            self.parentObj.clearEverything()
            print self.parentObj.chosenCharId
            res = self.parentObj.chosenCharId != UserGUIHandler.endCharId
            print "Comparing waitForLogin", self.parentObj.chosenCharId, UserGUIHandler.endCharId, res
            if res:
                self.parentObj.worldManager.runWorld(self.parentObj.playerId, self.parentObj.chosenCharId, self.parentObj.x, self.parentObj.y, self.parentObj.h)
            else:
                self.parentObj.clearEverything()
                self.parentObj.characterSelect()
            return task.done
        elif self.parentObj.loginState == 0:
            self.parentObj.errorLbl["text"] = "Login Fail"
        #print "test"
        return task.cont

    def loginUser(self):
        self.parentObj.playerId = self.username = self.de.getAsset().get()
        self.password = self.de2.getAsset().get()
        #print username, password
        self.parentObj.connectionManager.sendRequest(Constants.CMSG_AUTH, [self.username, self.password])

        taskMgr.add(self.waitForLogin, "waitForLogin")

    def waitForRegister(self, task):
        if self.parentObj.registerState == 1:
            #print "test"
            self.parentObj.clearEverything()
            self.parentObj.login()
            return task.done
        elif self.parentObj.registerState == 0:
            self.parentObj.errorLbl["text"] = "Registration Fail"
        #print "test"
        return task.cont



class DirectEntryHandler:
    def __init__(self, zPos, isObscured):
        self.asset = DirectEntry(initialText="", scale=.1, width=11, numLines=1, obscured=isObscured, pos=Vec3(-.55,0,zPos), command=self.setasset)
        # DirectEntry(initialText="password",scale=.4,width=10,numLines=1, command=setpassword)

    def setasset(self):
        print self.asset.get()

    def getAsset(self):
        return self.asset


class DirectRadioButtonHandler:
    def __init__(self):
        # Add button
        self.v = [1]
        self.buttons = [
            DirectRadioButton(text = 'Ralph', indicatorValue=0, variable=self.v, value=[1], scale=0.07, pos=(-0.3,0,0.2), command=self.setText),
            DirectRadioButton(text = 'Panda', indicatorValue=0, variable=self.v, value=[2], scale=0.07, pos=(0.1,0,0.2), command=self.setText),
            DirectRadioButton(text = 'Bike', indicatorValue=0, variable=self.v, value=[3], scale=0.07, pos=(0.5,0,0.2), command=self.setText)
        ]
        for self.button in self.buttons:
            self.button.setOthers(self.buttons)

    def setText(self, status=None):
        self.bk_text = self.v[0]
        print type(self.bk_text)
        print "Value:",int(self.bk_text)
        #print self.bk_text

    def getChoice(self):
        return self.bk_text

    def hide(self):
        for self.button in self.buttons:
            self.button.hide()

class UserGUIHandler:
    endCharId = 0
    def __init__(self):
        self.frame = None
        self.buttonFrame = None
        self.dl = None
        self.dl2 = None
        self.dl3 = None
        self.regSel = None
        self.loginSel = None
        self.uid = None
        self.pwd = None
        self.reg = None
        self.submit = None
        self.lbl = None
        self.errorLbl = None

        self.directRadioButtonHandler = None

        print "UserGUIHandler Init"
        self.playerId = ""
        self.loginState = -1
        self.registerState = -1
        self.worldManager = finalhw1.WorldManager()
        self.world = self.worldManager.w
        self.connectionManager = ConnectionManager(self)
        self.world.setConnectionManager(self.connectionManager)
        self.connectionManager.startConnection()

    def register(self):
        self.clearEverything()
        self.createFrame(-0.7)
        self.errorLbl = DirectLabel(text="", pos=Vec3(0,0,.55),frameColor=(.5,.5,.5,.5), scale=0.11)
        self.dl = DirectLabel(text="", pos=Vec3(0,0,0.8),frameColor=(.3,.2,.1,.5), scale=0.11)
        self.dl["text"] = "Welcome To Game Registration Page"
        self.dl2 = DirectLabel(text="", pos=Vec3(0,0,0.4),frameColor=(.5,.5,.5,.5), scale=0.1)
        self.dl2["text"] = "USERNAME"
        self.uid = DirectEntryHandler(0.2, 0)

        self.dl3 = DirectLabel(text="", pos=Vec3(0,0,0.0),frameColor=(.5,.5,.5,.5), scale=0.1)
        self.dl3["text"] = "PASSWORD"
        self.pwd = DirectEntryHandler(-0.2, 1)

        self.reg = DirectButtonHandler("Register", -0.4, self)
        self.reg.setUidPwd(self.uid, self.pwd)

    def showFirstPage(self):

        self.createFrame(-0.7)

        self.dl = DirectLabel(text="", pos=Vec3(0,0,0.8),frameColor=(.3,.2,.1,.5), scale=0.11)
        self.dl["text"] = "Welcome To New Game"
        self.dl2 = DirectLabel(text="", pos=Vec3(0,0,.5),frameColor=(.5,.5,.5,.5), scale=0.11)
        self.dl2["text"] = "Click for Registration"
        self.dl3 = DirectLabel(text="", pos=Vec3(0,0,-.1),frameColor=(.5,.5,.5,.5), scale=0.11)
        self.dl3["text"] = "Click for Login"
        self.regSel = DirectButton(text="Register", scale=.1, pos=(0,0, .3), command=self.initRegister)

        self.loginSel = DirectButton(text="Login", scale=.1, pos=(0,0, -.3), command=self.initLogin)

    def initRegister(self):
        self.clearEverything()
        self.register()
    def initLogin(self):
        self.clearEverything()
        self.login()
    def setLogin(self, state, inCharId, x, y, h):
        self.loginState = state
        self.chosenCharId = inCharId
        self.x = x
        self.y = y
        self.h = h

    def setRegister(self, state):
        self.registerState = state

    def createFrame(self, inLength):
        self.clearEverything()
        self.frame = DirectFrame(relief = DGG.RAISED, borderWidth = (0.05,0.05), frameSize = (-1,1,-1,1), frameColor=(.3,.2,.1,.5))
        self.buttonFrame = DirectFrame(parent=self.frame, relief=DGG.RAISED, borderWidth=(0.05,0.05), frameSize=(-.7,.7,inLength,.7), frameColor=(.5,.5,.5,.5), pos=(-0,0,0))

    def login(self):
        self.clearEverything()
        self.createFrame(-0.7)

        self.dl = DirectLabel(text="", pos=Vec3(0,0,0.8),frameColor=(.3,.2,.1,.5), scale=0.11)
        self.dl["text"] = "Enter Your Personal Details To Login"
        self.errorLbl = DirectLabel(text="", pos=Vec3(0,0,.55),frameColor=(.5,.5,.5,.5), scale=0.11)
        self.dl2 = DirectLabel(text="", pos=Vec3(0,0,0.4),frameColor=(.5,.5,.5,.5), scale=0.1)
        self.dl2["text"] = "USERNAME"
        self.uid = DirectEntryHandler(0.2, 0)

        self.dl3 = DirectLabel(text="", pos=Vec3(0,0,0.0),frameColor=(.5,.5,.5,.5), scale=0.1)
        self.dl3["text"] = "PASSWORD"
        self.pwd = DirectEntryHandler(-0.2, 1)

        self.reg = DirectButtonHandler("Login", -0.4, self)
        self.reg.setUidPwd(self.uid, self.pwd)

    def characterSelect(self):
        self.clearEverything()
        self.createFrame(-0.3)

        self.directRadioButtonHandler = DirectRadioButtonHandler()
        self.lbl = DirectLabel(parent = self.buttonFrame, text="", pos=Vec3(0,0,0.4),frameColor=(.5,.5,.5,.5), scale=0.1)
        self.lbl["text"] = "Choose Character"
        self.submit = DirectButtonHandler("Select Character", -0.4, self)
        self.submit.setCharHandle(self.directRadioButtonHandler)
        #self.runWorld = finalhw1.RunWorld()

    def clearEverything(self):
        if self.frame != None:
            self.frame.hide()
        if self.buttonFrame != None:
            self.buttonFrame.hide()
        if self.dl != None:
            self.dl.hide()
        if self.dl2 != None:
            self.dl2.hide()
        if self.dl3 != None:
            self.dl3.hide()
        if self.regSel != None:
            self.regSel.hide()
        if self.loginSel != None:
            self.loginSel.hide()
        if self.uid != None:
            self.uid.asset.hide()
        if self.pwd != None:
            if self.pwd.asset != None:
                self.pwd.asset.hide()
        if self.reg != None:
            if self.reg.asset != None:
                self.reg.asset.hide()
        if self.submit != None:
            if self.submit.getAsset() != None:
                self.submit.getAsset().hide()
        if self.lbl != None:
            self.lbl.hide()
        if self.directRadioButtonHandler != None:
            self.directRadioButtonHandler.hide()
        if self.errorLbl != None:
            self.errorLbl.hide()

    def setText(self, status=None):
        if(self.v):
            print "0"
        else:
            print self.v
        bk_text = "CurrentValue : %s"%self.v
        print bk_text
        #textObject.setText(bk_text)


if __name__ == '__main__':

    # Class for handling GUI
    userGUIHandler = UserGUIHandler()

    #userGUIHandler.showFirstPage()
    #Login Page
    #userGUIHandler.login()

    #Registration Page
    #userGUIHandler.register()

    #Select Character UI Page
    #userGUIHandler.characterSelect()


    userGUIHandler.worldManager.runWorld(1, 3, 0, 0, 0)
    run()
