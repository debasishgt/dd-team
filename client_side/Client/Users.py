__author__ = 'ASPIRE'
import direct.directbase.DirectStart
from direct.gui.OnscreenText import OnscreenText
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.showbase.DirectObject import DirectObject


class Users:
    def __init__(self, connection, world):
        self.cManager = connection
        self.world = world
        self.visible = False
        self.display_users = "Connected Users"
        # self.active_users = ["Connected Users"]
        self.users_object = OnscreenText(text=self.display_users, style=1, fg=(1, 1, 1, 1),
                                         pos=(0, .9), align=TextNode.ALeft, scale=.05, mayChange=1)
        self.users_object.hide()
        self.cManager.sendRequest(110)

    def get_active_users(self):
        return self.active_users

    def add(self, username):
        self.active_users.append(username)

    def remove(self, username):
        self.active_users.remove(username)

    def show(self):
        self.update()
        self.users_object.show()

    def hide(self):
        self.users_object.hide()

    def toggle(self):
        if self.visible == False:
            self.show()
            self.visible = True
        else:
            self.hide()
            self.visible = False

    def update(self):
        self.display_users = "Connected Users\n"
        # print self.world.characters.__len__()
        # print self.world.characters.__s
        for user in self.world.characters:
            print user.getPlayerId()
            self.display_users = self.display_users + user.getPlayerId() + "\n"
        self.users_object = OnscreenText(text=self.display_users, style=1, fg=(1, 1, 1, 1),
                                         pos=(0, .9), align=TextNode.ALeft, scale=.05)
