from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse

class ResponseRegister(ServerResponse):

    def execute(self, data):

        try:
            self.playerId = data.getString()
            self.msg = data.getUint16()

            print "ResponseRegister - ", self.msg

            if self.msg == 0:
                self.refObj.setRegister(0)
            elif self.msg == 1:
                self.refObj.setRegister(1)
            else:
                self.refObj.setRegister(-1)

            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
