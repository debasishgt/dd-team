from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse


class ResponseChatOne(ServerResponse):
    def execute(self, data):

        try:
            self.sender_id = data.getString()
            self.receiver_id = data.getString()
            self.msg = data.getString()

            print "ResponseChatOne - ", self.sender_id, self.receiver_id, self.msg

            self.temp = "/private/" + self.sender_id + ": " + self.msg
            self.world.chatbox.chat_memory.append(self.temp)

            # refresh chatbox
            self.world.chatbox.updateChat()

            # self.log('Received [' + str(Constants.CMSG_CHAT_ALL) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.CMSG_CHAT_ONE) + '] String Response')
            print_exc()
