from traceback import print_exc

from common.Constants import Constants
from net.response.ServerResponse import ServerResponse

class ResponseDisconnect(ServerResponse):

    def execute(self, data):

        try:
            self.playerId = data.getString()

            for character in self.world.characters :
              if character.playerId == self.playerId :
                print("Removing ", character )
                character.removeCharacter()
                self.world.characters.remove(character)
                print(self.world.characters)
                break


            #self.log('Received [' + str(Constants.RAND_STRING) + '] String Response')

        except:
            self.log('Bad [' + str(Constants.RAND_STRING) + '] String Response')
            print_exc()
