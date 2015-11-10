# Roaming-Ralph was modified to remove collision part.

#import direct.directbase.DirectStart
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
from Character import Character
from Chat import Chat
from Users import Users
import time


""" Custom Imports """
# import your modules
from common.Constants import Constants
from net.ConnectionManager import ConnectionManager

SPEED = 0.5

# Function to put instructions on the screen.
def addInstructions(pos, msg):
    return OnscreenText(text=msg, style=1, fg=(1,1,1,1),
                        pos=(-1.3, pos), align=TextNode.ALeft, scale = .05)

# Function to put title on the screen.
def addTitle(text):
    return OnscreenText(text=text, style=1, fg=(1,1,1,1),
                        pos=(1.3,-0.95), align=TextNode.ARight, scale = .07)

# Function to get bouding box dimensions
def getDimensions(nPath):
    pt1, pt2 = nPath.getTightBounds()
    xDim = pt2.getX() - pt1.getX()
    yDim = pt2.getY() - pt1.getY()
    zDim = pt2.getZ() - pt1.getZ()
    return [xDim,yDim,zDim]

class MyCar():
    def __init__(self,inWorld):
        self.world = inWorld
        self.x = 1
        self.y = 0
        self.carNode = render.attachNewNode('dummy_car')
        self.world.car = loader.loadModel("knucklehead.egg")
        self.car_tex = loader.loadTexture("knucklehead.jpg")
    	self.world.car.setTexture(self.car_tex, 1)
        self.world.car.reparentTo(self.carNode)
        self.world.car.setPos(20,20,0)
        self.world.car.setScale(.08)
        self.world.car.setP(-90)
        self.world.car.setColor(0.6, 0.6, 1.0, 1.0)
        self.world.car.setColorScale(0.6, 0.6, 1.0, 1.0)
        self.actor = self.world.car

    def makeCircle(self, task):
        distance = self.getDistance()
        if distance < 5:
            self.actor.setH(-500)
            self.actor.setPos(self.world.mainChar.getX() +distance*math.cos(self.x), self.world.mainChar.getY() - distance*math.sin(self.x),0)
            self.actor.lookAt(self.world.mainChar)
            self.actor.setP(-90)
            self.actor.setH(self.actor.getH()+45)
            self.x = self.x+math.pi/80
            if(self.x > 4*math.pi):
                self.actor.setH(-500)
                tempInterval = self.actor.posInterval(5, Point3(self.actor.getX() + 10, self.world.mainChar.getY()-10, 0),
                                                            startPos=Point3(self.actor.getX(), self.actor.getY(), 0))
                # Create and play the sequence that coordinates the intervals.

                driveAway = Sequence(tempInterval, name="driveAway")
                driveAway.start()
                self.x = -1
            time.sleep(0.01)

        return task.cont

    def getDistance(self):
        distanceVector = self.world.mainChar.getPos()-self.actor.getPos()
        distance = distanceVector.length()

        return distance


#--------------------- MyCar Class END-------------------------------#

class MyPanda:
    count = 0
    def __init__(self, tempworld):
        self.isWalk = False
        self.jumpState = False
        self.currentTime = 0
        self.idleTime = 0
        self.pandaPosIntervalX = 0
        self.pandaPaceX = None
        self.pandaPace = None
        self.mySequence = None
        MyPanda.count += 1
        self.myInterval1 = 0
        self.myInterval2 = 0
        self.myInterval3 = 0
        self.myInterval4 = 0
        self.pandaPosInterval1 = 0
        self.id = MyPanda.count
        self.world = tempworld

        self.pandaNode=render.attachNewNode('pandaNode')
        self.actor=Actor("models/panda-model",
                     {"walk": "models/panda-walk4"})
        self.actor.reparentTo(self.pandaNode)
        cPos = int(self.id) * 20
        self.actor.setPos(cPos,0,0)
        self.actor.setScale(0.002, 0.002, 0.002)

        # Create a collsion node for this object.
        self.cNode = CollisionNode('panda')
        # Attach a collision sphere solid to the collision node.
        self.cNode.addSolid(CollisionSphere(2, 0, 400, 500))
        # Attach the collision node to the object's model.
        self.frowneyC = self.actor.attachNewNode(self.cNode)
        #self.frowneyC.show()
        base.cTrav.addCollider(self.frowneyC, self.world.pusher)
        self.world.pusher.addCollider(self.frowneyC, self.actor, base.drive.node())

        self.setJumpSequence()

    def setJumpSequence(self):
        self.myInterval1 = self.actor.posInterval(0.3, Point3(self.actor.getX(), self.actor.getY(), 0))
        self.myInterval2 = self.actor.posInterval(0.3, Point3(self.actor.getX(), self.actor.getY(), 1))
        self.myInterval3 = self.actor.posInterval(0.2, Point3(self.actor.getX(), self.actor.getY(), 1))
        self.myInterval4 = self.actor.posInterval(0.1, Point3(self.actor.getX(), self.actor.getY(), 0))
        self.mySequence = Sequence(self.myInterval1, self.myInterval2, self.myInterval3, self.myInterval4)

    def getActor(self):
        return self.actor

    def timerTask(self, task):
        self.currentTime = int(task.time)
        return Task.cont

    def jumpPanda (self, task):
        if(int(self.currentTime) - int(self.idleTime) > 20 and self.jumpState == False and self.getDist() > 20):
            self.setJumpSequence()
            self.jumpState = True
            print "Jump"
            self.mySequence.start()
            self.mySequence.loop()
        return Task.cont

    def getDist(self):
        distanceVector = self.world.mainChar.getPos()-self.actor.getPos()
        dist = distanceVector.length()

        return dist

    def getId(self):
        return self.id

    def walkSequence(self):
        if self.isWalk == False:
            self.actor.play("walk")
            self.actor.loop("walk")
            self.isWalk = True
        self.actor.setPos(self.actor, 0, 5, 0)
        self.pandaMovement = self.pandaNode.hprInterval(10.0,Point3(0,0,0),startHpr=Point3(self.actor.getX(),self.actor.getY(),0))

    def pandaWalk(self, task):
        if(self.getDist() < 15 and self.getDist() > 3.5):
            self.idleTime = self.currentTime
            #Look at Ralph
            self.actor.find('**/+GeomNode').setH(180)
            self.actor.lookAt(self.world.mainChar)

            if self.jumpState == True:
                print "Stop Jump"
                self.jumpState = False
                #self.idleTime = self.currentTime
                self.mySequence.pause()
                self.actor.setPos(self.actor.getX(), self.actor.getY(), 0)
                print "Start Walk"

            self.walkSequence()
        if self.getDist() < 15 and self.actor.getZ() != 0:
            self.actor.setPos(self.actor.getX(), self.actor.getY(), 0)
        return Task.cont

    def pandaStop(self, task):
        if (self.getDist() > 15 or self.getDist() < 3.5) and self.isWalk:
            print "Stop Walk"
            self.isWalk = False
            self.actor.stop()
        return Task.cont

class MyRalph:
    count = 0
    def __init__(self, tempworld):
        self.world = tempworld

        self.actor = Actor("models/ralph",
                         {"run":"models/ralph-run",
                          "walk":"models/ralph-walk"})
        self.actor.reparentTo(render)
        self.actor.setScale(.2)
        self.actor.setPos(30, 30, 0)

        # Create a collsion node for this object.
        self.cNode = CollisionNode('ralph')
        # Attach a collision sphere solid to the collision node.
        self.cNode.addSolid(CollisionSphere(0, 0, 3, 3))
        # Attach the collision node to the object's model.
        self.smileyC = self.actor.attachNewNode(self.cNode)
        #self.frowneyC.show()
        base.cTrav.addCollider(self.smileyC, self.world.pusher)
        self.world.pusher.addCollider(self.smileyC, self.actor, base.drive.node())

    def getActor(self):
        return self.actor

#--------------------- MyPanda Class END-------------------------------#

class StationarySphere():
    count = 0
    def __init__(self, tempworld):
        StationarySphere.count += 1
        self.world = tempworld
        self.sizescale = 0.6                #Planet size scale
        if StationarySphere.count == 1:
            #Load the Sun
            self.world.sun = loader.loadModel("models/planet_sphere")
            self.sun_tex = loader.loadTexture("models/sun_1k_tex.jpg")
            self.world.sun.setTexture(self.sun_tex, 1)
            self.world.sun.reparentTo(render)
            self.world.sun.setScale(2 * self.sizescale)
            xDim,yDim,zDim = getDimensions(self.world.sun)
            self.world.sun.setPos( -25, -25, zDim/2.0)
            self.actor = self.world.sun
            self.boundingRadius = zDim
        elif StationarySphere.count == 2:
            #Load Venus
            self.world.venus = loader.loadModel("models/planet_sphere")
            self.venus_tex = loader.loadTexture("models/venus_1k_tex.jpg")
            self.world.venus.setTexture(self.venus_tex, 1)
            self.world.venus.reparentTo(render)
            self.world.venus.setScale(0.923 * self.sizescale)
            xDim,yDim,zDim = getDimensions(self.world.venus)
            self.world.venus.setPos( -20, 20, zDim/2.0)
            self.actor = self.world.venus
            self.boundingRadius = zDim
        elif StationarySphere.count == 3:
            #Load Earth
            self.world.earth = loader.loadModel("models/planet_sphere")
            self.earth_tex = loader.loadTexture("models/earth_1k_tex.jpg")
            self.world.earth.setTexture(self.earth_tex, 1)
            self.world.earth.reparentTo(render)
            self.world.earth.setScale(self.sizescale)
            xDim,yDim,zDim = getDimensions(self.world.earth)
            self.world.earth.setPos( 20, 20, zDim/2.0)
            self.actor = self.world.earth
            self.boundingRadius = zDim

    def getDistance(self, characterObject):
        distanceVector = characterObject.actor.getPos()-self.actor.getPos()
        distance = distanceVector.length()
        return distance

    def rotateForAll(self, task):
        shoudldRotate = False
        for characterObject in self.world.characters:
            distance = self.getDistance(characterObject)
            if distance < characterObject.boundingRadius + self.boundingRadius + 2:
                shoudldRotate = True
                break
        if shoudldRotate:
            self.actor.setH(self.actor.getH()+1)
            time.sleep(0.01)
        return task.cont

#--------------------- StationarySphere Class END-------------------------------#

class World(DirectObject):

    currentTime = 0
    idleTime = 0
    mySequence = None
    pandaPace = None
    jumpState = False
    isWalk = False
    previousPos = None #used to store the mainChar pos from one frame to another
    host = ""
    port = 0
    characters = []  #Stores the list of all the others players characters

    def __init__(self):
        # Network Setup
        print "Object Created"
        self.characters = []
    def setConnectionManager(self, connectionManager):
        self.cManager = connectionManager

    def initWorld(self, playerId, chosenCharId, x, y, h):

        self.keyMap = {"hello":0, "left":0, "right":0, "forward":0, "backward":0, "cam-left":0, "cam-right":0, "chat":0, "fast":0, "chat0": 0, "chat1": 0, "users": 0}
        base.win.setClearColor(Vec4(0,0,0,1))
		# chat box
        self.chatbox = Chat(self.cManager, self)
        self.activeUsers = Users(self.cManager, self)

        taskMgr.add(self.message, 'message')


        # Post the instructions

        self.title = addTitle("Panda3D Tutorial: Roaming Ralph (Walking on the Moon)")
        self.inst1 = addInstructions(0.95, "[ESC]: Quit")
        self.inst2 = addInstructions(0.90, "[A]: Rotate Ralph Left")
        self.inst3 = addInstructions(0.85, "[D]: Rotate Ralph Right")
        self.inst4 = addInstructions(0.80, "[W]: Run Ralph Forward")
        self.inst4 = addInstructions(0.75, "[S]: Run Ralph Backward")
        self.inst6 = addInstructions(0.70, "[Left Arrow]: Rotate Camera Left")
        self.inst7 = addInstructions(0.65, "[Right Arrow]: Rotate Camera Right")
        self.inst8 = addInstructions(0.60, "[0]: Toggle Chat Broadcast")
        self.inst9 = addInstructions(0.55, "[1]: Toggle Private Chat - username/")
        self.inst10 = addInstructions(0.50, "[L]: List Connected Users")


        # Set up the environment
        #
        self.environ = loader.loadModel("models/square")
        self.environ.reparentTo(render)
        self.environ.setPos(0,0,0)
        self.environ.setScale(100,100,1)
        self.moon_tex = loader.loadTexture("models/moon_1k_tex.jpg")
      	self.environ.setTexture(self.moon_tex, 1)

        #Collision Code
        # Initialize the collision traverser.
        base.cTrav = CollisionTraverser()
        # Initialize the Pusher collision handler.
        self.pusher = CollisionHandlerPusher()

        self.bTrav = base.cTrav
        # Create the main character, Ralph
        self.mainCharRef = Character(self, chosenCharId, True)
        self.mainCharRef.setPlayerId(playerId)
        self.characters.append(self.mainCharRef)
        self.mainChar = self.mainCharRef.getActor()


        #Create second bike
        self.mainCharRef2 = Character(self, 3, True)
        self.mainCharRef2.setPlayerId(2)
        self.characters.append(self.mainCharRef2)
        self.mainChar2 = self.mainCharRef2.getActor()

        resetPos = True
        if (x == 0 and y == 0):
            resetPos = False
        if resetPos:
            self.mainChar.setPos(x, y, 0)
        self.mainChar.setH(h)

        self.cManager.sendRequest(Constants.CMSG_CREATE_CHARACTER, [playerId,
                                                                    chosenCharId,
                                                                    self.mainChar.getX(),
                                                                    self.mainChar.getY(),
                                                                    self.mainChar.getZ()])


        self.previousPos = self.mainChar.getPos()
        taskMgr.doMethodLater(.1, self.updateMove, 'updateMove')

        # Creating Stationary spheres
        self.spheres = []
        self.sphereCount = 3
        for x in range(self.sphereCount):
            self.spheres.append(StationarySphere(self))
        for sphere in self.spheres:
            taskMgr.add(sphere.rotateForAll, "rotateSphere")

        self.floater = NodePath(PandaNode("floater"))
        self.floater.reparentTo(render)


        floorNode = render.attachNewNode("Floor NodePath")
        # Create a collision plane solid.
        collPlane = CollisionPlane(Plane(Vec3(0, 0, 1), Point3(0, 0, 0)))
        # Call our function that creates a nodepath with a collision node.
        floorCollisionNP = self.makeCollisionNodePath(floorNode, collPlane)

        # Accept the control keys for movement and rotation
        self.accept("escape", self.quitGame)
        self.accept("a", self.setKey, ["left",1])
        self.accept("d", self.setKey, ["right",1])
        self.accept("w", self.setKey, ["forward",1])
        self.accept("s", self.setKey, ["backward",1])
        self.accept("lshift", self.setKey, ["fast", 1])
        self.accept("arrow_left", self.setKey, ["cam-left",1])
        self.accept("arrow_right", self.setKey, ["cam-right",1])
        self.accept("a-up", self.setKey, ["left",0])
        self.accept("d-up", self.setKey, ["right",0])
        self.accept("w-up", self.setKey, ["forward",0])
        self.accept("s-up", self.setKey, ["backward",0])
        self.accept("lshift-up", self.setKey, ["fast", 0])
        self.accept("arrow_left-up", self.setKey, ["cam-left",0])
        self.accept("arrow_right-up", self.setKey, ["cam-right",0])
        self.accept("h", self.setKey, ["hello",1])
        self.accept("h-up", self.setKey, ["hello",0])
        self.accept("0", self.setKey, ["chat0", 1])
        self.accept("0-up", self.setKey, ["chat0", 0])
        self.accept("1", self.setKey, ["chat1", 1])
        self.accept("1-up", self.setKey, ["chat1", 0])
        self.accept("l", self.setKey, ["users", 1])
        self.accept("l-up", self.setKey, ["users", 0])

        taskMgr.add(self.move,"moveTask")

        # Game state variables
        self.isMoving = False

        # Set up the camera

        base.disableMouse()
        base.camera.setPos(self.mainChar.getX(),self.mainChar.getY()+10,2)

        # Create some lighting
        ambientLight = AmbientLight("ambientLight")
        ambientLight.setColor(Vec4(.3, .3, .3, 1))
        directionalLight = DirectionalLight("directionalLight")
        directionalLight.setDirection(Vec3(-5, -5, -5))
        directionalLight.setColor(Vec4(1, 1, 1, 1))
        directionalLight.setSpecularColor(Vec4(1, 1, 1, 1))
        render.setLight(render.attachNewNode(ambientLight))
        render.setLight(render.attachNewNode(directionalLight))

    def makeCollisionNodePath(self, nodepath, solid):
        '''
        Creates a collision node and attaches the collision solid to the
        supplied NodePath. Returns the nodepath of the collision node.
        '''
        # Creates a collision node named after the name of the NodePath.
        collNode = CollisionNode("%s c_node" % nodepath.getName())
        collNode.addSolid(solid)
        collisionNodepath = nodepath.attachNewNode(collNode)

        return collisionNodepath
    #Records the state of the arrow keys
    def setKey(self, key, value):
        self.keyMap[key] = value
        #print "In setKey", key, value
        self.cManager.sendRequest(Constants.CMSG_KEY, [key, value])

    # Accepts arrow keys to move either the player or the menu cursor,
    # Also deals with grid checking and collision detection
    def getDist(self):
        mainCharX = self.mainChar.getPos().x
        mainCharY = self.mainChar.getPos().y
        pandaX = self.pandaActor2.getPos().x
        pandaY = self.pandaActor2.getPos().y
        dist = math.sqrt(abs(mainCharX-pandaX)**2 + abs(mainCharY-pandaY)**2)
        return dist

    def move(self, task):
        # If the camera-left key is pressed, move camera left.
        # If the camera-right key is pressed, move camera right.

        base.camera.lookAt(self.mainChar)
        if (self.keyMap["cam-left"]!=0):
            base.camera.setX(base.camera, -20 * globalClock.getDt())
        if (self.keyMap["cam-right"]!=0):
            base.camera.setX(base.camera, +20 * globalClock.getDt())

        # save mainChar's initial position so that we can restore it,
        # in case he falls off the map or runs into something.

        startpos = self.mainChar.getPos()
        starthpr = self.mainChar.getHpr()

        # If left-shift is pressed, speed-up ralph
        ralphSpeed = 1
        if(self.keyMap["fast"]==1):
            ralphSpeed = 3
        # If a move-key is pressed, move ralph in the specified direction.
        if (self.keyMap["left"]!=0):
            self.mainChar.setH(self.mainChar.getH() + 300 * globalClock.getDt() * ralphSpeed)
        if (self.keyMap["right"]!=0):
            self.mainChar.setH(self.mainChar.getH() - 300 * globalClock.getDt() * ralphSpeed)
        if (self.keyMap["forward"] != 0 and self.mainCharRef.type == 2):
            self.mainChar.setY(self.mainChar, -500 * globalClock.getDt() * ralphSpeed)
        elif (self.keyMap["forward"] != 0 and self.mainCharRef.type == 3):
            self.mainChar.setZ(self.mainChar, 25 * globalClock.getDt() * ralphSpeed)
        elif (self.keyMap["forward"] != 0):
            self.mainChar.setY(self.mainChar, -25 * globalClock.getDt() * ralphSpeed)
        if (self.keyMap["backward"] != 0 and self.mainCharRef.type == 2):
            self.mainChar.setY(self.mainChar, 500 * globalClock.getDt() * ralphSpeed)
        elif (self.keyMap["backward"] != 0 and self.mainCharRef.type == 3):
            self.mainChar.setZ(self.mainChar, -25 * globalClock.getDt() * ralphSpeed)
        elif (self.keyMap["backward"] != 0):
            self.mainChar.setY(self.mainChar, 25 * globalClock.getDt() * ralphSpeed)

        # If ralph is moving, loop the run animation.
        # If he is standing still, stop the animation.

        if (self.keyMap["forward"] != 0) or (self.keyMap["backward"] != 0) or (self.keyMap["left"] != 0) or (
                    self.keyMap["right"] != 0):
            if self.isMoving is False :
                self.isMoving = True
                if self.mainCharRef.type != 3:
                    self.mainChar.loop("run")

        else:
            if self.isMoving:
                self.isMoving = False
                if self.mainCharRef.type != 3:
                    self.mainChar.stop()
                    self.mainChar.pose("walk",5)

        # Detecting collisions only when ralph is moving
        if self.isMoving:
            # Collision With Spheres
            isCollidingWithSphere = False
            for sphere in self.spheres:
                if self.mainCharRef.isColliding(sphere.actor.getPos(), sphere.boundingRadius):
                    isCollidingWithSphere = True

            if isCollidingWithSphere:
                self.mainChar.setPos(startpos)
                self.mainChar.setHpr(starthpr)
            # Collision With Other Ralphs
            isCollidingWithOtherRalphs = False
            for chacracter in self.characters:
                if not chacracter.isMainChar and self.mainCharRef.isColliding(chacracter.actor.getPos(), chacracter.boundingRadius):
                    isCollidingWithOtherRalphs = True
            if isCollidingWithOtherRalphs:
                self.mainChar.setH(self.mainChar.getH() - 180)

        # If the camera is too far from ralph, move it closer.
        # If the camera is too close to ralph, move it farther.

        camvec = self.mainChar.getPos() - base.camera.getPos()
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

        self.floater.setPos(self.mainChar.getPos())
        self.floater.setZ(self.mainChar.getZ() + 2.0)
        base.camera.lookAt(self.floater)

        return task.cont

    def startConnection(self):
        """Create a connection to the remote host.

        If a connection cannot be created, it will ask the user to perform
        additional retries.

        """
        if self.cManager.connection == None:
            if not self.cManager.startConnection():
                return False

        return True

    def message(self, task):
        # hide all chatboxes
        if self.keyMap["chat0"] != 0:
            if self.chatbox.getVisible() is False:
                self.chatbox.setVisible(True)
                self.chatbox.show(0)
            else:
                self.chatbox.setVisible(False)
                self.chatbox.hide()
            self.keyMap["chat0"] = 0
        if self.keyMap["chat1"] != 0:
            if self.chatbox.getVisible() is False:
                self.chatbox.setVisible(True)
                self.chatbox.show(1)
            else:
                self.chatbox.setVisible(False)
                self.chatbox.hide()
            self.keyMap["chat1"] = 0
        if self.keyMap["users"] != 0:
            self.activeUsers.toggle()
            self.keyMap["users"] = 0
        return task.cont

    def updateMove(self, task):
      if self.isMoving == True :
#         moving = self.mainChar.getPos() - self.previousPos
        moving = self.mainChar.getPos();
        self.cManager.sendRequest(Constants.CMSG_MOVE, [moving.getX() ,moving.getY(),moving.getZ(), self.mainChar.getH()])
        #self.cManager.sendRequest(Constants.RAND_FLOAT, 1.0)
        self.previousPos = self.mainChar.getPos()
      return task.again

    def quitGame(self):
      self.cManager.closeConnection()

      sys.exit()

class WorldManager:
    def __init__(self):
        self.w = World()
        #run()

    def runWorld(self, playerId, chosenCharId, x=0, y=0, h=0):
        print "runWorld",chosenCharId
        self.w.initWorld(playerId, chosenCharId, x, y, h)
