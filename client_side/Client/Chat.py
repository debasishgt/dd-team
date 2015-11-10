__author__ = 'ASPIRE'

import direct.directbase.DirectStart
from direct.gui.OnscreenText import OnscreenText
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.showbase.DirectObject import DirectObject


class Chat:
    def __init__(self, connection, world):

        self.chat_memory = []  # store all chats
        # index corresponds to user number
        # self.chat0 = []
        # self.chat1 = []
        # self.chat2 = []
        # self.chat3 = []
        # self.chat4 = []
        # self.chat5 = []
        # # chats are a list of lists
        # self.chat_memory.append(self.chat0)
        # self.chat_memory.append(self.chat1)
        # self.chat_memory.append(self.chat2)
        # self.chat_memory.append(self.chat3)
        # self.chat_memory.append(self.chat4)
        # self.chat_memory.append(self.chat5)

        self.cManager = connection
        self.world = world
        self.visible = False

        self.chat_text = ""

        self.initialText = "<Click to Chat>"
        # current view window
        self.current_chat_window = 0

        # add frame
        self.chatFrame = DirectFrame(frameColor=(0, 0, 0, 0), frameSize=(0, 1, 0, 1), pos=(-1.4, 0, -1))

        # allChatFrame = DirectScrolledFrame(parent=chatFrame, canvasSize=(-1, .9, -1, .9), frameSize=(0, 1, 0, 1),
        # autoHideScrollBars=False)

        self.chatObject = OnscreenText(parent=self.chatFrame, text=self.chat_text, style=1, fg=(1, 1, 1, 1),
                                       pos=(0.1, .9), align=TextNode.ALeft, scale=.05)



        # allChatFrame = DirectScrolledList(parent=chatFrame, frameSize=(0, 1, 0, 1), pos=(0, 0, 1))

        # chatObject = DirectLabel(parent=allChatFrame, text=bk_text, pos=(.2, 0, .9), scale=0.07)

        # textObject = OnscreenText(parent=allChatFrame, text=bk_text, pos=(0.02, .9),
        # scale=0.07, fg=(1, 1, 1, 1), align=TextNode.ALeft, mayChange=1)
        self.b = DirectEntry(parent=self.chatFrame, text="", scale=.05,
                             command=self.sendChat, width=15,
                             initialText=self.initialText, numLines=1, focus=1, focusInCommand=self.clearText,
                             focusOutCommand=self.defaultText, pos=(.1, 0, .01))
        self.chatFrame.hide()
        print "chat initialized"

    def show(self, other_id):
        if self.current_chat_window != 0:
            self.initialText = "<Username>/"
        else:
            self.initialText = "(Broadcast)"
        self.chatFrame.show()
        self.current_chat_window = other_id
        self.updateChat()

    def updateChat(self):  # callback function to set text
        self.chatObject.destroy()

        # while self.chat_memory[other_id].__len__() >= 15:
        while self.chat_memory.__len__() >= 15:
            del self.chat_memory[0]
        self.chat_text = ""
        i = self.chat_memory.__len__()
        for line in self.chat_memory:
            self.i = i - 1

            self.chat_text += "\n" + line
            # DirectLabel(parent=chatFrame, text=chat_text, pos=(0.01, 0, .9), scale=0.05, text_align=TextNode.ALeft)
        if self.visible:
            self.chatObject = OnscreenText(parent=self.chatFrame, text=self.chat_text, style=1, fg=(1, 1, 1, 1),
                                           pos=(0.1, .09 + i * .05), align=TextNode.ALeft, scale=.05)

    def hide(self):
        self.chatFrame.hide()

    # send input to server

    def clearText(self):  # clear the text
        self.b.enterText('')

    def defaultText(self):  # default text
        self.b.enterText('<Click to Chat>')

    def setVisible(self, boolean):
        self.visible = boolean

    def getVisible(self):
        return self.visible

    def sendChat(self, text_entered):
        # parse text
        # cmd = textEntered.split(" ")
        # if cmd[0] == "/all":
            # broadcast to all
        print "request sent"
        if self.current_chat_window == 0:
            self.cManager.sendRequest(105, [self.world.mainCharRef.playerId, text_entered])
        else:
            temp = text_entered.split("/")  # splits the user name from the message
            usr_len = len(temp[0]) + 1
            msg = text_entered[usr_len:]
            # print len(temp[0])
            self.cManager.sendRequest(106, [self.world.mainCharRef.playerId, temp[0], msg])

            # print "sent ", textEntered

# chatbox()
# run()
