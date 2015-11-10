from traceback import print_exc

from direct.distributed.PyDatagram import PyDatagram
from common.Constants import Constants
from net.request.ServerRequest import ServerRequest
from Character import Character

class RequestCreateCharacter(ServerRequest):


    def send(self, args = None):

        try:
            pkg = PyDatagram()
            pkg.addUint16(Constants.CMSG_CREATE_CHARACTER)
            pkg.addString(args[0])
            pkg.addUint16(args[1]) #TYPE
            pkg.addFloat32(args[2]) #x pos
            pkg.addFloat32(args[3]) #y pos
            pkg.addFloat32(args[4]) #z pos

            print "RequestCreateCharacter - id: ", args[0],"type: ", args[1], " x:", args[2], " y:", args[3]," z:", args[4]

            self.cWriter.send(pkg, self.connection)

            #self.log('Sent [' + str(Constants.RAND_STRING) + '] Int Request')
        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] Int Request')
            print_exc()
