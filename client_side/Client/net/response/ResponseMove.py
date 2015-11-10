from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse

class ResponseMove(ServerResponse):

    def execute(self, data):

        try:
            self.playerId = data.getString()
            self.x = data.getFloat32()
            self.y = data.getFloat32()
            self.z = data.getFloat32()
            self.h = data.getFloat32()


            for character in self.world.characters :
              if character.playerId == self.playerId :
                moving_vector = character.actor.getPos()
#                 character.actor.setPos( moving_vector.getX() + self.x, moving_vector.getY() + self.y, moving_vector.getZ() + self.z)
                character.actor.setPos( self.x, self.y, self.z )
                character.actor.setH(self.h)
                break

            #print "ResponseMove - ",self.playerId," moved ", self.x, " ", self.y, " ", self.z

            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
