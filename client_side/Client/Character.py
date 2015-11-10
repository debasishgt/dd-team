# Roaming-Ralph was modified to remove collision part.

import direct.directbase.DirectStart
from panda3d.core import Filename,AmbientLight,DirectionalLight
from panda3d.core import PandaNode,NodePath,Camera,TextNode
from panda3d.core import Vec3,Vec4,BitMask32
from direct.gui.OnscreenText import OnscreenText
from direct.actor.Actor import Actor
from direct.showbase.DirectObject import DirectObject
import random, sys, os, math
from panda3d.core import Point3, Plane
from panda3d.core import CollisionTraverser,CollisionNode, CollisionSphere
from panda3d.core import CollisionHandlerQueue,CollisionRay, CollisionHandlerPusher, CollisionPlane
from direct.interval.IntervalGlobal import Sequence
from direct.task import Task
import time



class Car:
    def __init__(self, type):
        self.armor = 0
        self.type = type
        if self.type == 1:
            self.actor = Actor("models/ralph",
                         {"run":"models/ralph-run",
                          "walk":"models/ralph-walk"})
            self.actor.setScale(.2)




class Character:
    def __init__(self, tempworld, type, isMain=False):
        self.world = tempworld
        self.isMainChar = isMain
        self.playerId = 0

        self.weight = 0
        self.armor = 0
        self.speed = 0
        self.health = 100

        self.type = type
        #print "w"
        print "Character create type: ", type
        if type == 1:
            self.actor = Actor("models/ralph",
                         {"run":"models/ralph-run",
                          "walk":"models/ralph-walk"})
            self.actor.setScale(.2)
            self.weight = 50
            self.armor = 0
            self.speed = 0
        elif type ==2 :
            self.actor=Actor("models/panda-model",
                     {"walk": "models/panda-walk4",
                      "run": "models/panda-walk4"})
            self.actor.setScale(0.002, 0.002, 0.002)
            self.weight = 100
            self.armor = 0
            self.speed = 0
        elif type == 3:
            self.actor = loader.loadModel("knucklehead.egg")
            self.tex = loader.loadTexture("knucklehead.jpg")
            self.actor.setTexture(self.tex, 1)
            self.actor.setScale(.04)
            self.actor.setP(-90)
            self.actor.setColor(0.6, 0.6, 1.0, 1.0)
            self.actor.setColorScale(0.6, 0.6, 1.0, 1.0)
        #print "x"
        self.actor.reparentTo(render)
        self.actor.setPos(50*random.random(), 50*random.random(), 0)

        # Create a collsion node for this object.
        self.cNode = CollisionNode('char')
        # Attach a collision sphere solid to the collision node.
        self.cNode.addSolid(CollisionSphere(0, 0, 3, 3))
        # Attach the collision node to the object's model.
        self.smileyC = self.actor.attachNewNode(self.cNode)
        #self.frowneyC.show()
        #print "y"
        #self.world.bTrav.addCollider(self.smileyC, self.world.pusher)
        #print "y1"
        #self.world.pusher.addCollider(self.smileyC, self.actor, base.drive.node())
        #print "z"
        if self.isMainChar == False:
            self.isMoving = False
            self.keyMap = {"left":0, "right":0, "forward":0, "backward":0}
            taskMgr.add(self.moveChar,"moveCharTask")

        # Bouding box radius
        pt1, pt2 = self.actor.getTightBounds()
        xDim = pt2.getX() - pt1.getX()
        yDim = pt2.getY() - pt1.getY()
        self.boundingRadius = max(xDim, yDim)


    def calculateDamage(self, fromCar, toCar, fromCollisionSection, toCollisionSection):
        #toCar takes more damage than fromCar

        fromWeight = fromCar.weight
        toWeight = toCar.weight
        fromSpeed = fromCar.speed
        toSpeed = toCar.speed

        #Speed Max = 100
        #Weights Max = 10
        #Front collisionSection = 3, mid = 2, back = 1
        damageFactor = (((fromWeight + toWeight) * (fromSpeed + toSpeed)) / 100)

        fromDamage = .2 * damageFactor / fromCollisionSection
        toDamage = .8 * damageFactor / toCollisionSection


        fromCar.armor -= fromDamage
        if fromCar.armor < 0:
            fromCar.armor = 0
            fromCar.health -= fromCar.armor

        toCar.armor -= toDamage
        if toCar.armor < 0:
            toCar.armor = 0
            toCar.health -= toCar.armor


    def calculateTilt(self, fromCar, toCar, fromCollisionSection, toCollisionSection):

        fromAngle = fromCar.actor.getH()%360
        toAngle = toCar.getH()%360
        #front to front section collision - Cars move away from each other, meet at a mid angle and become parallel
        #front to back section collision - Cars move to achieve a 180 degree diff, meet at a mid angle and become opposite to each other
        #back to back - Cars move away from each other, meet at a mid angle and become parallel
        factorAngle = 0
        diffAngle =  fromAngle - toAngle
        if diffAngle < 0:
            diffAngle *= -1
        if diffAngle%180 == 0:
            print "Head On or Parallel or back to back"
        if diffAngle % 90 == 0:
            print "Max Damage for vertical hit"

        if fromCollisionSection == 1:
            print fromAngle
            print toAngle
            if fromAngle > 180:
                diffAngle = toAngle - factorAngle
        elif fromCollisionSection == 2:
            print "2"

        elif fromCollisionSection == 3:
            print "3"




    def getActor(self):
        return self.actor
    def getPlayerId(self):
        return self.playerId
    def setPlayerId(self, inId):
        self.playerId = inId
    def setCharKey(self, key, value):
        self.keyMap[key] = value

    def moveChar(self, task):
        # If the camera-left key is pressed, move camera left.
        # If the camera-right key is pressed, move camera right.
        if self.isMainChar == True:
            base.camera.lookAt(self.actor)
            if (self.keyMap["cam-left"]!=0):
                base.camera.setX(base.camera, -20 * globalClock.getDt())
            if (self.keyMap["cam-right"]!=0):
                base.camera.setX(base.camera, +20 * globalClock.getDt())

        # save mainChar's initial position so that we can restore it,
        # in case he falls off the map or runs into something.

        #startpos = self.mainChar.getPos()

        # If a move-key is pressed, move ralph in the specified direction.

        if (self.keyMap["left"]!=0):
            self.actor.setH(self.actor.getH() + 300 * globalClock.getDt())
        if (self.keyMap["right"]!=0):
            self.actor.setH(self.actor.getH() - 300 * globalClock.getDt())
        if (self.keyMap["forward"] != 0 and self.type != 3):
            self.actor.setY(self.actor, -25 * globalClock.getDt())
        elif (self.keyMap["forward"] != 0 and self.type == 3):
            self.actor.setZ(self.actor, 25 * globalClock.getDt())
        elif (self.keyMap["forward"] != 0 and self.type == 2):
            self.actor.setY(self.actor, 25 * globalClock.getDt() * 100000)
        if (self.keyMap["backward"] != 0 and self.type != 3):
            #self.mainChar.setH(self.mainChar.getH() - 600)
            self.actor.setY(self.actor, 25 * globalClock.getDt())
        elif (self.keyMap["backward"] != 0 and self.type == 3):
            self.actor.setZ(self.actor, -25 * globalClock.getDt())
        elif (self.keyMap["backward"] != 0 and self.type == 2):
            self.actor.setY(self.actor, 25 * globalClock.getDt() * 100000)
        # If ralph is moving, loop the run animation.
        # If he is standing still, stop the animation.

        if (self.keyMap["forward"]!=0) or (self.keyMap["backward"]!=0) or (self.keyMap["left"]!=0) or (self.keyMap["right"]!=0):
            if self.isMoving is False and self.type != 3:
                self.actor.loop("run")
                self.isMoving = True

        else:
            if self.isMoving:
                self.actor.stop()
                self.actor.pose("walk",5)
                self.isMoving = False

        # If the camera is too far from ralph, move it closer.
        # If the camera is too close to ralph, move it farther.
        if self.isMainChar == True:
            camvec = self.actor.getPos() - base.camera.getPos()
            camvec.setZ(0)
            camdist = camvec.length()
            camvec.normalize()
            if (camdist > 10.0):
                base.camera.setPos(base.camera.getPos() + camvec*(camdist-10))
                camdist = 10.0
            if (camdist < 5.0):
                base.camera.setPos(base.camera.getPos() - camvec*(5-camdist))
                camdist = 5.0

        # The camera should look in ralph's direction,
        # but it should also try to stay horizontal, so look at
        # a floater which hovers above ralph's head.
        if self.isMainChar == True:
            self.floater.setPos(self.actor.getPos())
            self.floater.setZ(self.actor.getZ() + 2.0)
            base.camera.lookAt(self.floater)

        return task.cont

    def removeCharacter(self):
        self.actor.removeNode()

    def isColliding(self, _pos, _radius):
        xDiff = self.actor.getX() - _pos.x
        yDiff = self.actor.getY() - _pos.y
        distance = math.sqrt(math.pow(xDiff, 2) + math.pow(yDiff, 2))
        if distance < _radius+self.boundingRadius:
            return True
        return False
