from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse

class ResponseLogin(ServerResponse):

    def execute(self, data):

        try:
            self.playerId = data.getString()
            self.msg = data.getUint16()
            self.charId = data.getUint16()

            self.x = data.getFloat32()
            self.y = data.getFloat32()
            self.h = data.getFloat32()

            print "ResponseLogin - ", self.msg

            if self.msg == 0:
                self.refObj.setLogin(0, self.charId, self.x, self.y, self.h)
            elif self.msg == 1:
                self.refObj.setLogin(1, self.charId, self.x, self.y, self.h)
            else:
                self.refObj.setLogin(-1, self.charId, self.x, self.y, self.h)

            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
