# define Constants

class Constants:
    RAND_INT                            = 1
    RAND_STRING                         = 2
    RAND_SHORT                          = 3
    RAND_FLOAT                          = 4

    SERVER_IP = 'localhost'
    SERVER_PORT = 9252
    DEBUG = True
    MSG_NONE                            = 0
    CMSG_AUTH                           = 101
    CMSG_DISCONNECT                     = 102
    CMSG_REGISTER                       = 103
    CMSG_CREATE_CHARACTER               = 104
    CMSG_CHAT_ALL                       = 105
    CMSG_CHAT_ONE                       = 106
    CMSG_MOVE                           = 107
    CMSG_KEY                            = 108
    CMSG_USERS 							= 110
    REQ_HEARTBEAT                       = 301

    SMSG_AUTH                           = 201
    SMSG_DISCONNECT                     = 202
    SMSG_REGISTER                       = 203
    SMSG_CREATE_CHARACTER               = 204
    SMSG_CHAT_ALL                       = 205
    SMSG_CHAT_ONE                       = 206
    SMSG_MOVE                           = 207
    SMSG_KEY                            = 208
    SMSG_USERS 							= 210
    SMSG_RENDER_CHARACTER               = 310
    SMSG_REMOVE_CHARACTER               = 311