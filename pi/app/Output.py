import stomper
import asyncio
import io
import time
from base64 import b64encode

class Out:
    
    def __init__(self, ws, serverPath):
        self.ws = ws
        self.writePath = serverPath
        #time.sleep(0.5)
        
    def write(self, buf):
        message = stomper.send(self.serverPath, b64encode(buf).decode("utf-8"))
        try:
            self.ws.send(message)
        except:
            #self.connect()
            self.ws.send(message)
        
    def flush(self):
        #self.ws.flush()
        self.file.flush()
        print("flush")
        
    def close(self):
        self.file.close()
        print("close")