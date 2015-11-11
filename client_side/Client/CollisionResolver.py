__author__ = 'dthakurta'

import math


class CollisionResolver:
    def __init__(self):
        print "Resolver Init"
        self.rearResultAngle = 0
        self.fromAngle = 0
        self.toAngle = 0
        self.fromMagnitude = 0
        self.fromMagnitude = 0
        self.fromX = 0
        self.fromY = 0
        self.toX = 0
        self.toY = 0
        self.resultX = 0
        self.resultY = 0
        self.resultAngle = 0
        self.fromResultVelocity = 0
        self.toResultVelocity = 0

    def resolve(self, fromActor, toActor, isRearEnd=False):
        self.fromAngle = math.degrees(math.atan(fromActor.y/fromActor.x))
        self.toAngle = math.degrees(math.atan(toActor.y/toActor.x))
        if isRearEnd:
            self.toAngle += 180
            self.toAngle %= 360

        self.fromMagnitude = fromActor.mass * fromActor.velocity
        self.fromMagnitude = toActor.mass * toActor.velocity

        self.fromX = self.fromMagnitude * math.cos(self.fromAngle)
        self.fromY = self.fromMagnitude * math.sin(self.fromAngle)

        self.toX = self.toMagnitude * math.cos(self.toAngle)
        self.toY = self.toMagnitude * math.sin(self.toAngle)

        self.resultX = self.fromX + self.toX
        self.resultY = self.fromY + self.toY

        self.resultAngle = math.degrees(math.atan(self.resultY/self.resultX))

        if self.resultAngle < 0:
            self.resultAngle += 360

        self.resultAngle %= 360
        print "From Car angle: ", self.resultAngle

        self.fromResultVelocity = (fromActor.mass * fromActor.velocity) / (fromActor.mass + toActor.mass)
        print "From Velocity: ", self.fromResultVelocity
        self.toResultVelocity = (toActor.mass * toActor.velocity) / (fromActor.mass + toActor.mass)
        print "To Velocity: ", self.toResultVelocity

        if isRearEnd:
            self.rearResultAngle = self.resultAngle + 180
            self.rearResultAngle %= 360
            print "To Car angle: ", self.rearResultAngle
