import stomper
import io
import time
from base64 import b64encode

class Oas:
    
    def __init__(self, ws, serverPath, localPath):
        
        self.ws = ws
        f = open(localPath,"w")
        f.close
        self.serverPath = serverPath
        self.file = io.open(localPath,"wb")
        #time.sleep(0.5)
    
    def write(self, buf):
        message = stomper.send(self.serverPath, b64encode(buf).decode("utf-8"))
        try:
            self.ws.send(message)
        except:
            # reconnect somehow
            self.ws.send(message)
            
        self.file.write(buf)
        #print("send")
        
    def flush(self):
        #self.ws.flush()
        self.file.flush()
        print("flush")
        
    def close(self):
        self.file.close()
        print("close")