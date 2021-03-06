from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse
# from finalhw1 import Character
from Character import Character


class ResponseCreateCharacter(ServerResponse):

    def execute(self, data):

        try:
            self.playerId = data.getString()
            self.type = data.getUint16()
            self.x = data.getFloat32()
            self.y = data.getFloat32()
            self.z = data.getFloat32()


            #print "q"
            char = Character(self.world, self.type)

            char.actor.setPos(self.x,self.y,0)
            char.setPlayerId(self.playerId)
            charPresent = False
            for charTemp in self.world.characters:
                if charTemp.getPlayerId() == char.playerId:
                    charPresent = True
                    break

            if not charPresent:
                self.world.characters.append(char)

            print "ResponseCreateCharacter - ", self.playerId, " x:", self.x, " y:", self.y," z:", self.z

            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
