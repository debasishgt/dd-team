__author__ = 'dthakurta'

from direct.task import Task
import time, threading
import math


class PowerupTypes:
    def __init__(self):
        self.list = {1: {"name": "SHIELD", "rating": 20, "time": 5},
                     2: {"name": "BOOST", "rating": 20, "time": 5},
                     3: {"name": "INVINCIBLE", "rating": 0, "time": 5}}


class PowerupPositions:
    def __init__(self):
        self.positions = {
                1: {"id": 1, "x": 10, "y": 10, "z": 0},
                2: {"id": 2, "x": 20, "y": 20, "z": 0},
                3: {"id": 3, "x": 30, "y": 30, "z": 0},
                4: {"id": 4, "x": 40, "y": 40, "z": 0},
                5: {"id": 5, "x": 50, "y": 50, "z": 0},
                6: {"id": 6, "x": 60, "y": 60, "z": 0}
            }


class PowerupManager:
    #Max number of each powerup
    MAX_COUNT = 2
    POWERUP_RESET_TIME = 1

    def __init__(self, actors=None):
        self.actors = actors
        self.takenPowers = {}
        self.activePowers = {}

        powerupTypes = PowerupTypes()
        powerupPositions = PowerupPositions()
        for pwrKey, pwrVal in powerupTypes.list.iteritems():
            for x in range(PowerupManager.MAX_COUNT):
                #print key, val["id"], val["rating"], val["time"]
                posKey, posValue = powerupPositions.positions.popitem()
                posValue["item"] = pwrVal
                #print key, value
                self.activePowers[posKey] = posValue
                #print self.freeSlots

    def setActors(self, actors):
        self.actors = actors

    def checkPowerPickup(self, task):
        for actor in self.actors:
            self.powerPickup(actor.actorPos, actor.actorRadius)
        return task.cont

    def powerPickup(self, actorPos, actorRadius):
        powerObtained = None
        for key, powerUp in self.activePowers:
            if math.pow((actorPos.x-powerUp["x"]), 2) + math.pow((actorPos.y - powerUp["y"]), 2) + math.pow((actorPos.z - powerUp["z"]), 2) <= math.pow(actorRadius, 2):
                powerObtained = powerUp
                self.resetPowerupsTask(key)
                break

        return powerObtained

    def resetPowerupsTask(self, powerupId):
        processThread = threading.Thread(target=self.resetPowerup, args=(powerupId,))
        #Remove powerup from available list and add to taken list
        self.takenPowers[powerupId] = self.activePowers.get(powerupId)
        self.activePowers.pop(powerupId, 0)
        print self.activePowers
        print self.takenPowers
        #Start thread to reset powerup spawn
        processThread.start()

    def resetPowerup(self, powerupId):
        time.sleep(PowerupManager.POWERUP_RESET_TIME)
        #print self.takenPowers.get(powerupId)
        self.activePowers[powerupId] = self.takenPowers.get(powerupId)
        self.takenPowers.pop(powerupId, 0)
        print self.activePowers
        print self.takenPowers