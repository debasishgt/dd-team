from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse


class ResponseChatAll(ServerResponse):
    def execute(self, data):

        try:
            self.id = data.getString()
            self.msg = data.getString()
            # self.otherId = data.getInt32()
            print "ResponseChatAll - ", self.msg
            self.temp = "/all/" + self.id + ": " + self.msg
            # if self.otherId == self.world.character.playerId or self.otherId == 0:
            self.world.chatbox.chat_memory.append(self.temp)
            # refresh chatbox
            self.world.chatbox.updateChat()

            # self.log('Received [' + str(Constants.CMSG_CHAT_ALL) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.CMSG_CHAT_ALL) + '] String Response')
            print_exc()
