from direct.distributed.PyDatagram import PyDatagram
from common.Constants import Constants
from net.request.ServerRequest import ServerRequest


class RequestKey(ServerRequest):

  #args stores the x y z components of the moving
    def send(self, args = []):

        try:
            #print "In Req", args[0], args[1]
            pkg = PyDatagram()
            pkg.addUint16(Constants.CMSG_KEY)
            pkg.addString(args[0])
            pkg.addFloat32(args[1])


            self.cWriter.send(pkg, self.connection)

            #print("sent move: ", args[0], " ",args[1], " ", args[2])
            #self.log('Sent [' + str(Constants.RAND_FLOAT) + '] Float Request')
        except:
            self.log('Bad [' + str(Constants.CMSG_KEY) + '] Key Request')
