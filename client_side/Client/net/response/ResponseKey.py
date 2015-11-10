__author__ = 'dthakurta'
from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse

class ResponseKey(ServerResponse):

    def execute(self, data):
        try:
            self.playerId = data.getString()
            self.moveKey = data.getString()
            self.moveKeyVal = data.getFloat32()
            #print "In Key Response", self.moveKey, self.moveKeyVal
            for character in self.world.characters :
              if character.playerId == self.playerId :
                #moving_vector = character.actor.getPos()
                character.setCharKey( self.moveKey, self.moveKeyVal)
                break

            #print "ResponseMove - ",self.playerId," moved ", self.moveKey, " ", self.moveKeyVal

            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
